package org.launchcode.adventureland.models;

public class ChangePassword {

    private String newPassword;

    private String oldPassword;

    private String verifyPassword;


    public ChangePassword(String newPassword, String oldPassword, String verifyPassword) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
        this.verifyPassword = verifyPassword;
    }

    public ChangePassword() {
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
}
