package org.prgrms.vouchermanager.domain.product.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.product.domain.Product;
import org.prgrms.vouchermanager.domain.product.domain.ProductRepository;
import org.prgrms.vouchermanager.domain.product.domain.ProductStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class DefaultProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DefaultProductService productService;

    @BeforeEach
    void beforeEach() {
        productRepository.deleteAll();
    }

    @Test
    void registerProduct() {
        Product product = productService.registerProduct("Prodcuct", 1000, ProductStatus.FOR_SALE);

        Product findProduct = productService.findById(product.getId());

        assertThat(product).isEqualTo(findProduct);
    }

    @Test
    void findAllProducts() {
        List<Product> products = new ArrayList<>();
        IntStream.range(1, 10)
                .forEach(i -> products.add(productService.registerProduct("name" + i, i, ProductStatus.FOR_SALE)));

        List<Product> allProducts = productService.findAllProducts();

        assertThat(allProducts).containsAll(products);
    }

    @Test
    void findById() {
        Product product = productService.registerProduct("Prodcuct", 1000, ProductStatus.FOR_SALE);

        Product findProduct = productService.findById(product.getId());

        assertThat(findProduct).isEqualTo(product);
    }
}