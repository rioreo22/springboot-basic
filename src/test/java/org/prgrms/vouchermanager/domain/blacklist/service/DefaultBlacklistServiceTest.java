package org.prgrms.vouchermanager.domain.blacklist.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.blacklist.domain.Blacklist;
import org.prgrms.vouchermanager.domain.blacklist.persistence.CsvBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;

import java.util.List;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class DefaultBlacklistServiceTest {


    @Autowired
    private CsvBlacklistRepository csvBlacklistRepository;

    @Autowired
    private BlacklistService blacklistService;

    @Test
    @DisplayName("블랙리스트를 조회할 수 있다.")
    void getAll() {
        List<Blacklist> allByService = blacklistService.getAll();
        List<Blacklist> allByRepository = csvBlacklistRepository.getAll();

        Assertions.assertThat(allByService).containsAll(allByRepository);
    }
}