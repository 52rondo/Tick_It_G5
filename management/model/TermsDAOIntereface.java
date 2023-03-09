package tw.com.tibame.management.model;

import java.util.List;

public interface TermsDAOIntereface {

	public void insert(TermsVO bean);
    public void update(TermsVO bean);
    public void delete(Integer empno);
    public TermsVO findByPrimaryKey(Integer termsID);
    public List<TermsVO> selectAll();

}
