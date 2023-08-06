package servlets;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.AdminUtil;

/**
 * Servlet Filter implementation class Filter
 */
@WebFilter("/*")
public class Filter extends HttpFilter implements javax.servlet.Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public Filter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Check if the requested resource is a JSP file
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String requestURI = httpRequest.getRequestURI();
            if (requestURI.endsWith(".jsp") || requestURI.endsWith(".jspf")) {
                // If it's a JSP file, return an error or redirect to a suitable page
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Direct access to JSP files is not allowed.");
                return;
            }
        }
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
	    
	    Boolean isAdmin = AdminUtil.checkAdmin(httpRequest);
	    if (isAdmin) {
	    	httpRequest.setAttribute("role","admin");
	    }else if (AdminUtil.checkReseller(httpRequest)) {
	    	httpRequest.setAttribute("role","reseller");
	    }
	    

        // If it's not a JSP file, continue the filter chain
        chain.doFilter(request, response);
    }

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
