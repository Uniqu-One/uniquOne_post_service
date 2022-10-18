package com.sparos.uniquone.msapostservice.comment.dto.response;

import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private Long parentId;
    private String nickName;
    private String content;
    private Integer depth;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private List<CommentResponseDto> children = new ArrayList<>();

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.depth = comment.getDepth();
        this.regDate = comment.getRegDate();
        this.modDate = comment.getModDate();
//        this.nickName = comment.get
    }
}
