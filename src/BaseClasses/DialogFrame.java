package BaseClasses;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Класс диалогового окна ввода информации
 * @version 1.01 2019-13-12
 * @author Mikhail Chaykin 7305
 */
public class DialogFrame extends JDialog {

    private JLabel lab;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3 = new JLabel("");
    private JLabel label4 = new JLabel("");
    private JLabel label5 = new JLabel("");
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3 = new JTextField(" ");
    private JTextField textField4 = new JTextField(" ");
    private JTextField textField5 = new JTextField(" ");
    private JButton buttonOK = new JButton("Oк");
    private JButton buttonCancel = new JButton("Отмена");

    public static String[] newData = null;
    
    private boolean updateRecordToDB = false;


    /**
     * Конструктор класса диалогового окна
     * @param parent Окно-родитель
     * @param title Заголовок формы диалогового окна
     */
    public DialogFrame(JFrame parent, String title, String[] columnNames){
        super(parent, title, true);
        lab = new JLabel("Ввод информации");
        initComponents(columnNames, new String[]{"", "", "", "", ""});
        addToFrameComponents();
        initListeners(null);
        updateRecordToDB = false;
    }

    public DialogFrame(JFrame parent, String title, String[] columnNames, String[] text, DefaultTableModel tableModel){
        super(parent, title, true);
        lab = new JLabel("Изменение информации");
        initComponents(columnNames, text);
        addToFrameComponents();
        initListeners(tableModel);
        updateRecordToDB = true;
    }

    private void initComponents(String[] columnNames, String[] text){
        label1 = new JLabel(columnNames[0]);
        label2 = new JLabel(columnNames[1]);
        textField1 = new JTextField(text[0]);
        textField2 = new JTextField(text[1]);

        if(columnNames.length == 3)
        {
            label3 = new JLabel(columnNames[2]);
            textField3 = new JTextField(text[2]);
            label4.setEnabled(false);
            label5.setEnabled(false);
            textField4.setEnabled(false);
            textField5.setEnabled(false);
        }
        else
        if(columnNames.length == 2){
            label3.setEnabled(false);
            label4.setEnabled(false);
            label5.setEnabled(false);
            textField3.setEnabled(false);
            textField4.setEnabled(false);
            textField5.setEnabled(false);
        }
        else
        if(columnNames.length == 5) {
            label3 = new JLabel(columnNames[2]);
            label4 = new JLabel(columnNames[3]);
            label5 = new JLabel(columnNames[4]);
            textField3 = new JTextField(text[2]);
            textField4 = new JTextField(text[3]);
            textField5 = new JTextField(text[4]);
        }
    }

    /**
     * Add to frame components
     */
    private void addToFrameComponents(){

        Font mainLabelFont = new Font("Arial", Font.BOLD, 16);
        Font labelFont = new Font("Arial", Font.BOLD, 12);
        Font txtFldFont = new Font("Arial", Font.PLAIN, 12);
        setPreferredSize(new Dimension(300, 225));

        JPanel panelCenter = new JPanel();
        JPanel panelNorth = new JPanel();
        JPanel panelSouth = new JPanel();

        lab.setFont(mainLabelFont);
        textField1.setFont(txtFldFont);
        label1.setFont(labelFont);
        label2.setFont(labelFont);
        label3.setFont(labelFont);
        label4.setFont(labelFont);

        panelNorth.add(lab);

        panelCenter.setLayout(new GridLayout(5, 2));
        panelCenter.add(label1);
        panelCenter.add(textField1);
        panelCenter.add(label2);
        panelCenter.add(textField2);
        panelCenter.add(label3);
        panelCenter.add(textField3);
        panelCenter.add(label4);
        panelCenter.add(textField4);
        panelCenter.add(label5);
        panelCenter.add(textField5);

        add(panelNorth, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        panelSouth.add(buttonOK);
        buttonOK.setPreferredSize(buttonCancel.getPreferredSize());
        panelSouth.add(buttonCancel);

        add(panelSouth, BorderLayout.SOUTH);


        pack();

    }

    private boolean checkData(){
        switch (MainFrame.stateOfFrame){
            case FOOTBALLERS_STATE:
                try{
                    int number = Integer.parseInt(textField3.getText());
                    int goals = Integer.parseInt(textField4.getText());

                    if(!(number >= 1 && number <= 99) || !(goals >= 0))
                        return false;
                }
                catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
                break;

            case TEAMS_STATE:
                try{
                    int raiting = Integer.parseInt(textField2.getText());

                    if(!(raiting >= 1))
                        return false;
                }
                catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
                break;

            case MATCHES_STATE:
                Pattern p1 = Pattern.compile("^\\d{1,2}-\\d{1,2}$");
                Matcher m = p1.matcher(textField3.getText());

                if(!m.matches())
                    return false;
                break;

            case TOURN_STATE:
                break;
        }

        return !(textField1.getText().isEmpty() || textField2.getText().isEmpty() || textField3.getText().isEmpty() || textField4.getText().isEmpty() || textField5.getText().isEmpty());
    }

    /**
     * Initialize listeners for components
     */
    private void initListeners(DefaultTableModel tableModel){
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkData())
                {
                    switch (MainFrame.stateOfFrame){
                        case FOOTBALLERS_STATE:
                            newData = new String[]{textField1.getText(), textField2.getText(), textField3.getText(), textField4.getText(), textField5.getText()};
                            break;

                        case TEAMS_STATE:
                            newData = new String[]{textField1.getText(), textField2.getText(), textField3.getText()};
                            break;

                        case MATCHES_STATE:
                            newData = new String[]{textField1.getText(), textField2.getText(), textField3.getText()};
                            break;

                        case TOURN_STATE:
                            newData = new String[]{textField1.getText(), textField2.getText()};
                            break;
                    }
                    if(!updateRecordToDB)
                        MyMessages.showGoodMessage(DialogFrame.this, "Ввод", "Данные успешно введены!");
                    else
                        if(MyMessages.showQuestionMessage(DialogFrame.this, "Ввод", "Желаете обновить данную запись в БД?") == JOptionPane.YES_OPTION)
                        {
                            switch (MainFrame.stateOfFrame){
                                case FOOTBALLERS_STATE:
                                    MyTableIO.updateFootballer(tableModel);
                                    break;

                                case TEAMS_STATE:
                                    MyTableIO.updateTeam(tableModel);
                                    break;

                                case MATCHES_STATE:
                                    MyTableIO.updateMatch(tableModel);
                                    break;

                                case TOURN_STATE:
                                    MyTableIO.updateTourn(tableModel);
                                    break;
                            }
                        }
                        else
                            MyMessages.showGoodMessage(DialogFrame.this, "Ввод", "Данные успешно введены в таблицу!");

                    dispose();
                }
                else
                {
                    MyMessages.showErrorMessage(DialogFrame.this, "Ошибка", "Введены некорректные данные!");
                    //dispose();
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
