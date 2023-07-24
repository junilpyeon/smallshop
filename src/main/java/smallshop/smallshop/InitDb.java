package smallshop.smallshop;

import smallshop.smallshop.domain.*;
import smallshop.smallshop.domain.productsDomain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        // 데이터 초기화 - 1
        public void dbInit1() {
            // 회원 생성
            Member member = createMember("userA", "서울", "1", "1111");
            em.persist(member);

            // 상품 생성
            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000, 100);
            em.persist(book2);

            // 주문 상품 생성
            OrderProducts orderItem1 = createOrderProducts(book1, 10000, 1);
            OrderProducts orderItem2 = createOrderProducts(book2, 20000, 2);

            // 배송정보 생성
            Delivery delivery = createDelivery(member);

            // 주문 생성
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        // 데이터 초기화 - 2
        public void dbInit2() {
            // 회원 생성
            Member member = createMember("userB", "진주", "2", "2222");
            em.persist(member);

            // 상품 생성
            Book book1 = createBook("SPRING1 BOOK", 20000, 200);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 40000, 300);
            em.persist(book2);

            // 주문 상품 생성
            OrderProducts orderItem1 = createOrderProducts(book1, 20000, 3);
            OrderProducts orderItem2 = createOrderProducts(book2, 40000, 4);

            // 배송정보 생성
            Delivery delivery = createDelivery(member);

            // 주문 생성
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        // 회원 생성
        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        // 상품 생성
        private Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        // 배송정보 생성
        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        // 주문 상품 생성
        private OrderProducts createOrderProducts(Book book, int orderPrice, int count) {
            OrderProducts orderProducts = OrderProducts.createOrderProducts(book, orderPrice, count);
            book.decreaseStock(count);
            return orderProducts;
        }
    }
}