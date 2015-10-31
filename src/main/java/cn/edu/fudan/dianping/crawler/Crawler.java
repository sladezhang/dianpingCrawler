package cn.edu.fudan.dianping.crawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

/**
 * Created by Dawnwords on 2015/10/23.
 */
public abstract class Crawler {

    private String nextUrlSelector;

    public Crawler(String nextUrlSelector) {
        this.nextUrlSelector = nextUrlSelector;
    }

    public void crawl(String startUrl) {
        boolean hasNextUrl;
        boolean error = false;
        WebClient client = client();
        do {
            long timeToSleep = 1;
            hasNextUrl = false;
            while (true) {
                try {
                    HtmlPage page = client.getPage(startUrl);
                    parseDocument(page);
                    HtmlAnchor nextPage = page.getFirstByXPath(nextUrlSelector);
                    if (nextPage != null) {
                        hasNextUrl = true;
                        nextPage.click();
                    }
                    if (error) {
                        System.out.println("\n--------------------------");
                        error = false;
                    }
                    break;
                } catch (IOException e) {
                    System.out.printf("fetch:%s\t%s\n", startUrl, e.getMessage());
                    error = true;
                    client.getCookieManager().clearCookies();
                    timeToSleep = sleep(timeToSleep);
                }
            }
        } while (hasNextUrl);
    }

    protected abstract void parseDocument(HtmlPage page);

    private WebClient client() {
        WebClient client = new WebClient();
        client.setJavaScriptTimeout(0);
        return client;
    }

    private long sleep(long time) {
        long nextSleepTime = (long) (Math.log(time + 1) * (1 + Math.random()) / Math.log(3) * 20);
        System.out.printf("sleep:%d\t", nextSleepTime);
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException ignored) {
        }
        return nextSleepTime;
    }
}
