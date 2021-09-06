package com.afam.backendapistest.dao;

import com.afam.backendapistest.model.*;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao {

    CommentResponse createCommentResponse(CommentRequestModel commentRequestModel);

    CommentResponse showAllComments();

    CommentResponse searchByCommentIDResponse(CommentIDRequestModel idRequestCommentSearch);

    CommentResponse showCommentsByPost(PostIDCommentRequest postRequestCommentsSearch);

    GenericResponse deleteCommentByCommentID(CommentIDRequestModel idDeleteCommentRequest);


}
