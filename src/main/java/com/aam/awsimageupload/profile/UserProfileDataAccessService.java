package com.aam.awsimageupload.profile;

import com.aam.awsimageupload.dao.UserProfileRepository;
import com.aam.awsimageupload.datastore.FakeUserProfileDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserProfileDataAccessService {

    private final FakeUserProfileDataStore fakeUserProfileDataStore;
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileDataAccessService(FakeUserProfileDataStore fakeUserProfileDataStore) {
        this.fakeUserProfileDataStore = fakeUserProfileDataStore;
    }

    /*
    public List<UserProfile> getUserProfiles() {
        return fakeUserProfileDataStore.getUserProfiles();
    }
     */

    public List<UserProfile> getUserProfiles() {
        return userProfileRepository.findAll();
    }

    /*
    public Optional<UserProfile> getUserProfileByUserProfileId(UUID userProfileId) {
        return fakeUserProfileDataStore.getUserProfileByUserProfileId(userProfileId);
    }
    */

    public Optional<UserProfile> getUserProfileByUserProfileId(UUID userProfileId) {
        return null;
    }

    public long saveUserProfile(UserProfile userProfile) {
        try {
            userProfile.setUserProfileId(UUID.randomUUID());
            return userProfileRepository.save(userProfile).getId();
        }
        catch (Exception e) {
            throw new IllegalStateException(String.format("Error when saving the user: %s ", userProfile), e);
        }
    }
}
