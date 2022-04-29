package org.prgrms.vouchermanager.domain.wallet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.domain.wallet.domain.Wallet;
import org.prgrms.vouchermanager.domain.wallet.domain.WalletRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultWalletService implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public void registerWallet(UUID customerId, UUID voucherId) {
        walletRepository.insert(new Wallet(customerId, voucherId));
    }

    @Override
    public void useWallet(UUID id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("WalletService : 해당 {0}이 존재하지 않습니다.", id)));
        wallet.useWallet();
        walletRepository.update(wallet);
    }

    @Override
    public List<Wallet> findWalletsByVoucherId(UUID voucherId) {
        return walletRepository.findByVoucherId(voucherId);
    }

    @Override
    public List<Wallet> findWalletsByCustomerId(UUID customerId) {
        return walletRepository.findByCustomerId(customerId);
    }
}
