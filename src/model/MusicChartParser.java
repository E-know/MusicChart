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
 * @author SejongUniv ��â��
 * @version 1.7
 **/

public abstract class MusicChartParser {

    protected int _songCount = 0;            // �뷡 ������ ���
    protected String _url;                    // �Ľ��� �� ����Ʈ url
    protected JSONArray _chartList;            // ��Ʈ 100� ���� ������ ���� JSONArray, JSONObject 100���� �̷��������
    protected JSONObject _songDetailInfo;    // �뷡 �� ���� �� ������ ���� JSONObject, ��Ʈ 100����� ���� �� ���� �����鸸 ������

    // �Ľ� ������ ������ ���ڿ���
    protected String _isNotParsed = "�Ľ��� ���������� �̷������ �ʾҽ��ϴ� :(";
    protected String _isOnlyChartParse = "�ش� �޼ҵ�� ��Ʈ �Ľ̿��� ��밡���� �޼ҵ� �Դϴ� :(";
    protected String _isOnlyDetailParse = "�ش� �޼ҵ�� �뷡 1���� �� ���� �Ľ̿��� ��밡���� �޼ҵ� �Դϴ� :(";
    protected String _jsonDontHaveKey = "JSONObject ���� �ش� Ű ���� �����ϴ� :(";
    protected String _plzUseRightJSONObject = "�ùٸ� JSONObject ���� ������ּ��� :(";

    // ��Ʈ 100���� �Ľ��ϴ� abstract �޼ҵ�, �� ���� ����Ʈ �ļ����� �ʼ��� �ٸ��� �����ؾ� ��
    // parentComponent�� JPanel, JFrame ���� ������ �Ľ��� �ϸ鼭 �ش� Ŭ������ �ε�â�� �����
    // ProgressMonitor�� ��������� �� ���� ����ϸ� ���װ� �־� ProgressMonitor �κи� �ּ�ó�� �صξ���, ���� ���� ������ �Ʒ��� �������
    public abstract void chartDataParsing(Component parentComponent);

    // �뷡 �� ���� �� ������ �Ľ��ϴ� abstract �޼ҵ�, ���� parameter���� ������
    // parentComponent�� JPanel, JFrame ���� ������ �Ľ��� �ϸ鼭 �ش� Ŭ������ �ε�â�� �����
    // ProgressMonitor�� ��������� �� ���� ����ϸ� ���װ� �־� ProgressMonitor �κи� �ּ�ó�� �صξ���, ���� ���� ������ �Ʒ��� �������
    //public abstract void songDetailDataParsing(String songId, Component parentComponent);

    public abstract void songDetailDataParsing(JSONObject jObj, Component parentComponent);

    protected Thread _chartThread;                // ��Ʈ 100���� �Ľ��� �� ����� Thread
    protected Thread _songDetailThread;            // �뷡 �� � ���� �� ������ �Ľ��� �� ����� Thread

    /*
     * @deprecated
     * �Ľ� �߿� �ε�â���� ����� ProgressMonitor
     * �ٵ� �̰� ����ϸ� Thread�� ������� �ʴ� ������ ���ϴ� ��� ProgressMonitor�� ������ �ʴ� ���װ� �־� Deprecated ó���س�����
     * ProgressBar ���� Ŀ�����ؼ� ����ؾ� �� ��, ���� �ļ������� �ּ�ó�� �صξ���
     */
    @Deprecated
    protected ProgressMonitor progressMonitor;

    public boolean isParsed() { // �Ľ��� �̷�������� �Ǵ��ϴ� �޼ҵ�
        // chartDataParsing()�̳� songDetailDataParsing()�� �� ���̶� ȣ�������� songCount�� 1 �̻���
        if (_songCount == 0)
            return false;
        else
            return true;
    } // boolean isParsed()

    public JSONArray getChartList() { // ��Ʈ 100� ���� ������ ��� JSONArray�� chartList�� ��ȯ�ϴ� �޼ҵ�
        if (!isParsed()) {// �Ľ��� �� ���� ������
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1) { // �뷡 �� � ���� �� �Ľ��� �ߴٸ�
            System.out.println("getChartList() : " + _isOnlyChartParse);
            return null;
        }

        return _chartList;
    } // JSONArray getChartList()

    public String getTitle(int rank) { // �뷡 ������ ���� �ش� �뷡�� ������ ��ȯ�ϴ� �޼ҵ�
        if (rank < 1 || rank > 100) { // 1 <= rank <= 100�� ����� �������
            System.out.println("1~100�� �̳��� ������ �Է����ּ���");
            return null;
        }

        if (!isParsed()) { // �Ľ��� �̷������ �ʾҴٸ�
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1) { // �뷡 �� � ���� �� ���� �Ľ��� �̷�����ٸ� null ��ȯ(�� �������� ������ ���� ����)
            System.out.println("getTitle(int rank) : " + _isOnlyChartParse);
            return null;
        } else
            return ((JSONObject) _chartList.get(rank - 1)).get("title").toString();
    } // String getTitle(int rank)

    public String getArtistName(int rank) { // �뷡 ������ ���� �ش� �뷡�� ���� �̸��� ��ȯ�ϴ� �޼ҵ�
        if (rank < 1 || rank > 100) { // 1 <= rank <= 100�� ����� �������
            System.out.println("1 ~ 100�� �̳��� ������ �Է����ּ���");
            return null;
        }

        if (!isParsed()) { // �Ľ��� �̷������ �ʾҴٸ�
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1) { // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ�
            System.out.println("getArtistName(int rank) : " + _isOnlyChartParse);
            return null;
        }
        return ((JSONObject) _chartList.get(rank - 1)).get("artist").toString();
    } // String getArtistName(int rank)

    public String getAlbumName(int rank) { // �뷡 ������ ���� �ش� �뷡�� �ٹ� �̸��� ��ȯ�ϴ� �޼ҵ�
        if (rank < 1 || rank > 100) { // 1 <= rank <= 100�� ����� �������
            System.out.println("1~100�� �̳��� ������ �Է����ּ���");
            return null;
        }
        if (!isParsed()) { // �Ľ��� �̷������ �ʾҴٸ�
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1) { // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ�
            System.out.println("getAlbumName(int rank) : " + _isOnlyChartParse);
            return null;
        }

        return ((JSONObject) _chartList.get(rank - 1)).get("albumName").toString();
    } // String getArtistName(int rank)

    public String getSongId(int rank) { // �뷡 ������ ���� �ش� �뷡�� �ٹ� �̸��� ��ȯ�ϴ� �޼ҵ�
        if (rank < 1 || rank > 100) { // 1 <= rank <= 100�� ����� �������
            System.out.println("1~100�� �̳��� ������ �Է����ּ���");
            return null;
        }

        if (!isParsed()) { // �Ľ��� �̷������ �ʾҴٸ�
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1) { // �뷡 �� � ���� �� �Ľ��� �̷�����ٸ�
            System.out.println("getSongId(int rank) : " + _isOnlyChartParse);
            return null;
        }

        return ((JSONObject) _chartList.get(rank - 1)).get("songId").toString();
    } // String getSongId(int rank)

    public String getImageUrl(int rank) { // �뷡 ������ ���� �ش� �뷡�� �̹��� url�� ��ȯ�ϴ� �޼ҵ�
        if (rank < 1 || rank > 100) { // 1 <= rank <= 100�� ����� �������
            System.out.println("1~100�� �̳��� ������ �Է����ּ���");
            return null;
        }

        if (!isParsed()) { // �Ľ��� �̷������ �ʾҴٸ�
            System.out.println(_isNotParsed);
            return null;
        }

        if (_songCount == 1)
            return _songDetailInfo.get("imageUrl").toString(); // �뷡 �� � ���� �� ���� �Ľ��� �̷�����ٸ� �� ���� ū �̹��� url�� ��ȯ(�� ������ ������ ����� ����)
        else
            return ((JSONObject) _chartList.get(rank - 1)).get("smallImageUrl").toString(); // ��Ʈ 100� ���� �Ľ��� �̷�����ٸ� JSONArray�� �ִ� �뷡�� �� ������ �´� ������ ���� �̹��� url�� ��ȯ
    } // String getImageUrl(int rank)

    // getGenre(), getGenre(JSONObject jObj)�� MelonChartParser�� GenieChartParser������ ��밡���ϹǷ� �߻�Ŭ���������� ���ܵ�
    // getReleaseDate(), getReleaseDate(JSONObject jObj)�� MelonChartParser������ ��밡���ϹǷ� �߻�Ŭ���������� ���ܵ�
} // MusicChartParser class