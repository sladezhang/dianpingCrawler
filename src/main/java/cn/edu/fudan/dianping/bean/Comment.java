package cn.edu.fudan.dianping.bean;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

import java.util.List;

/**
 * Created by Dawnwords on 2015/10/23.
 */
public class Comment implements Outputable {
    private String userName, userId;
    private int total, taste, environment, service;
    private String content;

    public Comment(HtmlElement htmlElement) {
        if (htmlElement == null) {
            throw new NullPointerException("comment element is null");
        }
        setUserId(htmlElement);
        setUserName(htmlElement);
        setScore(htmlElement);
        setTotal(htmlElement);
        setContent(htmlElement);
    }

    private void setContent(HtmlElement htmlElement) {
        HtmlElement element = htmlElement.getFirstByXPath("//div[class='J_brief-cont']");
        if (element != null) {
            this.content = element.getTextContent();
        }
    }

    private void setTotal(HtmlElement htmlElement) {
        HtmlElement element = htmlElement.getFirstByXPath("//span[contains(@class,'item-rank-rst')]");
        if (element != null) {
            this.total = toScore(element.getTextContent());
        }
    }

    private void setScore(HtmlElement htmlElement) {
        List<HtmlElement> elements = (List<HtmlElement>) htmlElement.getByXPath("//em[class='col-exp']");
        if (elements.size() > 2) {
            this.taste = toScore(elements.get(0).getTextContent());
            this.environment = toScore(elements.get(1).getTextContent());
            this.service = toScore(elements.get(2).getTextContent());
        }
    }

    private void setUserName(HtmlElement htmlElement) {
        HtmlElement element = htmlElement.getFirstByXPath("//p[class='name']/a");
        if (element != null) {
            this.userName = element.getTextContent();
        }
    }

    private void setUserId(HtmlElement htmlElement) {
        HtmlElement element = htmlElement.getFirstByXPath("//a[class='J_card']");
        if (element != null) {
            this.userId = element.getAttribute("user-id");
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
        return userId + separator +
                userName + separator +
                total + separator +
                taste + separator +
                environment + separator +
                service + separator +
                content;
    }
}
