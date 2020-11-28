package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class ChartData extends SiteMBG{
    private static ChartData s_instance;

    //private int Site_M_B_G;
    private MelonChartParser melon;
    private BugsChartParser bugs;
    private GenieChartParser genie;
    private JSONArray[] chartData;

    private ChartData() {
        s_instance = this;
        setSite_M_B_G(1);
        melon = new MelonChartParser();
        bugs = new BugsChartParser();
        genie = new GenieChartParser();
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
        switch (_type) {
            case MELON:
                return melon;
            case BUGS:
                return bugs;
            case GENIE:
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
