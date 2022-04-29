package org.prgrms.vouchermanager.domain.wallet.service;

import org.prgrms.vouchermanager.domain.wallet.domain.Wallet;

import java.util.List;
import java.util.UUID;

public interface WalletService {

    /* 지갑 등록 */
    void registerWallet(UUID customerId, UUID voucherId);

    /* 지갑 사용 */
    void useWallet(UUID id);

    /* 특정 바우처가 포함된 지갑 목록 조회 */
    List<Wallet> findWalletsByVoucherId(UUID voucherId);

    /* 특정 고객이 포함된 지갑 목록 조회 */
    List<Wallet> findWalletsByCustomerId(UUID customerId);

}
