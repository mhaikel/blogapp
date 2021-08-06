package com.afam.backendapistest.controller;


import com.afam.backendapistest.model.GenericResponse;
import com.afam.backendapistest.postModel.IDModel;
import com.afam.backendapistest.postDao.PostDao;
import com.afam.backendapistest.postModel.PostRequestModel;
import com.afam.backendapistest.postModel.PostResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostDao postDao;

    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @PostMapping("/createpost")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequestModel model){
        PostResponse response = postDao.createPostResponse(model);
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
    public ResponseEntity<PostResponse> findPostByUser(@RequestBody IDModel username){
        PostResponse response = postDao.showPostsByUser(username);
        return ResponseEntity.ok(response);
    }

    @PostMapping("delete_post")
    public ResponseEntity<GenericResponse> deletePost(@RequestBody IDModel id){
        GenericResponse response = postDao.deletePostByID(id);
        return ResponseEntity.ok(response);
    }



}
