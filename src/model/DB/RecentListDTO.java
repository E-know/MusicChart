package model.DB;

public class RecentListDTO {
    private String _title;
    private int _siteNum;
    private int _rank;

    public String getTitle(){
        return _title;
    }
    public int getSiteNum(){ return _siteNum; }
    public int getRank(){
        return _rank;
    }

    public void setTitle(String title){
        this._title = title;
    }
    public void setSiteNum(int siteNum){
        this._siteNum = siteNum;
    }
    public void setRank(int rank){
        this._rank = rank;
    }
}