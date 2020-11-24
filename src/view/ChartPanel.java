package view;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.*;

import controller.ChartPanelController;
import model.ChartData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//encoding: UTF-8
@SuppressWarnings("serial")
public class ChartPanel extends JPanel {
	// - - - - - 인스턴스 데이터 - - - - -
	//차트 위에 표시되는 제목
	private JLabel _lblTitle;
	
	//차트 제공자를 나타내는 문자열("Melon" 또는 "Bugs" 또는 "Genie")
	private String _strChartName;
	
	//음악 차트를 담는 표
	public JTable _tableChart;
	
	//표의 모델(셀의 크기, 개수, 표시 자료형 등을 결정)
	public ChartModel _tableModel;
	
	//표에서 정렬 및 필터링 기능을 담당
	private TableRowSorter<ChartModel> _tableSorter;

    //배경색과 전경색
	private static final Color _ColorTitle = new Color(52,54,84);
	private static final Color _ColorTextColor = Color.black;
	private static final Color _ColorLblBackground = new Color(234, 234, 234);
	private static final Color _ColorListBackground = Color.white;
	
	//이벤트 리스너 객체

	// - - - - - 생성자 - - - - -
	public ChartPanel() {
		_strChartName = "Melon"; //프로그램 실행 직후 Melon 차트를 표시하기 위함
		
		setBackground(_ColorLblBackground);
		setLayout(null);
		setFont(new Font("맑은 고딕", Font.BOLD, 64));
		
		setInitLblTitle();
		setInitTableChart();
		setInitScrollBar();

		new ChartPanelController(this);
	} //생성자 끝

    private void setInitLblTitle(){
        _lblTitle = new JLabel(_strChartName + " TOP 100");
        _lblTitle.setBackground(Color.white);
        _lblTitle.setForeground(_ColorTitle);
        _lblTitle.setBounds(80, 30, 920, 80);
        _lblTitle.setFont(new Font("배달의민족 도현", Font.BOLD, 48));
        _lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        _lblTitle.setVerticalAlignment(SwingConstants.CENTER);
        add(_lblTitle);
    }

    private void setInitTableChart(){
	    setInitTableModel();
	    setInitTableSorter();

        _tableChart = new JTable(_tableModel);
        _tableChart.setBackground(_ColorListBackground);
        _tableChart.setForeground(_ColorTextColor);
        _tableChart.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        _tableChart.setRowHeight(60);
        makeTable();
        _tableChart.setRowSorter(_tableSorter);
        add(_tableChart);

    }

    private void setInitTableSorter(){
        _tableSorter = new TableRowSorter<ChartModel>(_tableModel);
        _tableSorter.setComparator(0, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        }); //표에서 순위를 기준으로 정렬되도록 설정(값이 작을수록 위에 있음)
    }

    private void setInitScrollBar(){
        //스크롤바 제공, 스크롤 가능한 요소(여기서는 tableChart)를 가진다
        JScrollPane _scrollBar = new JScrollPane(_tableChart, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        _scrollBar.setBounds(40, 130, 1000, 540);
        add(_scrollBar);
    }

    private void setInitTableModel(){
        if(!ChartData.getS_instance().getParser().isParsed())
			ChartData.getS_instance().getParser().chartDataParsing(this); //Melon 차트 정보 받아옴

        _tableModel = new ChartModel(ChartData.getS_instance().getParser().getChartList());
    }
    /*
	Name: buildTable
	Parameter: -
	Returns: -
	Description: 셀에 항목을 표시하기 전에 기본적인 셀 속성 설정
	*/
	private void makeTable() {
	    //index 0 - Ranking
	    _tableChart.getColumnModel().getColumn(0).setResizable(false);
        _tableChart.getColumnModel().getColumn(0).setPreferredWidth(10);
        //index 1 - Album image
		_tableChart.getColumnModel().getColumn(1).setResizable(false);
		_tableChart.getColumnModel().getColumn(1).setPreferredWidth(60);
        //index 2 - Title
		_tableChart.getColumnModel().getColumn(2).setResizable(false);
		_tableChart.getColumnModel().getColumn(2).setPreferredWidth(280);
		_tableChart.getColumnModel().getColumn(2).setMinWidth(100);
        //index 3 - Singer
		_tableChart.getColumnModel().getColumn(3).setResizable(false);
		_tableChart.getColumnModel().getColumn(3).setPreferredWidth(10);
		_tableChart.getColumnModel().getColumn(3).setMinWidth(80);

		_tableChart.getColumnModel().getColumn(4).setResizable(false);
		_tableChart.getColumnModel().getColumn(4).setPreferredWidth(40);
	} //method used in constructor & dataChange
	
	// - - - - - getter & setter - - - - -
	public JLabel get_lblTitle() {
		return _lblTitle;
	}
	public String get_strChartName() {
		return _strChartName;
	}
	public void set_strChartName(String name) {
		_strChartName = name;
	}
	public JTable get_tableChart() {
		return _tableChart;
	}
	public ChartModel get_tableModel() {
		return _tableModel;
	} //getter & setter 끝
	
	/*
	Name: changeData
	Parameter: -
	Returns: -
	Description: 다른 사이트의 차트를 표시하거나 새로고침할 때 표시되는 내용을 변경 
	*/
	public void changeData() {
		switch(ChartData.getS_instance().getSite_M_B_G()){
			case 1:
				_strChartName = "Melon";
				break;
			case 2:
				_strChartName = "Bugs";
				break;
			case 3:
				_strChartName = "Genie";
				break;
		}
		_lblTitle.setText(_strChartName + " TOP 100");
		_tableModel.setContents(ChartData.getS_instance().getParser().getChartList());
		makeTable();
		_tableChart.repaint();
	}

	/*
	Name: filter
	Parameter: (String) 검색할 내용, (int) 검색 요소로 제목(2) 또는 가수(3)
	Returns: -
	Description: 검색 결과에 맞는 곡만 표시되도록 설정
	*/
	public void filterTitleANDArtist(String text, int criteria) {
		if(text == null) 
		    _tableSorter.setRowFilter(null); //검색 중이 아닌 경우 모든 항목을 표시
		else 
		    _tableSorter.setRowFilter(RowFilter.regexFilter(text, criteria)); //실제 검색 실행하여 결과 표시
	}
	
	// - - - - - ChartModel 클래스 - - - - -
	public class ChartModel extends AbstractTableModel {
		// - - - - - 인스턴스 데이터 - - - - -
		//각각의 행 이름을 저장하는 배열
		private String[] arrColumnName;
		//각각의 셀의 항목을 저장하는 배열
		private Object[][] chartData;
		
		// - - - - - 생성자 - - - - -
		public ChartModel(JSONArray musics) {
			arrColumnName = new String[5];
			arrColumnName[0] = "Rank";
			arrColumnName[1] = "Album Image";
			arrColumnName[2] = "Title";
			arrColumnName[3] = "Singer";
			arrColumnName[4] = "Album Title";
			
			chartData = new Object[musics.size()][5];
			for(int i = 0; i < musics.size(); i++) chartData[i] = new Object[5];
			setContents(musics);
		} //생성자 끝
		
		/*
		Name: setContents
		Parameter: (JSONArray) 항목이 들어있음
		Returns: -
		Description: 표에서 표시할 항목 설정
		*/
		private void setContents(JSONArray musics) {
			for(int i = 0; i < musics.size(); i++) {
				JSONObject obj = (JSONObject) musics.get(i);
				chartData[i][0] = Integer.parseInt((String) (obj.get("rank")));
				try {
					ImageIcon loadedImage = new ImageIcon(new URL((String) obj.get("smallImageUrl"))); //지정된 URL로부터 이미지를 받아옴
					chartData[i][1] = new ImageIcon(loadedImage.getImage().getScaledInstance(50, 50, Image.SCALE_FAST)); //받은 이미지를 50 * 50 크기로 변환하여 사용
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				chartData[i][2] = (String) obj.get("title");
				chartData[i][3] = (String) obj.get("artist");
				chartData[i][4] = (String) obj.get("albumName");
			}
		}
		
		public Object[][] getChartData() {
			return chartData;
		}
		public Object[] getMusicData(int index) {
			return chartData[index];
		}
		@Override
		public int getColumnCount() {
			return arrColumnName.length;
		}
		@Override
		public int getRowCount() {
			return chartData.length;
		}
		@Override
		public Object getValueAt(int row, int column) {
			return chartData[row][column];
		}
		@Override
		public void setValueAt(Object value, int row, int column) {
			chartData[row][column] = value;
		}
		@Override
		public String getColumnName(int column) {
			return arrColumnName[column];
		}
		@Override
		public Class<?> getColumnClass(int column) {
			switch(column) {
			case 0: //순위
				return Integer.class;
			case 1: //이미지
				return ImageIcon.class;
			case 2: //제목
			case 3: //가수
			case 4: //앨범
				return String.class;
			default:
				return Object.class;
			}
		}
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
	} //ChartModel 클래스 끝

	public void addClickListener(MouseListener listenForMouse) {
		_tableChart.addMouseListener((listenForMouse));
	}

	// - - - - - ClickListener 클래스 - - - - -

}
