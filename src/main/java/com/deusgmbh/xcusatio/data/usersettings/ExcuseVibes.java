package com.deusgmbh.xcusatio.data.usersettings;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class ExcuseVibes {
    private boolean aggresiv;
    private boolean funny;
    private boolean suckUp; // einschleimen

    public ExcuseVibes(boolean aggresiv, boolean funny, boolean suckUp) {
        super();

        this.aggresiv = aggresiv;
        this.funny = funny;
        this.suckUp = suckUp;
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

}
