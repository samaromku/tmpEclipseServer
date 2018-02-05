package filtres;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import managers.TokenManager;
import storage.Token;

@WebFilter(urlPatterns = "/tasks/*")
public class LoginFilter implements Filter {
	TokenManager tokenManager = TokenManager.instance;
//	AuthManager authManager = AuthManager.instance;
	public LoginFilter() {
    }
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) resp;
	        HttpSession session = request.getSession(false);
	        String loginURI = request.getContextPath() + "/auth.jsp";
	        
	        boolean loggedIn = session != null && session.getAttribute("token") != null;
	        boolean loginRequest = request.getRequestURI().equals(loginURI);
	        Token token = null;
	        if(loggedIn){
	        	token = (Token)session.getAttribute("token");
	        }
	        if (loggedIn || loginRequest || tokenManager.isTokenInBase(token)) {
	            chain.doFilter(request, response);
	        } else {
	            response.sendRedirect(loginURI);
	        }
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {

	}
	@Override
	public void destroy() {
	}

}
