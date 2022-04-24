package org.prgrms.vouchermanager.domain.blacklist.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlacklistRepository {
    List<Blacklist> getAll();

    Optional<Blacklist> findById(UUID blockCustomerId);
}
