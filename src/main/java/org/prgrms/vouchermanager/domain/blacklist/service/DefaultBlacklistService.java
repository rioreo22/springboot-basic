package org.prgrms.vouchermanager.domain.blacklist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.domain.blacklist.domain.Blacklist;
import org.prgrms.vouchermanager.domain.blacklist.domain.BlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultBlacklistService implements BlacklistService {

    private final BlacklistRepository blacklistRepository;

    @Override
    public List<Blacklist> getAll() {
        return blacklistRepository.getAll();
    }
}
