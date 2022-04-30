package org.prgrms.vouchermanager.domain.customer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.domain.customer.domain.Customer;
import org.prgrms.vouchermanager.domain.customer.domain.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultCustomerService implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(String name, String email) {
        return customerRepository.insert(Customer.create(name, email));
    }

    @Override
    public Customer findById(UUID id) {
        return customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));
    }
}
