package DBConnection;

import BaseClasses.DialogFrame;
import BaseClasses.Main;
import BaseClasses.MyMessages;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DataBaseHandler extends DBConfig {

    protected Connection dbConnection = null;

    public DataBaseHandler(){
        try {
            dbConnection = getDBConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Connection getDBConnection() throws ClassNotFoundException, SQLException
    {
        String connectionString = "jdbc:mysql://" + dbHost + ":" +
                dbPort + "/" + dbName + "?useUnicode=true&serverTimezone=UTC&useSSL=false";

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public ResultSet getFootballersFromDB(){
        ResultSet resultSet = null;
        String query = "SELECT " + Const.FOOTBALLER_TABLE + "." + Const.FOOTBALLER_NAME + "," + Const.FOOTBALLER_TABLE + "." + Const.FOOTBALLER_POSITION + "," + Const.FOOTBALLER_TABLE + "." + Const.FOOTBALLER_NUMBER +
                "," + Const.FOOTBALLER_TABLE + "." + Const.FOOTBALLER_GOALS + "," + Const.FOOTBALLER_TABLE + "." + Const.FOOTBALLER_CLUB + " FROM " + Const.FOOTBALLER_TABLE;
        try {
            PreparedStatement prSt = dbConnection.prepareStatement(query);
            resultSet = prSt.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
            MyMessages.showErrorMessage(Main.mainFrame, "Ошибка", "При чтении списка футболистов произошла ошибка!");
        }

        return resultSet;
    }

    public ResultSet getTeamsFromDB(){
        ResultSet resultSet = null;
        String query = "SELECT " + Const.TEAM_TABLE + "." + Const.TEAM_NAME + ", " + Const.TEAM_TABLE + "." + Const.TEAM_RAITING + ", " + Const.TEAM_TABLE + "." + Const.TEAM_CITY + " FROM " + Const.TEAM_TABLE;

        try {
            PreparedStatement prSt = dbConnection.prepareStatement(query);
            resultSet = prSt.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
            MyMessages.showErrorMessage(Main.mainFrame, "Ошибка", "При чтении списка команд произошла ошибка!");
        }

        return resultSet;
    }

    public ResultSet getMatchesFromDB(){
        ResultSet resultSet = null;
        String query = "SELECT " + Const.MATCH_TABLE + "." + Const.MATCH_HOME + ", " + Const.MATCH_TABLE + "." + Const.MATCH_VISITOR + ", " + Const.MATCH_TABLE + "." + Const.MATCH_SCORE + " FROM " + Const.MATCH_TABLE;

        try {
            PreparedStatement prSt = dbConnection.prepareStatement(query);
            resultSet = prSt.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
            MyMessages.showErrorMessage(Main.mainFrame, "Ошибка", "При чтении списка матчей произошла ошибка!");
        }

        return resultSet;
    }

    public ResultSet getTournTeamFromDB(){
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + Const.TOURN_HAS_TEAM_TABLE;

        try {
            PreparedStatement prSt = dbConnection.prepareStatement(query);
            resultSet = prSt.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
            MyMessages.showErrorMessage(Main.mainFrame, "Ошибка", "При чтении списка турниров и участвующих в них команд произошла ошибка!");
        }

        return resultSet;
    }

    public void writeTeamsToDB(DefaultTableModel tableModel){
        String delete = "DELETE FROM " + Const.TEAM_TABLE;
        String insert = "INSERT INTO " + Const.TEAM_TABLE + "(" + Const.TEAM_NAME + "," + Const.TEAM_RAITING + "," +
                Const.TEAM_CITY + ")" + "VALUES(?, ?, ?)";

        PreparedStatement prSt = null;

        try {
            prSt = dbConnection.prepareStatement(delete);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            prSt = dbConnection.prepareStatement(insert);

            for(int i = 0; i < tableModel.getRowCount(); ++i) {
                prSt.setString(1, tableModel.getValueAt(i, 0).toString());
                prSt.setInt(2, Integer.parseInt(tableModel.getValueAt(i, 1).toString()));
                prSt.setString(3, tableModel.getValueAt(i, 2).toString());

                prSt.executeUpdate();
            }
            MyMessages.showGoodMessage(Main.mainFrame, "Запись", "Данные о командах успешно записаны");
        }
        catch (Exception e){
            MyMessages.showErrorMessage(Main.mainFrame, "Ошибка", "При записи списка команд произошла ошибка!");
        }
    }

    public void writeFootballersToDB(DefaultTableModel tableModel){
        String delete = "DELETE FROM " + Const.FOOTBALLER_TABLE;
        String insert = "INSERT INTO " + Const.FOOTBALLER_TABLE + "(" + Const.FOOTBALLER_NAME + "," + Const.FOOTBALLER_POSITION + "," +
                Const.FOOTBALLER_NUMBER + ", " + Const.FOOTBALLER_GOALS + ", " + Const.FOOTBALLER_CLUB + ")" + "VALUES(?, ?, ?, ?, ?)";

        PreparedStatement prSt = null;

        try {
            prSt = dbConnection.prepareStatement(delete);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            prSt = dbConnection.prepareStatement(insert);

            for(int i = 0; i < tableModel.getRowCount(); ++i) {
                prSt.setString(1, tableModel.getValueAt(i, 0).toString());
                prSt.setString(2, tableModel.getValueAt(i, 1).toString());
                prSt.setInt(3, Integer.parseInt(tableModel.getValueAt(i, 2).toString()));
                prSt.setInt(4, Integer.parseInt(tableModel.getValueAt(i, 3).toString()));
                prSt.setString(5, tableModel.getValueAt(i, 4).toString());

                prSt.executeUpdate();
            }
            MyMessages.showGoodMessage(Main.mainFrame, "Запись", "Данные о футболистах успешно записаны");
        }
        catch (Exception e){
            e.printStackTrace();
            MyMessages.showErrorMessage(Main.mainFrame, "Ошибка", "При записи списка футболистов произошла ошибка!");
        }
    }

    public void writeMatchesToDB(DefaultTableModel tableModel){
        String delete = "DELETE FROM " + Const.MATCH_TABLE;
        String insert = "INSERT INTO " + Const.MATCH_TABLE + "(" + Const.MATCH_HOME + "," + Const.MATCH_VISITOR + "," +
                Const.MATCH_SCORE + ")" + "VALUES(?, ?, ?)";

        PreparedStatement prSt = null;

        try {
            prSt = dbConnection.prepareStatement(delete);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            prSt = dbConnection.prepareStatement(insert);

            for(int i = 0; i < tableModel.getRowCount(); ++i) {
                prSt.setString(1, tableModel.getValueAt(i, 0).toString());
                prSt.setString(2, tableModel.getValueAt(i, 1).toString());
                prSt.setString(3, tableModel.getValueAt(i, 2).toString());

                prSt.executeUpdate();
            }
            MyMessages.showGoodMessage(Main.mainFrame, "Запись", "Данные о матчах успешно записаны");
        }
        catch (Exception e){
            e.printStackTrace();
            MyMessages.showErrorMessage(Main.mainFrame, "Ошибка", "При записи списка матчей произошла ошибка!");
        }
    }

    public void writeTournHasTeamToDB(DefaultTableModel tableModel){
        String delete = "DELETE FROM " + Const.TOURN_HAS_TEAM_TABLE;
        String insert = "INSERT INTO " + Const.TOURN_HAS_TEAM_TABLE + "(" + Const.TOURN_HAS_TEAM_TOURN_NAME + "," + Const.TOURN_HAS_TEAM_TEAM_NAME + ")" + "VALUES(?, ?)";

        PreparedStatement prSt = null;

        try {
            prSt = dbConnection.prepareStatement(delete);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            prSt = dbConnection.prepareStatement(insert);

            for(int i = 0; i < tableModel.getRowCount(); ++i) {
                prSt.setString(1, tableModel.getValueAt(i, 0).toString());
                prSt.setString(2, tableModel.getValueAt(i, 1).toString());

                prSt.executeUpdate();
            }
            MyMessages.showGoodMessage(Main.mainFrame, "Запись", "Данные о турнирах команд успешно записаны");
        }
        catch (Exception e){
            e.printStackTrace();
            MyMessages.showErrorMessage(Main.mainFrame, "Ошибка", "При записи списка турниров и участвующих в них команд произошла ошибка!");
        }
    }

    public void updateFootballerFromDB(DefaultTableModel tableModel){
        String update = "UPDATE `" + Const.FOOTBALLER_TABLE + "` SET `" + Const.FOOTBALLER_NAME + "` = '" + DialogFrame.newData[0] + "', `" + Const.FOOTBALLER_POSITION + "` = '" + DialogFrame.newData[1] + "', `" +
                Const.FOOTBALLER_NUMBER + "` = '" + DialogFrame.newData[2] + "', `" + Const.FOOTBALLER_GOALS + "` = '" + DialogFrame.newData[3] + "', `" + Const.FOOTBALLER_CLUB + "`= '" + DialogFrame.newData[4] +
                "' WHERE ( " + Const.FOOTBALLER_CLUB + " = '" + tableModel.getValueAt(Main.mainFrame.getSelectedRow(), 4).toString() + "' ) and ( " + Const.FOOTBALLER_NUMBER + " = '"
                + tableModel.getValueAt(Main.mainFrame.getSelectedRow(), 2).toString() + "' )";

        try{
            PreparedStatement prSt = dbConnection.prepareStatement(update);
            prSt.executeUpdate();
            MyMessages.showGoodMessage(Main.mainFrame, "Обновление", "Данная запись о футболисте была успешно обновлена в таблице и БД");
        }
        catch (Exception e){
            e.printStackTrace();
            MyMessages.showErrorMessage(Main.mainFrame, "Ошибка", "При обновлении записи о футболисте произошла ошибка!");
        }
    }

    public void updateTeamFromDB(DefaultTableModel tableModel){
        String update = "UPDATE `" + Const.TEAM_TABLE + "` SET `" + Const.TEAM_NAME + "` = '" + DialogFrame.newData[0] + "', `" + Const.TEAM_RAITING + "` = '" + DialogFrame.newData[1] + "', `" +
                Const.TEAM_CITY + "` = '" + DialogFrame.newData[2] + "' WHERE ( " + Const.TEAM_NAME + " = '" + tableModel.getValueAt(Main.mainFrame.getSelectedRow(), 0).toString() + "' )";

        try{
            PreparedStatement prSt = dbConnection.prepareStatement(update);
            prSt.executeUpdate();
            MyMessages.showGoodMessage(Main.mainFrame, "Обновление", "Данная запись о команде была успешно обновлена в таблице и БД");
        }
        catch (Exception e){
            e.printStackTrace();
            MyMessages.showErrorMessage(Main.mainFrame, "Ошибка", "При обновлении записи о команде произошла ошибка!");
        }
    }

    public void updateMatchFromDB(DefaultTableModel tableModel) {
        String update = "UPDATE `" + Const.MATCH_TABLE + "` SET `" + Const.MATCH_HOME + "` = '" + DialogFrame.newData[0] + "', `" + Const.MATCH_VISITOR + "` = '" + DialogFrame.newData[1] + "', `" +
                Const.MATCH_SCORE + "` = '" + DialogFrame.newData[2] + "' WHERE ( " + Const.MATCH_HOME + " = '" + tableModel.getValueAt(Main.mainFrame.getSelectedRow(), 0).toString() +
                "' ) and ( " + Const.MATCH_VISITOR + " = '" + tableModel.getValueAt(Main.mainFrame.getSelectedRow(), 1).toString() + "' )";

        try {
            PreparedStatement prSt = dbConnection.prepareStatement(update);
            prSt.executeUpdate();
            MyMessages.showGoodMessage(Main.mainFrame, "Обновление", "Данная запись о матче была успешно обновлена в таблице и БД");
        } catch (Exception e) {
            e.printStackTrace();
            MyMessages.showErrorMessage(Main.mainFrame, "Ошибка", "При обновлении записи о матче произошла ошибка!");
        }
    }

    public void updateTournFromDB(DefaultTableModel tableModel) {
        String update = "UPDATE `" + Const.TOURN_HAS_TEAM_TABLE + "` SET `" + Const.TOURN_HAS_TEAM_TOURN_NAME + "` = '" + DialogFrame.newData[0] + "', `" + Const.TOURN_HAS_TEAM_TEAM_NAME + "` = '" +
                DialogFrame.newData[1] + "' WHERE ( `" + Const.TOURN_HAS_TEAM_TOURN_NAME + "` = '" + tableModel.getValueAt(Main.mainFrame.getSelectedRow(), 0).toString() +
                "' ) and ( `" + Const.TOURN_HAS_TEAM_TEAM_NAME + "` = '" + tableModel.getValueAt(Main.mainFrame.getSelectedRow(), 1).toString() + "' )";

        try {
            PreparedStatement prSt = dbConnection.prepareStatement(update);
            prSt.executeUpdate();
            MyMessages.showGoodMessage(Main.mainFrame, "Обновление", "Данная запись об участнике турнира была успешно обновлена в таблице и БД");
        } catch (Exception e) {
            e.printStackTrace();
            MyMessages.showErrorMessage(Main.mainFrame, "Ошибка", "При обновлении записи об участнике турнира произошла ошибка!");
        }
    }



}
