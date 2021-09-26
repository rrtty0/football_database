package BaseClasses;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame extends JFrame {

    public static StateOfFrame stateOfFrame = StateOfFrame.FOOTBALLERS_STATE;

    private boolean isSearching = false;

    private int selectedRow = -1;

    public static final int DEFAULT_WIDTH = 1000;
    public static final int DEFAULT_HEIGHT = 600;

    JPanel footballersTablePanel = new JPanel();
    JPanel teamsTablePanel = new JPanel();
    JPanel matchesTablePanel = new JPanel();
    JPanel tournTablePanel = new JPanel();
    JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel leftNorthPanel = new JPanel();
    JPanel footballersRightNorthPanel = new JPanel();
    JPanel teamsRightNorthPanel = new JPanel();
    JPanel matchesRightNorthPanel = new JPanel();
    JPanel tournRightNorthPanel = new JPanel();


    JScrollPane jScrollPaneFootballers, jScrollPaneTeams, jScrollPaneMatches, jScrollPaneTourn;


    // контейнеры для верхней строчки меню
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem saveMenuIt, openMenuIt, exitMenuIt;

    JButton saveButton, openButton, addButton, editButton, deleteButton, adminButton, footballerListButton, teamListButton, matchListButton, tournListButton;

    JLabel searchLabel;
    JTextField searchField;
    JButton searchButton;
    JLabel sortLabel;
    JComboBox footballersSortComboBox, teamsSortComboBox, matchesSortComboBox, tournsSortComboBox;


    //таблица с данными
    JTable tableFootballers, tableTeams, tableMatches, tableTourn;
    DefaultTableModel tableModelFootballers, tableModelTeams, tableModelMatches, tableModelTourn;
    DefaultTableModel oldTableModel = null;

    Box box;

    //Объявляем массивы

    Object footballerColumnNames[], teamColumnNames[], matchColumnNames[], tournColumnNames[];
    Object dataFootballers[][], dataTeams[][], dataMatches[][], dataTourn[][];
    String footballersSortingFor[], teamsSortingFor[], matchesSortingFor[], tournSortingFor[];




    /**
     * Конструктор класса фрейма
     */
    public MainFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation(screenSize.width / 2 - DEFAULT_WIDTH / 2, screenSize.height / 2 - DEFAULT_HEIGHT * 2 / 3);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("Файл");
        openMenuIt = new JMenuItem("Открыть");
        saveMenuIt = new JMenuItem("Сохранить");
        exitMenuIt = new JMenuItem("Выйти");

        saveMenuIt.setEnabled(false);

        fileMenu.add(openMenuIt);
        fileMenu.add(saveMenuIt);
        fileMenu.addSeparator();
        fileMenu.addSeparator();
        fileMenu.add(exitMenuIt);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        saveButton = new JButton(new ImageIcon("src/Images/save.png"));
        openButton = new JButton(new ImageIcon("src/Images/open.png"));
        addButton = new JButton(new ImageIcon("src/Images/add.png"));
        editButton = new JButton(new ImageIcon("src/Images/edit.png"));
        deleteButton = new JButton(new ImageIcon("src/Images/delete.png"));
        adminButton = new JButton(new ImageIcon("src/Images/administrator.png"));
        footballerListButton = new JButton(new ImageIcon("src/Images/footballer.png"));
        teamListButton = new JButton(new ImageIcon("src/Images/team.png"));
        matchListButton = new JButton(new ImageIcon("src/Images/match.png"));
        tournListButton = new JButton(new ImageIcon("src/Images/cup.png"));

        saveButton.setToolTipText("Сохранить список");
        openButton.setToolTipText("Открыть список");
        addButton.setToolTipText("Добавить информацию");
        editButton.setToolTipText("Редактировать");
        deleteButton.setToolTipText("Удалить информацию");
        adminButton.setToolTipText("Профиль администратора");
        footballerListButton.setToolTipText("Таблица игроков");
        teamListButton.setToolTipText("Таблица команд");
        matchListButton.setToolTipText("Таблица матчей");
        tournListButton.setToolTipText("Таблица турниров команд");

        saveButton.setPreferredSize(new Dimension(32, 32));
        openButton.setPreferredSize(new Dimension(32, 32));
        addButton.setPreferredSize(new Dimension(32, 32));
        editButton.setPreferredSize(new Dimension(32, 32));
        deleteButton.setPreferredSize(new Dimension(32, 32));
        adminButton.setPreferredSize(new Dimension(32, 32));
        footballerListButton.setPreferredSize(new Dimension(32, 32));
        teamListButton.setPreferredSize(new Dimension(32, 32));
        matchListButton.setPreferredSize(new Dimension(32, 32));
        tournListButton.setPreferredSize(new Dimension(32, 32));

        saveButton.setEnabled(false);
        addButton.setEnabled(false);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        footballersSortingFor = new String[]{"Фамилии и имени", "Позиции", "Номеру", "Количеству голов", "Команде"};
        teamsSortingFor = new String[]{"Названию", "Рейтингу", "Городу"};
        matchesSortingFor =  new String[]{"Хозяевам","Гостям", "Счету"};
        tournSortingFor = new String[]{"Турниру", "Команде"};

        footballerColumnNames = new Object[]{"Фамилия и имя", "Позиция", "Номер", "Количество голов", "Фут клуб"};
        teamColumnNames = new Object[]{"Название", "Рейтинг", "Город"};
        matchColumnNames = new Object[]{"Хозяин", "Гость", "Счет"};
        tournColumnNames = new Object[]{"Турнир", "Фут клуб"};

        dataFootballers = new Object[0][5];
        dataTeams = new Object[0][3];
        dataMatches = new Object[0][3];
        dataTourn = new Object[0][2];

        searchLabel = new JLabel("Поиск:");
        searchField = new JTextField(10);
        searchButton = new JButton("Поиск");
        sortLabel = new JLabel("Сортировка по:");

        footballersSortComboBox = new JComboBox(footballersSortingFor);
        teamsSortComboBox = new JComboBox(teamsSortingFor);
        matchesSortComboBox = new JComboBox(matchesSortingFor);
        tournsSortComboBox = new JComboBox(tournSortingFor);

        /*
        textArea = new JTextArea(20, 30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEnabled(false);
*/
        tableModelFootballers = new DefaultTableModel(dataFootballers, footballerColumnNames){
            @Override
            public boolean isCellEditable(int i, int i1){
                return false;
            }
        };
        tableModelTeams = new DefaultTableModel(dataTeams, teamColumnNames){
            @Override
            public boolean isCellEditable(int i, int i1){
                return false;
            }
        };
        tableModelMatches = new DefaultTableModel(dataMatches, matchColumnNames){
            @Override
            public boolean isCellEditable(int i, int i1){
                return false;
            }
        };
        tableModelTourn = new DefaultTableModel(dataTourn, tournColumnNames){
            @Override
            public boolean isCellEditable(int i, int i1){
                return false;
            }
        };

        tableFootballers = new JTable(tableModelFootballers);
        tableTeams = new JTable(tableModelTeams);
        tableMatches = new JTable(tableModelMatches);
        tableTourn = new JTable(tableModelTourn);

        tableFootballers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableTeams.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableMatches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableTourn.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jScrollPaneFootballers = new JScrollPane(tableFootballers);
        jScrollPaneTeams = new JScrollPane(tableTeams);
        jScrollPaneMatches = new JScrollPane(tableMatches);
        jScrollPaneTourn = new JScrollPane(tableTourn);

        GridBagLayout gbl = new GridBagLayout();
        footballersTablePanel.setLayout(gbl);
        teamsTablePanel.setLayout(gbl);
        matchesTablePanel.setLayout(gbl);
        tournTablePanel.setLayout(gbl);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 1.0;

        footballersTablePanel.add(jScrollPaneFootballers, gbc);
        teamsTablePanel.add(jScrollPaneTeams, gbc);
        matchesTablePanel.add(jScrollPaneMatches, gbc);
        tournTablePanel.add(jScrollPaneTourn, gbc);

        box = Box.createHorizontalBox();
        box.add(saveButton);
        box.add(Box.createHorizontalStrut(3));
        box.add(openButton);
        box.add(Box.createHorizontalStrut(3));
        box.add(adminButton);
        box.add(Box.createHorizontalStrut(3));
        box.add(new JSeparator(SwingConstants.VERTICAL));
        box.add(Box.createHorizontalStrut(3));
        box.add(addButton);
        box.add(Box.createHorizontalStrut(3));
        box.add(deleteButton);
        box.add(Box.createHorizontalStrut(3));
        box.add(editButton);
        box.add(Box.createHorizontalStrut(3));
        box.add(new JSeparator(SwingConstants.VERTICAL));
        box.add(Box.createHorizontalStrut(3));
        box.add(footballerListButton);
        box.add(Box.createHorizontalStrut(3));
        box.add(teamListButton);
        box.add(Box.createHorizontalStrut(3));
        box.add(matchListButton);
        box.add(Box.createHorizontalStrut(3));
        box.add(tournListButton);
        box.add(Box.createHorizontalStrut(9));
        box.add(searchLabel);
        box.add(Box.createHorizontalStrut(3));
        box.add(searchField);
        box.add(Box.createHorizontalStrut(3));
        box.add(searchButton);
        box.add(Box.createHorizontalStrut(9));
        box.add(sortLabel);

        leftNorthPanel.add(box);
        footballersRightNorthPanel.add(footballersSortComboBox);
        teamsRightNorthPanel.add(teamsSortComboBox);
        matchesRightNorthPanel.add(matchesSortComboBox);
        tournRightNorthPanel.add(tournsSortComboBox);

        northPanel.add(leftNorthPanel);
        northPanel.add(footballersRightNorthPanel);

        getContentPane().add(northPanel, BorderLayout.NORTH);
        //getContentPane().add(new JScrollPane(textArea), BorderLayout.EAST);
        add(footballersTablePanel);

        addListeners();
    }


    private void deleteFromOldTableModel(int selRow, DefaultTableModel model){
        int j;
        if(isSearching){
            for(int i = 0; i < oldTableModel.getRowCount(); ++i)
                for(j = 0; j < oldTableModel.getColumnCount(); ++j){
                    if(!oldTableModel.getValueAt(i, j).toString().equals(model.getValueAt(selRow, j).toString()))
                        break;
                    System.out.println("AA");
                    if(j == oldTableModel.getColumnCount() - 1) {
                        oldTableModel.getDataVector().remove(i);
                        oldTableModel.fireTableDataChanged();
                        break;
                    }
                }
        }
    }

    /**
     * Initialize listeners for components
     */
    private void addListeners() {
        exitMenuIt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyTableIO.closeDBConnection();
                dispose();
            }
        });

        saveMenuIt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeToDB();
            }
        });

        openMenuIt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readFromDB();
            }
        });


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogFrame dialogFrame = null;

                switch (stateOfFrame){
                    case FOOTBALLERS_STATE:
                        dialogFrame = new DialogFrame(Main.mainFrame, "Добавление футболиста", new String[]{"Фамилия и имя", "Позиция", "Номер", "Количество голов", "Фут клуб"});
                        dialogFrame.setIconImage(new ImageIcon("footballer.png").getImage());
                        break;

                    case TEAMS_STATE:
                        dialogFrame = new DialogFrame(Main.mainFrame, "Добавление команды", new String[]{"Название", "Рейтинг", "Город"});
                        dialogFrame.setIconImage(new ImageIcon("team.png").getImage());
                        break;

                    case MATCHES_STATE:
                        dialogFrame = new DialogFrame(Main.mainFrame, "Добавление матча", new String[]{"Хозяин", "Гость", "Счет"});
                        dialogFrame.setIconImage(new ImageIcon("match.png").getImage());
                        break;

                    case TOURN_STATE:
                        dialogFrame = new DialogFrame(Main.mainFrame, "Добавление команды в турнир", new String[]{"Турнир", "Фут клуб"});
                        dialogFrame.setIconImage(new ImageIcon("cup.png").getImage());
                        break;
                }
                dialogFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                dialogFrame.setLocationRelativeTo(Main.mainFrame);
                dialogFrame.setVisible(true);

                switch (stateOfFrame){
                    case FOOTBALLERS_STATE:
                        if(DialogFrame.newData != null)
                        {
                            tableModelFootballers.addRow(DialogFrame.newData);
                            tableModelFootballers.fireTableDataChanged();
                        }
                        break;

                    case TEAMS_STATE:
                        if(DialogFrame.newData != null)
                        {
                            tableModelTeams.addRow(DialogFrame.newData);
                            tableModelTeams.fireTableDataChanged();
                        }
                        break;

                    case MATCHES_STATE:
                        if(DialogFrame.newData != null)
                        {
                            tableModelMatches.addRow(DialogFrame.newData);
                            tableModelMatches.fireTableDataChanged();
                        }
                        break;

                    case TOURN_STATE:
                        if(DialogFrame.newData != null)
                        {
                            tableModelTourn.addRow(DialogFrame.newData);
                            tableModelTourn.fireTableDataChanged();
                        }
                        break;
                }
                DialogFrame.newData = null;
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                switch (stateOfFrame){
                    case FOOTBALLERS_STATE:
                        selectedRow = tableFootballers.getSelectedRow();
                        if(selectedRow != -1 && MyMessages.showQuestionMessage(Main.mainFrame, "Удаление записи", "Вы уверены, что хотите удалить выбранную запись?") == JOptionPane.YES_OPTION)
                        {
                            deleteFromOldTableModel(selectedRow, tableModelFootballers);
                            tableModelFootballers.getDataVector().remove(selectedRow);
                            tableModelFootballers.fireTableDataChanged();
                        }
                        break;

                    case TEAMS_STATE:
                        selectedRow = tableTeams.getSelectedRow();
                        if(selectedRow != -1 && MyMessages.showQuestionMessage(Main.mainFrame, "Удаление записи", "Вы уверены, что хотите удалить выбранную запись?") == JOptionPane.YES_OPTION)
                        {
                            deleteFromOldTableModel(selectedRow, tableModelTeams);
                            tableModelTeams.getDataVector().remove(selectedRow);
                            tableModelTeams.fireTableDataChanged();
                        }
                        break;

                    case MATCHES_STATE:
                        selectedRow = tableMatches.getSelectedRow();
                        if(selectedRow != -1 && MyMessages.showQuestionMessage(Main.mainFrame, "Удаление записи", "Вы уверены, что хотите удалить выбранную запись?") == JOptionPane.YES_OPTION)
                        {
                            deleteFromOldTableModel(selectedRow, tableModelMatches);
                            tableModelMatches.getDataVector().remove(selectedRow);
                            tableModelMatches.fireTableDataChanged();
                        }
                        break;

                    case TOURN_STATE:
                        selectedRow = tableTourn.getSelectedRow();
                        if(selectedRow != -1 && MyMessages.showQuestionMessage(Main.mainFrame, "Удаление записи", "Вы уверены, что хотите удалить выбранную запись?") == JOptionPane.YES_OPTION)
                        {
                            deleteFromOldTableModel(selectedRow, tableModelTourn);
                            tableModelTourn.getDataVector().remove(selectedRow);
                            tableModelTourn.fireTableDataChanged();
                        }
                        break;
                }
                selectedRow = -1;
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogFrame dialogFrame = null;


                switch (stateOfFrame){
                    case FOOTBALLERS_STATE:
                        selectedRow = tableFootballers.getSelectedRow();
                        if(selectedRow != -1)
                        {
                            dialogFrame = new DialogFrame(Main.mainFrame, "Редактирование футболиста", new String[]{"Фамилия и имя", "Позиция", "Номер", "Количество голов", "Фут клуб"}, new String[]{(String) tableModelFootballers.getValueAt(selectedRow, 0),
                                    (String) tableModelFootballers.getValueAt(selectedRow, 1), (String) tableModelFootballers.getValueAt(selectedRow, 2), (String) tableModelFootballers.getValueAt(selectedRow, 3), (String) tableModelFootballers.getValueAt(selectedRow, 4)},
                                    tableModelFootballers);
                            dialogFrame.setIconImage(new ImageIcon("footballer.png").getImage());
                            dialogFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            dialogFrame.setLocationRelativeTo(Main.mainFrame);
                            dialogFrame.setVisible(true);
                        }
                        break;

                    case TEAMS_STATE:
                        selectedRow = tableTeams.getSelectedRow();
                        if(selectedRow != -1)
                        {
                            dialogFrame = new DialogFrame(Main.mainFrame, "Редактирование команды", new String[]{"Название", "Рейтинг", "Город"}, new String[]{(String) tableModelTeams.getValueAt(selectedRow, 0), (String) tableModelTeams.getValueAt(selectedRow, 1),
                                    (String) tableModelTeams.getValueAt(selectedRow, 2)}, tableModelTeams);
                            dialogFrame.setIconImage(new ImageIcon("team.png").getImage());
                            dialogFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            dialogFrame.setLocationRelativeTo(Main.mainFrame);
                            dialogFrame.setVisible(true);
                        }
                        break;

                    case MATCHES_STATE:
                        selectedRow = tableMatches.getSelectedRow();
                        if(selectedRow != -1)
                        {
                            dialogFrame = new DialogFrame(Main.mainFrame, "Редактирование матча", new String[]{"Хозяин", "Гость", "Счет"}, new String[]{(String) tableModelMatches.getValueAt(selectedRow, 0), (String) tableModelMatches.getValueAt(selectedRow, 1),
                                    (String) tableModelMatches.getValueAt(selectedRow, 2)}, tableModelMatches);
                            dialogFrame.setIconImage(new ImageIcon("match.png").getImage());
                            dialogFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            dialogFrame.setLocationRelativeTo(Main.mainFrame);
                            dialogFrame.setVisible(true);
                        }
                        break;

                    case TOURN_STATE:
                        selectedRow = tableTourn.getSelectedRow();
                        if(selectedRow != -1)
                        {
                            dialogFrame = new DialogFrame(Main.mainFrame, "Редактирование турниров и участвующих в них команд", new String[]{"Турнир", "Фут клуб"}, new String[]{(String) tableModelTourn.getValueAt(selectedRow, 0), (String) tableModelTourn.getValueAt(selectedRow, 1)},
                                    tableModelTourn);
                            dialogFrame.setIconImage(new ImageIcon("cup.png").getImage());
                            dialogFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            dialogFrame.setLocationRelativeTo(Main.mainFrame);
                            dialogFrame.setVisible(true);
                        }
                        break;
                }

                if(selectedRow != -1){
                    switch (stateOfFrame){
                        case FOOTBALLERS_STATE:
                            if(DialogFrame.newData != null)
                            {
                                if(isSearching)
                                    deleteFromOldTableModel(selectedRow, tableModelFootballers);
                                tableModelFootballers.getDataVector().remove(selectedRow);
                                tableModelFootballers.addRow(DialogFrame.newData);
                                tableModelFootballers.fireTableDataChanged();
                            }
                            break;

                        case TEAMS_STATE:
                            if(DialogFrame.newData != null)
                            {
                                if(isSearching)
                                    deleteFromOldTableModel(selectedRow, tableModelTeams);
                                tableModelTeams.getDataVector().remove(selectedRow);
                                tableModelTeams.addRow(DialogFrame.newData);
                                tableModelTeams.fireTableDataChanged();

                            }
                            break;

                        case MATCHES_STATE:
                            if(DialogFrame.newData != null)
                            {
                                if(isSearching)
                                    deleteFromOldTableModel(selectedRow, tableModelMatches);
                                tableModelMatches.getDataVector().remove(selectedRow);
                                tableModelMatches.addRow(DialogFrame.newData);
                                tableModelMatches.fireTableDataChanged();

                            }
                            break;

                        case TOURN_STATE:
                            if(DialogFrame.newData != null)
                            {
                                if(isSearching)
                                    deleteFromOldTableModel(selectedRow, tableModelTourn);
                                tableModelTourn.getDataVector().remove(selectedRow);
                                tableModelTourn.addRow(DialogFrame.newData);
                                tableModelTourn.fireTableDataChanged();

                            }
                            break;
                    }

                    if(isSearching)
                    {
                        oldTableModel.addRow(DialogFrame.newData);
                        oldTableModel.fireTableDataChanged();
                    }
                    DialogFrame.newData = null;
                }
                selectedRow = -1;
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeToDB();
            }
        });

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readFromDB();
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginAdminFrame loginAdminFrame = new LoginAdminFrame(Main.mainFrame, "Администратор вход");
                loginAdminFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                loginAdminFrame.setLocationRelativeTo(Main.mainFrame);
                loginAdminFrame.setIconImage(new ImageIcon("adminEnter.png").getImage());
                loginAdminFrame.setVisible(true);

                if(LoginAdminFrame.adminRoot)
                {
                    if(!isSearching)
                        addButton.setEnabled(true);

                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                    adminButton.setEnabled(false);
                    saveMenuIt.setEnabled(true);
                    saveButton.setEnabled(true);

                    setTitle("Футбольная база данных - [Администратор]");
                }
            }
        });

        footballerListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (stateOfFrame){
                    case FOOTBALLERS_STATE:
                        return;

                    case TEAMS_STATE:
                        remove(teamsTablePanel);
                        northPanel.remove(teamsRightNorthPanel);
                        break;

                    case MATCHES_STATE:
                        remove(matchesTablePanel);
                        northPanel.remove(matchesRightNorthPanel);
                        break;

                    case TOURN_STATE:
                        remove(tournTablePanel);
                        northPanel.remove(tournRightNorthPanel);
                        break;
                }
                stateOfFrame = StateOfFrame.FOOTBALLERS_STATE;
                add(footballersTablePanel);
                northPanel.add(footballersRightNorthPanel);
                revalidate();
                repaint();
            }
        });

        teamListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (stateOfFrame){
                    case FOOTBALLERS_STATE:
                        remove(footballersTablePanel);
                        northPanel.remove(footballersRightNorthPanel);
                        break;

                    case TEAMS_STATE:
                        return;

                    case MATCHES_STATE:
                        remove(matchesTablePanel);
                        northPanel.remove(matchesRightNorthPanel);
                        break;

                    case TOURN_STATE:
                        remove(tournTablePanel);
                        northPanel.remove(tournRightNorthPanel);
                        break;
                }
                stateOfFrame = StateOfFrame.TEAMS_STATE;
                add(teamsTablePanel);
                northPanel.add(teamsRightNorthPanel);
                revalidate();
                repaint();
            }
        });

        matchListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (stateOfFrame){
                    case FOOTBALLERS_STATE:
                        remove(footballersTablePanel);
                        northPanel.remove(footballersRightNorthPanel);
                        break;

                    case TEAMS_STATE:
                        remove(teamsTablePanel);
                        northPanel.remove(teamsRightNorthPanel);
                        break;

                    case MATCHES_STATE:
                        return;

                    case TOURN_STATE:
                        remove(tournTablePanel);
                        northPanel.remove(tournRightNorthPanel);
                        break;
                }
                stateOfFrame = StateOfFrame.MATCHES_STATE;
                add(matchesTablePanel);
                northPanel.add(matchesRightNorthPanel);
                revalidate();
                repaint();
            }
        });

        tournListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (stateOfFrame){
                    case FOOTBALLERS_STATE:
                        remove(footballersTablePanel);
                        northPanel.remove(footballersRightNorthPanel);
                        break;

                    case TEAMS_STATE:
                        remove(teamsTablePanel);
                        northPanel.remove(teamsRightNorthPanel);
                        break;

                    case MATCHES_STATE:
                        remove(matchesTablePanel);
                        northPanel.remove(matchesRightNorthPanel);
                        break;

                    case TOURN_STATE:
                        return;
                }
                stateOfFrame = StateOfFrame.TOURN_STATE;
                add(tournTablePanel);
                northPanel.add(tournRightNorthPanel);
                revalidate();
                repaint();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tableModel = null;

                if(!isSearching)
                {
                    String searchWord = searchField.getText();

                    switch (stateOfFrame){
                        case FOOTBALLERS_STATE:
                            tableModel = tableModelFootballers;
                            oldTableModel = new DefaultTableModel(dataFootballers, footballerColumnNames);
                            teamListButton.setEnabled(false);
                            matchListButton.setEnabled(false);
                            tournListButton.setEnabled(false);
                            break;

                        case TEAMS_STATE:
                            tableModel = tableModelTeams;
                            oldTableModel = new DefaultTableModel(dataTeams, teamColumnNames);
                            footballerListButton.setEnabled(false);
                            matchListButton.setEnabled(false);
                            tournListButton.setEnabled(false);
                            break;

                        case MATCHES_STATE:
                            tableModel = tableModelMatches;
                            oldTableModel = new DefaultTableModel(dataMatches, matchColumnNames);
                            teamListButton.setEnabled(false);
                            footballerListButton.setEnabled(false);
                            tournListButton.setEnabled(false);
                            break;

                        case TOURN_STATE:
                            tableModel = tableModelTourn;
                            oldTableModel = new DefaultTableModel(dataTourn, tournColumnNames);
                            teamListButton.setEnabled(false);
                            matchListButton.setEnabled(false);
                            footballerListButton.setEnabled(false);
                            break;
                    }

                    addButton.setEnabled(false);
                    openButton.setEnabled(false);
                    openMenuIt.setEnabled(false);
                    searchField.setEnabled(false);


                    for(int i = 0; i < tableModel.getRowCount(); ++i)
                    {
                        oldTableModel.addRow(new String[]{});
                        for(int j = 0; j < tableModel.getColumnCount(); ++j)
                            oldTableModel.setValueAt(tableModel.getValueAt(i, j), i, j);
                    }

                    int j;
                    for(int i = 0; i < tableModel.getRowCount(); ++i){
                        for(j = 0; j < tableModel.getColumnCount(); ++j)
                            if(tableModel.getValueAt(i, j).toString().contains(searchWord))
                                break;

                        if(j == tableModel.getColumnCount()){
                            tableModel.removeRow(i);
                            --i;
                        }
                    }

                    tableModel.fireTableDataChanged();

                    searchButton.setText("Отмена");

                    isSearching = true;
                }
                else
                {
                    switch (stateOfFrame){
                        case FOOTBALLERS_STATE:
                            tableModel = tableModelFootballers;
                            teamListButton.setEnabled(true);
                            matchListButton.setEnabled(true);
                            tournListButton.setEnabled(true);
                            break;

                        case TEAMS_STATE:
                            tableModel = tableModelTeams;
                            footballerListButton.setEnabled(true);
                            matchListButton.setEnabled(true);
                            tournListButton.setEnabled(true);
                            break;

                        case MATCHES_STATE:
                            tableModel = tableModelMatches;
                            teamListButton.setEnabled(true);
                            footballerListButton.setEnabled(true);
                            tournListButton.setEnabled(true);
                            break;

                        case TOURN_STATE:
                            tableModel = tableModelTourn;
                            teamListButton.setEnabled(true);
                            matchListButton.setEnabled(true);
                            footballerListButton.setEnabled(true);
                            break;
                    }

                    tableModel.getDataVector().removeAllElements();
                    for(int i = 0; i < oldTableModel.getRowCount(); ++i)
                    {
                        tableModel.addRow(new String[]{});
                        for(int j = 0; j < oldTableModel.getColumnCount(); ++j)
                            tableModel.setValueAt(oldTableModel.getValueAt(i, j), i, j);
                    }

                    if(LoginAdminFrame.adminRoot)
                        addButton.setEnabled(true);

                    openButton.setEnabled(true);
                    openMenuIt.setEnabled(true);
                    searchField.setEnabled(true);
                    searchButton.setText("Поиск:");

                    isSearching = false;
                }

            }

        });

        footballersSortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                int item = box.getSelectedIndex();
                boolean specFlag = false;
                if(item == 2 || item == 3)
                    specFlag = true;
                sortTable(item, tableModelFootballers, specFlag);
                tableModelFootballers.fireTableDataChanged();
            }
        });

        teamsSortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                int item = box.getSelectedIndex();
                boolean specFlag = false;
                if(item == 1)
                    specFlag = true;
                sortTable(item, tableModelTeams, specFlag);
                tableModelTeams.fireTableDataChanged();
            }
        });

        matchesSortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                int item = box.getSelectedIndex();
                sortTable(item, tableModelMatches, false);
                tableModelMatches.fireTableDataChanged();
            }
        });

        tournsSortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                int item = box.getSelectedIndex();
                sortTable(item, tableModelTourn, false);
                tableModelTourn.fireTableDataChanged();
            }
        });
    }

    public int getSelectedRow(){
        return selectedRow;
    }

    private void sortTable(int columnIndex, DefaultTableModel tableModel, boolean specFlag){
        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < tableModel.getRowCount(); ++i) {
                if ((!specFlag && compareStr(i, columnIndex, tableModel)) || (specFlag && compareInt(i, columnIndex, tableModel))) {
                    swap(i, tableModel);
                    needIteration = true;
                }
            }
        }
    }

    private boolean compareStr(int i, int columnIndex, DefaultTableModel tableModel){
        return (tableModel.getValueAt(i, columnIndex).toString().compareTo(tableModel.getValueAt(i - 1, columnIndex).toString())) < 0;
    }

    private boolean compareInt(int i, int columnIndex, DefaultTableModel tableModel){
        return Integer.parseInt(tableModel.getValueAt(i, columnIndex).toString()) < Integer.parseInt(tableModel.getValueAt(i - 1, columnIndex).toString());
    }

    private void swap(int i, DefaultTableModel tableModel){
        String curStr;

        for(int k = 0; k < tableModel.getColumnCount(); ++k)
        {
            curStr = tableModel.getValueAt(i, k).toString();
            tableModel.setValueAt(tableModel.getValueAt(i - 1, k), i, k);
            tableModel.setValueAt(curStr, i - 1, k);
        }
    }

    private void readFromDB(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                switch (stateOfFrame){
                    case FOOTBALLERS_STATE:
                        MyTableIO.readFootballers(tableModelFootballers);
                        break;

                    case TEAMS_STATE:
                        MyTableIO.readTeams(tableModelTeams);
                        break;

                    case MATCHES_STATE:
                        MyTableIO.readMatches(tableModelMatches);
                        break;

                    case TOURN_STATE:
                        MyTableIO.readTourn(tableModelTourn);
                        break;
                }
            }
        };

        Thread threadDB = new Thread(task);
        threadDB.start();
    }

    private void writeToDB(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                switch (stateOfFrame){
                    case FOOTBALLERS_STATE:
                        MyTableIO.writeFootballers(tableModelFootballers);
                        break;

                    case TEAMS_STATE:
                        MyTableIO.writeTeams(tableModelTeams);
                        break;

                    case MATCHES_STATE:
                        MyTableIO.writeMatches(tableModelMatches);
                        break;

                    case TOURN_STATE:
                        MyTableIO.writeTourHasTeam(tableModelTourn);
                        break;
                }
            }
        };

        Thread threadDB = new Thread(task);
        threadDB.start();
    }
}
