package controller.commentParser

import model.ChartData
import org.openqa.selenium.WebDriver
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.lang.Thread.sleep

class GenieAlbumCommentParser(val driver: WebDriver) {
	init {
		System.setProperty("webdriver.chrome.driver", "src/driver/chromedriver.exe")
	}

	fun crawl(): MutableMap<String, List<String>> {
		val result = mutableMapOf<String, List<String>>()
		val _setAlbumID = getAlbumIDtoSet()

		try {
			var doc: Document
			var arr: Elements
			var html: String
			var sleep_Flag = false
			for (id in _setAlbumID) {
				do {
					driver.get("https://www.genie.co.kr/detail/albumInfo?axnm=${id}")
					sleep(1000);
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
					strarr.add(arr[i].text().filter { it in '°¡'..'ÆR' || it.toInt() in 0..127})
					if(i == 4)
						break
				}
				result[id] = strarr

			}

			for(ele in result){
				println("Genie - Key : ${ele.key}")
				for(str in ele.value)
					println(str)
			}

		} catch (e: Exception) {
			e.printStackTrace()
		}

		return result
	}

	private fun getAlbumIDtoSet(): Set<String> {
		val result = mutableSetOf<String>()
		for (i in 1..100) {
			if (!ChartData.getS_instance().genieChartParser.isParsed)
				ChartData.getS_instance().genieChartParser.chartDataParsing(null)
			result.add(ChartData.getS_instance().genieChartParser.getAlbumID(i).filter { it in '0'..'9' })
		}
		return result
	}
}