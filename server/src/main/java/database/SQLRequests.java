package database;

public enum SQLRequests {
    addLabWork("INSERT INTO ss335153LabWorks " +
            "(id, name, xCoordinate, yCoordinate, minimalPoint, personalQualitiesMinimum, difficulty, " +
            "authorName, authorHeight, authorHairColor, authorEyeColor, authorNationality, username) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),

    generateId("SELECT nextval('ids')"),

    addUserWithPassword("INSERT INTO s335153users (login, hashPassword) VALUES(?, ?)"),

    checkUser("SELECT * FROM s335153users WHERE login=? AND hashPassword=?"),

    updateLabWork("UPDATE s335153LabWorks SET " +
            "name=?, xCoordinate=?, yCoordinate=?, minimalPoint=?, personalQualitiesMinimum=?, difficulty=?, " +
            "authorName=?, authorHeight=?, authorHairColor=?, authorEyeColor=?, authorNationality=? " +
            "WHERE id = ?"),

    getById("SELECT * FROM s335153LabWorks WHERE id = ?"),

    getByDifficulty("SELECT * FROM s335153LabWorks WHERE difficulty = ?"),

    deleteById("DELETE FROM s335153LabWorks WHERE id = ?"),

    deleteByDifficulty("DELETE FROM s335153LabWorks WHERE difficulty = ?"),

    clearAllByUser("DELETE FROM s335153LabWorks WHERE username = ?"),

    takeAll("SELECT * FROM s335153LabWorks");

    private final String statement;

    SQLRequests(String aStatement) {
        statement = aStatement;
    }

    public String getStatement() {
        return statement;
    }
}