package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Represents a status (or tweet) posted by a user.
 */
public class Status implements Serializable {
    /**
     * Text for the status.
     */
    public String post;
    /**
     * User who sent the status.
     */
    public User user;
    public String userAlias;
    /**
     * String representation of the date/time at which the status was sent.
     */
    public String datetime;
    /**
     * URLs contained in the post text.
     */
    public List<String> urls;
    /**
     * User mentions contained in the post text.
     */
    public List<String> mentions;

    public Status() {
    }

    public Status(String post, String userAlias, String datetime, List<String> urls, List<String> mentions) {
        this.post = post;
        this.userAlias = userAlias;
        this.datetime = datetime;
        this.urls = urls;
        this.mentions = mentions;
    }

    public Status(String post, User user, String datetime, List<String> urls, List<String> mentions) {
        this.post = post;
        this.user = user;
        this.userAlias = user.getAlias();
        this.datetime = datetime;
        this.urls = urls;
        this.mentions = mentions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public String getDate() {
        return datetime;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getPost() {
        return post;
    }

    public List<String> getUrls() {
        return urls;
    }

    public List<String> getMentions() {
        return mentions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(post, status.post) &&
                Objects.equals(userAlias, status.userAlias) &&
                Objects.equals(datetime, status.datetime) &&
                Objects.equals(mentions, status.mentions) &&
                Objects.equals(urls, status.urls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, userAlias, datetime, mentions, urls);
    }

    @Override
    public String toString() {
        return "Status{" +
                "post='" + post + '\'' +
                ", user=" + userAlias +
                ", datetime=" + datetime +
                ", mentions=" + mentions +
                ", urls=" + urls +
                '}';
    }

}