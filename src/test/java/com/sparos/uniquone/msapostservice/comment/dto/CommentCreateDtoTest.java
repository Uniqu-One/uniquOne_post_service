package com.sparos.uniquone.msapostservice.comment.dto;

import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;


class CommentCreateDtoTest {


    @Test
    void dtoTest(){

        CommentCreateDto commentCreateDto = new CommentCreateDto();


        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Comment map = mapper.map(commentCreateDto, Comment.class);

        //dto To entity
        new ModelMapper().map(commentCreateDto, Comment.class);


        //entity to dto

    }

}