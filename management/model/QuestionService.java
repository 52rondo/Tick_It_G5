package tw.com.tibame.management.model;

public class QuestionService {
	private QuestionDAOInterface dao;
	public QuestionService() {
		dao = new QuestionDAO();
	}
	
	public QuestionVO addQuestion(QuestionVO bean) {
		dao.insert(bean);
		return bean;
	}
}
