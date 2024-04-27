package com.games.fun.fun_games.dto;

/**
 * A data transfer object (DTO) class representing password information.
 */
public class PasswordDto {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    /**
     * Constructs an empty PasswordDto object.
     */
    public PasswordDto() {
    }

    /**
     * Constructs a PasswordDto object with the specified password information.
     *
     * @param oldPassword     the old password
     * @param newPassword     the new password
     * @param confirmPassword the confirmation of the new password
     */
    public PasswordDto(String oldPassword, String newPassword, String confirmPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    /**
     * Returns the old password.
     *
     * @return the old password
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Sets the old password.
     *
     * @param oldPassword the old password to set
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * Returns the new password.
     *
     * @return the new password
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets the new password.
     *
     * @param newPassword the new password to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Returns the confirmation of the new password.
     *
     * @return the confirmation of the new password
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Sets the confirmation of the new password.
     *
     * @param confirmPassword the confirmation of the new password to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
