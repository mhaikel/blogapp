package com.afam.backendapistest.controller;


import com.afam.backendapistest.model.*;
import com.afam.backendapistest.dao.PostDao;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/post")
public class PostController {


    @Autowired
    PostDao postDao;



    @Value("${jwt.secret}")
    private String secret;

    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @PostMapping("/createpost")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostMessage model, HttpServletRequest servletRequest){
        PostRequestModel requestModel = new PostRequestModel();

        String token = servletRequest.getHeader("Authorization");
        String username = null;
        if (token != null){
            username = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace("Bearer", ""))
                    .getBody()
                    .getSubject();
        }


        requestModel.setUsername(username);
        requestModel.setPostMessage(model.getPostMessage());

        PostResponse response = postDao.createPostResponse(requestModel);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/show_all_posts")
    public ResponseEntity<PostResponse> showAllPosts(){
        PostResponse response = postDao.showAllPosts();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/find_post_by_id")
    public ResponseEntity<PostResponse> findPostByID(@RequestBody IDModel id){
        PostResponse response = postDao.searchPostByIDResponse(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/find_post_by_user")
    public ResponseEntity<PostResponse> findPostByUser(HttpServletRequest servletRequest){
        UsernamePostRequest requestModel = new UsernamePostRequest();

        String token = servletRequest.getHeader("Authorization");
        String username = null;
        if (token != null){
            username = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace("Bearer", ""))
                    .getBody()
                    .getSubject();
        }

        requestModel.setUsername(username);

        PostResponse response = postDao.showPostsByUser(requestModel);
        return ResponseEntity.ok(response);
    }

    @PostMapping("delete_post")
    public ResponseEntity<GenericResponse> deletePost(@RequestBody IDModel id){
        GenericResponse response = postDao.deletePostByID(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/like_post")
    public ResponseEntity<GenericResponse> likePost(@RequestBody PostIDCommentRequest postId){
        GenericResponse response = postDao.likePost(postId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dislike_post")
    public ResponseEntity<GenericResponse> dislikePost(@RequestBody PostIDCommentRequest postId){
        GenericResponse response = postDao.dislikePost(postId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/undo_like_post")
    public ResponseEntity<GenericResponse> undoLikePost(@RequestBody PostIDCommentRequest postId){
        GenericResponse response = postDao.undoLikePost(postId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/undo_dislike_post")
    public ResponseEntity<GenericResponse> undoDislikePost(@RequestBody PostIDCommentRequest postId){
        GenericResponse response = postDao.undoDislikePost(postId);
        return ResponseEntity.ok(response);
    }


}
