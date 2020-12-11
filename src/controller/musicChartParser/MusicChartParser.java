package controller.musicChartParser;

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
    protected String _songDetailParsingTitle = "상세 정보 파싱중..";
    protected String _songDetailParsingMessage = "해당 노래에 대한 상세 정보를 파싱하는 중입니다 :)";

    // 차트 100곡을 파싱하는 abstract 메소드, 각 음원 사이트 파서별로 필수로 다르게 구현해야 함
    // parentComponent에 JPanel, JFrame 등을 넣으면 파싱을 하면서 해당 클래스에 로딩창을 띄워줌
    // ProgressMonitor를 사용했으나 이 것을 사용하면 버그가 있어 ProgressMonitor 부분만 주석처리 해두었음, 상세한 버그 내용은 아래에 기술했음

    public abstract void chartDataParsing(Component parentComponent);

    // 노래 한 곡의 상세 정보를 파싱하는 abstract 메소드, 여러 parameter들을 지원함
    // parentComponent에 JPanel, JFrame 등을 넣으면 파싱을 하면서 해당 클래스에 로딩창을 띄워줌
    // ProgressMonitor를 사용했으나 이 것을 사용하면 버그가 있어 ProgressMonitor 부분만 주석처리 해두었음, 상세한 버그 내용은 아래에 기술했음
    public abstract void songDetailDataParsing(int rank, JSONArray chartListData, Component parentComponent);
    public abstract void songDetailDataParsing(JSONObject jObj, Component parentComponent);
    abstract void detailDataparsing(JSONObject jObj, Component parentComponent);
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
        if(!isSongDetailParsed()){
            return null;
        }

        return _chartList;
    } // JSONArray getChartList()

    public String getAlbumID(int rank){
        int _rank = rank;
        if(!isRanked(_rank)){
            return null;
        }

        if(!isSongDetailParsed()){
            return null;
        }

        return ((JSONObject) _chartList.get(rank - 1)).get("albumID").toString();
    }
    public String getTitle(int rank) { // 노래 순위를 통해 해당 노래의 제목을 반환하는 메소드
        int _rank = rank;
        if(!isRanked(_rank)){
            return null;
        }

        if(!isSongDetailParsed()){
            return null;
        }

        return ((JSONObject) _chartList.get(rank - 1)).get("title").toString();
    } // String getTitle(int rank)

    public String getTitle(JSONObject jObj) { // JSONArray의 원소 중 하나를 이용하여 해당 노래의 제목을 반환하는 메소드

        if(!isSongDetailParsed()){
            return null;
        }


        JSONObject _jobj = jObj;
        if(isJSONObject("title",_jobj) != null){
            return isJSONObject("title",_jobj);
        }
        return null;
    } // String getTitle(JSONObject jObj)

    public String getArtistName(int rank) { // 노래 순위를 통해 해당 노래의 가수 이름을 반환하는 메소드
        int _rank = rank;
        if(!isRanked(_rank)){
            return null;
        }

        if(!isSongDetailParsed()){
            return null;
        }
        return ((JSONObject) _chartList.get(rank - 1)).get("artist").toString();
    } // String getArtistName(int rank)

    public String getArtistName(String title) { // 노래 제목을 통해 해당 노래의 가수 이름을 반환하는 메소드

        if(!isSongDetailParsed()){
            return null;
        }
        String _title = title;
        if(foundByTitle(_title, "artist")!=null){
            return foundByTitle(_title, "artist");
        }
        return null;
    } // String getArtistName(String title)

    public String getArtistName(JSONObject jObj) { // JSONArray의 원소 중 하나를 이용하여 해당 노래의 가수 이름을 반환하는 메소드
        if(!isSongDetailParsed()){
            return null;
        }

        JSONObject _jobj = jObj;
        if(isJSONObject("artist",_jobj) != null){
            return isJSONObject("artist",_jobj);
        }
        return null;
    } // String getArtistName(JSONObject jObj)

    public String getAlbumName(int rank) { // 노래 순위를 통해 해당 노래의 앨범 이름을 반환하는 메소드
        int _rank = rank;
        if(!isRanked(_rank)){
            return null;
        }

        if(!isSongDetailParsed()){
            return null;
        }

        return ((JSONObject) _chartList.get(rank - 1)).get("albumName").toString();
    } // String getArtistName(int rank)

    public String getAlbumName(String title) { // 노래 제목을 통해 해당 노래의 앨범 이름을 반환하는 메소드
        if(!isSongDetailParsed()){
            return null;
        }
        String _title = title;
        if(foundByTitle(_title, "albumName")!=null){
            return foundByTitle(_title, "albumName");
        }
        return null;
    } // String getAlbumName(String title)

    public String getAlbumName(JSONObject jObj) { // JSONArray의 원소 중 하나를 이용하여 해당 노래의 앨범 이름을 반환하는 메소드
        if(!isSongDetailParsed()){
            return null;
        }

        JSONObject _jobj = jObj;
        if(isJSONObject("albumName",_jobj) != null){
            return isJSONObject("albumName",_jobj);
        }
        return null;
    } // String getAlbumName(JSONObject jObj)

    public String getSongId(int rank) { // 노래 순위를 통해 해당 노래의 앨범 이름을 반환하는 메소드
        int _rank = rank;
        if(!isRanked(_rank)){
            return null;
        }

        if(!isSongDetailParsed()){
            return null;
        }

        return ((JSONObject) _chartList.get(rank - 1)).get("songId").toString();
    } // String getSongId(int rank)

    public String getSongId(String title) { // 노래 제목을 통해 해당 노래의 노래 아이디를 반환하는 메소드, 노래 아이디는 상세 페이지 url을 얻을 때 사용 됨
        if(!isSongDetailParsed()){
            return null;
        }
        String _title = title;
        if(foundByTitle(_title, "songId")!=null){
            return foundByTitle(_title, "songId");
        }
        return null;
    } // String getSongId(String title)

    public String getSongId(JSONObject jObj) { // JSONArray의 원소 중 하나를 이용하여 해당 노래의 앨범 이름을 반환하는 메소드
        if(!isSongDetailParsed()){
            return null;
        }
        JSONObject _jobj = jObj;
        if(isJSONObject("songId",_jobj) != null){
            return isJSONObject("songId",_jobj);
        }
        return null;
    } // String getSongId(JSONObject jObj)

    // getLikeNum()은 BugsChartParser와 GenieChartParser에서만 사용가능하므로 추상클래스에서는 제외됨
    // getLikeNum(int rank), getLikeNum(String title)는 MelonChartParser에서만 사용가능하므로 추상클래스에서는 제외됨

    public String getLikeNum(JSONObject jObj) { // JSONArray의 원소 중 하나를 이용하여 해당 노래의 좋아요 개수를 반환하는 메소드
        if(!isSongDetailParsed()){
            return null;
        }

        JSONObject _jobj = jObj;
        if(isJSONObject("likeNum",_jobj) != null){
            return isJSONObject("likeNum",_jobj);
        }
        return null;
    } // String getLikeNum(JSONObject jObj)

    // songDetailDataParsing 후에만 사용가능한 메소드
    public String getImageUrl() { // 노래 한 곡에 대한 상세 파싱이 이루어졌다면 그 곡의 큰 이미지 url을 반환하는 메소드
        if(isSongDetailParsed("imageUrl") != null){
            return isSongDetailParsed("imageUrl");
        }

        System.out.println("getImageUrl() : " + _isOnlyDetailParse);
        return null;
    } // String getImageUrl()

    public String getImageUrl(int rank) { // 노래 순위를 통해 해당 노래의 이미지 url을 반환하는 메소드
        int _rank = rank;
        if(!isRanked(_rank)){
            return null;
        }

        if(isSongDetailParsed("imageUrl") != null){
            return isSongDetailParsed("imageUrl");
        }

        return ((JSONObject) _chartList.get(rank - 1)).get("smallImageUrl").toString(); // 차트 100곡에 대한 파싱이 이루어졌다면 JSONArray에 있는 노래들 중 순위에 맞는 원소의 작은 이미지 url을 반환
    } // String getImageUrl(int rank)

    public String getImageUrl(String title) { // 노래 제목을 통해 해당 노래의 이미지 url을 반환하는 메소드
        if(isSongDetailParsed("imageUrl") != null){
            return isSongDetailParsed("imageUrl");
        }
        String _title = title;
        if(foundByTitle(_title, "smallImageUrl")!=null){
            return foundByTitle(_title, "smallImageUrl");
        }
        return null;
    } // String getImageUrl(String title)

    public String getImageUrl(JSONObject jObj) { // JSONArray의 원소 중 하나를 이용하여 해당 노래의 좋아요 개수를 반환하는 메소드
        if(isSongDetailParsed("imageUrl") != null){
            return isSongDetailParsed("imageUrl");
        }
        JSONObject _jobj = jObj;
        if(isJSONObject("smallImageUrl",_jobj) != null){
            return isJSONObject("smallImageUrl",_jobj);
        }
        return null;
    } // String getImageUrl(JSONObject jObj)

    String isSongDetailParsed(String string){
        if (!isParsed()) { // 파싱이 이루어지지 않았다면
            System.out.println(_isNotParsed);
            return null;
        }
        if (_songCount == 1) // 노래 한 곡에 대한 상세 파싱이 이루어졌다면
            return _songDetailInfo.get(string).toString(); // 노래 한 곡에 대한 상세 정보 파싱이 이루어졌다면 그 곡의 큰 이미지 url을 반환(상세 정보는 제목과 상관이 없음)
        return null;
    }
    boolean isSongDetailParsed(){
        if (!isParsed()) { // 파싱이 이루어지지 않았다면
            System.out.println(_isNotParsed);
            return false;
        }
        if (_songCount == 1){// 노래 한 곡에 대한 상세 파싱이 이루어졌다면{
            System.out.println(_isOnlyChartParse);
            return false;
        }
        return true;
    }
    String isJSONObject(String string,JSONObject jObj){
        if (jObj == null) {
            System.out.println(_plzUseRightJSONObject);
            return null;
        }

        if (jObj.containsKey(string)) // artist key값 유효성 검사
            return jObj.get(string).toString();
        else {
            System.out.println(_jsonDontHaveKey);
        }
        return null;

    }
    String foundByTitle(String title, String string){
        for (int i = 0; i < _songCount; i++) { // 차트 100곡에 대한 파싱이 이루어졌다면 JSONArray에 있는 노래들 중 title 제목에 맞는 원소의 노래 아이디를 반환
            if (((JSONObject) _chartList.get(i)).get("title") == title)
                return ((JSONObject) _chartList.get(i)).get(string).toString();
        }
        return null;
    }
    boolean isRanked(int rank){
        if (rank < 1 || rank > 100) { // 1 <= rank <= 100을 벗어나는 범위라면
            System.out.println("1~100위 이내의 순위를 입력해주세요");
            return false;
        }
        return true;
    }

    // getGenre(), getGenre(JSONObject jObj)는 MelonChartParser와 GenieChartParser에서만 사용가능하므로 추상클래스에서는 제외됨
    // getReleaseDate(), getReleaseDate(JSONObject jObj)는 MelonChartParser에서만 사용가능하므로 추상클래스에서는 제외됨
} // MusicChartParser class