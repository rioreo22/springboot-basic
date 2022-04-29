package org.prgrms.vouchermanager.domain.wallet.persistence;

import org.prgrms.vouchermanager.domain.wallet.domain.Wallet;
import org.prgrms.vouchermanager.domain.wallet.domain.WalletRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.util.Assert.isNull;

/**
 * Wallet은 인메모리 리포지토리로 작성하였습니다.
 */
@Repository
public class MemoryWalletRepository implements WalletRepository {

    private final Map<UUID, Wallet> storage = new ConcurrentHashMap<>();

    @Override
    public void insert(Wallet wallet) {
        isNull(storage.get(wallet.getId()), "이미 존재하는 Wallet 입니다.");
        storage.put(wallet.getId(), wallet);
    }

    @Override
    public Optional<Wallet> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        return storage.values().stream().filter(wallet -> wallet.getCustomerId().equals(customerId)).collect(Collectors.toList());
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        return storage.values().stream().filter(wallet -> wallet.getVoucherId().equals(voucherId)).collect(Collectors.toList());
    }

    @Override
    public Optional<Wallet> findByCustomerIdAndVoucherId(UUID customerId, UUID voucherId) {
        return findByCustomerId(customerId).stream().filter(wallet -> wallet.getVoucherId().equals(voucherId)).findFirst();
    }

    @Override
    public void delete(Wallet wallet) {
        Wallet removed = storage.remove(wallet.getId());
        checkNotNull(removed, "삭제할 wallet이 존재하지 않습니다.");
    }

    @Override
    public void deleteByVoucherId(UUID voucherId) {
        findByVoucherId(voucherId).forEach(this::delete);
    }

    @Override
    public void update(Wallet wallet) {
        storage.put(wallet.getId(), wallet);
    }

}
