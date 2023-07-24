package smallshop.smallshop.service;

import smallshop.smallshop.domain.*;
import smallshop.smallshop.domain.productsDomain.Product;
import smallshop.smallshop.repository.MemberRepository;
import smallshop.smallshop.repository.OrderRepository;
import smallshop.smallshop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smallshop.smallshop.repository.ProductsRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductsRepository productsRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long productId, int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Product products = productsRepository.findOne(productId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //주문상품 생성
        OrderProducts orderItem = OrderProducts.createOrderProducts(products, products.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
