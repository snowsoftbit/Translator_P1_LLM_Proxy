package ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import io.github.cdimascio.dotenv.Dotenv;

public class StatusPanel extends JPanel {


	private final JLabel status = new JLabel();
	private final JLabel label = new JLabel("");
	private final Boolean keyStatus;

	public StatusPanel() {

		setLayout(new BorderLayout());
		status.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
		add(status, BorderLayout.WEST);

		keyStatus = hasValidEnv();

		if (keyStatus) {
			status.setText("Status: App ist Bereit für das Übersetzen. Keys sind vorhanden. ");
		} else {
			status.setText("Status: Keine Keys vorhanden.");
		}

	}

	public static boolean hasValidEnv() {
		try {
			Dotenv dotenv = Dotenv.load();

			getRequiredEnvStringStatic(dotenv, "SAIA_API_KEY");
			getRequiredEnvStringStatic(dotenv, "SAIA_BASE_URL");
			getRequiredEnvStringStatic(dotenv, "SAIA_MODEL");

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	private static String getRequiredEnvStringStatic(Dotenv dotenv, String key) {
		String value = dotenv.get(key);

		if (value == null || value.isBlank()) {
			throw new IllegalStateException("No " + key + " in .env");
		}

		return value;
	}


}
