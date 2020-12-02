package controller.commentParser

import model.ChartData
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

fun main() {
	ChartData.getS_instance().bugsChartParser.chartDataParsing(null);
	val c = CrwalCommentTest()
	c.crawl()
}

class CrwalCommentTest {
	//Properties
	private var driver: WebDriver? = null
	private val WEB_DRIVER_ID = "webdriver.chrome.driver"
	private val WEB_DRIVER_PATH = "src/driver/chromedriver.exe"
	private var base_url: String? = "https://music.bugs.co.kr/album/20356321?wl_ref=list_tr_11_chart"

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
			//get page (= ���������� url�� �ּ�â�� ���� �� request �� �Ͱ� ����)
			driver!!.get(base_url)
			val html = driver!!.pageSource
			val doc: Document = Jsoup.parseBodyFragment(html)
			val arr = doc.select("p[name=\"comment\"]").toMutableList()

			for(ele in arr)
				println(ele)

		} catch (e: Exception) {
			e.printStackTrace()
		} finally {
			driver!!.close()
		}
	}

}