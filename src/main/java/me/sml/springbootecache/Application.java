package me.sml.springbootecache;

import lombok.extern.slf4j.Slf4j;
import me.sml.springbootecache.domain.Member;
import me.sml.springbootecache.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@EnableCaching
@SpringBootApplication
@Controller
public class Application {

    @Autowired
    MemberRepository memberRepository;


    @GetMapping("/member/nocache/{name}")
    @ResponseBody
    public Member getNoCacheMember(@PathVariable String name){
        long start = System.currentTimeMillis();
        Member member = memberRepository.findByNameNoCache(name);
        long end = System.currentTimeMillis();
        log.info(name + "의 수행시간 : " + Long.toString(end - start));
        return member;
    }

    @GetMapping("/member/cache/{name}")
    @ResponseBody
    public Member getCacheMember(@PathVariable String name){

        long start = System.currentTimeMillis(); // 수행시간 측정
        Member member = memberRepository.findByNameCache(name); // db 조회
        long end = System.currentTimeMillis();
        log.info(name + "의 수행시간 : " + Long.toString(end - start));
        return member;
    }

    @GetMapping("/member/refresh/{name}")
    @ResponseBody
    public String refresh(@PathVariable String name){
        memberRepository.refresh(name); // 캐시제거
        return "cache clear!";
    }

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}

