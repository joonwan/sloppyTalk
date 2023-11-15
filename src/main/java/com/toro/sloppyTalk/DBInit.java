package com.toro.sloppyTalk;

import com.toro.sloppyTalk.domain.Member;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DBInit {

    private final InitService initService;

    @PostConstruct
    private void init(){
        initService.init();
    }



    @Service
    @Slf4j
    @Transactional
    static class InitService{

        @PersistenceContext
        private EntityManager em;

        public void init(){
            log.info("save member1 and member2 !!");
            Member member1 = new Member("member1","member1","member1");
            Member member2 = new Member("member2","member2","member2");

            em.persist(member1);
            em.persist(member2);
        }
    }
}
