package org.prgrms.vouchermanager.domain.product.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.vouchermanager.JdbcTestConfig;
import org.prgrms.vouchermanager.domain.product.domain.Product;
import org.prgrms.vouchermanager.domain.product.domain.ProductRepository;
import org.prgrms.vouchermanager.domain.product.domain.ProductStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {JdbcTestConfig.class})
class JdbcProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    DataSource dataSource;

    @BeforeEach
    void boforeEach() {
        productRepository.deleteAll();
    }

    private void compareProduct(Product actual, Product expected) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        assertThat(actual.getStatus()).isEqualTo(expected.getStatus());
        assertThat(actual.getCreatedAt()).isEqualTo(expected.getCreatedAt());
    }

    @Test
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @DisplayName("insert 할 수 있다")
    void insert() {
        Product product = Product.create("tea", 1000, ProductStatus.FOR_SALE);

        productRepository.insert(product);
        Product foundProduct = productRepository.findById(product.getId()).get();

        compareProduct(product, foundProduct);
    }

    @Test
    @DisplayName("id로 Product를 조회할 수 있다")
    void findById() {
        Product product = Product.create("iceCream", 500, ProductStatus.FOR_SALE);
        UUID id = product.getId();
        productRepository.insert(product);

        Product foundProduct = productRepository.findById(id).get();

        compareProduct(foundProduct, product);
    }

    @Test
    @DisplayName("name으로 조회할 수 있다.")
    void findByName() {
        String name = "coffeeBean";
        List<Product> sameNameProducts = List.of(
                productRepository.insert(Product.create(name, 5000, ProductStatus.FOR_SALE)),
                productRepository.insert(Product.create(name, 10000, ProductStatus.SOLD_OUT))
        );

        List<Product> foundProducts = productRepository.findByName(name);

        assertThat(foundProducts).containsAll(sameNameProducts);
    }

    @Test
    @DisplayName("저장된 모든 Product를 조회할 수 있다.")
    void findAll() {
        List<Product> productList = new ArrayList<>();
        IntStream.range(1, 10).forEach(i -> productList.add(productRepository.insert(Product.create("name" + i, i, ProductStatus.FOR_SALE))));

        List<Product> found = productRepository.findAll();

        assertThat(found.size()).isEqualTo(productList.size());
        assertThat(found).containsAll(productList);
    }

    @Test
    @DisplayName("삭제할 수 있다.")
    void delete() {
        Product product = Product.create("tea", 1000, ProductStatus.FOR_SALE);
        productRepository.insert(product);

        productRepository.delete(product);
        Optional<Product> found = productRepository.findById(product.getId());

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("products 테이블의 모든 테이더를 삭제할 수 있다.")
    void deleteAll() {
        IntStream.range(1, 10).forEach(i -> productRepository.insert(Product.create("name" + i, i, ProductStatus.FOR_SALE)));

        productRepository.deleteAll();
        List<Product> found = productRepository.findAll();

        assertThat(found.size()).isEqualTo(0);
    }
}