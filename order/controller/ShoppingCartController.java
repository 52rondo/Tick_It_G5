package tw.com.tibame.order.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.tibame.member.model.MemberVO;
import tw.com.tibame.order.service.ShoppingCartService;
import tw.com.tibame.order.vo.ShoppingCartVO;
import tw.com.tibame.order.vo.ShowShoppingCartVO;

@RestController
@RequestMapping("cart")
public class ShoppingCartController {
	@Autowired
	private ShoppingCartService shoppingCartService;

	@GetMapping("selectAll")
	public List<ShowShoppingCartVO> selectAll() {
		return shoppingCartService.getAll();
	}

	@GetMapping("memberCart")
	public List<ShowShoppingCartVO> getByMemberNumber(HttpSession session) {
		MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
		Integer number = memberVO.getNumber();
		
		return shoppingCartService.getByMemberNumber(number);
	}

	@PostMapping("addToCart")
	public ShoppingCartVO addToCart(HttpSession session, @RequestBody ShoppingCartVO shoppingCartVO) {
		MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
		shoppingCartVO.setNumber(memberVO.getNumber());
			
		return shoppingCartService.insert(shoppingCartVO);
	}

	@PostMapping("update")
	public ShoppingCartVO updateQty(@RequestBody ShoppingCartVO shoppingCartVO) {
		Integer shoppingCartNo = shoppingCartVO.getShoppingCartNo();
		Integer shoppingQty = shoppingCartVO.getShoppingQty();
		
		return shoppingCartService.updateQty(shoppingCartNo, shoppingQty);
	}

	@GetMapping("remove")
	public boolean removeFromCart(@RequestParam Integer shoppingCartNo) {
		if (shoppingCartNo != null) {
			shoppingCartService.delete(shoppingCartNo);
			return true;
		}
		return false;
	}

}
