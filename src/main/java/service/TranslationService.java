package service;

import model.TranslationRequest;
import model.TranslationResponse;

public class TranslationService {



}

/*
 * Responsibility: This class will house the main translation logic for our app. It will call the
 * LLMProxy class with the TranslationRequest and then return a TranslationResponse. It sits between
 * the GUI and the app logic. It should not interact with anything from the ui package.
 *
 * Interactions: - model.TranslationRequest.java: receives and reads the request data that the API
 * call in the LLMProxy must make - model.TranslationResponse.java: creates and returns the response
 * data - service.LLMProxy.java: calls the LLM class and receives the LLM result from it
 */
