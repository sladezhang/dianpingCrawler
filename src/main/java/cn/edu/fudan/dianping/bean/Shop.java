package cn.edu.fudan.dianping.bean;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

import java.util.List;

/**
 * Created by Dawnwords on 2015/10/30.
 */
public class Shop implements Outputable {
    private String id, name, type, location;
    private double taste, environment, service;
    private int totalComment, avgPay;

    public Shop(HtmlElement htmlElement) {
        if (htmlElement == null) {
            throw new NullPointerException("comment element is null");
        }
        setId(htmlElement);
        setName(htmlElement);
        setTypeLocation(htmlElement);
        setScore(htmlElement);
        setTotalComment(htmlElement);
        setAvgPay(htmlElement);
    }

    private void setAvgPay(HtmlElement htmlElement) {
        HtmlElement element = htmlElement.getFirstByXPath("//a[class='mean-price']/b");
        if (element != null) {
            this.avgPay = Integer.parseInt(element.getTextContent().substring(1));
        }
    }

    private void setTotalComment(HtmlElement htmlElement) {
        HtmlElement element = htmlElement.getFirstByXPath("//a[class='review-num']/b");
        if (element != null) {
            this.totalComment = Integer.parseInt(element.getTextContent());
        }
    }

    private void setScore(HtmlElement htmlElement) {
        List<HtmlElement> elements = (List<HtmlElement>) htmlElement.getByXPath("//span[class='comment-list']/b");
        if (elements.size() > 2) {
            this.taste = Double.parseDouble(elements.get(0).getTextContent());
            this.environment = Double.parseDouble(elements.get(1).getTextContent());
            this.service = Double.parseDouble(elements.get(2).getTextContent());
        }
    }

    private void setTypeLocation(HtmlElement htmlElement) {
        List<HtmlElement> elements = (List<HtmlElement>) htmlElement.getByXPath("//div[class='tag-addr']/a/span");
        if (elements.size() > 2) {
            this.type = elements.get(0).getTextContent();
            this.location = elements.get(1).getTextContent();
        }
    }

    private void setName(HtmlElement htmlElement) {
        HtmlElement element = htmlElement.getFirstByXPath("//h4");
        if (element != null) {
            this.name = element.getTextContent();
        }
    }

    private void setId(HtmlElement htmlElement) {
        HtmlElement element = htmlElement.getFirstByXPath("//div[class='tit]/a");
        if (element != null) {
            this.id = element.getAttribute("href").substring(6);
        }
    }

    public String commentUrl() {
        return String.format("http://www.dianping.com/shop/%s/review_more", id);
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", taste=" + taste +
                ", environment=" + environment +
                ", service=" + service +
                ", totalComment=" + totalComment +
                ", avgPay=" + avgPay +
                '}';
    }

    @Override
    public String output() {
        return id + separator +
                name + separator +
                type + separator +
                location + separator +
                taste + separator +
                environment + separator +
                service + separator +
                totalComment + separator +
                avgPay;
    }

    public String id() {
        return id;
    }
}
