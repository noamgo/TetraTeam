package com.example.tetrateam;

public class Constants {
    public static class UserConstants {
        public static final int MIN_PASSWORD_LENGTH = 8;
        public static final int MAX_PASSWORD_LENGTH = 20;
        public static final int MAX_DISPLAY_NAME_LENGTH = 18;
        public static final int MIN_DISPLAY_NAME_LENGTH = 2;
    }

    public static class FirebaseConstants {

        //--- User Constants ---
        public static final String COLLECTION_USER = "Users";
        public static final String KEY_USER_USERNAME = "Username";
        public static final String KEY_USER_EMAIL_ADDRESS = "EmailAddress";
        public static final String KEY_USER_PHONE = "Phone";
        public static final String KEY_USER_PROFILE_IMAGE = "ProfileImage";
        public static final String KEY_USER_FCM_TOKEN = "FCM_Token";

        //--- Firebase Storage ---
        public static final String KEY_USER_PROFILE_IMAGE_STORAGE_REFERENCE = "UserProfileImages";
    }

}
