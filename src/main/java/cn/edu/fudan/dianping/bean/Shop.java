package cn.edu.fudan.dianping.bean;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Dawnwords on 2015/10/30.
 */
public class Shop implements Outputable {
    private String id, name, type, location;
    private double taste, environment, service;
    private int totalComment, avgPay;

    public Shop(Element jsoupElement) {
        if (jsoupElement == null) {
            throw new NullPointerException("comment element is null");
        }
        setId(jsoupElement);
        setName(jsoupElement);
        setTypeLocation(jsoupElement);
        setScore(jsoupElement);
        setTotalComment(jsoupElement);
        setAvgPay(jsoupElement);
    }

    private void setAvgPay(Element jsoupElement) {
        Elements select = jsoupElement.select(".mean-price b");
        if (select.size() > 0) {
            this.avgPay = Integer.parseInt(select.eq(0).text().substring(1));
        }
    }

    private void setTotalComment(Element jsoupElement) {
        Elements select = jsoupElement.select(".review-num b");
        if (select.size() > 0) {
            this.totalComment = Integer.parseInt(select.eq(0).text());
        }
    }

    private void setScore(Element jsoupElement) {
        Elements select = jsoupElement.select(".comment-list b");
        if (select.size() > 2) {
            this.taste = Double.parseDouble(select.eq(0).text());
            this.environment = Double.parseDouble(select.eq(1).text());
            this.service = Double.parseDouble(select.eq(2).text());
        }
    }

    private void setTypeLocation(Element jsoupElement) {
        Elements select = jsoupElement.select(".tag-addr .tag");
        if (select.size() > 1) {
            this.type = select.eq(0).text();
            this.location = select.eq(1).text();
        }
    }

    private void setName(Element jsoupElement) {
        Elements select = jsoupElement.select("h4");
        if (select.size() > 0) {
            this.name = select.text();
        }
    }

    private void setId(Element jsoupElement) {
        Elements select = jsoupElement.select(".tit > a");
        if (select.size() > 0) {
            this.id = select.attr("href").substring(6);
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
        return id + seperator +
                name + seperator +
                type + seperator +
                location + seperator +
                taste + seperator +
                environment + seperator +
                service + seperator +
                totalComment + seperator +
                avgPay;
    }

    public String id() {
        return id;
    }
}
