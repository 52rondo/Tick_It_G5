package tw.com.tibame.order.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.tibame.member.model.MemberVO;
import tw.com.tibame.order.service.OrderDetailService;
import tw.com.tibame.order.service.ProductOrderService;
import tw.com.tibame.order.service.ShoppingCartService;
import tw.com.tibame.order.vo.OrderDetailVO;
import tw.com.tibame.order.vo.OrderWrapper;
import tw.com.tibame.order.vo.ProductOrderVO;
import tw.com.tibame.order.vo.ViewOrderDetailVO;
import tw.com.tibame.order.vo.ViewProductOrderVO;
import tw.com.tibame.product.service.ProductService;
import tw.com.tibame.product.vo.ProductVO;

@RestController
@RequestMapping("order")
public class ProductOrderController {
	@Autowired
	private ProductOrderService productOrderService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private ProductService productService;
	
    @GetMapping("orderlist")
	public List<ProductOrderVO> memberOrder(HttpSession session) {
    	MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
    	Integer number =  memberVO.getNumber();
		List<ProductOrderVO> list = productOrderService.getByNumberOrder(number);
    	return list;
	}
    
    @GetMapping("orderdetail") 
    public List<ViewOrderDetailVO> memberOrderDetail(@RequestParam Integer prodOrderNo) {
    	List<ViewOrderDetailVO> list = orderDetailService.findByProdOrderNo(prodOrderNo);
    	return list;
	}
    
    @GetMapping("info") 
    public ViewProductOrderVO findAnOrder(@RequestParam Integer prodOrderNo) {
    	ViewProductOrderVO viewProductOrderVO = productOrderService.findAnOrder(prodOrderNo);
		return viewProductOrderVO;
    }
    
    @PostMapping("addProdOrder") 
    public boolean addProdOrder(HttpSession session, @RequestBody OrderWrapper orderWrapper) {
    	MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
    	Integer number = memberVO.getNumber();
    	
    	orderWrapper.getProductOrderVO().setNumber(number);
    	// ????????????
    	if(orderWrapper.getProductOrderVO() != null) {
    		ProductOrderVO newOrder = productOrderService.addOrder(orderWrapper.getProductOrderVO());
    		System.out.println(newOrder);
    	// ??????????????????
	    	for(OrderDetailVO orderDetailVO : orderWrapper.getOrderDetailList()) {
	    		orderDetailVO.setProdOrderNo(newOrder.getProdOrderNo());
	    		OrderDetailVO newOrderDetail = orderDetailService.addDetail(orderDetailVO);
	    		
	    // ??????????????????
	    		Integer prodNo = newOrderDetail.getProdNo();
	    		Integer prodQty = newOrderDetail.getProdQty();
	    		
	    		ProductVO productVO = productService.findByProdNo(prodNo); 
	    		Integer newStock = productVO.getProdStock() - prodQty;
	    		if(newStock >= 0) {
	    			productVO.setProdStock(newStock);
	    			productService.update(productVO);
	    		}
	    	}
	    	
	    // ?????????????????????
	    	shoppingCartService.deleteAllByMemberNumber(newOrder.getNumber());
	    	return true;
    	}
    	return false;
	}
        
    @PostMapping("updateReceiverInfo")
    public ProductOrderVO updateReceiverInfo() {
//    	Integer prodOrderNo =  (Integer) request.getSession().getAttribute("prodOrderNo");
    	Integer prodOrderNo = 2;		// ???????????????????????????!!
    	String receiverName = "??????1";	// ?????????????????????!
    	String receiverTel = "??????1";
    	String shippingAdd = "??????1";
    	ProductOrderVO productOrderVO = productOrderService.updateReceiverInfo(prodOrderNo, receiverName, receiverTel, shippingAdd);
		return productOrderVO;
	}
    
    @PutMapping("comment")
    public OrderDetailVO updateComment() {
//    	Integer itemNo =  (Integer) request.getSession().getAttribute("itemNo");
    	Integer itemNo = 12;       // ?????????????????????!
    	Float commentRanking = 4F;
    	String commentContent = "????????????";
    	
    	OrderDetailVO orderDetailVO = orderDetailService.updateComment(itemNo, commentRanking, commentContent);
    	return orderDetailVO;
	}
    
    @PutMapping("return")
    public OrderDetailVO updateReturn() {
    	Integer itemNo = 11;		// ?????????????????????!
    	String returnReason = "?????????";
    	
    	OrderDetailVO orderDetailVO = orderDetailService.updateReturn(itemNo, returnReason);
    	return orderDetailVO;
	}
	
}
