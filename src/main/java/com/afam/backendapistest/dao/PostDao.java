package com.afam.backendapistest.dao;

import com.afam.backendapistest.model.*;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao {

    PostResponse createPostResponse(PostRequestModel postRequestModel);

    PostResponse showAllPosts();

    PostResponse searchPostByIDResponse(IDModel idRequestPostSearch);

    PostResponse showPostsByUser(UsernamePostRequest userRequestPostsSearch);

    GenericResponse deletePostByID(IDModel idDeletePostRequest);

    GenericResponse likePost(PostIDCommentRequest postId);

    GenericResponse undoLikePost(PostIDCommentRequest postId);

    GenericResponse dislikePost(PostIDCommentRequest postId);

    GenericResponse undoDislikePost(PostIDCommentRequest postId);



}
