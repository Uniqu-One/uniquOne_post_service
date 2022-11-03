package com.sparos.uniquone.msapostservice.admin.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparos.uniquone.msapostservice.admin.domain.AdminBoard;
import com.sparos.uniquone.msapostservice.admin.domain.BoardType;
import com.sparos.uniquone.msapostservice.admin.dto.response.AdminBoardResponseDto;
import com.sparos.uniquone.msapostservice.admin.dto.response.AdminBoardResponseListDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparos.uniquone.msapostservice.admin.domain.QAdminBoard.adminBoard;

@Repository
public class AdminBoardRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public AdminBoardRepositorySupport(JPAQueryFactory queryFactory) {
        super(AdminBoard.class);
        this.queryFactory = queryFactory;
    }

    public AdminBoardResponseListDto findAllAdminBoard(Pageable pageable) {
//        Long boardId,Long updateUserId ,BoardType boardType, String title, String subTitle, String imgUrl)
        List<AdminBoardResponseDto> result = queryFactory.select(Projections.constructor(AdminBoardResponseDto.class,
                        adminBoard.id.as("boardId"),
                        adminBoard.writerId.as("updateUserId"),
                        adminBoard.boardType.as("boardType"),
                        adminBoard.title.as("title"),
                        adminBoard.subTitle.as("subTitle"),
                        adminBoard.imgUrl.as("imgUrl")
                )).from(adminBoard)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                //조건
                .where(adminBoard.boardType.eq(BoardType.ING))
//                정렬.
                .orderBy(new OrderSpecifier<>(Order.DESC,adminBoard.modDate))
                .fetch();

        boolean hasNext = false;
        if(result.size() > pageable.getPageSize()){
            hasNext = true;
            result.remove(pageable.getPageSize());
        }

        return new AdminBoardResponseListDto(result,hasNext);
    }
}
