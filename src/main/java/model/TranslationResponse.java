/*
 * Responsibility: Stores the translated text and response by the LLM. This class can also store
 * things like the explanation or key terms that the LLM provided.
 *
 * Interactions:
 * 
 * - service.TranslationService.java: creates or returns the response for this class
 * 
 * - ui.TranslationPanel.java: this class will first receive and then display the translation
 * response into the UI for the user
 */
package model;

public class TranslationResponse {

    private String translatedText;

    public TranslationResponse(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
}
