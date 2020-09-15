package fr.femtost.disc.glcomp.m1comp6.gui;

import java.io.File;

public class GUIModel {
    private static GUIModel instance;

    private File openedFile;
    private boolean isJJCPaneOpened;

    private GUIModel() {
        // Nothing to do
    }

    public static GUIModel getInstance() {
        if (instance == null) {
            instance = new GUIModel();
        }

        return instance;
    }

    public File getOpenedFile() {
        return openedFile;
    }

    public void setOpenedFile(File openedFile) {
        this.openedFile = openedFile;
    }

    public boolean isJJCPaneOpened() {
        return isJJCPaneOpened;
    }

    public void setJJCPaneOpened(boolean isJJCPaneOpened) {
        this.isJJCPaneOpened = isJJCPaneOpened;
    }

    public void reset() {
        openedFile = null;
    }
}
