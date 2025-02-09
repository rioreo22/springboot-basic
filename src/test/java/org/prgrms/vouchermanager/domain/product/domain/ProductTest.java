package org.prgrms.vouchermanager.domain.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ProductTest {

    @Nested
    @DisplayName("create 메소드로 Product 생성 테스트")
    class create {
        @Test
        @DisplayName("stock이 양수일 때 status FOR_SALE Product create 성공")
        void Product_status_FOR_SALE_생성_성공() {
            Product product = Product.create("productName", 1000L, ProductStatus.FOR_SALE);

            assertThat(product.getId()).isNotNull();
            assertThat(product.getName()).isEqualTo("productName");
            assertThat(product.getPrice()).isEqualTo(1000);
            assertThat(product.getStatus()).isEqualTo(ProductStatus.FOR_SALE);
        }

        @Test
        @DisplayName("stock이 0일 때 status SOLD_OUT Product create 성공")
        void Product_status_SOLD_OUT_생성_성공() {
            Product product = Product.create("productName", 1000L, ProductStatus.SOLD_OUT);

            assertThat(product.getId()).isNotNull();
            assertThat(product.getName()).isEqualTo("productName");
            assertThat(product.getPrice()).isEqualTo(1000);
            assertThat(product.getStatus()).isEqualTo(ProductStatus.SOLD_OUT);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void name이_공백이면_예외를_던진다(String name) {
            assertThatIllegalArgumentException().isThrownBy(() -> Product.create(name, 1000L, ProductStatus.FOR_SALE));
        }

    }

    @Nested
    class bind {

        @Test
        @DisplayName("Product status FOR_SALE bind 성공")
        void Product_status_FOR_SALE_생성_성공() {
            UUID id = UUID.randomUUID();
            LocalDateTime testTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
            Product product = Product.bind(id, "productName", 1000, ProductStatus.FOR_SALE, testTime);

            assertThat(product.getId()).isEqualTo(id);
            assertThat(product.getName()).isEqualTo("productName");
            assertThat(product.getPrice()).isEqualTo(1000);
            assertThat(product.getStatus()).isEqualTo(ProductStatus.FOR_SALE);
            assertThat(product.getCreatedAt()).isEqualTo(testTime);
        }

        @Test
        @DisplayName("Product status SOLD_OUT bind 성공")
        void Product_status_SOLD_OUT_생성_성공() {
            UUID id = UUID.randomUUID();
            LocalDateTime testTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
            Product product = Product.bind(id, "productName", 1000, ProductStatus.SOLD_OUT, testTime);

            assertThat(product.getId()).isEqualTo(id);
            assertThat(product.getName()).isEqualTo("productName");
            assertThat(product.getPrice()).isEqualTo(1000);
            assertThat(product.getStatus()).isEqualTo(ProductStatus.SOLD_OUT);
            assertThat(product.getCreatedAt()).isEqualTo(testTime);
        }

        @Test
        void id가_null이면_예외를_던진다() {
            assertThatNullPointerException().isThrownBy(() -> Product.bind(null, "productName", 1000L, ProductStatus.FOR_SALE, LocalDateTime.now()));
        }

        @ParameterizedTest
        @NullAndEmptySource
        void name이_공백이면_예외를_던진다(String name) {
            assertThatIllegalArgumentException().isThrownBy(() -> Product.bind(UUID.randomUUID(), name, 1000L, ProductStatus.FOR_SALE, LocalDateTime.now()));
        }

        @Test
        void price가_음수면_예외를_던진다() {
            assertThatIllegalArgumentException().isThrownBy(() -> Product.bind(UUID.randomUUID(), "productName", -1000L, ProductStatus.FOR_SALE, LocalDateTime.now()));
        }

    }

}