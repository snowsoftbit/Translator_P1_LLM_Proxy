package model;

public class ChatEntry {


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
