import guis.LoginGui;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        // invokeLater to make GUI updates thread-safe
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGui().setVisible(true);
            }
        });
    }
}
