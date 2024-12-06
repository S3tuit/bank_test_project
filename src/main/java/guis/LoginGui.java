package guis;

import db_obj.BankDB;
import db_obj.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginGui extends BaseFrame{

    public LoginGui() {
        super("Peppa Bank Login");
    }

    @Override
    protected void addGuiComponents() {

        // heading
        JLabel peppaBankLabel = new JLabel("Peppa Bank :)");
        peppaBankLabel.setBounds(0, 20, super.getWidth(), 40);
        peppaBankLabel.setFont(new Font("Dialog", Font.BOLD, 30));
        peppaBankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(peppaBankLabel);

        // username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 120, super.getWidth()-30, 24);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);

        // username field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 160, super.getWidth()-50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(usernameField);

        // password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 220, super.getWidth()-50, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        // password field
        JTextField passwordField = new JPasswordField();
        passwordField.setBounds(20, 260, super.getWidth()-50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        // login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(super.getWidth()/4, 350, super.getWidth()/2, 40);
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
        // when login clicked it'll check for the validity of the credentials
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getText());

                User user = BankDB.validateLogin(username, password);

                // if the user is not null then it's a valid account
                if(user != null) {

                    // valid login
                    LoginGui.this.dispose();
                    PeppaBankGui peppaBankGui = new PeppaBankGui(user);
                    peppaBankGui.setVisible(true);

                    JOptionPane.showMessageDialog(peppaBankGui, "Login Successful!");
                }else{

                    // invalid login
                    JOptionPane.showMessageDialog(LoginGui.this, "Login Failed!");
                }

            }
        });
        add(loginButton);

        // register label
        JLabel registerLabel = new JLabel("<html><a href=\"#\">Not a Peppa Bank fellow? Register</a></html>");
        registerLabel.setBounds(0, 410, super.getWidth() - 10, 30);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginGui.this.dispose();

                new RegisterGui().setVisible(true);
            }
        });
        add(registerLabel);
    }
}
