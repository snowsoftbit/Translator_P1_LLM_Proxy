package persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import model.ChatEntry;

public class FileChatHistoryDAO implements ChatHistoryDAO {

    private String filePath;

    public FileChatHistoryDAO(String filePath) {
        this.filePath = filePath;
    }

    //implemented from ChatHistoryDao. Saves a ChatEntry local as JSON file in directory data.
    @Override
    public void saveEntry(ChatEntry entry) {
        Gson gson = new Gson();
        
        try(Writer writer = new FileWriter(filePath, true)) {
            gson.toJson(entry, writer);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //implemented from ChatHistoryDao. Loads all ChatEntry objects from the local JSON file in directory data.
    @Override
    public List<ChatEntry> loadEntries() {
        List<ChatEntry> entries = new ArrayList<>();

        try{
            Scanner fileScanner = new Scanner(new FileReader(filePath));
            
            while(fileScanner.hasNextLine()) {
               String line = fileScanner.nextLine();
               Scanner lineScanner = new Scanner(line);
               lineScanner.useDelimiter(",");   
               
               String titel = lineScanner.next();
               String targetLanguage = lineScanner.next();
               String originalText = lineScanner.next();
               String translatedText = lineScanner.next();

               entries.add(new ChatEntry(titel, targetLanguage, originalText, translatedText));
            }
        }catch(IOException e) {
            e.printStackTrace();
        }

        return entries;
    }



}


/*
 * Responsibility: This class is the file based implementation of the ChatHistoryDAO. This will save
 * the translation entries to a local file. The local file is the JSON file from data/history.json.
 * It loads the entries when the app starts. The real persistence work is done here. File deletion
 * is done here. Gson is used here because Gson is easy to use.
 *
 * Interactions:
 * 
 * - persistence.ChatHistoryDAO.java: this class will implement the interface methods
 * 
 * - model.ChatEntry.java: this class will save and load ChatEntry objects
 * 
 * - data/history.json: this class will read from and write to the local JSON history file - Gson:
 * this class will use Gson to convert ChatEntry objects to and from JSON
 */
