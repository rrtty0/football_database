package mmsshh;


import DBConnection.Const;
import DBConnection.DataBaseHandler;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyTableIO {

    private static DataBaseHandler dataBaseHandler = new DataBaseHandler();

    public static void closeDBConnection(){
        dataBaseHandler.closeConnection();
    }

    public static void readFootballers(DefaultTableModel tableModel){
        ResultSet resultSet = dataBaseHandler.getFootballersFromDB();

        tableModel.getDataVector().removeAllElements();

        try{
            while (resultSet.next()){
                String str[] = new String[] {resultSet.getString(Const.FOOTBALLER_NAME), resultSet.getString(Const.FOOTBALLER_POSITION),
                        String.valueOf(resultSet.getInt(Const.FOOTBALLER_NUMBER)), String.valueOf(resultSet.getInt(Const.FOOTBALLER_GOALS)), resultSet.getString(Const.FOOTBALLER_CLUB)};
                tableModel.addRow(str);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        tableModel.fireTableDataChanged();
    }

    public static void readTeams(DefaultTableModel tableModel){
        ResultSet resultSet = dataBaseHandler.getTeamsFromDB();

        tableModel.getDataVector().removeAllElements();

        try{
            while (resultSet.next()){
                String str[] = new String[] {resultSet.getString(Const.TEAM_NAME), String.valueOf(resultSet.getInt(Const.TEAM_RAITING)),
                        resultSet.getString(Const.TEAM_CITY)};
                tableModel.addRow(str);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        tableModel.fireTableDataChanged();
    }

    public static void readMatches(DefaultTableModel tableModel){
        ResultSet resultSet = dataBaseHandler.getMatchesFromDB();

        tableModel.getDataVector().removeAllElements();

        try{
            while (resultSet.next()){
                String str[] = new String[] {resultSet.getString(Const.MATCH_HOME), resultSet.getString(Const.MATCH_VISITOR),
                        resultSet.getString(Const.MATCH_SCORE)};
                tableModel.addRow(str);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        tableModel.fireTableDataChanged();
    }

    public static void readTourn(DefaultTableModel tableModel){
        ResultSet resultSet = dataBaseHandler.getTournTeamFromDB();

        tableModel.getDataVector().removeAllElements();

        try{
            while (resultSet.next()){
                String str[] = new String[] {resultSet.getString(Const.TOURN_HAS_TEAM_TOURN_NAME), resultSet.getString(Const.TOURN_HAS_TEAM_TEAM_NAME)};
                tableModel.addRow(str);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        tableModel.fireTableDataChanged();
    }

    public static void writeTeams(DefaultTableModel tableModel){
        dataBaseHandler.writeTeamsToDB(tableModel);
    }

    public static void writeFootballers(DefaultTableModel tableModel){
        dataBaseHandler.writeFootballersToDB(tableModel);
    }

    public static void writeTourHasTeam(DefaultTableModel tableModel){
        dataBaseHandler.writeTournHasTeamToDB(tableModel);
    }

    public static void writeMatches(DefaultTableModel tableModel){
        dataBaseHandler.writeMatchesToDB(tableModel);
    }

    public static void updateFootballer(DefaultTableModel tableModel){
        dataBaseHandler.updateFootballerFromDB(tableModel);
    }

    public static void updateTeam(DefaultTableModel tableModel){
        dataBaseHandler.updateTeamFromDB(tableModel);
    }

    public static void updateMatch(DefaultTableModel tableModel){
        dataBaseHandler.updateMatchFromDB(tableModel);
    }

    public static void updateTourn(DefaultTableModel tableModel){
        dataBaseHandler.updateTournFromDB(tableModel);
    }
}
