package com.afam.backendapistest.postDao;

import com.afam.backendapistest.model.GenericResponse;
import com.afam.backendapistest.postModel.IDModel;
import com.afam.backendapistest.postModel.PostRequestModel;
import com.afam.backendapistest.postModel.PostResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao {

    PostResponse createPostResponse(PostRequestModel postRequestModel);

    PostResponse showAllPosts();

    PostResponse searchPostByIDResponse(IDModel idRequestPostSearch);

    PostResponse showPostsByUser(IDModel userRequestPostsSearch);

    GenericResponse deletePostByID(IDModel idDeletePostRequest);

}
