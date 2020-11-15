package model;

import java.awt.*;
import java.util.HashMap;

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

public class GenieChartParser extends MusicChartParser {

    /*
     * GenieChartParser Description (KO_KR)
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
     * �뷡 �帣		(key : genre)
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
     * [String]		getGenre()			getGenre(JSONObject jObj)
     * [String]		getSongTime()		getSongTime(JSONObject jObj)
     * [String]		getLikeNum()		getLikeNum(JSONObject jObj)
     *
     **************************************************
     *
     */

    private String genieChartParsingTitle = "���� ��Ʈ �Ľ���..";
    private String genieChartParsingMessage = "���� ��Ʈ 100� ���� ������ �ҷ����� �� �Դϴ� :)";

    public GenieChartParser() { // �ʱ�ȭ �۾��� ������
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
            _url = "https://www.genie.co.kr/chart/top200";

            try {
                // ���� ��Ʈ ���ῡ �ʿ��� header ���� �� ����
                Connection genieConnection1_50 = Jsoup.connect(_url).header("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .header("Upgrade-Insecure-Requests", "1")
                        .header("User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                        .method(Connection.Method.GET);

                // ���� �� ���������� �ܾ��
                Document genieDocument1_50 = genieConnection1_50.get();

                // 1~50���� ���� ������ �ҷ���
                Elements data1st50 = genieDocument1_50.select("table.list-wrap").first().select("tbody > tr.list");

                _chartList = new JSONArray();

                for (Element elem : data1st50) { // 1~50���� ���� ���� �Ľ�
                    // JSONObject�� �����͸� �ֱ� ���� �۾�
                    HashMap<String, Object> songAllInfo = new HashMap<String, Object>();

                    // key : songId, value : �뷡 ���̵�
                    songAllInfo.put("songId", elem.attr("songId").toString());

                    // key : rank, value : ����
                    songAllInfo.put("rank", elem.select("td.number").first().text().toString().split(" ")[0]);

                    // key : smallImageUrl, value : ���� �̹��� url ��ũ
                    songAllInfo.put("smallImageUrl",
                            "https:" + elem.select("td").get(2).select("img").first().attr("src").toString());

                    // key : title, value : �뷡 ����
                    songAllInfo.put("title", elem.select("td.info").first().select("a").first().text().toString());

                    // key : artist, value : ���� �̸�
                    songAllInfo.put("artist", elem.select("td.info").first().select("a").get(1).text().toString());

                    // key : albumName, value : �ٹ� �̸�
                    songAllInfo.put("albumName", elem.select("td.info").first().select("a").get(2).text().toString());

                    // ������ JSONObject�� ��ȯ
                    JSONObject jsonSongInfo = new JSONObject(songAllInfo);

                    // JSONArray�� �� �߰�
                    _chartList.add(jsonSongInfo);
                    _songCount++;
                    //	progressMonitor.setProgress(songCount);
                }

                String url51_100 = genieDocument1_50.select("div.page-nav.rank-page-nav").first().select("a").get(1).attr("href").toString();

                // ���� ��Ʈ ���ῡ �ʿ��� header ���� �� ����
                Connection genieConnection51_100 = Jsoup.connect(_url + url51_100).header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .header("Sec-Fetch-User", "?1")
                        .header("Upgrade-Insecure-Requests", "1")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                        .method(Connection.Method.GET);

                // ���� �� ���������� �ܾ��
                Document genieDocument51_100 = genieConnection51_100.get();

                // 51~100���� ���� ������ �ҷ���
                Elements data51st100 = genieDocument51_100.select("table.list-wrap").first().select("tbody > tr.list");

                for (Element elem : data51st100) { // 51~100���� ���� ���� �Ľ�
                    // JSONObject�� �����͸� �ֱ� ���� �۾�
                    HashMap<String, Object> songAllInfo = new HashMap<String, Object>();

                    // key : songId, value : �뷡 ���̵�
                    songAllInfo.put("songId", elem.attr("songId").toString());

                    // key : rank, value : ����
                    songAllInfo.put("rank", elem.select("td.number").first().text().toString().split(" ")[0]);

                    // key : smallImageUrl, value : ���� �̹��� url ��ũ
                    songAllInfo.put("smallImageUrl", "https:" + elem.select("td").get(2).select("img").first().attr("src").toString());

                    // key : title, value : �뷡 ����
                    songAllInfo.put("title", elem.select("td.info").first().select("a").first().text().toString());

                    // key : artist, value : ���� �̸�
                    songAllInfo.put("artist", elem.select("td.info").first().select("a").get(1).text().toString());

                    // key : albumName, value : �ٹ� �̸�
                    songAllInfo.put("albumName", elem.select("td.info").first().select("a").get(2).text().toString());

                    // ������ JSONObject�� ��ȯ
                    JSONObject jsonSongInfo = new JSONObject(songAllInfo);

                    // JSONArray�� �� �߰�, �뷡 ���� ����
                    _chartList.add(jsonSongInfo);
                    _songCount++;
                    //progressMonitor.setProgress(songCount);
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
                Connection songDetailConnection = Jsoup.connect(_url).header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .header("Sec-Fetch-User", "?1")
                        .header("Upgrade-Insecure-Requests", "1")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                        .method(Connection.Method.GET);

                // � ���� ���� ���� �� �������� �ܾ��
                Document songDetailDocument = songDetailConnection.get();
                Element songDetailInfo = songDetailDocument.select("div.song-main-infos").first();

                Element songDetailAlbumInfo = songDetailInfo.select("div.info-zone").first();

                // key : imageUrl, value : ū �̹��� url ��ũ
                songAllInfo.put("imageUrl", "https:" + songDetailInfo.select("div.photo-zone > a").first().attr("href").toString());


                // key : genre, value : �뷡 �帣
                songAllInfo.put("genre", songDetailAlbumInfo.select("ul.info-data > li").get(2).select("span.value").first().text().toString());

                // key : songTime, value : ��� �ð�
                songAllInfo.put("songTime", songDetailAlbumInfo.select("ul.info-data > li").get(3).select("span.value").first().text().toString());

                // key : likeNum, value : ���ƿ� ����
                songAllInfo.put("likeNum", songDetailAlbumInfo.select("p.song-button-zone > span.sns-like > a.like.radius > em#emLikeCount").first().text().toString());

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
        // progressMonitorManager(parentComponent, genieChartParsingTitle, genieChartParsingMessage);
        _chartThread.start(); // Thread ����
        try {
            _chartThread.join(); // ChartDataParsingThread�� ����Ǳ������� ���
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } // chartDataParsing(Component parentComponent)
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
        _url = "https://www.genie.co.kr/detail/songInfo?xgnm=" + obj.get("songId").toString(); // �Ľ��� url�� ����
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
    } // songDetailDataParsing(JSONObject jObj, Component parentComponent)
    // ���ϴ� �߸���(releaseDate)�� �� ���������� �������� �ʾ� getReleaseDate �޼ҵ尡 ����

    // songDetailDataParsing �Ŀ��� ��밡���� �޼ҵ�
    public String getGenre() { // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ� �� ���� �帣�� ��ȯ�ϴ� �޼ҵ�
        if (!isParsed()) { // �Ľ��� �̷�����ٸ�
            System.out.println(_isNotParsed);
            return null;
        }
        if (_songCount == 1) // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ�
            return _songDetailInfo.get("genre").toString();

        System.out.println("getGenre() : " + _isOnlyDetailParse);
        return null;
    } // String getGenre()

    // songDetailDataParsing �Ŀ��� ��밡���� �޼ҵ�
    public String getGenre(JSONObject jObj) { // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ� JSONObject�� �̿��Ͽ� �� ���� �帣�� ��ȯ�ϴ� �޼ҵ�
        if (!isParsed()) { // �Ľ��� �̷�����ٸ�
            System.out.println(_isNotParsed);
            return null;
        }

        if (jObj == null) {
            System.out.println(_plzUseRightJSONObject);
            return null;
        }

        if (_songCount == 1) { // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ�
            if (jObj.containsKey("genre")) // genre key���� ���� ��ȿ�� �˻�
                return jObj.get("genre").toString();
            else {
                System.out.println(_jsonDontHaveKey);
                return null;
            }
        }

        System.out.println("getGenre(JSONObject jObj) : " + _isOnlyDetailParse);
        return null;
    } // String getGenre(JSONObject jObj)

    // songDetailDataParsing �Ŀ��� ��밡���� �޼ҵ�
    public String getSongTime() { // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ� �� ���� ��� �ð��� ��ȯ�ϴ� �޼ҵ�
        if (!isParsed()) { // �Ľ��� �̷�����ٸ�
            System.out.println(_isNotParsed);
            return null;
        }
        if (_songCount == 1) // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ�
            return _songDetailInfo.get("songTime").toString();

        System.out.println("getSongTime() : " + _isOnlyDetailParse);
        return null;
    } // String getSongTime()

    // songDetailDataParsing �Ŀ��� ��밡���� �޼ҵ�
    public String getSongTime(JSONObject jObj) { // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ� JSONObject�� �̿��Ͽ� �� ���� ��� �ð��� ��ȯ�ϴ� �޼ҵ�
        if (!isParsed()) { // �Ľ��� �̷�����ٸ�
            System.out.println(_isNotParsed);
            return null;
        }

        if (jObj == null) {
            System.out.println(_plzUseRightJSONObject);
            return null;
        }

        if (_songCount == 1) // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ�
            return jObj.get("songTime").toString();

        System.out.println(_jsonDontHaveKey);
        return null;
    } // String getSongTime(JSONObject jObj)

    // songDetailDataParsing �Ŀ��� ��밡���� �޼ҵ�
    public String getLikeNum() { // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ� �� ���� ���ƿ� ������ ��ȯ�ϴ� �޼ҵ�
        if (!isParsed()) { // �Ľ��� �̷�����ٸ�
            System.out.println(_isNotParsed);
            return null;
        }
        if (_songCount == 1) // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ�
            return _songDetailInfo.get("likeNum").toString();

        System.out.println("getLikeNum() : " + _isOnlyDetailParse);
        return null;
    } // String getLikeNum()

} // GenieChartParser class