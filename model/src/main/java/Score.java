/**
 * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 10/05/2018.
 */
public class Score {

    private User user;
    private Level level;

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(final Level level) {
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

        final Score score = (Score) o;

        if (user != null ? !user.equals(score.user) : score.user != null) {
            return false;
        }
        return level != null ? level.equals(score.level) : score.level == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }
}
