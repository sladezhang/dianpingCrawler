package cn.edu.fudan.dianping.crawler;

import cn.edu.fudan.dianping.bean.Comment;
import cn.edu.fudan.dianping.handler.CommentHandler;
import cn.edu.fudan.dianping.handler.PrintCommentHandler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by Dawnwords on 2015/10/30.
 */
public class CommentCrawler extends Crawler {

    private CommentHandler handler;

    public CommentCrawler(CommentHandler handler) {
        super(".NextPage");
        this.handler = handler;
    }

    public static void main(String[] args) {
        new CommentCrawler(new PrintCommentHandler()).crawl("http://www.dianping.com/shop/21761749/review_more");
    }

    @Override
    protected void parseDocument(String referURL, Document document) {
        for (Element element : document.select(".comment-list > ul > li")) {
            handler.handle(new Comment(element));
        }
    }
}
