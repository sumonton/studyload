//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

@WebServlet("/commandAsync.jsp")
public class CorsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CorsServlet() {
        super();
    }

    /**
     * @see HttpServlet#doOptions(HttpServletRequest, HttpServletResponse)
     */
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cors(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp);
    }

    private void cors(HttpServletRequest req, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin,X-Requested-With,Content-Type,Accept,"
                + "content-Type,origin,x-requested-with,content-type,accept,authorization,token,id,X-Custom-Header,X-Cookie,Connection,User-Agent,Cookie,*");
        response.setHeader("Access-Control-Request-Headers",
                "Authorization,Origin, X-Requested-With,content-Type,Accept");
        response.setHeader("Access-Control-Expose-Headers", "*");
    }

    private void doService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        cors(request,response);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        List<Object> parmList = new ArrayList<Object>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String key = null;
        String[] value = null;
        String path = null;
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            if (value != null && value.length > 0) {
                if ("b".equalsIgnoreCase(key)) {
                    path = entry.getValue()[0];
                } else {
                    parmList.add(entry.getValue()[0]);
                }
            }

        }
        JSONObject result = new JSONObject();
		/*try {
			CommandUtils.exeCommandAsync(path, parmList);
			result.put("code", "0");
			result.put("msg", "执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "1");
			result.put("msg", "执行失败：" + e.getMessage());
		}*/
        try {
//			int temp = CommandUtils.exeCommandAsync1(path, parmList);
            int temp = CommandUtils.exeCommandSync1(path, parmList);
            if(temp == 0){
                result.put("code", "0");
                result.put("msg", "命令执行成功");
            }else{
                result.put("code", "1");
                result.put("msg", "命令执行失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "1");
            result.put("msg", "执行失败：" + e.getMessage());
        }
        response.getWriter().write(result.toJSONString());
    }

}