package ui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.ChatEntry;
import persistence.ChatHistoryDAO;
import persistence.FileChatHistoryDAO;

public class HistoryPanel extends JPanel {

	private final DefaultListModel<ChatEntry> listModel = new DefaultListModel<>();
	private final JList<ChatEntry> historyList = new JList<>(listModel);
	private final ChatHistoryDAO chatHistoryDAO = new FileChatHistoryDAO("history.json");
	private final MainFrame mainFrame;
	private final int MAX_ENTRIES_TO_SHOW = 10;

	public HistoryPanel(MainFrame mainFrame) {

		this.mainFrame = mainFrame;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(250, 600));

		historyList.setFont(new Font("Arial", Font.PLAIN, 16));
		historyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		historyList.addListSelectionListener(new HistorySelectionListener());

		JLabel historyLabel = new JLabel("Verlauf", JLabel.CENTER);
		historyLabel.setFont(new Font("Arial", Font.BOLD, 16));
		add(historyLabel, BorderLayout.NORTH);
		add(new JScrollPane(historyList), BorderLayout.CENTER);

		refreshChatHistory();
	}

	public void refreshChatHistory() {
		listModel.clear();

		try {
			List<ChatEntry> entries = chatHistoryDAO.loadEntries();
			int count = 0;

			if (entries != null) {
				entries.sort(new ChatEntryComparator());

				for (ChatEntry entry : entries) {
					if (count >= MAX_ENTRIES_TO_SHOW) {
						break;
					}
					listModel.addElement(entry);
					count++;
				} 
			}
		} catch (Exception ex) {
			System.out.println("Fehler beim Laden des Chatverlaufs");
		}

	}

	private class HistorySelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				ChatEntry selectedChatEntry = historyList.getSelectedValue();

				if (selectedChatEntry != null && mainFrame != null) {

					 mainFrame.selectChatFromHistory(selectedChatEntry);
				}
			}
		}
	}

	private class ChatEntryComparator implements Comparator<ChatEntry> {
		@Override
		public int compare(ChatEntry entry1, ChatEntry entry2) {
			return entry2.getTimestamp().compareTo(entry1.getTimestamp());
		}
	}

	public void clearSelection() {
		historyList.clearSelection();
	}
}

/*
 * Responsibility: It will display the user's previous translation results, meaning chats, in a
 * list. It will enable the user to select previous chats to load into the GUI. They must be sorted
 * by date, so that the most recent chat is always on top.
 *
 * It should notify other Swing GUI components, meaning MainFrame and then the TranslationPanel,
 * that a historical chat was selected by the user and should be displayed.
 *
 * The active chat should be highlighted so that the user knows what chat is being used.
 *
 * It can let the user delete old ChatEntry objects by clicking on a chat. Then a delete button
 * becomes visible. This must include a confirmation dialog so that the user does not delete a chat
 * on purpose.
 *
 * It gets removed from the historical JSON list immediately. The JSON file gets reloaded, so a
 * refresh history method is good.
 *
 * Interactions:
 * 
 * - model.ChatEntry.java: displays ChatEntry objects in the history list
 * 
 * - persistence.ChatHistoryDAO.java: calls the Data Access Object methods to load saved history
 * entries and delete them
 * 
 * - ui.MainFrame.java: tells MainFrame when a user has selected and deleted an entry
 */
