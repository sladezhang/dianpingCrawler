package cn.edu.fudan.dianping.handler;

import cn.edu.fudan.dianping.bean.Comment;
import cn.edu.fudan.dianping.bean.Shop;

import java.io.*;

/**
 * Created by Dawnwords on 2015/10/23.
 */
public class FileCommentHandler implements CommentHandler, Closeable {

    private PrintWriter output;

    public FileCommentHandler(Shop shop, File outputDir) {
        if (!outputDir.exists() || !outputDir.isDirectory()) {
            outputDir.mkdirs();
        }
        try {
            File file = new File(outputDir, shop.id());
            if (!file.exists()) {
                output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
                output.println(shop.output());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(Comment comment) {
        output.println(comment.output());
    }

    @Override
    public void close() {
        if (output != null) {
            output.close();
        }
    }

    public boolean shouldCrawl() {
        return output != null;
    }
}
