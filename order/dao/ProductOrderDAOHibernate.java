package tw.com.tibame.order.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tw.com.tibame.order.vo.ProductOrderVO;
import tw.com.tibame.order.vo.ViewProductOrderVO;

@Repository
@Transactional
public class ProductOrderDAOHibernate implements ProductOrderDAO {

	@PersistenceContext
	private Session session;
	
	public Session getSession() {
		return this.session;
	}
	
	@Override
	public List<ProductOrderVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override // 會員中心 - 查詢所有訂單
	public List<ProductOrderVO> getByNumber(Integer number) {
		List<ProductOrderVO> result = new ArrayList<>();
		if (number != null) {
			Query<ProductOrderVO> query = getSession().createQuery(
					"from ProductOrderVO where number =: number order by prodOrderNo desc", ProductOrderVO.class); 
			query.setParameter("number", number);
			result = query.list();
			return result;
		}
		return null;
	}

	@Override // 會員中心 - 查詢單筆訂單
	public ProductOrderVO getByPrimaryKey(Integer prodOrderNo) {
		if (prodOrderNo != null) {
			ProductOrderVO productOrderVO = this.getSession().get(ProductOrderVO.class, prodOrderNo);
			return productOrderVO;
		}
		return null;
	}

	@Override // 新增訂單                              
	public ProductOrderVO insert(ProductOrderVO productOrderVO) { 
		if (productOrderVO != null) {
			this.getSession().persist(productOrderVO);
			return productOrderVO;
		}
		return null;
	}

	@Override // 更新收件資訊及狀態
	public boolean update(ProductOrderVO productOrderVO) {
		if (productOrderVO != null && productOrderVO.getProdOrderNo() != null) {
			ProductOrderVO temp = this.getSession().get(ProductOrderVO.class, productOrderVO.getProdOrderNo());
			if (temp != null) {
				this.getSession().merge(productOrderVO);
				return true;
			}
		}
		return false;
	}

	@Override // 會員中心 - 查詢所有訂單(view)
	public List<ViewProductOrderVO> findByNumber(Integer number) {
		List<ViewProductOrderVO> result = new ArrayList<>();
		if (number != null) {
			Query<ViewProductOrderVO> query = getSession().createQuery("from ViewProductOrderVO where number =: number", ViewProductOrderVO.class); 
			query.setParameter("number", number);
			result = query.list();
			return result;
		}
		return null;
	}

	@Override // 會員中心 - 查詢單筆訂單(view)
	public ViewProductOrderVO findByPrimaryKey(Integer prodOrderNo) {
		if (prodOrderNo != null) {
			ViewProductOrderVO productOrderVO = this.getSession().get(ViewProductOrderVO.class, prodOrderNo);
			return productOrderVO;
		}
		return null;
	}

}
