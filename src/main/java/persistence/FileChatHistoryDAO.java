package persistence;

import model.ChatEntry;
import java.util.List;

public class FileChatHistoryDAO implements ChatHistoryDAO {



}


/*
 * Responsibility: This class is the file based implementation of the ChatHistoryDAO. This will save
 * the translation entries to a local file. The local file is the JSON file from data/history.json.
 * It loads the entries when the app starts. The real persistence work is done here. File deletion
 * is done here. Gson is used here because Gson is easy to use.
 *
 * Interactions: - persistence.ChatHistoryDAO.java: this class will implement the interface methods
 * - model.ChatEntry.java: this class will save and load ChatEntry objects - data/history.json: this
 * class will read from and write to the local JSON history file - Gson: this class will use Gson to
 * convert ChatEntry objects to and from JSON
 */
