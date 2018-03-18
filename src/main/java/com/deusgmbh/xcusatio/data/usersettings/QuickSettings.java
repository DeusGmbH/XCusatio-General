package com.deusgmbh.xcusatio.data.usersettings;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class QuickSettings {
    private ContextMode contextMode;
    private boolean aggresiv;
    private boolean funny;
    private boolean suckUp; // einschleimen

    public QuickSettings(ContextMode contextMode, boolean aggresiv, boolean funny, boolean suckUp) {
        super();
        this.contextMode = contextMode;
        this.aggresiv = aggresiv;
        this.funny = funny;
        this.suckUp = suckUp;
    }

    public ContextMode getContextMode() {
        return contextMode;
    }

    public void setContextMode(ContextMode contextMode) {
        this.contextMode = contextMode;
    }

    public boolean isAggresiv() {
        return aggresiv;
    }

    public void setAggresiv(boolean aggresiv) {
        this.aggresiv = aggresiv;
    }

    public boolean isFunny() {
        return funny;
    }

    public void setFunny(boolean funny) {
        this.funny = funny;
    }

    public boolean isSuckUp() {
        return suckUp;
    }

    public void setSuckUp(boolean suckUp) {
        this.suckUp = suckUp;
    }

    public enum ContextMode {
        AUTOMATIC, MANUALLY;
    }
}
