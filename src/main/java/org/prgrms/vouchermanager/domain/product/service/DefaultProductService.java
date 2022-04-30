package org.prgrms.vouchermanager.domain.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.domain.product.domain.Product;
import org.prgrms.vouchermanager.domain.product.domain.ProductRepository;
import org.prgrms.vouchermanager.domain.product.domain.ProductStatus;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product registerProduct(String name, long price, ProductStatus status) {
        Product product = Product.create(name, price, status);
        return productRepository.insert(product);
    }

    @Override
    public Product findById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("존재하지 않는 Product 입니다. {0}", id)));
    }
}
