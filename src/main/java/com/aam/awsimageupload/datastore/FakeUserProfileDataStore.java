package com.aam.awsimageupload.datastore;

import com.aam.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private final static List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("fc44e377-43b5-4f5f-9829-4dad9e8943d9"), "JohnJones", null));

        USER_PROFILES.add(new UserProfile(UUID.fromString("0e0c2006-9654-4bfd-8b34-97e171dae4a4"), "BarryBlack", null));
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }

    public Optional<UserProfile> getUserProfileByUserProfileId(UUID userProfileId) {
        return USER_PROFILES.stream().filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId)).findFirst();
    }
}
