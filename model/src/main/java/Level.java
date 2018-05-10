/**
 * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 10/05/2018.
 */
public class Level {

    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
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

        return level == level1.level;
    }

    @Override
    public int hashCode() {
        return level;
    }
}
