package org.prgrms.vouchermanager.domain.blacklist.service;

import org.prgrms.vouchermanager.domain.blacklist.domain.Blacklist;

import java.util.List;

public interface BlacklistService {
    List<Blacklist> getAll();
}
