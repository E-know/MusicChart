package model

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class MelonAlbumCommentParser : CommentParser{
	//Properties
	private var driver: WebDriver? = null
	private val WEB_DRIVER_ID = "webdriver.chrome.driver"
	private val WEB_DRIVER_PATH = "src/driver/chromedriver.exe"
	private var base_url: String? = null

	init {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH)
		driver = ChromeDriver()
	}

	override fun crawl() {
		if (base_url == null) {
			println("Url is null")
			return
		}
		try {
			//get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
			driver!!.get(base_url)
			val html = driver!!.pageSource
			val doc: Document = Jsoup.parseBodyFragment(html)
			//print(doc.body().select("div.cmt_cont"))
			val arr = doc.body().select("div.cntt").text().split("내용").toMutableList()
			arr.forEach {
				if (it.contains("재생 다운로드 곡명")) {
					val index = it.indexOf("재생 다운로드 곡명")
					print("++++")
					print(it.substring(0,index))
					println("++++")
				}
			}
			for (ele in arr.subList(3, arr.lastIndex - 1)) {
				println(ele)
			}
			//System.out.println(driver.getPageSource());
		} catch (e: Exception) {
			e.printStackTrace()
		} finally {
			driver!!.close()
		}
	}

	fun setAlbumUrl(str: String): Unit {
		base_url = "https://www.melon.com/album/detail.htm?albumId=$str";
	}


}