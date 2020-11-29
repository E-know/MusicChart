package model.DB;

public class RecentListDTO {
    private String title;
    private int siteNum;
    private int rank;

    public String getTitle(){
        return title;
    }
    public int getSiteNum(){
        return siteNum;
    }
    public int getRank(){
        return rank;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setSiteNum(int siteNum){
        this.siteNum = siteNum;
    }
    public void setRank(int rank){
        this.rank = rank;
    }
}