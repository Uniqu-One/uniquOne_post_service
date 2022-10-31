package com.sparos.uniquone.msapostservice.admin.domain;

import com.sparos.uniquone.msapostservice.admin.dto.request.AdminBoardRequestDto;
import com.sparos.uniquone.msapostservice.admin.dto.response.AdminBoardResponseDto;
import com.sparos.uniquone.msapostservice.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminBoard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //글쓴 운영자
    private Long writerId;

    //수정한 운영자.
    private Long updateId;

    @Setter
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private String title;

    private String subTitle;

    private String imgUrl;

    public AdminBoardResponseDto updateAdminBoard(BoardType boardType,Long updateId ,String title, String subTitle, String imgUrl){
        this.boardType = boardType;
        this.updateId = updateId;
        this.title = title;
        this.subTitle = subTitle;
        this.imgUrl = imgUrl;

        return new AdminBoardResponseDto(this.id,updateId,boardType,title,subTitle,imgUrl);
    }

}
