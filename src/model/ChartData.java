package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class ChartData{
    private static ChartData s_instance;

    private int _siteMBG;
    private MelonChartParser melon;
    private BugsChartParser bugs;
    private GenieChartParser genie;
    private JSONArray[] chartData;

    private ChartData() {
        s_instance = this;
        _siteMBG=1;
        melon = new MelonChartParser();
        bugs = new BugsChartParser();
        genie = new GenieChartParser();
    }
    public void setSiteMBG(int type){
        _siteMBG = type;
    }
    public int getSiteMBG() {
        return _siteMBG;
    }
    public MelonChartParser getMelonChartParser() {
        return melon;
    }

    public BugsChartParser getBugsChartParser() {
        return bugs;
    }

    public GenieChartParser getGenieChartParser() {
        return genie;
    }

    public MusicChartParser getParser() {
        switch (_siteMBG) {
            case SITE.MELON:
                return melon;
            case SITE.BUGS:
                return bugs;
            case SITE.GENIE:
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
        if (s_instance == null) s_instance = new ChartData();
        return s_instance;
    }
}
