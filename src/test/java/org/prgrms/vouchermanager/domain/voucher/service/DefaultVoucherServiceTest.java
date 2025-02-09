package org.prgrms.vouchermanager.domain.voucher.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.voucher.domain.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.domain.PercentDiscountVoucher;
import org.prgrms.vouchermanager.domain.voucher.domain.Voucher;
import org.prgrms.vouchermanager.domain.voucher.domain.VoucherRepository;
import org.prgrms.vouchermanager.domain.voucher.persistence.MemoryVoucherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultVoucherServiceTest {

    @Test
    @DisplayName("createVoucher는 VoucherRepository에 insert한다.")
    void createVoucherTest() {
        //given
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        VoucherService voucherService = new DefaultVoucherService(voucherRepository);

        //when
        UUID insertedVoucherId = voucherService.createVoucher("FIXED", 10L);

        //then
        assertThat(voucherRepository.findById(insertedVoucherId)).isNotEmpty();
    }

    @Test
    @DisplayName("존재하는 VoucherList를 반환한다.")
    void findVouchers() {
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        VoucherService voucherService = new DefaultVoucherService(voucherRepository);
        List<Voucher> expected = new ArrayList<>();
        LongStream.range(1, 10).forEach(i -> {
            expected.add(voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(),i)));
            expected.add(voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(),i)));
        });

        List<Voucher> vouchers = voucherService.findVouchers();

        Assertions.assertThat(vouchers).containsAll(expected);
    }

    @Test
    @DisplayName("createVoucher에서 잘못된 타입을 입력 받았을 때, 예외를 던진다.")
    void testWithIllegalTypeArg() {
        //given
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        VoucherService voucherService = new DefaultVoucherService(voucherRepository);

        //then
        assertThatThrownBy(() -> voucherService.createVoucher("WringVoucherType", 10L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("저장되지 않은 voucherId로 findVoucher할 경우 예외를 던진다.")
    void findVoucher_test() {
        //given
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        VoucherService voucherService = new DefaultVoucherService(voucherRepository);

        //then
        assertThatThrownBy(() -> voucherService.findVoucher(UUID.randomUUID())).isInstanceOf(IllegalArgumentException.class);
    }

}