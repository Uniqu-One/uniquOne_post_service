package com.sparos.uniquone.msapostservice.comment.domain;

import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.util.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@DynamicInsert
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userNickName;

    @Column(nullable = false)
    private Long cornId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    @Setter
    private String content;

    @Setter
    @Column(columnDefinition = "integer default 0")
    private Integer depth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

//    public void update(CommentRequestDto commentRequestDto){
//        this.content = commentRequestDto;
//    }

    public void updateParent(Comment parent){
        this.parent = parent;
    }

//    public boolean validateUser(Long userId){
//        return !this.userId == userId;
//    }

}
