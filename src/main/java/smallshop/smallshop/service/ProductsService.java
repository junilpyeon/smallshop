package smallshop.smallshop.service;

import smallshop.smallshop.domain.productsDomain.Product;
import smallshop.smallshop.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    @Transactional
    public void saveProduct(Product product) {
        productsRepository.save(product);
    }

    @Transactional
    public void updateProduct(Long productId, String name, int price, int stockQuantity) {
        Product product = productsRepository.findOne(productId);
        product.setName(name);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);
    }

    public List<Product> findProducts() {
        return productsRepository.findAll();
    }

    public Product findOne(Long productId) {
        return productsRepository.findOne(productId);
    }

}