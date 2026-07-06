/*
 * Responsibility: This is the main frame of our app. This class will generate the panels for our
 * app. It will house all the panels. It will define and arrange the structure of our window. There
 * is no translation logic. It sets the window size, BorderLayout and the JSplitPane. It is the
 * container for the UI.
 *
 * Interactions:
 * 
 * - ui.HeaderPanel.java: creates and places the header panel in the frame
 * 
 * - ui.HistoryPanel.java: creates and places the history panel in the frame
 * 
 * - ui.TranslationPanel.java: creates and places the translation panel in the frame
 * 
 * - app.Main.java: executes this frame
 */
package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import model.ChatEntry;

public class TranslaitorFrame extends JFrame {

    private JSplitPane horizontalSplitPane;
    private TranslationPanel translationPanel;
    private HistoryPanel historyPanel;
    private HeaderPanel headerPanel = new HeaderPanel();
    private StatusPanel statusPanel = new StatusPanel();

    public TranslaitorFrame() {
        super("TranslAItor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.historyPanel = new HistoryPanel(this);
        this.translationPanel = new TranslationPanel(this.historyPanel);
        horizontalSplitPane =
                new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, historyPanel, translationPanel);

        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(horizontalSplitPane, BorderLayout.CENTER);
        getContentPane().add(statusPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    public void selectChatFromHistory(ChatEntry selectedChat) {
        translationPanel.loadHistoryChat(selectedChat.getOriginalText(),
                selectedChat.getTranslatedText());
    }
}
