package smallshop.smallshop.controller;

import smallshop.smallshop.domain.BookForm;
import smallshop.smallshop.domain.productsDomain.Book;
import smallshop.smallshop.domain.productsDomain.Product;
import smallshop.smallshop.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @GetMapping("/products/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "products/createProductForm";
    }

    @PostMapping("/products/new")
    public String create(BookForm form) {

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        productsService.saveProduct(book);
        return "redirect:/";
    }

    @GetMapping("/products")
    public String list(Model model) {
        List<Product> products = productsService.findProducts();
        model.addAttribute("products", products);
        return "products/productsList";
    }

    @GetMapping("products/{productId}/edit")
    public String updateProductForm(@PathVariable("productId") Long productId, Model model) {
        Book product = (Book) productsService.findOne(productId);

        BookForm form = new BookForm();
        form.setId(product.getId());
        form.setName(product.getName());
        form.setPrice(product.getPrice());
        form.setStockQuantity(product.getStockQuantity());
        form.setAuthor(product.getAuthor());
        form.setIsbn(product.getIsbn());

        model.addAttribute("form", form);
        return "products/updateProductForm";
    }

    @PostMapping("products/{productId}/edit")
    public String updateProduct(@PathVariable Long productId, @ModelAttribute("form") BookForm form) {

        productsService.updateProduct(productId, form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/products";
    }
}