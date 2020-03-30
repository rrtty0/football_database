package DBConnection;

public class Const {
    public static final String FOOTBALLER_TABLE = "footballer";
    //public static final String FOOTBALLER_ID = "idfootballer";
    public static final String FOOTBALLER_NAME = "name";
    public static final String FOOTBALLER_NUMBER = "number";
    public static final String FOOTBALLER_POSITION = "position";
    public static final String FOOTBALLER_GOALS = "goals";
    public static final String FOOTBALLER_CLUB = "footbalclub";

    public static final String TEAM_TABLE = "team";
    //public static final String TEAM_ID = "idteam";
    public static final String TEAM_NAME = "name";
    public static final String TEAM_RAITING = "raiting";
    public static final String TEAM_CITY = "city";

    public static final String MATCH_TABLE = "matches";
    //public static final String MATCH_ID = "idmatch";
    public static final String MATCH_HOME = "home";
    public static final String MATCH_VISITOR = "visitor";
    public static final String MATCH_SCORE = "score";

    public static final String TOURN_TABLE = "tournament";
    //public static final String TOURN_ID = "idtournament";
    public static final String TOURN_NAME = "name";
    public static final String TOURN_PRESTIGE = "prestige";
    public static final String TOURN_SUM = "sumtowinner";

    public static final String TOURN_HAS_TEAM_TABLE = "tournament_has_team";
    //public static final String TOURN_HAS_TEAM_TEAM_IDTEAM = "team_idteam";
    public static final String TOURN_HAS_TEAM_TOURN_NAME = "tournament_name";
    public static final String TOURN_HAS_TEAM_TEAM_NAME = "team_name";
}
