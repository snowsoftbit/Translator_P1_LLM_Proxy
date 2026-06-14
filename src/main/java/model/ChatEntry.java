package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatEntry {

    private int id;
    private String title;
    private String targetLanguage;
    private String originalText;
    private String translatedText;
    private String timestamp;

    public ChatEntry(String title, String targetLanguage, String originalText, String translatedText) {
        setId();
        setCurrentTimestamp();

        this.title = title;
        this.targetLanguage = targetLanguage;
        this.originalText = originalText;
        this.translatedText = translatedText;
    }

    private void setCurrentTimestamp() {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

    private void setId() {
        this.id = 123;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
}

/*
 * Responsibility: Stores the data of one completed request with: - the original text - the target
 * language the text should be translated into - the date and time of the chat - the title of the
 * chat - the translated text
 *
 * So it has these properties: - unique id - title - source language - target language - original
 * text - translated text - time and date stamp
 *
 * Interactions:
 * 
 * - persistence.FileChatHistoryDAO.java: saved to and loaded from the history file
 * 
 * - persistence.ChatHistoryDAO.java: uses the interface methods
 * 
 * - ui.HistoryPanel.java: displayed in the history list
 * 
 * - ui.TranslationPanel.java: can be used to load up a previous translation into the GUI
 */
