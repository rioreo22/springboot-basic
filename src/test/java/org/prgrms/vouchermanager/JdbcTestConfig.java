package org.prgrms.vouchermanager;

import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.vouchermanager.domain.customer.persistence.JdbcCustomerRepository;
import org.prgrms.vouchermanager.domain.product.persistence.JdbcProductRepository;
import org.prgrms.vouchermanager.domain.voucher.persistence.JdbcVoucherRepository;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@TestConfiguration
@ComponentScan(basePackages = {"org.prgrms.vouchermanager"},
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {JdbcCustomerRepository.class, JdbcVoucherRepository.class, JdbcProductRepository.class}
                )
        }
)
public class JdbcTestConfig {
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost/test_order_mgmt")
                .username("root")
                .password("1234")
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}