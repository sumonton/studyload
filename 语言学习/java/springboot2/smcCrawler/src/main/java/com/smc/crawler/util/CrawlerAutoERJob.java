package com.smc.crawler.util;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlHeading5;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.smc.crawler.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Date 2022/5/28
 * @Author smc
 * @Description:
 */
@Slf4j
@Component
public class CrawlerAutoERJob {

    @Autowired
    private AutoHomeApiService autoHomeApiService;

    /**
     * 爬取动态网页
     */
    public void executeInternal() {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> start crawlerAutoERJob");
        // 获取spring容器
//        ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap()
//                .get("context");
        String rmbRate = null;
        String inrRate = null;
        String idrRate = null;
        //获取美元人民币汇率，插入数据库

//        String usdUrl = "http://www.webmasterhome.cn/huilv/USD/USDCNY/";
//        String usdUrl = "https://www.huilv.cc/USD_CNY/";
        String usdUrl = "https://finance.sina.com.cn/money/forex/hq/USDCNY.shtml";
        HtmlPage htmlPage = null;
        for (int i = 0; i <20 ; i++) {
            htmlPage = autoHomeApiService.getDynamicHtml(usdUrl);
            if(htmlPage !=null){
//                DomElement firstByXPath = htmlPage.getFirstByXPath("//span[contains(concat(' ', normalize-space(@class), ' '), 'price')]");
                // 使用 XPath 获取 <div> 下的 <h5> 元素
                HtmlHeading5 h5Element = htmlPage.getFirstByXPath("//div[@class='price']/h5");
                rmbRate = h5Element.getTextContent();
//                rmbRate = firstByXPath.getTextContent();
                break;
            }
        }

        //获取美元卢比汇率，插入数据库
//        String inrUrl = "http://www.webmasterhome.cn/huilv/USD/USDINR/";
//        String inrUrl = "https://www.huilv.cc/USD_INR/";
        String inrUrl = "https://finance.sina.com.cn/money/forex/hq/USDINR.shtml";
        HtmlPage inrHtmlPage = null;
        for (int i = 0; i < 20; i++) {
            inrHtmlPage = autoHomeApiService.getDynamicHtml(inrUrl);
            if(inrHtmlPage!=null){
//                DomElement inr_fx_susdcny = inrHtmlPage.getFirstByXPath("//span[contains(concat(' ', normalize-space(@class), ' '), ' back ')]");
//                inrRate = inr_fx_susdcny.getTextContent();
                HtmlHeading5 inr_h5Element = inrHtmlPage.getFirstByXPath("//div[@class='price']/h5");
                inrRate = inr_h5Element.getTextContent();
                break;

            }
        }

        //获取美元卢比汇率，插入数据库
//        String idrUrl = "http://www.webmasterhome.cn/huilv/USD/USDIDR/";
//        String idrUrl = "https://www.huilv.cc/USD_IDR/";
        String idrUrl = "https://finance.sina.com.cn/money/forex/hq/USDIDR.shtml";
        HtmlPage idrHtmlPage = null;
        for (int i = 0; i < 20; i++) {
            idrHtmlPage = autoHomeApiService.getDynamicHtml(idrUrl);
            if(idrHtmlPage!=null){
//                DomElement idr_fx_susdcny = idrHtmlPage.getFirstByXPath("//span[contains(concat(' ', normalize-space(@class), ' '), ' back ')]");
//                idrRate = idr_fx_susdcny.getTextContent();
                HtmlHeading5 idr_h5Element = idrHtmlPage.getFirstByXPath("//div[@class='price']/h5");
                idrRate = idr_h5Element.getTextContent();
                break;

            }
        }

        if(rmbRate != null && inrRate !=null && inrRate !=null && idrRate !=null){
            autoHomeApiService.saveRate(Double.parseDouble(rmbRate),Double.parseDouble(inrRate),Double.parseDouble(idrRate));
        }
    }
}
