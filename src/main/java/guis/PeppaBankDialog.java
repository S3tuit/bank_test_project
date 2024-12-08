package guis;

import db_obj.BankDB;
import db_obj.Transaction;
import db_obj.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

public class PeppaBankDialog extends JDialog implements ActionListener {
    private User user;
    private PeppaBankGui peppaBankGui;
    private JLabel balanceLabel, enterAmountLabel, enterUserLabel;
    private JTextField enterAmountTextField, enterUserField;
    private JButton actionButton;
    private JPanel pastTransactionPanel;
    private ArrayList<Transaction> pastTransactions;

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
        actionButton.addActionListener(this);
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
        if(BankDB.addTransactionToDB(transaction) && BankDB.updateCurrentBalance(user)){
            JOptionPane.showMessageDialog(this, transactionType + " Successfully!");

            // reset fields
            updateFields();
        }else{
            JOptionPane.showMessageDialog(this, transactionType + " Failed :(");
        }
    }

    private void updateFields() {
        enterAmountTextField.setText("");

        if(enterUserField != null) {
            enterUserField.setText("");
        }

        balanceLabel.setText("Balance: $" + user.getCurrentBalance());
        peppaBankGui.getCurrentBalanceField().setText("$" + user.getCurrentBalance());
    }

    private void handleTransfer(User user, String transferredUser, double transferAmount) {
        // attempt transfer
        if(BankDB.transfer(user, transferredUser, transferAmount)) {
            JOptionPane.showMessageDialog(this, "Transfer Success!");
            updateFields();
        }else{
            JOptionPane.showMessageDialog(this, "Transfer Failed");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();
        double amountValue = Double.parseDouble(enterAmountTextField.getText());

        if(buttonPressed.equalsIgnoreCase("Deposit")){
            handleTransaction(buttonPressed, amountValue);
        }else{
            // withdraw or transfer

            // -1 = entered amout is greater, 0 is equal, 1 is lower
            if(user.getCurrentBalance().compareTo(BigDecimal.valueOf(amountValue)) < 0){
                JOptionPane.showMessageDialog(this, "Error: entered amout is greater than current balance");
                return;
            }

            if(buttonPressed.equalsIgnoreCase("Withdraw")){
                handleTransaction(buttonPressed, amountValue);
            }else{
                String transferredUser = enterUserField.getText();
                handleTransfer(user, transferredUser, amountValue);
            }
        }
    }

    public void showPastTransactions() {
        pastTransactionPanel = new JPanel();
        pastTransactionPanel.setLayout(new BoxLayout(pastTransactionPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(pastTransactionPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 20, getWidth() - 15, getHeight() - 80);

        pastTransactions = BankDB.getPastTransactions(user);

        for(Transaction pastTrans : pastTransactions){
            JPanel pastTransactionContainer = new JPanel();
            pastTransactionContainer.setLayout(new BorderLayout());

            JLabel transactionTypeLabel = new JLabel(pastTrans.getTransactionType());
            transactionTypeLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            JLabel transactionAmountLabel = new JLabel(pastTrans.getTransactionAmount().toString());
            transactionAmountLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            JLabel transactionDateLabel = new JLabel(pastTrans.getTransactionDate().toString());
            transactionDateLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            pastTransactionContainer.add(transactionTypeLabel, BorderLayout.WEST);
            pastTransactionContainer.add(transactionAmountLabel, BorderLayout.EAST);
            pastTransactionContainer.add(transactionDateLabel, BorderLayout.NORTH);

            pastTransactionContainer.setBackground(Color.WHITE);
            pastTransactionContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pastTransactionPanel.add(pastTransactionContainer);

        }

        add(scrollPane);
    }
}
