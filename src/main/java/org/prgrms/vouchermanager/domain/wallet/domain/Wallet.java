package org.prgrms.vouchermanager.domain.wallet.domain;

import lombok.Getter;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
public class Wallet {

    /* Wallet 아이디 */
    private final UUID id = UUID.randomUUID();

    /* Voucher 아이디 */
    private final UUID voucherId;

    /* Customer 아이디 */
    private final UUID customerId;

    /* 지갑 상태 */
    private WalletStatus walletStatus = WalletStatus.USABLE;

    public Wallet(UUID customerId, UUID voucherId) {
        checkNotNull(customerId);
        checkNotNull(voucherId);

        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public void useWallet() {
        this.walletStatus = WalletStatus.UNUSABLE;
    }
}
