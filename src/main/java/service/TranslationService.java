package service;

import model.TranslationRequest;
import model.TranslationResponse;

public class TranslationService {

    // needs to be declared to store a LLMProxy Object
    private final LLMProxy llmProxy;

    public TranslationService() {

        // creates a LLMProcy object and assings it
        this.llmProxy = new LLMProxy();
    }

    public TranslationResponse translateText(TranslationRequest request) {

        // TranslationRequest must give me a getChatEntryText() method
        String UserInputText = request.getChatEntryText();


        // uses the LLMProxy classes sendRequest method to send a Request to the LLM
        // saves it to a string
        String llmAnswer = llmProxy.sendRequest(UserInputText);

        // puts the LLM's answer into a response Object for TranslationReponse to handle
        TranslationResponse response = new TranslationResponse(llmAnswer);

        // returns the reponse back to TranslationReponse
        return response;

    }



}

/*
 * Responsibility: This class will house the main translation logic for our app. It will call the
 * LLMProxy class with the TranslationRequest and then return a TranslationResponse. It sits between
 * the GUI and the app logic. It should not interact with anything from the ui package.
 *
 * Interactions:
 * 
 * - model.TranslationRequest.java: receives and reads the request data that the API call in the
 * LLMProxy must make
 * 
 * - model.TranslationResponse.java: creates and returns the response data
 * 
 * - service.LLMProxy.java: calls the LLM class and receives the LLM result from it
 */
