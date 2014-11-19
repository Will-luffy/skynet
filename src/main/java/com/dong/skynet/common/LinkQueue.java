package com.dong.skynet.common;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dongshuwang on 14-11-19.
 */
public class LinkQueue {
    private static Set visitedUrl = new HashSet();
    private static Queue unVisitedUrl = new Queue();

    //获得URL 队列
    public static Queue getUnVisitedUrl() {
        return unVisitedUrl;
    }
    //添加到访问过的URL 队列中
    public static void addVisitedUrl(String url) {
        visitedUrl.add(url);
    }
    //移除访问过的URL
    public static void removeVisitedUrl(String url) {
        visitedUrl.remove(url);
    }
    //未访问的URL 出队列
    public static Object unVisitedUrlDeQueue() {
        return unVisitedUrl.deQueue();
    }
    // 保证每个URL 只被访问一次
    public static void addUnvisitedUrl(String url) {
        if (url != null && !url.trim().equals("")
                && !visitedUrl.contains(url)
                && !unVisitedUrl.contains(url))
            unVisitedUrl.enQueue(url);
    }
    //获得已经访问的URL 数目
    public static int getVisitedUrlNum() {
        return visitedUrl.size();
    }
    //判断未访问的URL 队列中是否为空
    public static boolean unVisitedUrlsEmpty() {
        return unVisitedUrl.isQueueEmpty();
    }
}
