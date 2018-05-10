import java.util.function.Predicate;

/**
 * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 10/05/2018.
 */
public class Level {

    public static final Predicate<Level> IS_VALID = l -> l.id >= 0;

    private int id;

    public int getLevel() {
        return id;
    }

    public void setLevel(final int level) {
        this.id = level;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Level level1 = (Level) o;

        return id == level1.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
