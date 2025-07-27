package com.smc.crawler.service;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @Date 2022/5/23
 * @Author smc
 * @Description:
 */
public interface AutoHomeApiService {
    /**
     * 使用get请求获取静态页面数据
     *
     * @param url
     * @return
     */
    public String getHtml(String url);

    /**
     * 使用get请求获取动态页面数据
     *
     * @param url
     * @return
     */
    HtmlPage getDynamicHtml(String url);

    /**
     * 使用get请求下载图片,返回图片名称
     *
     * @param url
     * @return
     */
    String getImage(String url);

    boolean saveRate(Double rmbRate,Double inrRate,Double idrRate);

}
