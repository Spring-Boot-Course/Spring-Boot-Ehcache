package me.sml.springbootecache.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Member {
    private long idx;

    private String email;

    private String name;


    public Member(long idx, String email, String name){
        this.idx = idx;
        this.email = email;
        this.name = name;
    }
}
