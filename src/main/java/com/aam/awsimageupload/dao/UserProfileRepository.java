package com.aam.awsimageupload.dao;

import com.aam.awsimageupload.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
