package com.example.ilove.rental;

import android.app.Application;

import com.google.firebase.database.Query;

public class MyGlobals extends Application {

    private String mode = "Rental"; //default value toggles between actual data and test data
    private Query searchHistory;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Query getSearchHistory() {
        return searchHistory;
    }

    public void setSearchHistory(Query searchHistory) {
        this.searchHistory = searchHistory;
    }
}
