package cn.edu.fudan.dianping;

import cn.edu.fudan.dianping.crawler.ShopCrawler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * Created by Dawnwords on 2015/11/2.
 */
public class Main {

    public static final File OUTPUT_DIR = new File("data/shanghai");
    public static final String START_URL = "http://www.dianping.com/search/category/1/10";

    public static void main(String[] args) throws IOException {
//        doCrawl();
        System.out.println("total comments:" + countComments());
    }

    private static int countComments() throws IOException {
        File[] files = OUTPUT_DIR.listFiles();
        int result = 0;
        if (files != null) {
            for (File file : files) {
                LineNumberReader reader = null;
                try {
                    reader = new LineNumberReader(new FileReader(file));
                    reader.skip(Long.MAX_VALUE);
                    int commentNum = reader.getLineNumber();
                    result += commentNum;
                    System.out.printf("id:%s,commentNum:%d\n", file.getName(), commentNum);
                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                }
            }
        }
        return result;
    }

    private static void doCrawl() {
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8087");
        new ShopCrawler(OUTPUT_DIR).crawl(START_URL);
    }
}
