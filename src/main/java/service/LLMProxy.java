package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.github.cdimascio.dotenv.Dotenv;



public class LLMProxy {

    private final String apiKey;
    private final String baseUrl;
    private final String modelName;

    private final HttpClient httpClient;
    private final Gson gson;

    public LLMProxy() {


        // Dotenv loads secret values from the .env file
        Dotenv dotenv = Dotenv.load();

        // uses my private method to check if
        // the modelName, baseUrl and apiKey is empty??
        this.apiKey = getRequiredEnvString(dotenv, "SAIA_API_KEY");
        this.baseUrl = getRequiredEnvString(dotenv, "SAIA_BASE_URL");
        this.modelName = getRequiredEnvString(dotenv, "SAIA_MODEL");

        // Creates and assigns a HttpClient that
        // sends requests to the API over the internet.
        this.httpClient = HttpClient.newHttpClient();

        // Gson converts Java JsonObjects into JSON text
        // and JSON text back into Java objects
        this.gson = new Gson();

    }

    private String getRequiredEnvString(Dotenv dotenv, String key) {

        // what happens when modelName, baseUrl and apiKey is empty??
        // test the cases+
        String value = dotenv.get(key);


        // if something only has whitespaces or is empty isBlank() will be true
        // null points to no object or reference
        if (value == null || value.isBlank()) {
            // state and not and argument parsed into the method like chatEntryToTranslate
            throw new IllegalStateException("No " + key + " in .env");

        }
        return value;

    }

    private JsonObject buildRequestBody(String chatEntryToTranslate) {

        // 1. build the Json Body
        // JSON body is the data we send to the LLM API.
        JsonObject body = new JsonObject();
        body.addProperty("model", modelName);

        // GWDG/KISS KI API expects a list of messages
        JsonArray messages = new JsonArray();

        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content",
                "You are a translation assistant. Translate the user's text into the desired language. Also provide a short summary. Do not explain your reasoning.");

        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", chatEntryToTranslate);

        messages.add(systemMessage);
        messages.add(userMessage);

        // strict translation and no creativity
        body.addProperty("temperature", 0);

        // limits the answer length of the model
        body.addProperty("max_tokens", 512);

        // adds the userMessage and systemMessage we made in the JsonArray messages
        body.add("messages", messages);

        return body;

    }

    private HttpRequest buildHttpRequest(JsonObject body) {

        // 2. build the HTTP request here.
        // HTTP request is the message from our app to the SAIA/GWDG server.
        // POST is used because we are sending data to the server.


        // Authenticates with the API Key that
        // asks to send the response back as JSON.
        // proves that we are allowed to use the API.
        // Content-Type tells the server that the body is JSON
        // gson.toJson(body) method converts the Java JSON object into JSON text.

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/chat/completions")).header("Accept", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(body)))

                .build();

        return request;
    }

    private HttpResponse<String> sendHttpRequest(HttpRequest request) throws Exception {

        // sends the HTTP request and stores the server's response as a String.
        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // HTTP status codes from 200 to 299 are successful
        // If the API returns a different status code
        // that means the response probably does not contain
        // the normal choices field that we need.
        // Therefore, we stop here and show the error response instead
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new RuntimeException("API request failed with status code: "
                    + response.statusCode() + "\nResponse body: " + response.body());
        }

        return response;
    }

    private String extractLlmAnswer(HttpResponse<String> response) {

        // 4. get the answer back
        // Converts the JSON response text back into a Java JsonObject for us to use
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        // assigns the LLMs answers into a String
        String modelAnswer = jsonResponse.getAsJsonArray("choices").get(0).getAsJsonObject()
                .getAsJsonObject("message").get("content").getAsString();

        // the trim method from the String class removes the blank lines, spaces and tabs
        return modelAnswer.trim();

    }

    public String sendRequest(String chatEntryToTranslate) {

        if (chatEntryToTranslate == null || chatEntryToTranslate.isBlank()) {
            throw new IllegalArgumentException("No chat entry to translate was found");

        }

        try {

            JsonObject body = buildRequestBody(chatEntryToTranslate);
            HttpRequest request = buildHttpRequest(body);
            HttpResponse<String> response = sendHttpRequest(request);


            return extractLlmAnswer(response);



        }

        catch (Exception e) {
            throw new RuntimeException("We could not connect to the GWDG/KISS LLM", e);
        }



    }

}



/*
 * Responsibility: This class will be the center piece of our app. It will handle the communication
 * with the LLM model. It will receive the raw and unfiltered response from the model as a chat
 * entry and build the API request to the LLM. All the API specific code will be here. there is no
 * back and forth communication. only reqeust and one answer.
 *
 * Interactions:
 * 
 * - service.TranslationService.java: receives calls from the TranslationService class and returns
 * the LLM results back to it
 * 
 * - The external LLM: in this case the Qwen model
 * 
 * - .env: reads the API key, base URL and model name
 */
