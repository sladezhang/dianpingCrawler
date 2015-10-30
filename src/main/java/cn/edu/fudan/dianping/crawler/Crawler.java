package cn.edu.fudan.dianping.crawler;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Dawnwords on 2015/10/23.
 */
public abstract class Crawler {

    private String nextUrlSelector;


    public Crawler(String nextUrlSelector) {
        this.nextUrlSelector = nextUrlSelector;
    }


    public void crawl(String nextUrl) {
        do {
            long timeToSleepMilli = 1000;
            while (true) {
                System.out.println(nextUrl);

                try {
                    Response response = connection(nextUrl, Cookie.cookie()).execute();

                    Document document;
                    if (Cookie.cookie() == null) {
                        Cookie.cookie(response.cookies());
                    }
                    document = response.parse();
                    parseDocument(nextUrl, document);
                    nextUrl = nextUrl(nextUrl, document.select(nextUrlSelector));
                    break;
                } catch (IOException e) {
                    Cookie.clearCookie();
                    timeToSleepMilli = sleep(timeToSleepMilli);
                }
            }
        } while (nextUrl != null);
    }

    protected abstract void parseDocument(String referURL, Document document);

    private Connection connection(String commentUrl, Map<String, String> cookie) {
        Connection connection = Jsoup.connect(commentUrl)
                .timeout(30 * 1000)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        if (cookie != null) {
            connection = connection.cookies(cookie);
        }
        return connection;
    }

    private long sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {
        }
        return (long) (time * (2 + Math.random()));
    }

    private String nextUrl(String referURL, Elements nextPage) {
        if (nextPage.size() <= 0) {
            return null;
        }
        int qmIndex = referURL.indexOf('?');
        if (qmIndex > 0) {
            referURL = referURL.substring(0, qmIndex);
        }
        return referURL + nextPage.eq(0).attr("href");
    }
}
