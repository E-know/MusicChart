package main;

import model.*;

public class Test {
    public static void main(String[] args) {
        //MELON
        /*
        MelonChartParser melon = new MelonChartParser();
        melon.isParsed();
        melon.chartDataParsing(null);
        System.out.println(melon.getAlbumUrl(1));

        MelonAlbumCommentParser com = new MelonAlbumCommentParser();
        com.setAlbumUrl(melon.getAlbumUrl(1));
        com.crawl();
*/

        //BUGS
        /*
        BugsAlbumCommentParser bugs = new BugsAlbumCommentParser();
        bugs.crawl();

        BugsChartParser b = new BugsChartParser();
        b.chartDataParsing(null);
        System.out.print(b.getAlbumUrl(1));
*/

        //GENIE
        GenieAlbumCommentParser genie = new GenieAlbumCommentParser();
        genie.crawl();
    }
}
