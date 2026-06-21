package ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel{
	
	private JLabel status = new JLabel("Bereit");
	private JLabel label = new JLabel("");

	public StatusPanel() {
		
		setLayout(new BorderLayout());
		status.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
		add(status, BorderLayout.WEST);
	}

}
