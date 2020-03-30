package mmsshh;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс диалогового окна ввода пароля админа
 * @version 1.01 2019-13-12
 * @author Mikhail Chaykin 7305
 */
public class LoginAdminFrame extends JDialog {

    public static boolean adminRoot = false;

    private String adminPassword = "12345";

    private JLabel passwordLabel = new JLabel("    Введите пароль:");
    private JPasswordField passwordField = new JPasswordField();
    private JButton buttonOK = new JButton("ОК");
    private JButton buttonCancel = new JButton("Отмена");


    /**
     * Конструктор класса диалогового окна
     * @param parent Окно-родитель
     * @param title Заголовок формы диалогового окна
     */
    public LoginAdminFrame(JFrame parent, String title){
        super(parent, title, true);
        initComponents();
        initListeners();
    }

    /**
     * Initialize components
     */
    private void initComponents(){
        Font labelFont = new Font("Arial", Font.BOLD, 12);
        Font txtFldFont = new Font("Arial", Font.PLAIN, 12);
        setPreferredSize(new Dimension(300, 100));

        JPanel panelCenter = new JPanel();
        JPanel panelSouth = new JPanel();

        passwordLabel.setFont(labelFont);
        passwordField.setFont(txtFldFont);

        panelCenter.setLayout(new GridLayout(1, 2));
        panelCenter.add(passwordLabel);
        panelCenter.add(passwordField);

        panelSouth.add(buttonOK);
        buttonOK.setPreferredSize(buttonCancel.getPreferredSize());
        panelSouth.add(buttonCancel);

        add(panelCenter, BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);

        pack();
    }

    private void initListeners(){
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(passwordField.getText().equals(adminPassword))
                {
                    adminRoot = true;
                    MyMessages.showGoodMessage(LoginAdminFrame.this, "Вход", "Вход успешно выполнен!");
                    dispose();
                }
                else{
                    MyMessages.showErrorMessage(LoginAdminFrame.this, "Вход", "Пароль не верный!");
                }

            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
