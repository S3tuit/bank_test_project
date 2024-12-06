package guis;

import db_obj.User;

import javax.swing.*;

/*
Blueprint that all the GUIs will follow
 */
public abstract class BaseFrame extends JFrame {
    // store user info, used by main page of the bank
    protected User user;

    public BaseFrame(String title) {
        initialize(title);
    }
    public BaseFrame(String title, User user) {
        this.user=user;

        initialize(title);
    }

    private void initialize(String title) {
        // add title to the bar
        setTitle(title);

        // config
        setSize(420, 600);

        // terminate program when GUI closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // launch at center
        setLocationRelativeTo(null);
        // manually specify the location of each element
        setLayout(null);

        // based on the subclass
        addGuiComponents();
    }

    protected abstract void addGuiComponents();
}
