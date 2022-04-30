package org.prgrms.vouchermanager.domain.customer.service;

import org.prgrms.vouchermanager.domain.customer.domain.Customer;

import java.util.UUID;

public interface CustomerService {

    Customer createCustomer(String name, String email);

    Customer findById(UUID id);

}
