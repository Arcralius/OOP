package com.arcralius.ff.lwjgl3.input_output;

public class DisplayManager {
    private String resolutionScreen;
    private String currentScreen;

    public DisplayManager() {
        this.resolutionScreen = "1920x1080";  // Default resolution
        this.currentScreen = "MainMenuScreen";  // Default starting screen
    }

    public void initializeDisplay() {
        System.out.println("Initializing display with resolution: " + resolutionScreen);
    }

    public void setCurrentScreen(String screen) {
        if (!this.currentScreen.equals(screen)) {
            System.out.println("Switching from " + this.currentScreen + " to " + screen);
        }
        this.currentScreen = screen;
    }

    public String getCurrentScreen() {
        return currentScreen;
    }

    public void setResolution(String resolution) {
        if (!this.resolutionScreen.equals(resolution)) {
            System.out.println("Resolution changed from " + this.resolutionScreen + " to " + resolution);
        }
        this.resolutionScreen = resolution;
    }

    public String getResolution() {
        return resolutionScreen;
    }

    public void renderFrame() {
//        System.out.println("Rendering frame on screen: " + currentScreen + " at resolution " + resolutionScreen);
    }
}
