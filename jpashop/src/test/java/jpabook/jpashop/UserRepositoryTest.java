package jpabook.jpashop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void save() {
        User user = new User();
        user.setUsername("test");
        Long saveId = userRepository.save(user);
        User findUser = userRepository.find(saveId);

        assertThat(findUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findUser).isEqualTo(user);
        assertThat(findUser).isSameAs(user);

    }


}