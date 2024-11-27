package com.example.kiteapp.Modelo;

public class localUserData {
    private String userId;
    private byte[] profilePic;
    private String settings;

    public localUserData(String userId, byte[] profilePic, String settings) {
        this.userId = userId;
        this.profilePic = profilePic;
        this.settings = settings;
    }

    public String getUserId() {
        return userId;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public String getSettings() {
        return settings;
    }
}