package com.sparos.uniquone.msapostservice.comment.service;

import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import com.sparos.uniquone.msapostservice.comment.dto.request.CommentCreateRequestDto;
import com.sparos.uniquone.msapostservice.comment.dto.response.CommentResponseDto;
import com.sparos.uniquone.msapostservice.comment.dto.response.CommentUpdateResponseDto;
import com.sparos.uniquone.msapostservice.comment.repository.CommentRepositorySupport;
import com.sparos.uniquone.msapostservice.comment.repository.ICommentRepository;
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

    @Override
    @Transactional
    public ResponseEntity<?> createComment(CommentCreateRequestDto requestDto, HttpServletRequest request) {
        //post 가 존재하는지 확인.
//        Optional<Post> optionalPost = postRepository.findById(requestDto.getPostId());
        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(() ->
                new UniquOneServiceException(ExceptionCode.NO_SUCH));

        Comment parent = null;

        //자식 댓글인 경우.
        if (requestDto.getParentId() != null) {
            parent = commentRepository.findById(requestDto.getParentId()).orElseThrow(() ->
                    new UniquOneServiceException(ExceptionCode.NO_SUCH));

            if (parent.getPost().getId() != requestDto.getPostId()) {
                throw new UniquOneServiceException(ExceptionCode.NO_SUCH);
            }
        }
        //user-service 와 통신을 하느냐 아니면 토큰 정보를 받아와서 사용하느냐.
        String token = JwtProvider.getTokenFromRequestHeader(request);
        Long userPkId = JwtProvider.getUserPkId(token);
        String userEmail = JwtProvider.getUserEmail(token);
        String userNickName = JwtProvider.getUserNickName(token);

        Comment comment = Comment.builder()
                .userId(userPkId)
                .userEmail(userEmail)
                .userNickName(userNickName)
                .post(post)
                .content(requestDto.getContent())
                .build();

        if (null != parent) {
            comment.updateParent(parent);
            comment.setDepth(parent.getDepth() + 1);
        }

        commentRepository.save(comment);

        CommentResponseDto commentResponseDto = null;

        if (parent != null) {
            commentResponseDto = CommentResponseDto.builder()
                    .commentId(comment.getId())
                    .writerNick(comment.getUserNickName())
                    .content(comment.getContent())
                    .depth(comment.getDepth())
                    .regDate(comment.getRegDate())
                    .modDate(comment.getModDate())
                    .parentId(comment.getParent().getId())
                    .build();
        } else {
            commentResponseDto = CommentResponseDto.builder()
                    .commentId(comment.getId())
                    .writerNick(comment.getUserNickName())
                    .content(comment.getContent())
                    .depth(0)
                    .regDate(comment.getRegDate())
                    .modDate(comment.getModDate())
                    .build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH));

        List<Comment> commentList = commentRepositorySupport.findAllByPost(post);

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        Map<Long, CommentResponseDto> map = new ConcurrentHashMap<>();

        commentList.stream().forEach(c -> {
            CommentResponseDto cdto = new CommentResponseDto(c);
            if (c.getParent() != null) {
                cdto.setParentId(c.getParent().getId());
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
    @Transactional
    public ResponseEntity<?> updateCommentById(Long commentId, String content, HttpServletRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new UniquOneServiceException(ExceptionCode.NO_SUCH));

        //현재 content의 pkID requestPkId랑 비교.
        if(comment.getUserId() != JwtProvider.getUserPkId(request)){
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH);
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
                new UniquOneServiceException(ExceptionCode.NO_SUCH));

        //현재 content의 pkID requestPkId랑 비교.
        if(comment.getUserId() != JwtProvider.getUserPkId(request)){
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH);
        }

        commentRepository.deleteById(commentId);

        return ResponseEntity.status(HttpStatus.OK).body("삭제완료.");
    }
}
