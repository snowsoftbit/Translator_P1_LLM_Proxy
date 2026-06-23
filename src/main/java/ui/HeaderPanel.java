package ui;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

    

public class HeaderPanel extends JPanel {
    private JLabel titleLabel;

    public HeaderPanel() {
        titleLabel = new JLabel("<html>Transl<b>AI</b>tor</html>");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        titleLabel.setForeground(new Color(41, 128, 185));
        
        
        add(titleLabel);
    }
}

/*
 * Responsibility: This class displays our application name and possible cute logo lmao. It contains
 * no logic and no stuff from the persistence.
 *
 * Interactions:
 * 
 * - ui.MainFrame.java: The MainFrame will implement the header on top of the window
 */
