package cn.edu.fudan.dianping.crawler;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Dawnwords on 2015/10/23.
 */
public abstract class Crawler {

    private String nextUrlSelector;


    public Crawler(String nextUrlSelector) {
        this.nextUrlSelector = nextUrlSelector;
    }


    public void crawl(String nextUrl) {
        String referUrl = null;
        do {
            long timeToSleep = 1;
            while (true) {
                System.out.printf("fetch:%s, refer:%s\t", nextUrl, referUrl);
                try {
                    Response response = connection(nextUrl, referUrl).execute();
                    Cookie.cookie(response.cookies());
                    Document document = response.parse();
                    parseDocument(nextUrl, document);
                    referUrl = nextUrl;
                    nextUrl = nextUrl(nextUrl, document.select(nextUrlSelector));
                    System.out.println("succeed!");
                    break;
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    Cookie.clearCookie();
                    timeToSleep = sleep(timeToSleep);
                }
            }
        } while (nextUrl != null);
    }

    protected abstract void parseDocument(String referURL, Document document);

    private Connection connection(String commentUrl, String referUrl) {
        Connection connection = Jsoup.connect(commentUrl)
                .timeout(30 * 1000)
                .header("Upgrade-Insecure-Requests", "1")
                .header("pragma", "no-cache")
                .header("cache-control", "no-cache")
                .header("Accept-Language", "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4")
                .header("Accept-Encoding", "gzip, deflate, sdch")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        if (referUrl != null) {
            connection.header("Referer", referUrl);
        }
        if (Cookie.cookie() != null) {
            connection = connection.cookies(Cookie.cookie());
        }
        return connection;
    }

    private long sleep(long time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException ignored) {
        }
        long nextSleepTime = (long) (Math.log(time + 1) * (1 + Math.random()) / Math.log(3) * 20);
        System.out.printf("sleep:%d\t", nextSleepTime);
        return nextSleepTime;
    }

    private String nextUrl(String referURL, Elements nextPage) {
        if (nextPage.size() <= 0) {
            return null;
        }
        int qmIndex = referURL.indexOf('?');
        if (qmIndex > 0) {
            referURL = referURL.substring(0, qmIndex);
        }
        String href = nextPage.eq(0).attr("href");
        switch (href.charAt(0)) {
            case '?':
                return referURL + href;
            case '/':
                return referURL.substring(0, referURL.indexOf('/', "http://".length())) + href;
        }
        throw new IllegalArgumentException("unknown href" + href);
    }

}
