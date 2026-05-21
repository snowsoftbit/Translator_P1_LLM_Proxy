package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



public class LLMProxy {

}

/*
 * Responsibility: This class will be the center piece of our app. It will handle the communication
 * with the LLM model. It will receive the raw and unfiltered response from the model and build the
 * API request. All the API specific code will be here.
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
