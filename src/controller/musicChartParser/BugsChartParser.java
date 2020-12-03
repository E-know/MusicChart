package controller.musicChartParser;

import java.awt.Component;
import java.util.HashMap;

import controller.musicChartParser.MusicChartParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author SejongUniv ��â��
 * @version 1.7
 **/

public class BugsChartParser extends MusicChartParser {

    /*
     * BugsChartParser Description (KO_KR)
     *
     **************************************************
     *
     * ** ��Ʈ 100���� �Ľ��� �ÿ� ���� �� �ִ� �͵� **
     * �뷡 ���̵�		(key : songId)
     * �뷡 ����		(key : rank)
     * �뷡 ���� �̹���	(key : smallImageUrl)
     * �뷡 ����		(key : title)
     * ���� �̸�		(key : artist)
     * �ٹ� �̸�		(key : albumName)
     *
     * ** ��Ʈ 100���� �Ľ��� �ÿ� ��� ������ �޼ҵ� **
     * - �޼ҵ� �̸��� ���� �޼ҵ���� ��ȯ���� ��� ����.
     * - [��ȯ��] �޼ҵ��̸�() �� ���� ǥ���ߴ�.
     *
     * <��Ʈ 100�� �Ľ� ���� �޼ҵ�>
     * [void]		chartDataParsing(Component parentComponent)
     * [boolean]	isParsed()
     *
     * <��Ʈ 100�� �뷡 ���� get �޼ҵ�>
     * [JSONArray]	getChartList()
     * [JSONObject]	getSongData(int rank)	getSongData(String title)
     * [int]		getRank(String title)	getRank(JSONObject jObj)
     * [String]		getTitle(int rank)		getTitle(JSONObject jObj)
     * [String]		getArtistName(int rank)	getArtistName(String title)		getArtistName(JSONObject jObj)
     * [String]		getAlbumName(int rank)	getAlbumName(String title)		getAlbumName(JSONObject jObj)
     * [String]		getImageUrl(int rank)	getImageUrl(String title)		getImageUrl(JSONObject jObj)
     * [String]		getSongId(int rank)		getSongId(String title)			getSongId(JSONObject jObj)
     *
     **************************************************
     *
     * ** �뷡 1���� ���� �� ������ �Ľ��� �ÿ� ���� �� �ִ� �͵� **
     * �뷡 ū �̹���		(key : imageUrl)
     * �뷡 ����ð�		(key : songTime)
     * �뷡 ���ƿ� ����	(key : likeNum)
     *
     * ** �뷡 1���� ���� �� ������ �Ľ��� �ÿ� ��� ������ �޼ҵ� **
     * - �޼ҵ� �̸��� ���� �޼ҵ���� ��ȯ���� ��� ����.
     * - [��ȯ��] �޼ҵ��̸�() �� ���� ǥ���ߴ�.
     *
     * <�뷡 1���� ���� �� ���� �Ľ� ���� �޼ҵ�>
     * [void]		songDetailDataParsing(String songId, Component parentComponent)
     * [void]		songDetailDataParsing(JSONObject jObj, Component parentComponent)
     * [void]		songDetailDataParsing(int rank, JSONArray chartListData, Component parentComponent)
     * [void]		songDetailDataParsing(String title, JSONArray chartListData, Component parentComponent)
     * [boolean]	isParsed()
     *
     * <�뷡 1���� ���� �� ���� get �޼ҵ�>
     * [JSONObject]	getSongData()
     * [String]		getImageUrl()		getImageUrl(JSONObject jObj)	getImageUrl(int rank)	getImageUrl(String title)
     * [String]		getSongTime()		getSongTime(JSONObject jObj)
     * [String]		getLikeNum()		getLikeNum(JSONObject jObj)
     *
     **************************************************
     *
     */

    private String bugsChartParsingTitle = "���� ��Ʈ �Ľ���..";
    private String bugsChartParsingMessage = "���� ��Ʈ 100� ���� ������ �ҷ����� �� �Դϴ� :)";

    public BugsChartParser() { // �ʱ�ȭ �۾��� ������
        _songCount = 0;                // �Ľ��� �뷡 ����(�ʱⰪ�� 0)
        _chartList = null;            // ��Ʈ 100� ���� ������ ���� JSONArray
        _songDetailInfo = null;        // �뷡 �� � ���� �� ������ ���� JSONObject
        _url = null;                    // �Ľ��� �� ����Ʈ url
        _chartThread = null;            // ��Ʈ 100�� �Ľ̿� ����� Thread
        _songDetailThread = null;    // �뷡 �� � ���� �� ���� �Ľ̿� ����� Thread
        progressMonitor = null;    // ProgressMonitor�� ����ϸ� Thread�� ������� �ʴ� ���׿� ProgressMonitor�� ����� ������ �ʴ� ���װ� �߻��Ͽ� ����ϴ� �κ��� �ּ�ó�� �صξ���
    } // constructor

    private class ChartDataParsingThread implements Runnable { // ��Ʈ 100�� �Ľ��� �ϴ� Runnable class
        @Override
        public void run() {
            // ���� ��Ʈ 1~100���� �뷡�� �Ľ���
            _songCount = 0;
            _url = "https://music.bugs.co.kr/chart";

            try {
                // ���� ��Ʈ ���ῡ �ʿ��� header ���� �� ����
                Connection bugsConnection = Jsoup.connect(_url).header("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .header("Sec-Fetch-User", "?1").header("Upgrade-Insecure-Requests", "1")
                        .header("User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                        .method(Connection.Method.GET);

                // ���� �� ���������� �ܾ��
                Document bugsDocument = bugsConnection.get();

                // 1~100���� ���� ������ �ҷ���, ������ ���� ���� ������ �̱� ���� ��ũ�� �̴� �뵵�� ���
                Elements data1st100 = bugsDocument.select("table.list").first().select("tr[rowtype=track]");

                _chartList = new JSONArray();

                for (Element elem : data1st100) { // 1~100���� ���� ���� �Ľ�
                    // JSONObject�� �����͸� �ֱ� ���� �۾�
                    HashMap<String, Object> songAllInfo = new HashMap<String, Object>();

                    // key : rank, value : ����
                    songAllInfo.put("rank", elem.select("div.ranking > strong").first().text().toString());

                    // key : smallImageUrl, value : ���� �̹��� url
                    songAllInfo.put("smallImageUrl", elem.select("img").attr("src").toString());

                    // key : songId, value : �뷡 ���̵�
                    songAllInfo.put("songId", elem.select("tr").attr("trackid").toString());

                    // key : title, value : ����
                    songAllInfo.put("title", elem.select("th[scope=row]").first().select("a").first().text().toString());

                    // key : artist, value : ���� �̸�
                    songAllInfo.put("artist", elem.select("td.left").first().select("a").first().text().toString());

                    // key : albumName, value : �ٹ� �̸�
                    songAllInfo.put("albumName", elem.select("td.left").get(1).select("a").first().text().toString());

                    songAllInfo.put("albumID", elem.select("td.left").get(1).select("a").attr("href").substring(31,39));

                    // ������ JSONObject�� ��ȯ
                    JSONObject jsonSongInfo = new JSONObject(songAllInfo);

                    // JSONArray�� �� �߰�, �뷡 ���� ����
                    _chartList.add(jsonSongInfo);
                    _songCount++;
                    // progressMonitor.setProgress(songCount);
                }

                // �Ľ� ��� ���(�׽�Ʈ��)
				/*
				for (Object o : chartList) {
					if (o instanceof JSONObject)
						System.out.println(((JSONObject) o));
				}
				*/

            } catch (HttpStatusException e) {
                e.printStackTrace();
                _chartList = null;
                _songDetailInfo = null;
                System.out.println("���� ��û���� ���� �ҷ����⿡ �����Ͽ����ϴ�.");
                _songCount = 0;
                return;
            } catch (NullPointerException e) { // ������ �ܾ���� ���� �������� ��(�±׳� �Ӽ��� ���� ��)
                e.printStackTrace();
                _chartList = null;
                _songDetailInfo = null;
                System.out.println("Url ��ũ�� �߸��Ǿ��ų�, �� ������ ������ ����Ǿ� �Ľ̿� �����߽��ϴ� :(");
                _songCount = 0;
                return;
            } catch (Exception e) { // �� ���� ��� ����
                e.printStackTrace();
                _chartList = null;
                _songDetailInfo = null;
                System.out.println("�Ľ̵��� ������ �߻��߽��ϴ� :(");
                _songCount = 0;
                return;
            }
        } // run()
    } // ChartDataParsingThread Runnable class

    private class SongDetailDataParsingThread implements Runnable { // �뷡 �� � ���� �� �Ľ��� �ϴ� Runnable class
        @Override
        public void run() {
            // �뷡 �� � ���� �� ���� �Ľ�
            _songCount = 0; // �뷡 ���� �ʱ�ȭ
            HashMap<String, Object> songAllInfo = new HashMap<String, Object>();

            try {
                // songId�� ���� � ���� ���� ������ ��� ���� ����
                Connection songDetailConnection = Jsoup.connect(_url).header("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .header("Sec-Fetch-User", "?1").header("Upgrade-Insecure-Requests", "1")
                        .header("User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                        .method(Connection.Method.GET);

                // � ���� ���� ���� �� �������� �ܾ��
                Document songDetailDocument = songDetailConnection.get();
                Element songDetailInfo = songDetailDocument.select("div.basicInfo").first();

                Element songDetailAlbumInfo = songDetailInfo.select("table.info").first().select("tbody").first();
                Element songDetailLikeInfo = songDetailDocument.select("div.etcInfo").first();

                // key : imageUrl, value : ū �̹��� url ��ũ
                songAllInfo.put("imageUrl",
                        songDetailInfo.select("div.photos").first().select("ul > li > a > img").attr("src").toString());

                // key : songTime, value : ��� �ð�
                songAllInfo.put("songTime",
                        songDetailAlbumInfo.select("tr").get(3).select("td > time").get(0).text().toString());

                // key : likeNum, value : ���ƿ� ����
                songAllInfo.put("likeNum",
                        songDetailLikeInfo.select("span").first().select("a > span > em").first().text().toString());

            } catch (HttpStatusException e) {
                e.printStackTrace();
                _chartList = null;
                _songDetailInfo = null;
                System.out.println("���� ��û���� ���� �ҷ����⿡ �����Ͽ����ϴ�.");
                _songCount = 0;
                return;
            } catch (NullPointerException e) { // ������ �ܾ���� ���� �������� ��(�±׳� �Ӽ��� ���� ��)
                e.printStackTrace();
                _chartList = null;
                _songDetailInfo = null;
                System.out.println("Url ��ũ�� �߸��Ǿ��ų�, �� ������ ������ ����Ǿ� �Ľ̿� �����߽��ϴ� :(");
                _songCount = 0;
                return;
            } catch (Exception e) { // �� ���� ��� ����
                e.printStackTrace();
                _chartList = null;
                _songDetailInfo = null;
                System.out.println("�Ľ̵��� ������ �߻��߽��ϴ� :(");
                _songCount = 0;
                return;
            }
            _songDetailInfo = new JSONObject(songAllInfo); // HashMap�� JSONObject�� ��ȯ�Ͽ� ����
            _songCount++; // �뷡 ���� ����
        } // run()
    } // SongDetailDataParsingThread Runnable class

    @Override
    public void chartDataParsing(Component parentComponent) { // ��Ʈ 100���� �Ľ��ϴ� Thread�� �����ϴ� �޼ҵ�
        if (_chartThread != null) { // Thread�� ����ϴ°� ó���� �ƴ� ��
            if (_chartThread.isAlive()) // Thread�� ��������� ����
                _chartThread.stop();
        }
        _chartThread = new Thread(new ChartDataParsingThread()); // Thread�� ������ �ȵǱ� ������ �ٽ� ��ü�� ������
        // progressMonitorManager(parentComponent, bugsChartParsingTitle, bugsChartParsingTitle);
        _chartThread.start(); // Thread ����
        try {
            _chartThread.join(); // ChartDataParsingThread�� ����Ǳ������� ���
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } // chartDataParsing(Component parentComponent)
    @Override
    public void songDetailDataParsing(int rank, JSONArray chartListData, Component parentComponent) { // �뷡 �� � ���� �� ������ �Ľ��ϴ� Thread�� �����ϴ� �޼ҵ�
        if (chartListData == null) {
            System.out.println("��Ʈ �Ľ̵� �����Ͱ� ���� �޼ҵ� ������ �����մϴ� :(");
            return;
        }
        JSONObject jObj = (JSONObject) chartListData.get(rank - 1);
        detailDataparsing(jObj, parentComponent);
    } // songDetailDataParsing(int rank, JSONArray chartListData, Component parentComponent)
    @Override
    public void songDetailDataParsing(JSONObject obj, Component parentComponent) { // �뷡 �� � ���� �� ������ �Ľ��ϴ� Thread�� �����ϴ� �޼ҵ�
        if (obj == null) {
            System.out.println(_plzUseRightJSONObject);
            return;
        }

        if (!obj.containsKey("songId")) { // songId key�� ��ȿ�� �˻�
            System.out.println(_jsonDontHaveKey);
            return;
        }
        JSONObject _jObj = obj;
        detailDataparsing(_jObj, parentComponent);

    } // songDetailDataParsing(JSONObject jObj, Component parentComponent)
    @Override
    void detailDataparsing(JSONObject jObj, Component parentComponent){
        _url = "https://music.bugs.co.kr/track/" + jObj.get("songId").toString() + "?wl_ref=list_tr_08_chart"; // �Ľ��� url�� ����
        if (_songDetailThread != null) { // Thread�� ����ϴ� �� ó���� �ƴ� ��
            if (_songDetailThread.isAlive()) // Thread�� ��������� ����
                _songDetailThread.stop();
        }
        _songDetailThread = new Thread(new SongDetailDataParsingThread()); // Thread�� ������ �ȵǱ� ������ �ٽ� ��ü�� ������
        _songDetailThread.start();
        try {
            _songDetailThread.join(); // SongDetailDataParsingThread�� ����Ǳ� ������ ���
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // songDetailDataParsing �Ŀ��� ��밡���� �޼ҵ�
    public String getLikeNum() { // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ� �� ���� ���ƿ� ������ ��ȯ�ϴ� �޼ҵ�
        if(isSongDetailParsed("likeNum") != null){
            return isSongDetailParsed("likeNum");
        }
        return null;
    } // String getLikeNum()

	/*  ������ �߸���(releaseDate)�� �帣(genre)��
	 	�� ���������� �������� �ʾ� getReleaseDate �޼ҵ�� getGenre�޼ҵ尡 ����		*/

    // songDetailDataParsing �Ŀ��� ��밡���� �޼ҵ�
    public String getSongTime() { // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ� �� ���� ��� �ð��� ��ȯ�ϴ� �޼ҵ�
        if(isSongDetailParsed("songTime") != null){
            return isSongDetailParsed("songTime");
        }
        return null;
    } // String getSongTime()

    // songDetailDataParsing �Ŀ��� ��밡���� �޼ҵ�
    public String getSongTime(JSONObject jObj) { // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ� JSONObject�� �̿��Ͽ� �� ���� ��� �ð��� ��ȯ�ϴ� �޼ҵ�
        if (!isParsed()) { // �Ľ��� �̷������ �ʾҴٸ�
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1) {// �뷡 �� � ���� �� �Ľ��� �̷�����ٸ�
            JSONObject _jobj = jObj;
            if(isJSONObject("songTime",_jobj) != null){
                return isJSONObject("songTime",_jobj);
            }
            return null;
        }
        return null;
    } // String getSongTime(JSONObject jObj)


}
