package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.ChatEntry;
import model.TranslationRequest;
import model.TranslationResponse;
import persistence.FileChatHistoryDAO;
import persistence.ChatHistoryDAO;
import service.TranslationService;


public class TranslationPanel extends JPanel{

	private final JSplitPane verticalSplitPane;
	private final ChatHistoryDAO chatHistoryDAO = new FileChatHistoryDAO("history.json");
	private final HistoryPanel historyPanel;

	private final JTextArea inputArea = new JTextArea();
	private final JTextArea outputArea = new JTextArea();
	private final JButton translateButton = new JButton("Übersetzen");
	private final JButton newChatButton = new JButton("Neuer Chat");
	private final JComboBox<String> targetLanguageComboBox = new JComboBox<>();
	private final JLabel characterCountLabel = new JLabel("0 von 2000 Zeichen");

	public TranslationPanel(HistoryPanel historyPanel) {

		//History panel is needed for refreshing chat history after a new chat entry
		this.historyPanel = historyPanel;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(750, 600));

		Font biggerText = new Font("Arial", Font.PLAIN, 16);
		Font boldText = new Font("Arial", Font.BOLD, 16);

		inputArea.setFont(biggerText);
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

		JPanel controlPanel = new JPanel();	

		targetLanguageComboBox.setPreferredSize(new Dimension(150,40));
		targetLanguageComboBox.setFont(boldText);
		translateButton.setPreferredSize(new Dimension(150, 40));
		translateButton.setBackground(new Color(220, 240, 240));
		newChatButton.setPreferredSize(new Dimension(150, 40));
		translateButton.setFont(boldText);
		newChatButton.setFont(boldText);
		
		addLanguagesToComboBox();
		controlPanel.add(targetLanguageComboBox);
		controlPanel.add(translateButton);
		translateButton.setEnabled(false);
		controlPanel.add(newChatButton);
		newChatButton.setVisible(false);
		controlPanel.add(characterCountLabel);

		topPanel.add(controlPanel, BorderLayout.SOUTH);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		outputArea.setFont(biggerText);
		outputArea.setLineWrap(true);
		outputArea.setWrapStyleWord(true);
		outputArea.setEditable(false);
		
		bottomPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel);
		verticalSplitPane.setResizeWeight(0.5);

		add(verticalSplitPane, BorderLayout.CENTER);

		newChatButton.addActionListener(new NewChatButtonListener());
		translateButton.addActionListener(new TranslateButtonListener());
		inputArea.getDocument().addDocumentListener(new CharCounterListener());
	}

	// Loading selected chat-entry in input and output field
	public void loadHistoryChat(String inputText, String translatedText) {
		setInputText(inputText);
		setOutputText(translatedText);

		inputArea.setEditable(false);
		translateButton.setEnabled(false);
		newChatButton.setVisible(true);
	}

	public String getInputText() {
		return inputArea.getText();
	}

	public void setInputText(String text) {
		inputArea.setText(text);
	}

	public String getTargetLanguage() {
		return (String) targetLanguageComboBox.getSelectedItem();
	}

	public void setOutputText(String text) {
		outputArea.setText(text);
	}

	private void addLanguagesToComboBox() {
		String[] languages = { "Englisch", "Chinesisch", "Hindi", "Spanisch", "Französisch", "Arabisch",
				"Bengalisch", "Portugiesisch", "Russisch", "Urdu", "Indonesisch", "Deutsch", "Japanisch", "Pidgin",
				"Marathi", "Telugu", "Türkisch", "Tamil", "Kantonesisch", "Vietnamesisch" };

		for (String language : languages) {
			targetLanguageComboBox.addItem(language);
		}
	}

	// "Übersetzen" Button Listener: creates chat entry, sends request and gets
	// response -> showing result, saving chat entry and refreshing History Panel
	private class TranslateButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String inputText = getInputText();
			String targetLanguage = getTargetLanguage();

			ChatEntry chatEntry = new ChatEntry("TestTitle", targetLanguage, inputText, "");
			TranslationRequest request = new TranslationRequest(chatEntry);

			TranslationService translationService = new TranslationService();
			TranslationResponse response = translationService.getTranslatedText(request);
			String translatedText = response.getTranslatedText();
			setOutputText(translatedText);
			chatEntry.setTranslatedText(translatedText);

			try {
				chatHistoryDAO.saveEntry(chatEntry);
				historyPanel.refreshChatHistory();
			} catch (Exception ex) {
				System.out.println("Fehler beim Speichern des Chat-Eintrags");
			}
		}
	}

	// "Neuer Chat" Button Listener: Clear input and output area and start a new translation 
	private class NewChatButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setInputText("");
			setOutputText("");

			inputArea.setEditable(true);
			translateButton.setEnabled(false);
			newChatButton.setVisible(false);
			characterCountLabel.setText("0 von 2000 Zeichen");

			if (historyPanel != null) {
				historyPanel.clearSelection();
			}
		}
	}

	// Character Counter Listener: Counts character, if more than 2000, button is disabled
	private class CharCounterListener implements DocumentListener {
		private void updateCount() {
			int length = getInputText().length();
			characterCountLabel.setText(length + " von 2000 Zeichen");

			if (length > 0 && length <= 2000) {
				translateButton.setEnabled(true);
			} else {
				translateButton.setEnabled(false);
			}
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			updateCount();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			updateCount();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
		}
	}
	 
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
