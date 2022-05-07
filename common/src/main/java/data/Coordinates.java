package data;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates  implements Serializable {

    private final Integer x;
    private final int y;


    public Coordinates(Integer x, int y) {
        this.x = x;
        this.y = y;
    }


    public Integer getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

    public String toCsv() {
        return x + "," + y;
    }

    @Override
    public boolean equals(Object obj) {
        Coordinates cord = (Coordinates) obj;
        return Objects.equals(x, cord.getX()) && y == cord.getY();
    }
}
