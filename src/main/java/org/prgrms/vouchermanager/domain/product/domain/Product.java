package org.prgrms.vouchermanager.domain.product.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@EqualsAndHashCode(of = "id", doNotUseGetters = true)
@Getter
public class Product {

    /* 아이디 */
    private UUID id;

    /* 이름 */
    private final String name;

    /* 가격 */
    private final long price;

    /*  상품 상태 */
    private ProductStatus status;

    /* 생성 일시 */
    private LocalDateTime createdAt;

    private Product(String name, long price) {
        checkArgument(Strings.isNotBlank(name), "name은 null 혹은 공백이 될 수 없습니다.");
        checkArgument(price >= 0, "price는 양수여야 합니다.");

        this.name = name;
        this.price = price;
    }

    public static Product create(String name, long price, ProductStatus status) {
        return new Product(name, price).withId(UUID.randomUUID()).withStatus(status).withCreatedAt(LocalDateTime.now());
    }

    public static Product bind(UUID id, String name, long price,ProductStatus status, LocalDateTime createdAt) {
        return new Product(name, price).withId(id).withStatus(status).withCreatedAt(createdAt);
    }

    private Product withId(UUID id) {
        checkNotNull(id, "아이디 필수");

        this.id = id;

        return this;
    }

    private Product withCreatedAt(LocalDateTime createdAt) {
        checkNotNull(createdAt, "생성 일시 필수");

        this.createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS);

        return this;
    }

    private Product withStatus(ProductStatus status) {
        checkNotNull(status, "상품 상태 필수");

        this.status = status;

        return this;
    }
}
