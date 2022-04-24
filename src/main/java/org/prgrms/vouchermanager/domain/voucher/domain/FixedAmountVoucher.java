package org.prgrms.vouchermanager.domain.voucher.domain;

import lombok.ToString;

import java.util.UUID;

@ToString(callSuper = true)
public class FixedAmountVoucher extends AbstractVoucher {

    public static final long MAX_FIXED_VOUCHER_AMOUNT = 10000;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId, VoucherType.FIXED);
        validateAmount(amount);

        this.amount = amount;
    }

    private void validateAmount(long amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount > MAX_FIXED_VOUCHER_AMOUNT)
            throw new IllegalArgumentException("amount는 %d 를 초과할 수 없습니다.".formatted(MAX_FIXED_VOUCHER_AMOUNT));
    }

    @Override
    public long getDiscountValue() {
        return this.amount;
    }

    @Override
    public Long discount(long beforeDiscount) {
        return beforeDiscount - amount < 0 ? 0 : beforeDiscount - amount;
    }
}
