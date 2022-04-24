package org.prgrms.vouchermanager.domain.voucher.domain;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.UUID;

public interface Voucher extends Serializable {
    UUID getVoucherId();

    VoucherType getVoucherType();

    long getDiscountValue();

    Long discount(long beforeDiscount);

    static Voucher create(UUID voucherId, VoucherType type, long amount) {
        switch (type) {
            case FIXED -> {
                return new FixedAmountVoucher(voucherId, amount);
            }
            case PERCENT -> {
                return new PercentDiscountVoucher(voucherId, amount);
            }
            default -> throw new IllegalArgumentException(MessageFormat.format("{0} 타입의 바우처가 존재하지 않습니다.", type));
        }
    }
}
