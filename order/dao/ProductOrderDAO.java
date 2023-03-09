package tw.com.tibame.order.dao;

import java.util.List;

import tw.com.tibame.order.vo.ProductOrderVO;
import tw.com.tibame.order.vo.ViewProductOrderVO;

public interface ProductOrderDAO {
	List<ProductOrderVO> getAll();
	
	public List<ProductOrderVO> getByNumber(Integer number);		// 會員中心 - 查詢單筆訂單
	public ProductOrderVO getByPrimaryKey(Integer prodOrderNo);		// 會員中心 - 查詢所有訂單
	public ProductOrderVO insert(ProductOrderVO productOrderVO);	// 新增訂單	
	public boolean update(ProductOrderVO productOrderVO);			// 會員中心 - 修改收件資訊
	
	public List<ViewProductOrderVO> findByNumber(Integer number);		// 會員中心 - 查詢單筆訂單
	public ViewProductOrderVO findByPrimaryKey(Integer prodOrderNo);		// 會員中心 - 查詢所有訂單
}
