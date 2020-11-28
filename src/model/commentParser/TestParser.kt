package model.commentParser

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

fun main() {
	val t = TestParser()
	t.crawl()
}

class TestParser {
	private var driver: WebDriver? = null
	private val WEB_DRIVER_ID = "webdriver.chrome.driver"
	private val WEB_DRIVER_PATH = "src/driver/chromedriver.exe"
	private var base_url: String? = "https://www.melon.com/album/detail.htm?albumId=10479150"

	init {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH)
		driver = ChromeDriver()
	}

	fun crawl() {
		if (base_url == null) {
			println("Url is null")
			return
		}
		try {
			//get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
			base_url = 	"https://www.genie.co.kr/detail/albumInfo?axnm=81545399"
			driver!!.get(base_url)
			var html = driver!!.pageSource
			var doc: Document = Jsoup.parseBodyFragment(html)
			var arr = doc.select("div.reply-text > p")
			println(arr)
		} catch (e: Exception) {
			e.printStackTrace()
		} finally {
			driver!!.close()
		}
	}
}