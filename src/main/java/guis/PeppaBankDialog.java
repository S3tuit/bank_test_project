package guis;

import db_obj.Transaction;
import db_obj.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class PeppaBankDialog extends JDialog implements ActionListener {
    private User user;
    private PeppaBankGui peppaBankGui;
    private JLabel balanceLabel, enterAmountLabel, enterUserLabel;
    private JTextField enterAmountTextField, enterUserField;
    private JButton actionButton;

    public PeppaBankDialog(PeppaBankGui peppaBankGui, User user) {
        setSize(400, 400);
        // can't interact with anything else until dialog closed
        setModal(true);
        setLocationRelativeTo(peppaBankGui);
        setResizable(false);
        setLayout(null);

        this.peppaBankGui = peppaBankGui;
        this.user = user;
    }

    public void addCurrentBalance() {
        balanceLabel = new JLabel("Balance: $" + user.getCurrentBalance());
        balanceLabel.setBounds(0, 10, getWidth()-20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        enterAmountLabel = new JLabel("Enter Amount:");
        enterAmountLabel.setBounds(0, 50, getWidth()-20, 20);
        enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);

        enterAmountTextField = new JTextField();
        enterAmountTextField.setBounds(15, 80, getWidth()-50, 40);
        enterAmountTextField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterAmountTextField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountTextField);
    }

    public void addActionButton(String actionType) {
        actionButton = new JButton(actionType);
        actionButton.setBounds(15, 300, getWidth()-50, 40);
        actionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        add(actionButton);
    }

    public void addUserField(){
        enterUserLabel = new JLabel("Enter User:");
        enterUserLabel.setBounds(0, 160, getWidth()-20, 20);
        enterUserLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserLabel);

        enterUserField = new JTextField();
        enterUserField.setBounds(15, 190, getWidth()-50, 40);
        enterUserField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterUserField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserField);
    }

    private void handleTransaction(String transactionType, double transactionAmount) {
        Transaction transaction;

        if(transactionType.equalsIgnoreCase("Deposit")){
            user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(transactionAmount)));
            // date is null because it'll be handled by NOW() in SQL
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(transactionAmount), null);

        }else{
            // withdraw or transfer transaction type, in both cases amount is subtracted
            user.setCurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(transactionAmount)));
            transaction = new Transaction(user.getId(), transactionType, BigDecimal.valueOf(-transactionAmount), null);
        }

        // Update database

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();
        double amountValue = Double.parseDouble(enterAmountTextField.getText());

        if(buttonPressed.equalsIgnoreCase("Deposit")){}
    }
}
