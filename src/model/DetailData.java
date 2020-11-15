package model;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.awt.*;
public class DetailData {
    private static DetailData s_instance;

    private MelonChartParser detailMelon;
    private BugsChartParser detailBugs;
    private GenieChartParser detailGenie;

    private DetailData(){
        s_instance = this;

        detailMelon = null;
        detailBugs = null;
        detailGenie = null;
    }
    public void detailDataPassing(int rank, JSONArray chartListData, Component parentComponent){
        System.out.println("Parsing Detail Data");
        switch(ChartData.getS_instance().getSite_M_B_G()){
            case 1:
                if(detailMelon == null) detailMelon = new MelonChartParser();
                detailMelon.songDetailDataParsing(((JSONObject) chartListData.get(rank - 1)), parentComponent);
                //Parsing Method
                break;
            case 2:
                if(detailBugs == null)
                    detailBugs = new BugsChartParser();
                detailBugs.songDetailDataParsing(((JSONObject) chartListData.get(rank - 1)), parentComponent);
                System.out.println("Bugs Detail Parse");
                //Parsing Method
                break;
            case 3:
                if(detailGenie == null)
                    detailGenie = new GenieChartParser();
                detailGenie.songDetailDataParsing(((JSONObject) chartListData.get(rank - 1)), parentComponent);
                System.out.println("Genie Detail Parse");
                //Parsing Method
                break;
        }
    }

    public MusicChartParser getDetailParser() {
        System.out.println("Parsing Detail Data");
        switch (ChartData.getS_instance().getSite_M_B_G()) {
            case 1:
                if (detailMelon == null) {
                    System.out.println("Parser is not exist");
                    break;
                }
                return detailMelon;
            case 2:
                if (detailBugs == null) {
                    System.out.println("Parser is not exist");
                    //return Parser
                    break;
                }
                return detailBugs;
            case 3:
                if (detailGenie == null) {
                    System.out.println("Parser is not exist");
                    //return Parser
                    break;
                }
                return detailGenie;
        }
        return null;
    }
    public static DetailData getS_instance() {
        if(s_instance == null) s_instance = new DetailData();
        return s_instance;
    }
}
