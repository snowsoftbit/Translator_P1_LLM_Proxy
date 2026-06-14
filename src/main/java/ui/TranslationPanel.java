package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;


public class TranslationPanel extends JPanel{

	public TranslationPanel() {
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(750, 600));
		
	}

	// 
}

/*
 * Responsibility: It must have: - input text area - language target selection dropdown menu -
 * translate or enter button - reacts to the user's button clicks - creates a ChatEntry object -
 * creates a TranslationRequest - receives a TranslationResponse - displays the translated text as a
 * chat bubble - creates and saves a ChatEntry object after a successful translation - uses
 * ChatHistoryDAO - updates the HistoryPanel after saving a new entry
 *
 * Interactions:
 * 
 * - model.TranslationRequest.java: creates request objects from GUI input
 * 
 * - model.TranslationResponse.java: receives and reads the response objects
 * 
 * - model.ChatEntry.java: creates history entries after successful translations
 * 
 * - persistence.ChatHistoryDAO.java: calls DAO methods to save new entries to the local history
 * 
 * - service.TranslationService.java: sends the translation request to the service and receives the
 * translation response
 */
