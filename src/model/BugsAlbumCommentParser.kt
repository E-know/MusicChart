package model

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class BugsAlbumCommentParser(var driver: WebDriver) {
	//System Property SetUp //Driver setup
	//Properties
	private val WEB_DRIVER_ID = "webdriver.chrome.driver"
	private val WEB_DRIVER_PATH = "src/driver/chromedriver.exe"
	private var base_url: String? = "https://music.bugs.co.kr/album/20361954?wl_ref=list_tr_11_chart"

	init {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH)
	}

	fun crawl(): MutableMap<String, List<String>> {
		val result = mutableMapOf<String, List<String>>()
		val _setAlbumID = mutableSetOf<String>()

		if (base_url == null) {
			println("Url is null")
			return result
		}

		for (i in 1..100) {
			if (!ChartData.getS_instance().bugsChartParser.isParsed)
				ChartData.getS_instance().bugsChartParser.chartDataParsing(null)
			_setAlbumID.add(ChartData.getS_instance().bugsChartParser.getAlbumID(i).filter { it in '0'..'9' })
		}

		try {
			var doc: Document
			var arr: Elements
			var html: String

			for (id in _setAlbumID) {
				do {
					println(id)
					driver.get("https://music.bugs.co.kr/album/${id}?wl_ref=list_tr_11_chart")
					html = driver.pageSource
					doc = Jsoup.parseBodyFragment(html)
					arr = doc.select("p[name=\"comment\"]")
					if(!arr.any())
						println("Re $id")
				}while (!arr.any())

				val strarr = mutableListOf<String>()
				for (i in 0 until arr.size) {
					strarr.add(arr[i].text())
					if (i == 4)
						break
				}
				result[id] = strarr
			}


			for (ele in result) {
				println("Bugs - Key : ${ele.key}")
				for (str in ele.value)
					println(str)
			}


		} catch (e: Exception) {
			e.printStackTrace()
		}

		return result
	}
}