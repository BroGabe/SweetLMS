package com.thedev.sweetlms.modules.types;

public class SafetyCheckManager {

    private final LocationManager locationManager;

    private final KitManager kitManager;

    public SafetyCheckManager(LocationManager locationManager, KitManager kitManager) {
        this.locationManager = locationManager;
        this.kitManager = kitManager;
    }

    public boolean validGameSetup() {
        if(locationManager.getLMSLocation() == null || locationManager.getSpawnLocation() == null) return false;

        return kitManager.isValidKit();
    }
}
