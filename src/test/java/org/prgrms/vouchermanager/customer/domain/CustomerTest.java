package org.prgrms.vouchermanager.customer.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.prgrms.vouchermanager.domain.customer.domain.Customer;
import org.prgrms.vouchermanager.domain.customer.domain.CustomerStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @Nested
    class Create {
        @Test
        void Customer_생성_성공() {
            final Customer customer = Customer.create("example", "example@email.com");

            assertThat(customer.getId()).isNotNull();
            assertThat(customer.getName()).isEqualTo("example");
            assertThat(customer.getEmail()).isEqualTo("example@email.com");
            assertThat(customer.getStatus()).isEqualTo(CustomerStatus.CREATED);
            assertThat(customer.getCreateAt()).isNotNull();
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 생성자에서_name이_공백이면_예외를_던진다(String name) {
            assertThatThrownBy(() -> Customer.create(name, "example@email.com"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 생성자에서_email이_공백이면_예외를_던진다(String email) {
            assertThatThrownBy(() -> Customer.create("example", email))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 생성자에서_email이_엣을_포함하지_않으면_예외를_던진다() {
            assertThatThrownBy(() -> Customer.create("example", "exampleemail.com"))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class BlackList {
        @Test
        void 블랙리스트등록_성공() {
            final Customer customer = Customer.create("example", "example@email.com");

            customer.changeBlackList();

            assertThat(customer.getStatus()).isEqualTo(CustomerStatus.BLOCKED);
        }
    }
}