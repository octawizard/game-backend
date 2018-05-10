package com.robertomanca.game.model;

import java.util.function.Predicate;

import static com.robertomanca.game.util.Integers.IS_NOT_NEGATIVE;

/**
 * Created by Roberto Manca on 10/05/2018.
 */
public class User {

    public static final Predicate<User> IS_VALID = u -> IS_NOT_NEGATIVE.test(u.getUserId());

    private int userId;
    private String name;
    private String email;

    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final User user = (User) o;

        if (userId != user.userId) {
            return false;
        }
        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }
        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
