package org.prgrms.vouchermanager.domain.product.service;

import org.prgrms.vouchermanager.domain.product.domain.Product;
import org.prgrms.vouchermanager.domain.product.domain.ProductStatus;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> findAllProducts();

    Product registerProduct(String name, long price, ProductStatus status);

    Product findById(UUID id);
}
