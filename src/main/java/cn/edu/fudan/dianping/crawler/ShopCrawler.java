package cn.edu.fudan.dianping.crawler;

import cn.edu.fudan.dianping.bean.Shop;
import cn.edu.fudan.dianping.handler.FileCommentHandler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;

/**
 * Created by Dawnwords on 2015/10/30.
 */
public class ShopCrawler extends Crawler {
    private File outputDir;

    public ShopCrawler(File outputDir) {
        super(".next");
        this.outputDir = outputDir;
    }

    public static void main(String[] args) {
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8087");
        new ShopCrawler(new File("data/beijing")).crawl("http://www.dianping.com/search/category/2/10");
    }

    @Override
    protected void parseDocument(String referURL, Document document) {
        for (Element shopElement : document.select("#shop-all-list > ul > li")) {
            Shop shop = new Shop(shopElement);
            System.out.println(shop);
            FileCommentHandler handler = new FileCommentHandler(shop, outputDir);
            if (handler.shouldCrawl()) {
                new CommentCrawler(handler).crawl(shop.commentUrl());
                handler.close();
            }
        }
    }
}
