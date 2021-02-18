package springSecurityFormLogin.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
//lombok을 통해 getter와 toString 생성
public class Member {
    @Id @GeneratedValue
    private Long id;

    private String memberId;
    private String password;
    private Integer age;
    private Boolean isMarried;

    public Member(String memberId, String password, Integer age, Boolean isMarried){
        //Entity에 public, private 기본생성자가 있어야 하는 이유는 JPA의 proxy 패턴 연관!
        //추가학습 point JPA의 proxy 패턴이란?
        this.memberId = memberId;
        this.password = password;
        this.age = age;
        this.isMarried = isMarried;
    }

    public void setPassword(String password){
        //패스워드 암호화를 위해 setPassword는 별도로 생성
        this.password = password;
    }
}
