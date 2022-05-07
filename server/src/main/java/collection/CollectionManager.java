package collection;

import data.LabWork;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;


public class CollectionManager implements CollectionManagerInterface {

    private ArrayList<data.LabWork> labWorks = new ArrayList<>();
    private final LocalDateTime createdDate = LocalDateTime.now();
    private final HashSet<Integer> idList = new HashSet<>();
    private int highId = 0;


    @Override
    public ArrayList<LabWork> getCollection() {
        return labWorks;
    }

    @Override
    public HashSet<Integer> getIdList() {
        return idList;
    }

    @Override
    public String getInfo() {
        return  "Collection type      : ArrayList\n" +
                "Collection name      : LabWorks\n" +
                "Initialization date  : " + createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n" +
                "Collection size      : " + labWorks.size() + "\n";
    }

    @Override
    public void setCollection(ArrayList<LabWork> labWorks) {
        this.labWorks = labWorks;
    }

    @Override
    public void add(LabWork labWork) {
        int newLabWorkId = labWork.getId();
        if (labWorks.add(labWork)) {
            if (!idList.add(newLabWorkId)){
                labWorks.remove(this.getLabworkById(newLabWorkId));
            }
            if (highId < newLabWorkId) highId = newLabWorkId;
        }
    }

    @Override
    public boolean remove(LabWork labWork) {
        idList.remove(labWork.getId());
        return labWorks.remove(labWork);
    }

    @Override
    public LabWork getLabworkById(int id) {
        return labWorks.stream().filter(labWork -> labWork.getId() == id).findAny().orElse(null);
    }

    @Override
    public void clear(String user) {
        labWorks
                .stream()
                .filter(sg -> sg.getUser().equals(user))
                .forEach(sg -> labWorks.remove(sg));
    }

    @Override
    public LabWork getMax() {
        return labWorks.stream().max(LabWork::compareTo).orElse(null);
    }

    public int getHighId() {return highId;}

    public LabWork getMinPersonalQualitiesMinimum() {
        return labWorks
                .stream()
                .min(Comparator.comparing(LabWork::getPersonalQualitiesMinimum))
                .orElse(null);
    }
}
