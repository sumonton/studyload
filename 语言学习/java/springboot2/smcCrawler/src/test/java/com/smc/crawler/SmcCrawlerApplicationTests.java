package com.smc.crawler;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.smc.crawler.bean.CarTest;
import com.smc.crawler.service.AutoHomeApiService;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@SpringBootTest
class SmcCrawlerApplicationTests {
    @Autowired
    private AutoHomeApiService autoHomeApiService;
    /**
     * 测试获取HTML内容
     */
    @Test
    public void getHtml() {
        String html = autoHomeApiService.getHtml("https://www.autohome.com.cn/dongguan/");
        System.out.println("html = " + html);
    }

    /**
     * 测试获取图片
     */
    @Test
    public void getImage() {
        String image = autoHomeApiService.getImage("https://car2.autoimg.cn/cardfs/product/g24/M09/AE/EB/800x0_1_q87_autohomecar__wKgHIVpxGh6AFSN1AAY8kcz3Aww921.jpg");
        System.out.println("image = " + image);
    }

    /**
     * 获取评测数据
     */
    @Test
    public void testGetEvaluatingResult() {
        List<CarTest> saveList = new ArrayList<>();
        String baseUrl = "https://www.autohome.com.cn/dongguan/";
        String html = autoHomeApiService.getHtml(baseUrl);
        Document document = Jsoup.parse(html);
        Elements carElements = document.getElementsByClass("uibox");
        for (Element carElement : carElements) {
            String carTitle = carElement.getElementsByClass("uibox-title uibox-title-border").text();
           /* if (titleFilter.contains(carTitle)) {
                continue;
            }*/
            CarTest carTest = marshalCarElement(carElement);
            String imageNames = marshalImageNames(carElement);
            carTest.setImage(imageNames);
            saveList.add(carTest);
        }

    }
    /**
     * 解析数据下载评测图片
     * @param carElement
     * @return
     */

    private String marshalImageNames(Element carElement) {
        String carImageName = null;
        List<String> imageNameList = new ArrayList<>();
        Elements imageElements = carElement.select(".piclist-box.fn-clear ul.piclist02 a");
        for (Element imageElement : imageElements) {
            String imageUrl = "https:" + imageElement.getElementsByTag("img").attr("src");
            String imageName = autoHomeApiService.getImage(imageUrl);
            imageNameList.add(imageName);
        }
        if (!CollectionUtils.isEmpty(imageNameList)) {
            carImageName =  StringUtils.join(imageNameList, ",");
        }
        return carImageName;
    }

    /**
     * 解析数据封装成汽车评测对象
     * @param carElement
     * @return
     */
    private CarTest marshalCarElement(Element carElement) {
        CarTest carTest = new CarTest();
        String carTitle = carElement.getElementsByClass("uibox-title uibox-title-border").text();
        carTest.setTitle(carTitle);
        String testSpeed = carElement.select(".tabbox1 dd:nth-child(2) > div.dd-div2").first().text();
        carTest.setTestSpeed(covertStrToNum(testSpeed));
        String testBrake = carElement.select(".tabbox1 dd:nth-child(3) > div.dd-div2").first().text();
        carTest.setTestBrake(covertStrToNum(testBrake));
        String testOil = carElement.select(".tabbox1 dd:nth-child(4) > div.dd-div2").first().text();
        carTest.setTestOil(covertStrToNum(testOil));
        String editorName1 = carElement.select(".tabbox2.tabbox-score dd:nth-child(2) > div.dd-div1").first().text();
        carTest.setEditorName1(editorName1);
        String editorRemark1 = carElement.select(".tabbox2.tabbox-score dd:nth-child(2) > div.dd-div3").first().text();
        carTest.setEditorRemark1(editorRemark1);
        String editorName2 = carElement.select(".tabbox2.tabbox-score dd:nth-child(3) > div.dd-div1").first().text();
        carTest.setEditorName2(editorName2);
        String editorRemark2 = carElement.select(".tabbox2.tabbox-score dd:nth-child(3) > div.dd-div3").first().text();
        carTest.setEditorRemark2(editorRemark2);
        String editorName3 = carElement.select(".tabbox2.tabbox-score dd:nth-child(4) > div.dd-div1").first().text();
        carTest.setEditorName3(editorName3);
        String editorRemark3 = carElement.select(".tabbox2.tabbox-score dd:nth-child(4) > div.dd-div3").first().text();
        carTest.setEditorRemark3(editorRemark3);
        Date currentDate = new Date();
        carTest.setCreated(currentDate);
        carTest.setUpdated(currentDate);
        return carTest;
    }


    /**
     * 把字符串去掉最后一个数，转为乘以1000的数字
     * @param str
     * @return
     */
    private int covertStrToNum(String str) {
        try {
            if ("--".equals(str)) {
                return 0;
            }
            // 字符串去掉随后一个数
            str = StringUtils.substring(str, 0, str.length() - 1);
            // 转换为小数并乘以1000
            Number num = Float.valueOf(str) * 1000;
            return num.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(str);
        }
        return 0;
    }
    @Test
    public void HtmlUnitTest() throws DocumentException {
        String url ="http://www.qidian.com";
        url = "https://www.usd-cny.com/usd-rmb.htm";
        // 1创建WebClient
        WebClient webClient=new WebClient(BrowserVersion.CHROME);
        // 2 启动JS
        webClient.getOptions().setJavaScriptEnabled(true);
        // 3 禁用Css，可避免自动二次請求CSS进行渲染
        webClient.getOptions().setCssEnabled(false);
        // 4 启动客戶端重定向
        webClient.getOptions().setRedirectEnabled(true);
        // 5 js运行错誤時，是否拋出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // 6 设置超时
        webClient.getOptions().setTimeout(50000);       //获取网页
        HtmlPage htmlPage = null;
        try {
            htmlPage = webClient.getPage(url);
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
        // 等待JS驱动dom完成获得还原后的网页
        webClient.waitForBackgroundJavaScript(10000);
        DomElement tbl_fx_susdcny = htmlPage.getHtmlElementById("tbl_fx_susdcny").getFirstElementChild();
        String textContent = tbl_fx_susdcny.getTextContent();
        // 网页内容
//        String pageHtml = htmlPage.asXml();
        SAXReader reader = new SAXReader();
//        System.out.println("\n------\n");
        //网页内容---纯文本形式
//        String pageText = htmlPage.toString();
//        System.out.println(pageText );
//        Document document = Jsoup.parse(htmlPage.getHtmlElementById("tbl_fx_susdcny").toString());
//        Elements carElements = document.get("id","hq");
//        Elements carElements = document.getElementsByAttributeValue("id","tbl_fx_susdcny");
//        for (Element carElement : carElements) {
//            String erValue = carElement.getElementsByClass("loading").text();
//
//        }
        //输出网页的title
//        String title = htmlPage.getTitleText();
//        System.out.println(title );

        //close
        webClient.close();
    }
}
