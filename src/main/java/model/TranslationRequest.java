/*
 * Responsibility: This class will store the user input data needed for the translation: - the
 * original text - the selected target language
 *
 * Interactions:
 * 
 * - ui.TranslationPanel.java: created from the user's text area and the target language selection
 * 
 * - service.TranslationService.java: input data is passed to the service to start the translation
 * process
 */
package model;

public class TranslationRequest {

    private final String TARGET_LANGUAGE;
    private final String ORIGINAL_TEXT;

    public TranslationRequest(ChatEntry chatEntry) {
        this.TARGET_LANGUAGE = chatEntry.getTargetLanguage();
        this.ORIGINAL_TEXT = chatEntry.getOriginalText();
    }

    public String getChatEntryText() {
        String chatEntryText =
                "Translate the following text to " + TARGET_LANGUAGE + ": " + ORIGINAL_TEXT;

        return chatEntryText;
    }


}
