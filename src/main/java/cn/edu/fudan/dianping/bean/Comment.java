package cn.edu.fudan.dianping.bean;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Dawnwords on 2015/10/23.
 */
public class Comment implements Outputable {
    private String userName, userId;
    private int total, taste, environment, service;
    private String content;

    public Comment(Element jsoupElement) {
        if (jsoupElement == null) {
            throw new NullPointerException("comment element is null");
        }
        setUserId(jsoupElement);
        setUserName(jsoupElement);
        setScore(jsoupElement);
        setTotal(jsoupElement);
        setContent(jsoupElement);
    }

    private void setContent(Element jsoupElement) {
        Elements select = jsoupElement.select(".J_brief-cont");
        if (select.size() > 0) {
            this.content = select.eq(0).text();
        }
    }

    private void setTotal(Element jsoupElement) {
        Elements select = jsoupElement.select(".item-rank-rst");
        if (select.size() > 0) {
            this.total = toScore(select.eq(0).attr("title"));
        }
    }

    private void setScore(Element jsoupElement) {
        Elements select = jsoupElement.select(".col-exp");
        if (select.size() > 2) {
            this.taste = toScore(select.eq(0).text());
            this.environment = toScore(select.eq(1).text());
            this.service = toScore(select.eq(2).text());
        }
    }

    private void setUserName(Element jsoupElement) {
        Elements select = jsoupElement.select(".name a");
        if (select.size() > 0) {
            this.userName = select.eq(0).text();
        }
    }

    private void setUserId(Element jsoupElement) {
        Elements select = jsoupElement.select(".J_card");
        if (select.size() > 0) {
            this.userId = select.eq(0).attr("user-id");
        }
    }

    private int toScore(String scoreText) {
        if (scoreText.contains("非常好")) return 4;
        if (scoreText.contains("很好")) return 3;
        if (scoreText.contains("好")) return 2;
        if (scoreText.contains("一般")) return 1;
        if (scoreText.contains("很差")) return 0;
        return -1;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", total=" + total +
                ", taste=" + taste +
                ", environment=" + environment +
                ", service=" + service +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public String output() {
        return userId + seperator +
                userName + seperator +
                total + seperator +
                taste + seperator +
                environment + seperator +
                service + seperator +
                content;
    }
}
