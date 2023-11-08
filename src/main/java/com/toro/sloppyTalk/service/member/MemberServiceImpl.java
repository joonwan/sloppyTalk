package com.toro.sloppyTalk.service.member;

import com.toro.sloppyTalk.Repository.friend.FriendRepository;
import com.toro.sloppyTalk.Repository.member.MemberRepository;
import com.toro.sloppyTalk.domain.Friend;
import com.toro.sloppyTalk.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;


    @Override
    @Transactional(readOnly = false)
    public Long save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public Member findLoginMember(String loginId){
        log.info("loginId = {}", loginId);
        return memberRepository.findByLoginId(loginId);
    }

    @Override
    public List<Member> findMembers(Long memberId){
        List<Long> friendIds = new ArrayList<>();
        List<Member> result = new ArrayList<>();
        List<Member> findMembers = memberRepository.findAll(memberId);
        List<Friend> friends = findMember(memberId).getFriends();

        for (Friend friend : friends) {
            friendIds.add(friend.getOther().getId());
        }

        for(Member findMember : findMembers){
            Long findMemberId = findMember.getId();
            if(!(friendIds.contains(findMemberId))){
                result.add(findMember);
            }
        }

        return result;
    }


    @Override
    @Transactional(readOnly = false)
    public Long follow(Long memberId, Long targetId) {
        Member member = memberRepository.findById(memberId);
        Member target = memberRepository.findById(targetId);


        Friend friend = new Friend(member, target);
        return friendRepository.save(friend);
    }

    @Override
    public List<Friend> findFriends(Long memberId) {
        Member member = memberRepository.findById(memberId);
        return friendRepository.findFriends(member);
    }
}
