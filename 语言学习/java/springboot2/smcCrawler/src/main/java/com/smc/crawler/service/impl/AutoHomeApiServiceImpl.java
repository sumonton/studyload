package com.smc.crawler.service.impl;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import com.smc.crawler.bean.RateMysql;
import com.smc.crawler.bean.RateOracle;
import com.smc.crawler.service.AutoHomeApiService;
import com.smc.crawler.service.RateMysqlService;
import com.smc.crawler.service.RateOracleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;

/**
 * @Date 2022/5/23
 * @Author smc
 * @Description:
 */
@Service
@Slf4j
public class AutoHomeApiServiceImpl implements AutoHomeApiService {

//    @Autowired
//    private PoolingHttpClientConnectionManager connectionManager;
    @Autowired
    private WebClient webClient;

    @Autowired
    private RateMysqlService rateMysqlServiceImpl;

    @Autowired
    private RateOracleService rateOracleServiceImpl;

    @Autowired
    private RateMysql rateMysql;

    @Autowired
    private RateOracle rateOracle;

    @Value("${execute:oracle}")
    private String execute;

    @Override
    public String getHtml(String url) {
//        // 使用连接池管理器获取连接
//        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
//        // 创建httpGet请求
//        HttpGet httpGet = new HttpGet(url);
//        CloseableHttpResponse httpResponse = null;
//        String html = null;
//        try {
//            // 发起请求
//            httpResponse = httpClient.execute(httpGet);
//            Thread.sleep(5000);
//            // 判断请求是否成功
//            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {
//                // 判断是否有响应体
//                if (httpResponse.getEntity() != null) {
//                    // 如果有响应体，则进行解析
//                    html = EntityUtils.toString(httpResponse.getEntity(), "GBK");
//                    return html;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.error("获取汽车之家信息异常：{}", e);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            if (httpResponse != null) {
//                try {
//                    httpResponse.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    log.error("获取汽车之家信息响应关闭异常：{}", e);
//                }
//            }
//        }
        return null;
    }

    @Override
    public HtmlPage getDynamicHtml(String url) {
        // 创建httpGet请求
        HtmlPage htmlPage = null;


        // 使用 MockWebConnection 替换默认的 WebConnection

        try {
//            WebConnectionWrapper wrapper = new WebConnectionWrapper(webClient) {
//                @Override
//                public WebResponse getResponse(WebRequest request) throws IOException {
//                    String url = request.getUrl().toExternalForm();
//                    if (isAdRequest(url)) {
//                        // 返回一个空的 WebResponse
//                        WebResponseData responseData = new WebResponseData(
//                                "".getBytes(), // 空内容
//                                200, // HTTP 状态码
//                                "OK", // 状态消息
//                                Collections.<NameValuePair>emptyList() // 响应头
//                        );
//                        return new WebResponse(responseData, request, 0);
//                    }
//                    return super.getResponse(request);
//                }
//
//                private boolean isAdRequest(String url) {
//                    // 判断是否是广告请求
//                    return url.contains("google_vignette") || url.contains("huilv.paihang8.com/js/pc/commons.js");
//                }
//            };
//            webClient.setWebConnection(wrapper);
            htmlPage = webClient.getPage(url);
            // 等待JS驱动dom完成获得还原后的网页
            webClient.waitForBackgroundJavaScript(10000);

            return htmlPage;
        } catch (FailingHttpStatusCodeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getImage(String url) {
//        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
//        HttpGet httpGet = new HttpGet(url);
//        CloseableHttpResponse httpResponse = null;
//        String fileName = null;
//        try {
//            httpResponse = httpClient.execute(httpGet);
//            // 判断请求是否成功
//            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {
//                // 判断是否有响应体
//                if (httpResponse.getEntity() != null) {
//                    // 如果有响应体，则进行解析
//                    String contentTypeVal = httpResponse.getFirstHeader("Content-Type").getValue();
//                    if(contentTypeVal.contains("image/")){
//                        String extName =  contentTypeVal.split("/")[1];
//                        fileName = UUID.randomUUID().toString().replace("-","") + "." + extName;
//                        OutputStream os = new FileOutputStream(new File("classpath://test/" + fileName));
//                        httpResponse.getEntity().writeTo(os);
//                        return fileName;
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.error("获取汽车之家评测图片异常：{}", e);
//        }finally {
//            if (httpResponse != null) {
//                try {
//                    httpResponse.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    log.error("获取汽车之家评测图片响应关闭异常：{}", e);
//                }
//            }
//        }
        return null;
    }

    @Override
    public boolean saveRate(Double rmbRate, Double inrRate, Double idrRate) {
        boolean flag =false;
        if(execute.equals("mysql")){
            rateMysql.setUsdRmb(rmbRate);
            rateMysql.setUsdInr(inrRate);
            rateMysql.setUsdIdr(idrRate);
            rateMysql.setUpdatetime(new Timestamp(System.currentTimeMillis()));
            log.info("rmb exchange rate："+rmbRate);
            log.info("inr exchange rate："+inrRate);
            log.info("idr exchange rate："+idrRate);
            flag = rateMysqlServiceImpl.save(rateMysql);
        }else{
            //        rateOracle.setUsdRmb(BigDecimal.valueOf(Double.parseDouble(rmbRate)));
            rateOracle.setUsdRmb(BigDecimal.valueOf(rmbRate));
            rateOracle.setUsdInr(BigDecimal.valueOf(inrRate));
            rateOracle.setUsdIdr(BigDecimal.valueOf(idrRate));
            rateOracle.setUpdatetime(new Date());
            log.info("rmb exchange rate："+rmbRate);
            log.info("inr exchange rate："+inrRate);
            log.info("idr exchange rate："+idrRate);
            flag = rateOracleServiceImpl.save(rateOracle);
        }

        return flag;
    }
}
