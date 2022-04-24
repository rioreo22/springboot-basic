package org.prgrms.vouchermanager.blacklist;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.blacklist.domain.Blacklist;

import static org.assertj.core.api.Assertions.assertThat;

class BlacklistTest {

    @Nested
    class Create {
        @Test
        void BlackList_생성_성공() {
            final Blacklist blacklist = new Blacklist("blackList@gmail.com");

            assertThat(blacklist.getId()).isNotNull();
            assertThat(blacklist.getEmail()).isEqualTo("blackList@gmail.com");
        }

        @Test
        @DisplayName("email은 공백이 될 수 없다.")
        void email은_공백이_될_수_없다() {
            Assertions.assertThatThrownBy(() -> new Blacklist("  ")).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("email은 @가 포함되야 한다.")
        void email은_골뱅이가_포함되야_한다() {
            Assertions.assertThatThrownBy(() -> new Blacklist("  ")).isInstanceOf(IllegalArgumentException.class);
        }

    }
}