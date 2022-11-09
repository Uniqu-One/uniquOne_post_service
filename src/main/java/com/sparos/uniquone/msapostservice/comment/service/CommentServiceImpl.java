package com.sparos.uniquone.msapostservice.comment.service;

import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import com.sparos.uniquone.msapostservice.comment.domain.CommentUtils;
import com.sparos.uniquone.msapostservice.comment.dto.request.CommentCreateRequestDto;
import com.sparos.uniquone.msapostservice.comment.dto.response.CommentResponseDto;
import com.sparos.uniquone.msapostservice.comment.dto.response.CommentUpdateResponseDto;
import com.sparos.uniquone.msapostservice.comment.dto.response.CommentUserInfoResponseDto;
import com.sparos.uniquone.msapostservice.comment.repository.CommentRepositorySupport;
import com.sparos.uniquone.msapostservice.comment.repository.ICommentRepository;
import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.noti.domain.NotiType;
import com.sparos.uniquone.msapostservice.noti.service.IEmitterService;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final IPostRepository postRepository;
    private final ICommentRepository commentRepository;
    private final CommentRepositorySupport commentRepositorySupport;
    private final ICornRepository cornRepository;
    private final IEmitterService iEmitterService;

    @Override
    @Transactional
    public ResponseEntity<?> createComment(CommentCreateRequestDto requestDto, HttpServletRequest request) {
        //post 가 존재하는지 확인.
//        Optional<Post> optionalPost = postRepository.findById(requestDto.getPostId());
        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(() ->
                new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK));

        Comment parent = null;

        //자식 댓글인 경우.
        if (requestDto.getParentId() != null) {
            parent = commentRepository.findById(requestDto.getParentId()).orElseThrow(() ->
                    new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK));

            if (parent.getPost().getId() != requestDto.getPostId()) {
                throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK);
            }
        }
        //user-service 와 통신을 하느냐 아니면 토큰 정보를 받아와서 사용하느냐.
        String token = JwtProvider.getTokenFromRequestHeader(request);
        Long userPkId = JwtProvider.getUserPkId(token);
        String userEmail = JwtProvider.getUserEmail(token);
        String userNickName = JwtProvider.getUserNickName(token);

        Corn corn = cornRepository.findByUserId(userPkId).orElseThrow(() -> {
            throw new UniquOneServiceException(ExceptionCode.NOTFOUND_CORN, HttpStatus.OK);
        });

        Comment comment = Comment.builder()
                .userId(userPkId)
                .userEmail(userEmail)
                .userNickName(userNickName)
                .cornId(corn.getId())
                .post(post)
                .content(requestDto.getContent())
                .build();

        if (null != parent) {
            comment.updateParent(parent);
            comment.setDepth(parent.getDepth() + 1);
        }

        comment = commentRepository.save(comment);

        CommentResponseDto commentResponseDto = null;

        if (parent != null) {
            commentResponseDto = CommentResponseDto.builder()
                    .commentId(comment.getId())
                    .cornId(comment.getCornId())
                    .userId(userPkId)
                    .writerNick(comment.getUserNickName())
                    .content(comment.getContent())
                    .depth(comment.getDepth())
                    .regDate(CommentUtils.converter(comment.getRegDate()))
                    .modDate(comment.getModDate())
                    .parentId(comment.getParent().getId())
                    .build();
        } else {
            commentResponseDto = CommentResponseDto.builder()
                    .commentId(comment.getId())
                    .cornId(comment.getCornId())
                    .userId(userPkId)
                    .writerNick(comment.getUserNickName())
                    .content(comment.getContent())
                    .depth(0)
                    .regDate(CommentUtils.converter(comment.getRegDate()))
                    .modDate(comment.getModDate())
                    .build();
        }

        iEmitterService.send(post.getCorn().getUserId(), comment, NotiType.COMMENT);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK));

        List<Comment> commentList = commentRepositorySupport.findAllByPost(post);

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        Map<Long, CommentResponseDto> map = new ConcurrentHashMap<>();

        commentList.stream().forEach(c -> {
            CommentResponseDto cdto = new CommentResponseDto(c);
            //성능상 문제가 있을거같은데..  쿼리를 한번에 날려서 받아올것! 현재는 하다가 변경하려니 구조 자체가 망가짐.
            cornRepository.findByUserId(c.getUserId()).ifPresent(corn -> {
                        cdto.setCornImgUrl(corn.getImgUrl());
                    }
            );
            if (c.getParent() != null) {
                cdto.setParentId(c.getParent().getId());
                cdto.setParentNickname(c.getParent().getUserNickName());
            }
            map.put(cdto.getCommentId(), cdto);
            if (c.getParent() != null) {
                map.get(c.getParent().getId()).getChildren().add(cdto);
            } else {
                commentResponseDtoList.add(cdto);
            }
        });

        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDtoList);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllCommentsByPost2(Long postId) {
        return null;
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK));
//
////        List<Comment> commentList = commentRepositorySupport.findAllByPost(post);
//        List<CommentListResponseDto> commentList = commentRepositorySupport.findAllByPost2(post);
//        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
//        Map<Long, CommentResponseDto> map = new ConcurrentHashMap<>();
//
//        commentList.stream().forEach(c -> {
//            CommentResponseDto cdto = new CommentResponseDto(c);
//
//            //성능상 문제가 있을거같은데..
//
//
//            if (c.getParent() != null) {
//                cdto.setParentId(c.getParent().getId());
//                cdto.setParentNickname(c.getParent().getUserNickName());
//            }
//            map.put(cdto.getCommentId(), cdto);
//            if (c.getParent() != null) {
//                map.get(c.getParent().getId()).getChildren().add(cdto);
//            } else {
//                commentResponseDtoList.add(cdto);
//            }
//        });
//
//        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDtoList);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateCommentById(Long commentId, String content, HttpServletRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK));

        //현재 content의 pkID requestPkId랑 비교.

        if (comment.getUserId() != JwtProvider.getUserPkId(request)) {
            throw new UniquOneServiceException(ExceptionCode.INVALID_USERID, HttpStatus.OK);
        }

        comment.setContent(content);
        commentRepository.save(comment);

        CommentUpdateResponseDto commentUpdateResponseDto = new CommentUpdateResponseDto();
        commentUpdateResponseDto.setContent(content);


        return ResponseEntity.status(HttpStatus.OK).body(commentUpdateResponseDto);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteCommentById(Long commentId, HttpServletRequest request) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK));

        //현재 content의 pkID requestPkId랑 비교.
        if (comment.getUserId() != JwtProvider.getUserPkId(request)) {
            throw new UniquOneServiceException(ExceptionCode.INVALID_USERID, HttpStatus.OK);
        }

        commentRepository.deleteById(commentId);

        return ResponseEntity.status(HttpStatus.OK).body("삭제완료.");
    }

    @Override
    public ResponseEntity<?> getUserIdInfo(HttpServletRequest request) {
        //헤더 가 없으면 익셉션 발생하는지 확인해야됨
        String token = JwtProvider.getTokenFromRequestHeader(request);
        if (!JwtProvider.validateToken(token)) {
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.OK);
        }
        CommentUserInfoResponseDto responseDto = new CommentUserInfoResponseDto();
        responseDto.setUserId(JwtProvider.getUserPkId(token));
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
