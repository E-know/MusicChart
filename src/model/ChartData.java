package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.awt.*;

public class ChartData {
    private static ChartData s_instance;

    private int Site_M_B_G;
    private MelonChartParser melon;
    private BugsChartParser bugs;
    private GenieChartParser genie;
    private JSONArray[] chartData;
    private ChartData(){
        s_instance = this;
        Site_M_B_G = 1;
        melon = new MelonChartParser();
        bugs = new BugsChartParser();
        genie = new GenieChartParser();
    }
    public void setSite_M_B_G(int M_B_G){
        Site_M_B_G = M_B_G;
    }
    public int getSite_M_B_G() {
        return Site_M_B_G;
    }

    public MelonChartParser getMelonChartParser() {
        return melon;
    }
    public BugsChartParser getBugsChartParser() { return bugs; }
    public GenieChartParser getGenieChartParser() {
        return genie;
    }

    public MusicChartParser getParser() {
        switch (Site_M_B_G) {
            case 1:
                return melon;
            case 2:
                return bugs;
            case 3:
                return genie;
            default:
                return null;
        }
    }

    public void DataPassing(Component parentComponent) {
        System.out.println("Parsing Data");
        getParser().chartDataParsing(parentComponent);
    }

    public static ChartData getS_instance() {
        if(s_instance == null) s_instance = new ChartData();
        return s_instance;
    }
}
