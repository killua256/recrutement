package com.recrutement.enums;

public enum ApplicationStatus {

    PENDING("Pending"),
    INVITED_FOR_INTERVIEW("Invited for interview"),
    REJECTED("Rejected"),
    HIRED("Hired");

    private final String displayName;

    ApplicationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
