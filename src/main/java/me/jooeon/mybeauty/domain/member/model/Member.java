package me.jooeon.mybeauty.domain.member.model;

import jakarta.persistence.*;
import lombok.*;
import me.jooeon.mybeauty.global.common.model.enums.Status;
import me.jooeon.mybeauty.global.common.model.entity.BaseEntity;
import me.jooeon.mybeauty.domain.article.model.Comment;
import me.jooeon.mybeauty.domain.article.model.Scrap;
import me.jooeon.mybeauty.domain.review.model.Review;
import me.jooeon.mybeauty.domain.likes.model.Likes;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // 연관 관계 mapping
    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Scrap> scraps = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private MemberProfile memberProfile;

//    @OneToOne(mappedBy = "member")
//    private Auth auth;

    public static Member of(String email, String name, Role role) {
        return Member.builder()
                .email(email)
                .nickname(name)
                .role(role)
                .status(Status.ACTIVE)
                .build();
    }
}
