package com.afam.backendapistest.controller;


import com.afam.backendapistest.model.*;
import com.afam.backendapistest.dao.CommentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentDao commentDao;

    private final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @PostMapping("/create_comment")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequestModel commentMessage){
        CommentResponse response = commentDao.createCommentResponse(commentMessage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/show_all_comments")
    public ResponseEntity<CommentResponse> allComments(){
        CommentResponse response = commentDao.showAllComments();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search_comment_by_id")
    public ResponseEntity<CommentResponse> showCommentById(@RequestBody CommentIDRequestModel commentId){
        CommentResponse response = commentDao.searchByCommentIDResponse(commentId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/all_post_comments")
    public ResponseEntity<CommentResponse> allPostComments(@RequestBody PostIDCommentRequest postId){
        CommentResponse response = commentDao.showCommentsByPost(postId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete_comment")
    public ResponseEntity<GenericResponse> deleteCommentById(@RequestBody CommentIDRequestModel commentId){
        GenericResponse response = commentDao.deleteCommentByCommentID(commentId);
        return ResponseEntity.ok(response);
    }







}
