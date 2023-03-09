package tw.com.tibame.management.model;

import java.util.List;

public interface QuestionDAOInterface {
		public void insert(QuestionVO bean);
	    public void update(QuestionVO bean);
	    public void delete(Integer commonID);
	    public QuestionVO findByPrimaryKey(Integer commonID);
	    public List<QuestionVO> selectAll();
	    public List <QuestionVO> searchOrganizerByAll(String searchString);
}
