package model

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class GenieAlbumCommentParser : CommentParser {
	//Properties
	private var driver: WebDriver? = null
	private val WEB_DRIVER_ID = "webdriver.chrome.driver"
	private val WEB_DRIVER_PATH = "src/driver/chromedriver.exe"
	val albumnum = "81743752"
	private var base_url: String? = "https://www.genie.co.kr/detail/albumInfo?axnm=$albumnum"

	init {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH)
		driver = ChromeDriver()
	}

	override fun crawl() : Unit {
		if (base_url == null) {
			println("Url is null")
			return
		}
		try {
			//get page (= ���������� url�� �ּ�â�� ���� �� request �� �Ͱ� ����)
			driver!!.get(base_url)
			val html = driver!!.pageSource
			val doc: Document = Jsoup.parseBodyFragment(html)
			val arr = doc.select("div.reply-text > p")
			//val arr = doc.select("d.reply-text > p").toMutableList()
			//print(doc.select("div.reply-text > p"))

		} catch (e: Exception) {
			e.printStackTrace()
		} finally {
			driver!!.close()
		}
	}


}