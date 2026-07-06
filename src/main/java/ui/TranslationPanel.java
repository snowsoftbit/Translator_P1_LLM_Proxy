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

package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.ChatEntry;
import model.TranslationRequest;
import model.TranslationResponse;
import persistence.ChatHistoryDAO;
import persistence.FileChatHistoryDAO;
import service.TranslationService;

public class TranslationPanel extends JPanel {

	private final JSplitPane verticalSplitPane;
	private final ChatHistoryDAO chatHistoryDAO = new FileChatHistoryDAO("data/history.json");
	private final HistoryPanel historyPanel;
	private final TranslationService translationService = new TranslationService();
	private final JTextArea inputArea = new JTextArea();
	private final JTextArea outputArea = new JTextArea();
	private final JButton translateButton = new JButton("Übersetzen");
	private final JButton newChatButton = new JButton("Neuer Chat");
	private final JLabel selectTargetLanguageLabel = new JLabel("Zielsprache auswählen: ");
	private final JComboBox<String> targetLanguageComboBox = new JComboBox<>();
	private final JLabel characterCountLabel = new JLabel("0 von 2000 Zeichen");

	public TranslationPanel(HistoryPanel historyPanel) {

		// History panel is needed for refreshing chat history after a new chat entry
		this.historyPanel = historyPanel;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(750, 600));

		Font biggerText = new Font("Arial", Font.PLAIN, 16);
		Font boldText = new Font("Arial", Font.BOLD, 16);

		inputArea.setFont(biggerText);
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);

		JPanel topPanel = new JPanel(new BorderLayout());

		JLabel sourceLabel = new JLabel("Quelltext");
		sourceLabel.setFont(boldText);
		topPanel.add(sourceLabel, BorderLayout.NORTH);
		JScrollPane inputScrollPane = new JScrollPane(inputArea);
		inputScrollPane.setBorder(null);
		topPanel.add(inputScrollPane, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new BorderLayout());

		JLabel translationLabel = new JLabel("Übersetzung");
		translationLabel.setFont(boldText);
		bottomPanel.add(translationLabel, BorderLayout.NORTH);
		outputArea.setFont(biggerText);
		outputArea.setLineWrap(true);
		outputArea.setWrapStyleWord(true);
		outputArea.setEditable(false);

		JScrollPane outputScrollPane = new JScrollPane(outputArea);
		outputScrollPane.setBorder(null);
		bottomPanel.add(outputScrollPane, BorderLayout.CENTER);

		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel);
		verticalSplitPane.setResizeWeight(0.5);
		verticalSplitPane.setDividerLocation(300);

		JPanel controlPanel = new JPanel();

		targetLanguageComboBox.setPreferredSize(new Dimension(150, 40));
		targetLanguageComboBox.setFont(boldText);
		selectTargetLanguageLabel.setFont(boldText);
		translateButton.setPreferredSize(new Dimension(150, 40));
		newChatButton.setPreferredSize(new Dimension(150, 40));
		translateButton.setFont(boldText);
		newChatButton.setFont(boldText);

		addLanguagesToComboBox();
		controlPanel.add(selectTargetLanguageLabel);
		controlPanel.add(targetLanguageComboBox);
		controlPanel.add(translateButton);
		translateButton.setEnabled(false);
		controlPanel.add(newChatButton);
		newChatButton.setVisible(false);
		controlPanel.add(characterCountLabel);

		add(verticalSplitPane, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);

		newChatButton.addActionListener(new NewChatButtonListener());
		translateButton.addActionListener(new TranslateButtonListener());
		inputArea.getDocument().addDocumentListener(new CharCounterListener());
	}

	// Loading selected chat-entry in input and output textfield
	public void loadHistoryChat(String inputText, String translatedText) {
		inputArea.setText(inputText);
		outputArea.setText(translatedText);

		inputArea.setEditable(false);
		translateButton.setEnabled(false);
		newChatButton.setVisible(true);
	}

	private void addLanguagesToComboBox() {
		String[] languages = {"Englisch", "Chinesisch", "Hindi", "Spanisch", "Französisch",
				"Arabisch", "Bengalisch", "Portugiesisch", "Russisch", "Urdu", "Indonesisch",
				"Deutsch", "Japanisch", "Pidgin", "Marathi", "Telugu", "Türkisch", "Tamil",
				"Kantonesisch", "Koreanisch", "Vietnamesisch"};

		for (String language : languages) {
			targetLanguageComboBox.addItem(language);
		}
	}

	// "Übersetzen" Button Listener: creates chat entry, sends request and gets
	// response -> showing result, saving chat entry and refreshing History Panel
	private class TranslateButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String inputText = inputArea.getText();
			if (inputText.isBlank()) {
				JOptionPane.showMessageDialog(null, "Bitte einen Text eingeben.", "Hinweis",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String targetLanguage = (String) targetLanguageComboBox.getSelectedItem();

			ChatEntry chatEntry = new ChatEntry("", targetLanguage, inputText, "");
			TranslationRequest request = new TranslationRequest(chatEntry);
			TranslationResponse response = translationService.getTranslatedText(request);
			String translatedText = response.getTranslatedText();
			outputArea.setText(translatedText);

			String titleOfEntry;
			if (inputText.length() > 15) {
				titleOfEntry = inputText.substring(0, 15);
			} else {
				titleOfEntry = inputText;
			}
			chatEntry.setTranslatedText(translatedText);
			chatEntry.setTitle(titleOfEntry);

			chatHistoryDAO.saveEntry(chatEntry);
			historyPanel.refreshChatHistory();
		}
	}

	// "Neuer Chat" Button Listener: Clear input and output area and start a new
	// translation
	private class NewChatButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			inputArea.setText("");
			outputArea.setText("");

			inputArea.setEditable(true);
			translateButton.setEnabled(false);
			newChatButton.setVisible(false);
			characterCountLabel.setText("0 von 2000 Zeichen");

			if (historyPanel != null) {
				historyPanel.clearSelection();
			}
		}
	}

	// Character Counter Listener: Counts character, if more than 2000, button is
	// disabled
	private class CharCounterListener implements DocumentListener {
		private void updateCount() {
			int length = inputArea.getText().length();
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
		public void changedUpdate(DocumentEvent e) {}
	}

}
