package model;

public class SiteMBG {
    public int _type;
    static final int MELON = 1;
    static final int BUGS = 2;
    static final int GENIE = 3;

    public void setSite_M_B_G(int type){
        _type = type;
    }
    public int getSite_M_B_G() {
        return _type;
    }
}
