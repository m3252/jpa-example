package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MemberApiController {

    private final MemberService memberService;

    @Autowired
    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> members = memberService.findMembers();
        List<MemberDto> memberDtos = members.stream()
                .map(o -> new MemberDto(o.getName()))
                .collect(Collectors.toList());

        return new Result<>(memberDtos);
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMember createMember) {
        String name = createMember.getName();

        Member member = new Member();
        member.setName(name);

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable Long id,
            @RequestBody @Valid UpdateMember updateMember
    ) {
        memberService.update(id, updateMember.getName());
        return new UpdateMemberResponse(id);
    }


    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateMember {
        @NotBlank
        private String name;

        public CreateMember() {
        }

        public CreateMember(String name) {
            this.name = name;
        }
    }

    @Data
    static class UpdateMemberResponse {
        private Long id;

        public UpdateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class UpdateMember {
        private Long id;

        private String name;

        public UpdateMember() {
        }

        public UpdateMember(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Data
    static class Result<T> {
        private T data;
        private int count;

        public Result(T data) {
            this.data = data;
        }

        public Result(T data, int count) {
            this.data = data;
            this.count = count;
        }
    }

    @Data
    static class MemberDto {
        private String name;

        public MemberDto(String name) {
            this.name = name;
        }
    }
}
