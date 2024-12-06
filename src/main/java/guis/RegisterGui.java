package guis;

import db_obj.BankDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterGui extends BaseFrame{

    public RegisterGui() {
        super("Become a Peppa Fellow");
    }

    @Override
    protected void addGuiComponents() {

        // heading
        JLabel peppaBankLabel = new JLabel("Become a Peppa Fellow");

        peppaBankLabel.setBounds(0, 20, super.getWidth(), 40);
        peppaBankLabel.setFont(new Font("Dialog", Font.BOLD, 30));
        peppaBankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(peppaBankLabel);

        // username label
        JLabel usernameLabel = new JLabel("Username:");

        usernameLabel.setBounds(20, 90, super.getWidth()-30, 24);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);

        // username field
        JTextField usernameField = new JTextField();

        usernameField.setBounds(20, 130, super.getWidth()-50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(usernameField);

        // password label
        JLabel passwordLabel = new JLabel("Password:");

        passwordLabel.setBounds(20, 190, super.getWidth()-50, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        // password field
        JTextField passwordField = new JPasswordField();

        passwordField.setBounds(20, 230, super.getWidth()-50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        // re-type password label
        JLabel rePasswordLabel = new JLabel("Re-type Password:");

        rePasswordLabel.setBounds(20, 290, super.getWidth()-50, 24);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(rePasswordLabel);

        // re-type password field
        JTextField rePasswordField = new JPasswordField();

        rePasswordField.setBounds(20, 330, super.getWidth()-50, 40);
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(rePasswordField);

        // register button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(super.getWidth()/4, 420, super.getWidth()/2, 40);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getText());
                String rePassword = String.valueOf(rePasswordField.getText());

                if(validateRegistration(username, password, rePassword)){
                    // try to register the user
                    if(BankDB.registerUser(username, password)){
                        // registration success
                        RegisterGui.this.dispose();

                        LoginGui loginGui = new LoginGui();
                        loginGui.setVisible(true);
                        JOptionPane.showMessageDialog(loginGui, "Account Peppa Pig Successfully!");
                    }else{
                        // registration failed
                        JOptionPane.showMessageDialog(RegisterGui.this, "Username already taken");
                    }
                }else{
                    // invalid user input
                    JOptionPane.showMessageDialog(RegisterGui.this, "'r you dumbass!?");
                }
            }
        });
        add(registerButton);

        // login label
        JLabel loginLabel = new JLabel("<html><a href=\"#\">Already a fellow Peppa?</a></html>");
        loginLabel.setBounds(0, 470, super.getWidth() -10, 30);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterGui.this.dispose();
                new LoginGui().setVisible(true);
            }
        });
        add(loginLabel);
    }

    private boolean validateRegistration(String username, String password, String rePassword) {
        if(username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) return false;

        if(username.length() < 5 || password.length() < 5 || rePassword.length() < 5) return false;

        if(password.equals(rePassword)) return true;

        return false;
    }
}

