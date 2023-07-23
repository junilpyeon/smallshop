package smallshop.smallshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import smallshop.smallshop.domain.productsDomain.Products;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProducts {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Products_id")
    private Products products;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    //==생성 메서드==//
    public static OrderProducts createOrderItem(Products products, int orderPrice, int count) {
        OrderProducts orderProducts = new OrderProducts();
        orderProducts.setProducts(products);
        orderProducts.setOrderPrice(orderPrice);
        orderProducts.setCount(count);

        products.removeStock(count);
        return orderProducts;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getProducts().addStock(count);
    }

    //==조회 로직==//

    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
