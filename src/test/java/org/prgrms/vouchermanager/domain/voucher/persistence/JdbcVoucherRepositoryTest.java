package org.prgrms.vouchermanager.domain.voucher.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.vouchermanager.JdbcTestConfig;
import org.prgrms.vouchermanager.domain.voucher.domain.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.domain.PercentDiscountVoucher;
import org.prgrms.vouchermanager.domain.voucher.domain.Voucher;
import org.prgrms.vouchermanager.domain.voucher.domain.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JdbcTestConfig.class})
@ActiveProfiles("jdbc")
class JdbcVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("insert를 하면 voucher를 삽입한다.")
    void insert() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);

        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);
        Voucher foundFixedVoucher = voucherRepository.findById(fixedAmountVoucher.getId()).get();
        Voucher foundPercentVoucher = voucherRepository.findById(percentDiscountVoucher.getId()).get();

        assertThat(foundFixedVoucher).isEqualTo(fixedAmountVoucher);
        assertThat(foundPercentVoucher).isEqualTo(percentDiscountVoucher);
    }

    @Test
    @DisplayName("voucherId로 저장된 voucher를 찾아서 반환한다.")
    void findById() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        voucherRepository.insert(fixedAmountVoucher);

        Voucher foundVoucher = voucherRepository.findById(fixedAmountVoucher.getId()).get();

        assertThat(foundVoucher).isEqualTo(fixedAmountVoucher);
    }

    @Test
    @DisplayName("저장된 모든 Voucher를 List로 반환한다.")
    void getAll() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        List<Voucher> testVoucherList = List.of(fixedAmountVoucher, percentDiscountVoucher);
        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        List<Voucher> all = voucherRepository.findAll();

        assertThat(all).isNotEmpty().containsAll(testVoucherList);
    }

    @Test
    @DisplayName("이미 존재하는 Voucher를 insert 할 수 없다")
    void insert_이미_존재하는_Voucher를_insert할_수_없다() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);

        voucherRepository.insert(fixedAmountVoucher);

        assertThatThrownBy(() -> voucherRepository.insert(fixedAmountVoucher)).isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("findById의 voucher가 존재하지 않는 경우 Optional.empty()를 반환한다.")
    void findById_해당_voucher가_존재하지_않을_수_있다() {
        UUID notExsistVoucherId = UUID.randomUUID();

        Optional<Voucher> foundVoucher = voucherRepository.findById(notExsistVoucherId);

        assertThat(foundVoucher).isEqualTo(Optional.empty());
    }

}