package com.dong.skynet.crawl;

import com.dong.skynet.common.DownloadFile;
import com.dong.skynet.common.HtmlParserTool;
import com.dong.skynet.common.LinkFilter;
import com.dong.skynet.common.LinkQueue;

import java.util.Set;

/**
 * Created by dongshuwang on 14-11-19.
 */
public class Crawler {
    /**
     * 使用种子初始化URL 队列
     *
     * @param seeds 种子URL
     * @return
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++)
            LinkQueue.addUnvisitedUrl(seeds[i]);
    }

    /**
     * 抓取过程
     *
     * @param seeds
     * @return
     */
    public void crawling(String[] seeds) { //定义过滤器，提取以http://www.lietu.com 开头的链接
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if (url.equals("http://www.douguo.com/")
                        || url.startsWith("http://www.douguo.com/cookbook/"))
                    return true;
                else
                    return false;
            }
        };
        //初始化URL 队列
        initCrawlerWithSeeds(seeds);
        //循环条件：待抓取的链接不空且抓取的网页不多于1000
        while (!LinkQueue.unVisitedUrlsEmpty()
                && LinkQueue.getVisitedUrlNum() <= 1000) {
            //队头URL 出队列
            String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
            if (visitUrl == null)
                continue;
            DownloadFile downLoader = new DownloadFile();
            //下载网页
            downLoader.downloadFile(visitUrl);
            //该URL 放入已访问的URL 中
            LinkQueue.addVisitedUrl(visitUrl);
            //提取出下载网页中的URL
            Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter);
            //新的未访问的URL 入队
            for (String link : links) {
                LinkQueue.addUnvisitedUrl(link);
            }
        }
    }

    //main 方法入口
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.crawling(new String[]{"http://www.douguo.com/"});
    }
}
