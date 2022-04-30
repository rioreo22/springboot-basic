package org.prgrms.vouchermanager.domain.customer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.vouchermanager.domain.customer.domain.Customer;
import org.prgrms.vouchermanager.domain.customer.domain.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class DefaultCustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DefaultCustomerService customerService;

    @BeforeEach
    void beforeEach() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("createCustomer 생성 성공")
    void create() {
        String testName = "testName";
        String email = "testEmail@email.com";

        Customer insertedCustomer = customerService.createCustomer(testName, email);
        Customer customer = customerRepository.findById(insertedCustomer.getId()).get();

        assertThat(insertedCustomer.getName()).isEqualTo(testName);
        assertThat(insertedCustomer.getEmail()).isEqualTo(email);
        assertThat(customer).isEqualTo(insertedCustomer);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("name이 null 혹은 공백인 경우 예외를 던진다")
    void createCustomer_name_test(String name) {
        assertThatIllegalArgumentException().isThrownBy(() -> customerService.createCustomer(name, "testEmail@email.com"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"noAtemail"})
    @DisplayName("email이 null 혹은 공백인 경우 예외를 던진다")
    void createCustomer_email_test(String email) {
        assertThatIllegalArgumentException().isThrownBy(() -> customerService.createCustomer("testName", email));
    }
}