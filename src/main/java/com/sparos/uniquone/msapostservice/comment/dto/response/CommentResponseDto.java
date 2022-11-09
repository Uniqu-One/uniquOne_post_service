package com.sparos.uniquone.msapostservice.comment.dto.response;

import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import com.sparos.uniquone.msapostservice.comment.domain.CommentUtils;
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
public class CommentResponseDto {

    private Long cornId;
    private Long commentId;

    private Long parentId;

    private String parentNickname;
    private Long userId;

    private String cornImgUrl;
    private String writerNick;

    private String content;
    private Integer depth;
    private String regDate;
    private LocalDateTime modDate;

    private List<CommentResponseDto> children = new ArrayList<>();

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.userId = comment.getUserId();
        this.cornId = comment.getCornId();
        this.writerNick = comment.getUserNickName();
        this.content = comment.getContent();
        this.depth = comment.getDepth();
        this.regDate = CommentUtils.converter(comment.getRegDate());
        this.modDate = comment.getModDate();
//        this.nickName = comment.get
    }
}
