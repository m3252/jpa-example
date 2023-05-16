package study.datajpa.repository;

import org.springframework.data.jpa.domain.Specification;
import study.datajpa.entity.Member;

import java.util.BitSet;

public class MemberSpec {

    public static Specification<Member> teamName(final String teamName) {
        return (Specification<Member>) (root, query, builder) -> builder.equal(root.get("team").get("name"), teamName);
    }

    public static Specification<Member> username(final String username) {
        return (Specification<Member>) (root, query, builder) -> builder.equal(root.get("username"), username);
    }
}
