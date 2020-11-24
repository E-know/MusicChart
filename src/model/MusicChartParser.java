package model;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.ProgressMonitor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author SejongUniv 오창한
 * @version 1.7
 **/

public abstract class MusicChartParser {

    protected int _songCount = 0;            // 노래 개수를 담당
    protected String _url;                    // 파싱할 웹 사이트 url
    protected JSONArray _chartList;            // 차트 100곡에 대한 정보를 담을 JSONArray, JSONObject 100개로 이루어져있음
    protected JSONObject _songDetailInfo;    // 노래 한 곡의 상세 정보를 담을 JSONObject, 차트 100곡에서는 얻을 수 없는 정보들만 포함함

    // 파싱 오류시 보여줄 문자열들
    protected String _isNotParsed = "파싱이 정상적으로 이루어지지 않았습니다 :(";
    protected String _isOnlyChartParse = "해당 메소드는 차트 파싱에만 사용가능한 메소드 입니다 :(";
    protected String _isOnlyDetailParse = "해당 메소드는 노래 1개의 상세 정보 파싱에만 사용가능한 메소드 입니다 :(";
    protected String _jsonDontHaveKey = "JSONObject 내에 해당 키 값이 없습니다 :(";
    protected String _plzUseRightJSONObject = "올바른 JSONObject 값을 사용해주세요 :(";

    // 차트 100곡을 파싱하는 abstract 메소드, 각 음원 사이트 파서별로 필수로 다르게 구현해야 함
    // parentComponent에 JPanel, JFrame 등을 넣으면 파싱을 하면서 해당 클래스에 로딩창을 띄워줌
    // ProgressMonitor를 사용했으나 이 것을 사용하면 버그가 있어 ProgressMonitor 부분만 주석처리 해두었음, 상세한 버그 내용은 아래에 기술했음
    public abstract void chartDataParsing(Component parentComponent);

    // 노래 한 곡의 상세 정보를 파싱하는 abstract 메소드, 여러 parameter들을 지원함
    // parentComponent에 JPanel, JFrame 등을 넣으면 파싱을 하면서 해당 클래스에 로딩창을 띄워줌
    // ProgressMonitor를 사용했으나 이 것을 사용하면 버그가 있어 ProgressMonitor 부분만 주석처리 해두었음, 상세한 버그 내용은 아래에 기술했음
    //public abstract void songDetailDataParsing(String songId, Component parentComponent);

    public abstract void songDetailDataParsing(JSONObject jObj, Component parentComponent);

    protected Thread _chartThread;                // 차트 100곡을 파싱할 때 사용할 Thread
    protected Thread _songDetailThread;            // 노래 한 곡에 대한 상세 정보를 파싱할 때 사용할 Thread

    /*
     * @deprecated
     * 파싱 중에 로딩창으로 사용할 ProgressMonitor
     * 근데 이걸 사용하면 Thread가 종료되지 않는 오류와 원하는 대로 ProgressMonitor가 나오지 않는 버그가 있어 Deprecated 처리해놓았음
     * ProgressBar 등을 커스텀해서 사용해야 할 듯, 현재 파서에서는 주석처리 해두었음
     */
    @Deprecated
    protected ProgressMonitor progressMonitor;

    public boolean isParsed() { // 파싱이 이루어졌는지 판단하는 메소드
        // chartDataParsing()이나 songDetailDataParsing()을 한 번이라도 호출했으면 songCount는 1 이상임
        if (_songCount == 0)
            return false;
        else
            return true;
    } // boolean isParsed()

    public JSONArray getChartList() { // 차트 100곡에 대한 정보를 담는 JSONArray인 chartList를 반환하는 메소드
        if (!isParsed()) {// 파싱을 한 적이 없으면
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1) { // 노래 한 곡에 대한 상세 파싱을 했다면
            System.out.println("getChartList() : " + _isOnlyChartParse);
            return null;
        }

        return _chartList;
    } // JSONArray getChartList()

    public String getTitle(int rank) { // 노래 순위를 통해 해당 노래의 제목을 반환하는 메소드
        if (rank < 1 || rank > 100) { // 1 <= rank <= 100을 벗어나는 범위라면
            System.out.println("1~100위 이내의 순위를 입력해주세요");
            return null;
        }

        if (!isParsed()) { // 파싱이 이루어지지 않았다면
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1) { // 노래 한 곡에 대한 상세 정보 파싱이 이루어졌다면 null 반환(상세 정보에는 제목이 없기 때문)
            System.out.println("getTitle(int rank) : " + _isOnlyChartParse);
            return null;
        } else
            return ((JSONObject) _chartList.get(rank - 1)).get("title").toString();
    } // String getTitle(int rank)

    public String getArtistName(int rank) { // 노래 순위를 통해 해당 노래의 가수 이름을 반환하는 메소드
        if (rank < 1 || rank > 100) { // 1 <= rank <= 100을 벗어나는 범위라면
            System.out.println("1 ~ 100위 이내의 순위를 입력해주세요");
            return null;
        }

        if (!isParsed()) { // 파싱이 이루어지지 않았다면
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1) { // 노래 한 곡에 대한 상세 파싱이 이루어졌다면
            System.out.println("getArtistName(int rank) : " + _isOnlyChartParse);
            return null;
        }
        return ((JSONObject) _chartList.get(rank - 1)).get("artist").toString();
    } // String getArtistName(int rank)

    public String getAlbumName(int rank) { // 노래 순위를 통해 해당 노래의 앨범 이름을 반환하는 메소드
        if (rank < 1 || rank > 100) { // 1 <= rank <= 100을 벗어나는 범위라면
            System.out.println("1~100위 이내의 순위를 입력해주세요");
            return null;
        }
        if (!isParsed()) { // 파싱이 이루어지지 않았다면
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1) { // 노래 한 곡에 대한 상세 파싱이 이루어졌다면
            System.out.println("getAlbumName(int rank) : " + _isOnlyChartParse);
            return null;
        }

        return ((JSONObject) _chartList.get(rank - 1)).get("albumName").toString();
    } // String getArtistName(int rank)

    public String getSongId(int rank) { // 노래 순위를 통해 해당 노래의 앨범 이름을 반환하는 메소드
        if (rank < 1 || rank > 100) { // 1 <= rank <= 100을 벗어나는 범위라면
            System.out.println("1~100위 이내의 순위를 입력해주세요");
            return null;
        }

        if (!isParsed()) { // 파싱이 이루어지지 않았다면
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1) { // 노래 한 곡에 대한 상세 파싱이 이루어졌다면
            System.out.println("getSongId(int rank) : " + _isOnlyChartParse);
            return null;
        }

        return ((JSONObject) _chartList.get(rank - 1)).get("songId").toString();
    } // String getSongId(int rank)

    public String getImageUrl(int rank) { // 노래 순위를 통해 해당 노래의 이미지 url을 반환하는 메소드
        if (rank < 1 || rank > 100) { // 1 <= rank <= 100을 벗어나는 범위라면
            System.out.println("1~100위 이내의 순위를 입력해주세요");
            return null;
        }

        if (!isParsed()) { // 파싱이 이루어지지 않았다면
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1)
            return _songDetailInfo.get("imageUrl").toString(); // 노래 한 곡에 대한 상세 정보 파싱이 이루어졌다면 그 곡의 큰 이미지 url을 반환(상세 정보는 순위에 상관이 없음)
        else
            return ((JSONObject) _chartList.get(rank - 1)).get("smallImageUrl").toString(); // 차트 100곡에 대한 파싱이 이루어졌다면 JSONArray에 있는 노래들 중 순위에 맞는 원소의 작은 이미지 url을 반환
    } // String getImageUrl(int rank)

    // getGenre(), getGenre(JSONObject jObj)는 MelonChartParser와 GenieChartParser에서만 사용가능하므로 추상클래스에서는 제외됨
    // getReleaseDate(), getReleaseDate(JSONObject jObj)는 MelonChartParser에서만 사용가능하므로 추상클래스에서는 제외됨
} // MusicChartParser class