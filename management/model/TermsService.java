package tw.com.tibame.management.model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TermsService {
	private TermsDAOIntereface dao;
	
	public TermsService() {
		dao = new TermsDAO();
	}
	
	public TermsVO createNewTerm(TermsVO bean) {
		if(bean!=null) {
			dao.insert(bean);
		}
		return bean;
	}

}
