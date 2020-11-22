package model;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;

/**
 * @author SejongUniv 오창한
 * @version 1.7
 **/

public class MelonChartParser extends MusicChartParser {

    /*
     * MelonChartParser Description (KO_KR)
     *
     **************************************************
     *
     * ** 차트 100곡을 파싱할 시에 얻을 수 있는 것들 **
     * 노래 아이디		(key : songId)
     * 노래 순위		(key : rank)
     * 노래 작은 이미지	(key : smallImageUrl)
     * 노래 제목		(key : title)
     * 가수 이름		(key : artist)
     * 앨범 이름		(key : albumName)
     * 노래 좋아요 개수	(key : likeNum)
     *
     * ** 차트 100곡을 파싱할 시에 사용 가능한 메소드 **
     * - 메소드 이름이 같은 메소드들은 반환형이 모두 같다.
     * - [반환형] 메소드이름() 과 같이 표기했다.
     *
     * <차트 100곡 파싱 관련 메소드>
     * [void]		chartDataParsing(Component parentComponent)
     * [boolean]	isParsed()
     *
     * <차트 100곡 노래 정보 get 메소드>
     * [JSONArray]	getChartList()
     * [JSONObject]	getSongData(int rank)	getSongData(String title)
     * [int]		getRank(String title)	getRank(JSONObject jObj)
     * [String]		getTitle(int rank)		getTitle(JSONObject jObj)
     * [String]		getArtistName(int rank)	getArtistName(String title)		getArtistName(JSONObject jObj)
     * [String]		getAlbumName(int rank)	getAlbumName(String title)		getAlbumName(JSONObject jObj)
     * [String]		getLikeNum(int rank)	getLikeNum(String title)		getLikeNum(JSONObject jObj)
     * [String]		getImageUrl(int rank)	getImageUrl(String title)		getImageUrl(JSONObject jObj)
     * [String]		getSongId(int rank)		getSongId(String title)			getSongId(JSONObject jObj)
     *
     **************************************************
     *
     * ** 노래 1개에 대한 상세 정보를 파싱할 시에 얻을 수 있는 것들 **
     * 노래 큰 이미지		(key : imageUrl)
     * 노래 발매일		(key : releaseDate)
     * 노래 장르		(key : genre)
     *
     * ** 노래 1개에 대한 상세 정보를 파싱할 시에 사용 가능한 메소드 **
     * - 메소드 이름이 같은 메소드들은 반환형이 모두 같다.
     * - [반환형] 메소드이름() 과 같이 표기했다.
     *
     * <노래 1개에 대한 상세 정보 파싱 관련 메소드>
     * [void]		songDetailDataParsing(String songId, Component parentComponent)
     * [void]		songDetailDataParsing(JSONObject jObj, Component parentComponent)
     * [void]		songDetailDataParsing(int rank, JSONArray chartListData, Component parentComponent)
     * [void]		songDetailDataParsing(String title, JSONArray chartListData, Component parentComponent)
     * [boolean]	isParsed()
     *
     * <노래 1개에 대한 상세 정보 get 메소드>
     * [JSONObject]	getSongData()
     * [String]		getImageUrl()		getImageUrl(JSONObject jObj)	getImageUrl(int rank)	getImageUrl(String title)
     * [String]		getReleaseDate()	getReleaseDate(JSONObject jObj)
     * [String]		getGenre()			getGenre(JSONObject jObj)
     *
     **************************************************
     *
     */

    private String melonChartParsingTitle = "멜론 차트 파싱중..";
    private String melonChartParsingMessage = "멜론 차트 100곡에 대한 정보를 불러오는 중 입니다 :)";

    public int chart = 1;

    public MelonChartParser() { // 초기화 작업을 진행함
        _songCount = 0;                // 파싱한 노래 개수(초기값은 0)
        _chartList = null;            // 차트 100곡에 대한 정보를 담을 JSONArray
        _songDetailInfo = null;        // 노래 한 곡에 대한 상세 정보를 담을 JSONObject
        _url = null;                    // 파싱할 웹 사이트 url
        _chartThread = null;            // 차트 100곡 파싱에 사용할 Thread
        _songDetailThread = null;    // 노래 한 곡에 대한 상세 정보 파싱에 사용할 Thread
        progressMonitor = null;    // ProgressMonitor를 사용하면 Thread가 종료되지 않는 버그와 ProgressMonitor가 제대로 나오지 않는 버그가 발생하여 사용하는 부분은 주석처리 해두었음
    } // constructor

    private class ChartDataParsingThread implements Runnable { // 차트 100곡 파싱을 하는 Runnable class
        @Override
        public void run() {
            // 멜론 차트 1~100위의 노래를 파싱함
            _songCount = 0; // 노래 개수 초기화
            _url = "https://www.melon.com/chart/index.htm"; // 파싱할 url

            try {
                // 멜론 차트 연결에 필요한 header 설정 및 연결
                Connection melonConnection = Jsoup.connect(_url).header("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .header("Sec-Fetch-User", "?1").header("Upgrade-Insecure-Requests", "1")
                        .header("User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
                        .method(Connection.Method.GET);

                // 연결 후 웹페이지를 긁어옴
                Document melonDocument = melonConnection.get();

                // 1~100위에 대한 정보를 불러옴, 순위와 곡의 상세한 정보를 뽑기 위한 링크를 뽑는 용도로 사용
                Elements data1st100 = melonDocument.select("table").first().select("tbody > tr");

                _chartList = new JSONArray();

                for (Element elem : data1st100) { // 1~100위에 대한 내용 파싱

                    // JSONObject에 데이터를 넣기 위한 작업
                    HashMap<String, Object> songAllInfo = new HashMap<String, Object>();

                    // key : songId, value : 노래 아이디 - 상세 정보를 파싱할 때 필요함
                    songAllInfo.put("songId", elem.attr("data-song-no").toString());

                    // key : rank, value : 순위
                    songAllInfo.put("rank", Integer.toString(chart++));

                    // key : smallImageUrl, value : 노래 이미지(사이즈 작음) 링크 (큰 사이즈 이미지는 detailDataParsing에서 다룸)
                    songAllInfo.put("smallImageUrl", elem.select("a > img").first().attr("src").toString());

                    // key : title, value : 노래 제목
                    songAllInfo.put("title", elem.select("div.ellipsis > span > a").first().text().toString());

                    // key : artist, value : 가수 이름
                    songAllInfo.put("artist", elem.select("div.ellipsis").get(1).select("a").first().text().toString());

                    // key : albumName, value : 앨범 이름
                    songAllInfo.put("albumName", elem.select("div.ellipsis").get(2).select("a").text().toString());

                    // 노래의 좋아요 개수를 뽑아내기 위한 url을 만듬, 멜론은 좋아요 개수를 따로 불러오는 방식이라 단순 크롤링으로는 불러와지지 않기 때문
                    String likeNumUrl = "https://www.melon.com/commonlike/getSongLike.json?contsIds="
                            + songAllInfo.get("songId").toString();

                    // 노래의 좋아요 개수를 뽑아내기 위한 url에 연결 후 JSON을 받아옴
                    Document likeNumDocument = Jsoup.connect(likeNumUrl).header("Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                            .header("Sec-Fetch-User", "?1").header("Upgrade-Insecure-Requests", "1")
                            .header("User-Agent",
                                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
                            .ignoreContentType(true).get();

                    // JSONParser로 파싱하여 JSONObject로 변환하고, HashMap에 추가함
                    // key : likeNum, value : 좋아요 개수
                    JSONParser parser = new JSONParser();
                    JSONObject obj = (JSONObject) parser.parse(likeNumDocument.text());
                    songAllInfo.put("likeNum",
                            ((JSONObject) (((JSONArray) obj.get("contsLike")).get(0))).get("SUMMCNT").toString());

                    // 값들을 JSONObject로 변환
                    JSONObject jsonSongInfo = new JSONObject(songAllInfo);

                    // JSONArray에 값 추가, 노래 개수 증가
                    _chartList.add(jsonSongInfo);
                    _songCount++;
                    //progressMonitor.setProgress(songCount);
                }

                // 파싱 결과 출력(테스트용)

/*
                for (Object o : chartList) {
                    if (o instanceof JSONObject)
                        System.out.println(((JSONObject) o));
                }


 */
            } // try
            catch (HttpStatusException e) { // 멜론의 경우 Request Header를 같이 보내주어도 너무 자주 파싱을 시도할 시에 일시적 차단을 하므로 그에 대한 처리
                e.printStackTrace();
                _chartList = null;
                _songDetailInfo = null;
                System.out.println("많은 요청으로 인해 불러오기에 실패하였습니다.");
                _songCount = 0;
                return;
            } catch (NullPointerException e) { // 데이터 긁어오는 데에 실패했을 때(태그나 속성이 없을 때)
                e.printStackTrace();
                _chartList = null;
                _songDetailInfo = null;
                System.out.println("Url 링크가 잘못되었거나, 웹 페이지 구조가 변경되어 파싱에 실패했습니다 :(");
                _songCount = 0;
                return;
            } catch (Exception e) { // 그 외의 모든 에러
                e.printStackTrace();
                _chartList = null;
                _songDetailInfo = null;
                System.out.println("파싱도중 에러가 발생했습니다 :(");
                _songCount = 0;
                return;
            }
        } // run()
    } // ChartDataParsingThread Runnable class

    private class SongDetailDataParsingThread implements Runnable { // 노래 한 곡에 대한 상세 파싱을 하는 Runnable class
        @Override
        public void run() {
            // 노래 한 곡에 대한 상세 정보 파싱
            _songCount = 0; // 노래 개수 초기화
            HashMap<String, Object> songAllInfo = new HashMap<String, Object>();

            try {
                // songId를 통해 곡에 대한 상세한 정보를 얻기 위한 접근
                Connection songDetailConnection = Jsoup.connect(_url).header("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .header("Sec-Fetch-User", "?1").header("Upgrade-Insecure-Requests", "1")
                        .header("User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
                        .method(Connection.Method.GET);

                // 곡에 대한 상세한 정보 웹 페이지를 긁어옴
                Document songDetailDocument = songDetailConnection.get();

                Element songDetailInfo = songDetailDocument.select(".wrap_info").first();

                // key : imageUrl, value : 노래 이미지 링크(이미지 사이즈가 큼)
                String songImageUrl = songDetailInfo.getElementsByTag("img").first().attr("src");
                songAllInfo.put("imageUrl", songImageUrl);

                Element songDetailEtcInfo = songDetailInfo.select("dl.list").first();

                // key : releaseDate, value : 노래 발매일
                String songReleaseDate = songDetailEtcInfo.getElementsByTag("dd").get(1).text();
                songAllInfo.put("releaseDate", songReleaseDate);

                // key : genre, value : 노래 장르
                String songGenre = songDetailEtcInfo.getElementsByTag("dd").get(2).text();
                songAllInfo.put("genre", songGenre);
            } // try
            catch (HttpStatusException e) { // 멜론의 경우 Request Header를 같이 보내주어도 너무 자주 파싱을 시도할 시에 일시적 차단을 하므로 그에 대한 처리
                e.printStackTrace();
                _chartList = null;
                _songDetailInfo = null;
                System.out.println("많은 요청으로 인해 불러오기에 실패하였습니다.");
                _songCount = 0;
                return;
            } catch (NullPointerException e) { // 데이터 긁어오는 데에 실패했을 때(태그나 속성이 없을 때)
                e.printStackTrace();
                _chartList = null;
                _songDetailInfo = null;
                System.out.println("Url 링크가 잘못되었거나, 웹 페이지 구조가 변경되어 파싱에 실패했습니다 :(");
                _songCount = 0;
                return;
            } catch (Exception e) { // 그 외의 모든 에러
                e.printStackTrace();
                _chartList = null;
                _songDetailInfo = null;
                System.out.println("파싱도중 에러가 발생했습니다 :(");
                _songCount = 0;
                return;
            }
            _songDetailInfo = new JSONObject(songAllInfo); // HashMap을 JSONObject로 변환하여 저장
            _songCount++; // 노래 개수 증가
        } // run()
    } // SongDetailDataParsingThread Runnable class

    @Override
    public void chartDataParsing(Component parentComponent) { // 차트 100곡을 파싱하는 Thread를 시작하는 메소드
        if (_chartThread != null) { // Thread를 사용하는게 처음이 아닐 때
            if (_chartThread.isAlive()) { // Thread가 살아있으면 정지
                _chartThread.stop();
                System.out.println("Chart Thread is Alive");
            } else
                System.out.println("Chart Thread is dead");
        }
        _chartThread = new Thread(new ChartDataParsingThread()); // Thread는 재사용이 안되기 때문에 다시 객체를 생성함
        // progressMonitorManager(parentComponent, melonChartParsingTitle, melonChartParsingMessage);
        _chartThread.start(); // Thread 시작
        try {
            _chartThread.join(); // ChartDataParsingThread가 종료되기전까지 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } // chartDataParsing(Component parentComponent)
    @Override
    public void songDetailDataParsing(JSONObject jObj, Component parentComponent) { // 노래 한 곡에 대한 상세 정보를 파싱하는 Thread를 시작하는 메소드
        if (jObj == null) {
            System.out.println(_plzUseRightJSONObject);
            return;
        }

        if (!jObj.containsKey("songId")) { // songId key값 유효성 검사
            System.out.println(_jsonDontHaveKey);
            return;
        }
        _url = "https://www.melon.com/song/detail.htm?songId=" + jObj.get("songId").toString(); // 파싱할 url을 만듬
        if (_songDetailThread != null) { // Thread를 사용하는 게 처음이 아닐 때
            if (_songDetailThread.isAlive()) // Thread가 살아있으면 정지
                _songDetailThread.stop();
        }
        _songDetailThread = new Thread(new SongDetailDataParsingThread()); // Thread는 재사용이 안되기 때문에 다시 객체를 생성함
        _songDetailThread.start();
        try {
            _songDetailThread.join(); // SongDetailDataParsingThread가 종료되기 전까지 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } // songDetailDataParsing(JSONObject jObj, Component parentComponent)

    // chartDataParsing 후에만 사용가능한 메소드
    public String getLikeNum(int rank) { // 노래 순위를 이용하여 해당 노래의 좋아요 개수를 반환하는 메소드
        if (rank < 1 || rank > 100) { // 1 <= rank <= 100을 벗어나는 범위라면
            System.out.println("1~100위 이내의 순위를 입력해주세요");
            return null;
        }

        if (!isParsed()) { // 파싱이 이루어지지 않았다면
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1) { // 상세 파싱이 이루어졌다면
            System.out.println("getLikeNum(int rank) : " + _isOnlyChartParse);
            return null;
        }

        return ((JSONObject) _chartList.get(rank - 1)).get("likeNum").toString();
    } // String getLikeNum(int rank)
    /*
    //chartDataParsing 후에만 사용가능한 메소드
    public String getLikeNum(String title) { // 노래 제목을 이용하여 해당 노래의 좋아요 개수를 반환하는 메소드
        if (!isParsed()) { // 파싱이 이루어지지 않았다면
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1) { // 상세 파싱이 이루어졌다면
            System.out.println("getLikeNum(String title) : " + _isOnlyChartParse);
            return null;
        }

        for (int i = 0; i < _songCount; i++) { // 차트 100곡에 대한 파싱이 이루어졌다면 JSONArray에서 노래 제목에 맞는 원소를 찾아 해당 노래의 좋아요 개수를 반환하는 함수
            if (((JSONObject) _chartList.get(i)).get("title") == title)
                return ((JSONObject) _chartList.get(i)).get("likeNum").toString();
        }

        return null;
    } // String getLikeNum(String title)
    */
    // songDetailDataParsing 후에만 사용가능한 메소드
    public String getReleaseDate() { // 노래 한 곡에 대한 상세 파싱이 이루어졌다면 그 곡의 발매일을 반환하는 메소드
        if (!isParsed()) { // 파싱이 이루어지지 않았다면
            System.out.println(_isNotParsed);
            return null;
        }
        if (_songCount == 1) // 상세 파싱이 이루어졌다면
            return _songDetailInfo.get("releaseDate").toString();

        System.out.println("getReleaseDate() : " + _isOnlyDetailParse);
        return null;
    } // String getReleaseDate()
    /*
    // songDetailDataParsing 후에만 사용가능한 메소드
    public String getReleaseDate(JSONObject jObj) { // 노래 한 곡에 대한 상세 파싱이 이루어졌다면 JSONObject를 이용하여 그 곡의 발매일을 반환하는 메소드
        if (!isParsed()) { // 파싱이 이루어지지 않았다면
            System.out.println(_isNotParsed);
            return null;
        }

        if (jObj == null) {
            System.out.println(_plzUseRightJSONObject);
            return null;
        }

        if (_songCount == 1) { // 상세 파싱이 이루어졌다면
            if (jObj.containsKey("releaseDate")) // release key값 유효성 검사
                return jObj.get("releaseDate").toString();
            else {
                System.out.println(_jsonDontHaveKey);
                return null;
            }
        }

        System.out.println("getReleaseDate(JSONObject jObj) : " + _isOnlyDetailParse);
        return null;
    } // String getReleaseDate(JSONObject jObj)
    */
    // songDetailDataParsing 후에만 사용가능한 메소드
    public String getGenre() { // 노래 한 곡에 대한 상세 정보 파싱이 이루어졌다면 그 곡의 장르를 반환하는 메소드
        if (!isParsed()) { // 파싱이 이루어지지 않았다면
            System.out.println(_isNotParsed);
            return null;
        }
        if (_songCount == 1) // 상세 파싱이 이루어졌다면
            return _songDetailInfo.get("genre").toString();

        System.out.println("getGenre() : " + _isOnlyDetailParse);
        return null;
    } // String getGenre()
    /*
    // songDetailDataParsing 후에만 사용가능한 메소드
    public String getGenre(JSONObject jObj) { // 노래 한 곡에 대한 상세 정보 파싱이 이루어졌다면 JSONObject를 이용하여 그 곡의 장르를 반환하는 메소드
        if (!isParsed()) { // 파싱이 이루어지지 않았다면
            System.out.println(_isNotParsed);
            return null;
        }

        if (jObj == null) {
            System.out.println(_plzUseRightJSONObject);
            return null;
        }

        if (_songCount == 1) { // 노래 한 곡에 대한 상세 파싱이 이루어졌다면
            if (jObj.containsKey("genre")) // genre key값 유효성 검사
                return jObj.get("genre").toString();
            else {
                System.out.println(_jsonDontHaveKey);
                return null;
            }
        }

        System.out.println("getGenre(JSONObject jObj) : " + _isOnlyDetailParse);
        return null;
    } // String getGenre(JSONObject jObj)
    */
} // MelonChartParser class
