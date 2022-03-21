package com.aam.awsimageupload.profile;

import com.aam.awsimageupload.bucket.BucketName;
import com.aam.awsimageupload.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;


    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore, FileStore fileStore1) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore1;
    }

    List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        isFileEmpty(file);

        isImage(file);

        UserProfile currentUser = getUserProfileOrThrow(userProfileId);

        Map<String, String> metadata = extractMetadata(file);

        String path = getPath(userProfileId);
        String filename = setFileName(file);
        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            currentUser.setUserProfileImageLink(filename);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] downloadUserProfileImage(UUID userProfileId) {
        UserProfile userProfile = getUserProfileOrThrow(userProfileId);

        return userProfile.getUserProfileImageLink()
                .map(imageLink -> fileStore.download(getPath(userProfileId), imageLink))
                .orElse(new byte[0]);
    }


    public long saveUserProfile(UserProfile userProfile) {
        return userProfileDataAccessService.saveUserProfile(userProfile) ;
    }

    private String setFileName(MultipartFile file) {
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        return filename;
    }

    private String getPath(UUID userProfileId) {
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), userProfileId);
        return path;
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private UserProfile getUserProfileOrThrow(UUID userProfileId) {
        return userProfileDataAccessService
                .getUserProfileByUserProfileId(userProfileId)
                .orElseThrow(() -> new IllegalStateException(String.format("User %s does not exist in the database", userProfileId)));
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(),
                        ContentType.IMAGE_PNG.getMimeType(),
                        ContentType.IMAGE_GIF.getMimeType())
                .contains(file.getContentType())) {
            throw new IllegalStateException("File is not an image : [" + file.getContentType() + "] !");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("File is empty !");
        }
    }

}
