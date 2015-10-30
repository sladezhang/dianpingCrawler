package cn.edu.fudan.dianping.handler;

import cn.edu.fudan.dianping.bean.Comment;

/**
 * Created by Dawnwords on 2015/10/23.
 */
public class PrintCommentHandler implements CommentHandler {
    @Override
    public void handle(Comment comment) {
        System.out.println(comment.toString());
    }
}
