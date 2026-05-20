package ui;


import model.ChatEntry;
import persistence.ChatHistoryDAO;

public class HistoryPanel {

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
 * Interactions: - model.ChatEntry.java: displays ChatEntry objects in the history list -
 * persistence.ChatHistoryDAO.java: calls the Data Access Object methods to load saved history
 * entries and delete them - ui.MainFrame.java: tells MainFrame when a user has selected and deleted
 * an entry
 */
