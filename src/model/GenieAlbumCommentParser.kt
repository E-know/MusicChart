package model

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.lang.Thread.sleep

class GenieAlbumCommentParser(val driver: WebDriver) {
	//Properties
	private val WEB_DRIVER_ID = "webdriver.chrome.driver"
	private val WEB_DRIVER_PATH = "src/driver/chromedriver.exe"
	private val base_url: String = "https://www.genie.co.kr/detail/albumInfo?axnm="

	init {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH)
	}

	fun crawl(): MutableMap<String, List<String>> {
		val result = mutableMapOf<String, List<String>>()
		val _setAlbumID = mutableSetOf<String>()

		for (i in 1..100) {
			if (!ChartData.getS_instance().genieChartParser.isParsed)
				ChartData.getS_instance().genieChartParser.chartDataParsing(null)
			_setAlbumID.add(ChartData.getS_instance().genieChartParser.getAlbumID(i))
		}

		try {
			var doc: Document
			var arr: Elements
			var html: String
			var sleep_Flag = false
			for (id in _setAlbumID) {
				do {
					driver.get("https://www.genie.co.kr/detail/albumInfo?axnm=${id}")
					if(sleep_Flag) {
						sleep_Flag = false
						sleep(500)
					}
					html = driver.pageSource
					doc = Jsoup.parseBodyFragment(html)
					arr = doc.select("div.reply-text > p")
					if(!arr.any())
						sleep_Flag = true
					else
						println(id)
				} while (!arr.any())


				val strarr = mutableListOf<String>()
				for(i in 0 until arr.size){
					strarr.add(arr[i].text())
					if(i == 4)
						break
				}

				result[id] = strarr

			}

			for(ele in result){
				println("Key : ${ele.key}")
				for(str in ele.value)
					println(str)
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}

		return result
	}


}