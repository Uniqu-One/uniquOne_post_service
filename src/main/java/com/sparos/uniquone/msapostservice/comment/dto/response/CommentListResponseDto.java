package com.sparos.uniquone.msapostservice.comment.dto.response;

import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponseDto {
    private Long commentId;
    private Long parentId;
    private String parentNickname;
    private Long userId;
    private String writerNick;
    //    private String nickName;
    private String content;
    private Integer depth;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private String cornImgUrl;

    private List<CommentResponseDto> children = new ArrayList<>();

    public CommentListResponseDto(Comment comment){
        this.commentId = comment.getId();
        this.userId = comment.getUserId();
        this.writerNick = comment.getUserNickName();
        this.content = comment.getContent();
        this.depth = comment.getDepth();
        this.regDate = comment.getRegDate();
        this.modDate = comment.getModDate();
//        this.nickName = comment.get
    }
}
