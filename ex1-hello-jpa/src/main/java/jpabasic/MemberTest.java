package jpabasic;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "MEMBER_TEST_SEQ_GENERATOR",
        sequenceName = "MEMBER_TEST_SEQ",
        initialValue = 1, allocationSize = 50
)
public class MemberTest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_TEST_SEQ_GENERATOR")
    private Long id;

    private String username;

    public MemberTest() {
    }

    public MemberTest(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
