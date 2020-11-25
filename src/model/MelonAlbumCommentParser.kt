package model

import main.AppManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class MelonAlbumCommentParser(var driver: WebDriver) {
	//Properties
	private val WEB_DRIVER_ID = "webdriver.chrome.driver"
	private val WEB_DRIVER_PATH = "src/driver/chromedriver.exe"
	private var base_url: String? = "https://www.melon.com/album/detail.htm?albumId="

	init {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH)
	}

	fun crawl() : MutableMap<String,List<String>>{
		val result = mutableMapOf<String, List<String>>()
		val _setAlbumID = mutableSetOf<String>()

		if (base_url == null) {
			println("Url is null")
			return result
		}

		for (i in 1..100) {
			if(!ChartData.getS_instance().melonChartParser.isParsed)
				ChartData.getS_instance().melonChartParser.chartDataParsing(null)
			_setAlbumID.add(ChartData.getS_instance().melonChartParser.getAlbumID(i).filter { it in '0'..'9' })
		}

		try {
			var doc: Document
			var arr: MutableList<String>
			var html: String

			for (id in _setAlbumID) {
				driver.get(base_url + id)
				html = driver.pageSource
				doc = Jsoup.parseBodyFragment(html)
				arr = doc.body().select("div.cntt").text().split("내용").toMutableList()
				println(id)
				result[id] = arr.refine()
			}

			for (ele in result) {
				println("KEY : ${ele.key}")
				for (str in ele.value)
					println(str)
			}

		} catch (e: Exception) {
			e.printStackTrace()
		}

		return result
	}


	fun MutableList<String>.refine(): MutableList<String> {
		this.replaceAll {
			if (it.contains("재생 다운로드 곡명"))
				it.substring(5, it.indexOf("재생 다운로드 곡명"))
			else if (it.contains("NEW"))
				it.substring(5)
			else
				it.substring(1)
		}
		return this.subList(3, 8)
	}
}