package main

import model.commentParser.MelonAlbumCommentParser
import org.openqa.selenium.chrome.ChromeDriver

fun main() {

	val WEB_DRIVER_ID = "webdriver.chrome.driver"
	val WEB_DRIVER_PATH = "src/driver/chromedriver.exe"
	System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH)
	val driver = ChromeDriver()

	println("=============MELON=============")
	val m = MelonAlbumCommentParser(driver)
	m.crawl()


/*	println("=============BUGS=============")
	val b = BugsAlbumCommentParser(driver)
	b.crawl()*/
/*	println("=============GENIE=============")
	val g = GenieAlbumCommentParser(driver)
	g.crawl()*/

/*	ChartData.getS_instance().melonChartParser.chartDataParsing(null)
	val bm = BugsAlbumCommentParser()
	bm.crawl()*/


	driver.close()
}