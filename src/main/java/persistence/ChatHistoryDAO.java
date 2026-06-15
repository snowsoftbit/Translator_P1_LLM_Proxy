package persistence;

import java.util.List;

import model.ChatEntry;

public interface ChatHistoryDAO {
    void saveEntry(ChatEntry entry);

    List<ChatEntry> loadEntries();
}


/*
 * Responsibility: This interface will define the data access operations for the translation
 * history. It acts as the Data Access Object interface for the history storage. It separates
 * storage access from the GUI.
 *
 * It will define what every history storage class must do: - define methods for saving ChatEntry
 * objects - define methods for loading ChatEntry objects - define methods for deleting ChatEntry
 * objects
 *
 * The actual work is done by FileChatHistoryDAO.java.
 *
 * Interactions:
 * 
 * - model.ChatEntry.java: the methods in this interface will use ChatEntry objects as a parameter
 * and, or a return type
 * 
 * - persistence.FileChatHistoryDAO.java: implements the interface methods
 */


