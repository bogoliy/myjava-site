package ua.com.myjava.webapp.filters;


import java.io.*;
import javax.servlet.*;
public class SelectLanguageFilter implements Filter {
    private String encoding; 
    
    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("requestEncoding");
        
        if(encoding==null) encoding="utf-8";
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
    throws IOException, ServletException {
        try {
        	System.out.println("filter");
            request.setCharacterEncoding(encoding);
            //response.setCharacterEncoding(encoding);
            next.doFilter(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(){}
}