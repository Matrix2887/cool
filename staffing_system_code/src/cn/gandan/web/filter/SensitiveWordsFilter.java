package cn.gandan.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {

    private List<String> list = new ArrayList<String>();

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1.创建代理对象，增强getParameter方法

        ServletRequest proxy_req = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //增强getParameter方法
                //判断是否是getParameter方法
                if(method.getName().equals("getParameter")){
                    //增强返回值
                    //获取返回值
                    String value = (String) method.invoke(req,args);
                    if(value != null){
                        for (String str : list) {
                            if(value.contains(str)){
                                value = value.replaceAll(str,"盛利利");
                            }
                        }
                    }

                    return  value;
                }

                //判断方法名是否是 getParameterMap

                if(method.getName().equals("getParameterMap")){
                    System.out.println("MAP执行--------------------------------------------------------");
                    //增强返回值
                    //获取返回值
                    // 注意修改的地址,不要改变地址
                    Map<String, String[]> map = (Map<String, String[]>) method.invoke(req,args);
                    Set<String> keySet = map.keySet();
                    for (String s : keySet) {
                        String[] value = map.get(s);
                        if(value != null){
                            for (String str : list) {
                                if(value[0].contains(str)){
                                    value[0] = value[0].replaceAll(str,"盛利利");
                                    System.out.println(value);
                                }
                            }
                        }
                    }
                    Set<String> k = map.keySet();
                    for (String s : k) {
                        String[] strings = map.get(s);
                        System.out.println(s + "-->" + strings.toString() + "-->" + strings[0]);
                    }

                    return  map;
                }

                //判断方法名是否是 getParameterValue

                if(method.getName().equals("getParameterValue")){
                    //增强返回值
                    //获取返回值
                    String[] array = (String[]) method.invoke(req,args);
                    for (String value : array) {
                        if(value != null){
                            for (String str : list) {
                                if(value.contains(str)){
                                    value = value.replaceAll(str,"盛利利");
                                }
                            }
                        }
                    }

                    return  array;
                }


                return method.invoke(req,args);
            }
        });
        chain.doFilter(proxy_req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        try {
            ServletContext servletContext = config.getServletContext();
            String realPath = servletContext.getRealPath("/WEB-INF/classes/敏感词汇.txt");

            BufferedReader br = new BufferedReader(new FileReader(realPath));

            String line;

            while((line = br.readLine()) != null){
                list.add(line);
            }

            br.close();
            System.out.println(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }

}
