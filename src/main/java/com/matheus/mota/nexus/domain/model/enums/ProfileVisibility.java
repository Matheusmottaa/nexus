package com.matheus.mota.nexus.domain.model.enums;

public enum ProfileVisibility {
    PUBLIC("PUBLIC"),
    PRIVATE("PRIVATE");

    private String visibility;

    ProfileVisibility(String visibility) {
        this.visibility = visibility;
    }
}
