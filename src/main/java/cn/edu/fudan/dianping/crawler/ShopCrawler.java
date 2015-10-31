package cn.edu.fudan.dianping.crawler;

import cn.edu.fudan.dianping.bean.Shop;
import cn.edu.fudan.dianping.handler.FileCommentHandler;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.File;
import java.util.List;

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
        new ShopCrawler(new File("data/shanghai")).crawl("http://www.dianping.com/search/category/1/10/p12");
    }

    @Override
    protected void parseDocument(HtmlPage page) {
        List<HtmlElement> shopElements = (List<HtmlElement>) page.getByXPath("//div[@id='shop-all-list']/ul/li");
        for (HtmlElement shopElement : shopElements) {
            processShop(new Shop(shopElement));
        }
    }

    private void processShop(Shop shop) {
        System.out.println(shop);
        FileCommentHandler handler = new FileCommentHandler(shop, outputDir);
        if (handler.shouldCrawl()) {
            new CommentCrawler(handler).crawl(shop.commentUrl());
            handler.close();
        }
    }


}
