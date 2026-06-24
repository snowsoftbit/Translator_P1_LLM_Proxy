package ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

    

public class HeaderPanel extends JPanel {
    
    private JLabel titleLabel;

    public HeaderPanel() {
        titleLabel = new JLabel("<html>Transl<b>AI</b>tor</html>");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        titleLabel.setForeground(new Color(41, 128, 185));
        
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/HWR_LOGO.png"));
            
            Image img = originalIcon.getImage();
            Image scaledImg = img.getScaledInstance(-1, 35, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImg);
            
            titleLabel.setIcon(scaledIcon);
            titleLabel.setIconTextGap(15); 
        } catch (Exception e) {
            System.out.println("Logo konnte nicht geladen werden. Nutze reinen Text-Titel. \n" + e.getMessage());
        }
        
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
