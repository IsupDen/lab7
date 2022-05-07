package util;

import collection.CollectionManager;
import data.*;
import database.DBWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Logger;


public class Receiver {

    final Logger logger = Logger.getLogger(Receiver.class.getCanonicalName());
    private final CollectionManager collectionManager;
    private final DBWorker dbWorker;

    public Receiver(CollectionManager aCollectionManager, DBWorker aDBWorker) {
        collectionManager = aCollectionManager;
        dbWorker = aDBWorker;
        getCollection();
    }

    private void getCollection() {
        try {
            ResultSet data = dbWorker.getCollection();
            while (data.next()) {
                collectionManager.add(new LabWork(
                        data.getInt(1),
                        data.getString(2),
                        new Coordinates(data.getInt(3), data.getInt(4)),
                        LocalDateTime.of(data.getDate(5).toLocalDate(), data.getTime(6).toLocalTime()),
                        data.getDouble(7),
                        data.getInt(8),
                        data.getString(9) != null ? Difficulty.valueOf(data.getString(9)) : null,
                        new Person(data.getString(10),
                                data.getDouble(11),
                                HairColor.valueOf(data.getString(12)),
                                EyeColor.valueOf(data.getString(13)),
                                Country.valueOf(data.getString(14))),
                        data.getString(15)));
            }
        } catch (SQLException ignored) {

        } catch (NullPointerException e) {
            logger.warning("Collection is empty!");
        }
    }

    public String info() {
        return collectionManager.getInfo();
    }

    public ArrayList<LabWork> show() {
        if (collectionManager.getCollection().isEmpty()) return null;
        else return collectionManager.getCollection();
    }

    public TypeOfAnswer add(LabWork labWork) {
        Integer id = dbWorker.addLabWork(labWork);

        if (id != 0) {
            labWork.setId(id);
            collectionManager.add(labWork);
            return TypeOfAnswer.SUCCESSFUL;
        } else {
            return TypeOfAnswer.DUPLICATESDETECTED;
        }
    }

    public TypeOfAnswer updateId(LabWork updatedLabWork, int id) {
        TypeOfAnswer status = dbWorker.updateById(updatedLabWork, id);

        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
            LabWork labWork = collectionManager.getLabworkById(id);
            collectionManager.remove(labWork);
            updatedLabWork.setId(id);
            collectionManager.add(updatedLabWork);
            return TypeOfAnswer.SUCCESSFUL;
        } else return status;
    }

    public TypeOfAnswer removeById(String user, int id) {
        TypeOfAnswer status = dbWorker.removeById(id, user);

        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
            LabWork labWork = collectionManager.getLabworkById(id);
            collectionManager.remove(labWork);
            return TypeOfAnswer.SUCCESSFUL;
        } else return status;
    }

    public TypeOfAnswer clear(String user) {
        TypeOfAnswer status = dbWorker.clear(user);
        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
            collectionManager.clear(user);
            return TypeOfAnswer.SUCCESSFUL;
        } else return status;
    }

    public TypeOfAnswer addIfMax(LabWork labWork) {
        if (collectionManager.getMax() != null && labWork.compareTo(collectionManager.getMax()) >= 0)
            return add(labWork);
        else return TypeOfAnswer.ISNTMAX;
    }

    public Long countLessThanPersonalQualitiesMinimum(Integer count) {

        if (collectionManager.getCollection().isEmpty()) return null;
        return collectionManager.getCollection()
                .stream()
                .filter(labWork -> labWork.getPersonalQualitiesMinimum() < count)
                .count();
    }

    public TypeOfAnswer removeByDifficulty(String difficulty, String user) {
        collectionManager.getCollection().
                removeIf(labWork -> dbWorker.removeByDifficulty(difficulty, user) == TypeOfAnswer.SUCCESSFUL);
        return TypeOfAnswer.SUCCESSFUL;
    }

    public boolean registerUser(String user, String password) {
        return dbWorker.addUser(user, password);
    }

    public boolean loginUser(String user, String password) {
        return dbWorker.loginUser(user, password);
    }
}
