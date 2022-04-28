package org.prgrms.vouchermanager.domain.customer.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.vouchermanager.JdbcTestConfig;
import org.prgrms.vouchermanager.domain.customer.domain.Customer;
import org.prgrms.vouchermanager.domain.customer.domain.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {JdbcTestConfig.class})
class JdbcCustomerRepositoryTest {

    @Autowired
    CustomerRepository customerJdbcRepository;
    @Autowired
    DataSource dataSource;

    @BeforeEach
    void boforeEach() {
        customerJdbcRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 삽입할 수 있다")
    void insert_customer를_삽입할_수_있다() {
        Customer customer = Customer.create("customer01", "testEmail01@email.com");

        customerJdbcRepository.insert(customer);
        Customer foundCustomer = customerJdbcRepository.findById(customer.getId()).get();

        assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @DisplayName("고객을 id로 조회할 수 있다")
    void findById_id로_조회할_수_있다() {
        Customer customer = Customer.create("customer02", "testEmail02@email.com");
        customerJdbcRepository.insert(customer);

        UUID customerId = customer.getId();
        Customer foundCustomer = customerJdbcRepository.findById(customerId).get();

        assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    @DisplayName("고객을 name으로 조회할 수 있다")
    void findByName_name으로_조회할_수_있다() {
        List<Customer> sameNameCustomers = List.of(
                customerJdbcRepository.insert(Customer.create("customer03", "testEmail04@email.com")),
                customerJdbcRepository.insert(Customer.create("customer03", "testEmail15@email.com")),
                customerJdbcRepository.insert(Customer.create("customer03", "testEmail16@email.com"))
        );

        List<Customer> foundCustomers = customerJdbcRepository.findByName("customer03");

        assertThat(foundCustomers).containsAll(sameNameCustomers);
    }

    @Test
    @DisplayName("고객을 email로 조회할 수 있다")
    void findByEmail_email로_조회할_수_있다() {
        Customer customer = Customer.create("customer05", "testEmail05@email.com");
        customerJdbcRepository.insert(customer);

        Customer foundCustomer = customerJdbcRepository.findByEmail(customer.getEmail()).get();

        assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    @DisplayName("모든 고객을 조회할 수 있다")
    void findAll_모든_고객을_조회할_수_있다() {
        List<Customer> expectList = List.of(
                Customer.create("customer06", "testEmail06@email.com"),
                Customer.create("customer07", "testEmail07@email.com"));
        expectList.forEach(customer -> customerJdbcRepository.insert(customer));

        List<Customer> all = customerJdbcRepository.findAll();

        assertThat(all).containsAll(expectList);
    }

    @Test
    @DisplayName("고객을 id로 삭제할 수 있다")
    void delete_id로_고객을_삭제할_수_있다() {
        Customer customer = Customer.create("customer08", "testEmail08@email.com");
        customerJdbcRepository.insert(customer);

        customerJdbcRepository.delete(customer.getId());

        assertThat(customerJdbcRepository.findById(customer.getId())).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("고객을 전부 삭제 할 수 있다")
    void deleteAll_전부_삭제_할_수_있다() {
        Customer customer = Customer.create("customer09", "testEmail09@email.com");
        customerJdbcRepository.insert(customer);

        customerJdbcRepository.deleteAll();

        assertThat(customerJdbcRepository.findAll()).containsAll(List.of());
    }
}
