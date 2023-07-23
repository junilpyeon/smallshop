package smallshop.smallshop.domain.productsDomain;

import smallshop.smallshop.domain.Category;
import smallshop.smallshop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "products")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    /**
     * 재고 증가
     */
    public void increaseStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 재고 감소
     */
    public void decreaseStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}