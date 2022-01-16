package edu.byu.cs.tweeter.model.domain;


import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a follow relationship.
 */
public class Follow implements Serializable {
    /**
     * The user doing the following.
     */
    public String followerAlias;
    /**
     * The user being followed.
     */
    public String followeeAlias;

    public Follow() {
    }

    public Follow(String followerAlias, String followeeAlias) {
        this.followerAlias = followerAlias;
        this.followeeAlias = followeeAlias;
    }

    public String getFollowerAlias() {
        return followerAlias;
    }

    public String getFolloweeAlias() {
        return followeeAlias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow that = (Follow) o;
        return followerAlias.equals(that.followerAlias) &&
                followeeAlias.equals(that.followeeAlias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerAlias, followeeAlias);
    }

    @Override
    public String toString() {
        return "Follow{" +
                "follower=" + followerAlias +
                ", followee=" + followeeAlias +
                '}';
    }
}
