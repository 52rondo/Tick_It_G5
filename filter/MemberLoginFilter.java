package tw.com.tibame.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tw.com.tibame.member.model.MemberVO;

@WebFilter({
	"/cart/addToCart",
	"/cart/remove",
	"/cart/memberCart",
	"/order/**",
	"/front-end/member/order.html",
	"/front-end/member/orderdetail.html",
	"/front-end/product/checkout.html"
})
public class MemberLoginFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;

	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
		
		//判斷有無會員登入
		if(memberVO != null) {
		//已登入
			chain.doFilter(request, response);
		}else {
		//未登入導向登入頁面
			response.sendRedirect("/TGA104G5/front-end/member/memberLogin.jsp");
		}
		
	}

}
