package smallshop.smallshop.service;

import smallshop.smallshop.domain.Member;
import smallshop.smallshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    @Transactional(readOnly = true)
    public Member findMemberById(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    /**
     * 회원 삭제
     */
    public void deleteMember(Member member) {
        // memberRepository를 사용하여 회원을 삭제합니다.
        memberRepository.delete(member);
    }

    /**
     * 회원 정보 수정
     */
    public void updateMember(Member member) {
        Member existingMember = memberRepository.findOne(member.getId());
        if (existingMember == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        // 수정할 필드들을 업데이트합니다.
        existingMember.setName(member.getName());
        existingMember.setAddress(member.getAddress());
        // 다른 필드도 필요하다면 해당 필드도 업데이트

        // 업데이트된 회원 정보를 저장합니다.
        memberRepository.save(existingMember);
    }

}