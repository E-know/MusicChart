package model

import jdk.internal.net.http.common.Log
import main.AppManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.util.logging.Logger

class MelonAlbumCommentParser(var driver: WebDriver) {
	//Properties
	private val WEB_DRIVER_ID = "webdriver.chrome.driver"
	private val WEB_DRIVER_PATH = "src/driver/chromedriver.exe"
	private var base_url: String = "https://www.melon.com/album/detail.htm?albumId="

	init {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH)
	}

	fun crawl() : MutableMap<String,List<String>>{
		val result = mutableMapOf<String, List<String>>()
		val _setAlbumID = getAlbumIDtoSet()

		try {
			var doc: Document
			var arr: MutableList<String>
			var html: String

			for (id in _setAlbumID) {
				driver.get(base_url + id)
				html = driver.pageSource
				doc = Jsoup.parseBodyFragment(html)
				arr = doc.body().select("div.cntt").text().split("내용").toMutableList()
				result[id] = arr.refine()
			}

			for (ele in result) {
				Logger.getGlobal().info("Melon-Key${ele.key}")
				for (str in ele.value)
					Logger.getGlobal().info(str)
			}

		} catch (e: Exception) {
			e.printStackTrace()
		}

		return result
	}


	private fun MutableList<String>.refine(): MutableList<String> {
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

	private fun getAlbumIDtoSet(): Set<String> {
		val result = mutableSetOf<String>()
		for (i in 1..100) {
			if(!ChartData.getS_instance().melonChartParser.isParsed)
				ChartData.getS_instance().melonChartParser.chartDataParsing(null)
			result.add(ChartData.getS_instance().melonChartParser.getAlbumID(i).filter { it in '0'..'9' })
		}
		return result
	}
}