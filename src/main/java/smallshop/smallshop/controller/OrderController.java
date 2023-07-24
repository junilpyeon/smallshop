package smallshop.smallshop.controller;

import smallshop.smallshop.domain.Member;
import smallshop.smallshop.domain.Order;
import smallshop.smallshop.domain.productsDomain.Product;
import smallshop.smallshop.repository.OrderSearch;
import smallshop.smallshop.service.ProductsService;
import smallshop.smallshop.service.MemberService;
import smallshop.smallshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ProductsService productsService;

    @GetMapping("/orders/new")
    public String createOrderForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Product> products = productsService.findProducts();

        model.addAttribute("members", members);
        model.addAttribute("products", products);

        return "order/orderForm";
    }

    @PostMapping("/orders/new")
    public String createOrder(@RequestParam("memberId") Long memberId,
                              @RequestParam("itemId") Long itemId,
                              @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
        return "order/orderForm";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/order";
    }
}