package com.arcralius.ff.lwjgl3.input_output;

public class DisplayManager {
    private String resolutionScreen;
    private String currentScreen;

    public DisplayManager() {
        this.resolutionScreen = "1920x1080";  // Default resolution
        this.currentScreen = "MainMenuScreen";  // Default starting screen
    }

    public void renderFrame() {
        System.out.println("Rendering frame on screen: " + currentScreen);
    }

    public void initializeDisplay() {
        System.out.println("Initializing display with resolution: " + resolutionScreen);
    }

    public void setCurrentScreen(String screen) {
        this.currentScreen = screen;
    }

    public String getCurrentScreen() {
        return currentScreen;
    }
}
