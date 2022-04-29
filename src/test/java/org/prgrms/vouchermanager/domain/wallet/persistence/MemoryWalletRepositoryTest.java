package org.prgrms.vouchermanager.domain.wallet.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.wallet.domain.Wallet;
import org.prgrms.vouchermanager.domain.wallet.domain.WalletStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryWalletRepositoryTest {

    private final MemoryWalletRepository memoryWalletRepository = new MemoryWalletRepository();

    @Test
    @DisplayName("Wallet을 저장 할 수 있다.")
    void insert() {
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        Wallet wallet = new Wallet(customerId, voucherId);
        memoryWalletRepository.insert(wallet);

        assertThat(memoryWalletRepository.findById(wallet.getId()).get()).isEqualTo(wallet);
    }

    @Test
    @DisplayName("voucherId로 wallets를 찾을 수 있다.")
    void findByVoucherId() {
        UUID voucherId = UUID.randomUUID();
        List<Wallet> wallets = List.of(
                new Wallet(UUID.randomUUID(), voucherId),
                new Wallet(UUID.randomUUID(), voucherId),
                new Wallet(UUID.randomUUID(), voucherId)
        );
        wallets.forEach(memoryWalletRepository::insert);

        List<Wallet> byVoucherId = memoryWalletRepository.findByVoucherId(voucherId);

        assertThat(byVoucherId).containsAll(wallets);
    }

    @Test
    @DisplayName("customerId로 wallet List를 찾을 수 있다.")
    void findByCustomerId() {
        UUID customerId = UUID.randomUUID();
        List<Wallet> walletList = new ArrayList<>();

        IntStream.range(1, 10).forEach(i -> {
            Wallet wallet = new Wallet(customerId, UUID.randomUUID());
            walletList.add(wallet);
            memoryWalletRepository.insert(wallet);
        });

        List<Wallet> findWallets = memoryWalletRepository.findByCustomerId(customerId);

        assertThat(findWallets).containsAll(walletList);
    }

    @Test
    @DisplayName("wallet을 업데이트 할 수 있다.")
    void update() {
        Wallet wallet = new Wallet(UUID.randomUUID(), UUID.randomUUID());
        memoryWalletRepository.insert(wallet);

        wallet.useWallet();
        memoryWalletRepository.update(wallet);

        assertThat(wallet.getWalletStatus()).isEqualTo(WalletStatus.UNUSABLE);
    }

    @Test
    @DisplayName("customerId로 사용가능한 wallets를 조회한다.")
    void findUsableWalletsByCustomerId() {

    }

    @Test
    @DisplayName("wallet을 삭제할 수 있다.")
    void delete() {
        Wallet wallet = new Wallet(UUID.randomUUID(), UUID.randomUUID());
        memoryWalletRepository.insert(wallet);

        memoryWalletRepository.delete(wallet);

        assertThat(memoryWalletRepository.findById(wallet.getId())).isEqualTo(Optional.empty());
    }

    @Test
    void findByCustomerIdAndVoucherId() {
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        Wallet wallet = new Wallet(customerId, voucherId);

        memoryWalletRepository.insert(wallet);

        assertThat(memoryWalletRepository.findByCustomerIdAndVoucherId(customerId, voucherId).get()).isEqualTo(wallet);
    }

    @Test
    @DisplayName("voucherId로 wallet을 삭제할 수 있다.")
    void deleteByVoucherId() {
        UUID voucherId = UUID.randomUUID();
        List<Wallet> sameVoucherIdWallets = List.of(
                new Wallet(UUID.randomUUID(), voucherId),
                new Wallet(UUID.randomUUID(), voucherId),
                new Wallet(UUID.randomUUID(), voucherId)
        );
        sameVoucherIdWallets.forEach(memoryWalletRepository::insert);

        memoryWalletRepository.deleteByVoucherId(voucherId);
        List<Wallet> foundWallets = memoryWalletRepository.findByVoucherId(voucherId);

        assertThat(foundWallets).containsAll(List.of());
        assertThat(foundWallets.size()).isEqualTo(0);
    }
}