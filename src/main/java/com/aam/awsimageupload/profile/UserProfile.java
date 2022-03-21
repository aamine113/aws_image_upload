package com.aam.awsimageupload.profile;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Table(name = "userprofile")
@Entity(name = "UserProfile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_profile_id",
            nullable = false)
    private UUID userProfileId;

    @Column(name = "user_name",
            nullable = false)
    private String userName;

    @Column(name = "user_profile_image_link")
    private String userProfileImageLink;

    public UserProfile() {};

    public UserProfile(UUID userProfileId, String userName, String userProfileImageLink) {
        this.userProfileId = userProfileId;
        this.userName = userName;
        this.userProfileImageLink = userProfileImageLink;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUserProfileId() {
        return userProfileId;
    }

    public String getUserName() {
        return userName;
    }

    public Optional<String> getUserProfileImageLink() {
        return Optional.ofNullable(userProfileImageLink);
    }

    public void setUserProfileId(UUID userProfileId) {
        this.userProfileId = userProfileId;
    }

    public void setUserProfileImageLink(String userProfileImageLink) {
        this.userProfileImageLink = userProfileImageLink;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return id == that.id && userProfileId.equals(that.userProfileId) && userName.equals(that.userName) && userProfileImageLink.equals(that.userProfileImageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userProfileId, userName, userProfileImageLink);
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", userProfileId=" + userProfileId +
                ", userName='" + userName + '\'' +
                ", userProfileImageLink='" + userProfileImageLink + '\'' +
                '}';
    }
}
