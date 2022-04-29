package org.prgrms.vouchermanager.domain.wallet.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {

    void insert(Wallet wallet);

    Optional<Wallet> findById(UUID id);

    List<Wallet> findByCustomerId(UUID customerId);

    List<Wallet> findByVoucherId(UUID voucherId);

    Optional<Wallet> findByCustomerIdAndVoucherId(UUID customerId, UUID voucherId);

    void delete(Wallet wallet);

    void deleteByVoucherId(UUID voucherId);

    void update(Wallet wallet);
}
