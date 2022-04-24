package org.prgrms.vouchermanager.blacklist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.blacklist.domain.Blacklist;
import org.prgrms.vouchermanager.domain.blacklist.persistence.CsvBlacklistRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CsvBlacklistRepositoryTest {

    private static final String SAVE_PATH = "src/main/resources/customer_blacklist.csv";

    private CsvBlacklistRepository csvBlacklistRepository;

    @BeforeEach
    void init() {
        csvBlacklistRepository = new CsvBlacklistRepository(SAVE_PATH);
        csvBlacklistRepository.init();
    }

    @Test
    @DisplayName("블랙리스트 고객 목록 반환")
    void getBlacklist() {
        assertThat(csvBlacklistRepository.getAll()).isNotEmpty();
    }

    @Test
    @DisplayName("아이디로 블랙리스트에서 조회한다.")
    void findById_아이디로_블랙리스트에서_조회한다() {
        //given
        String existBlacklistID = "5153948e-bf99-11ec-9d64-0242ac120002";

        //when
        Optional<Blacklist> byId = csvBlacklistRepository.findById(UUID.fromString(existBlacklistID));

        //then
        assertThat(byId.get()).isInstanceOf(Blacklist.class);
    }
}