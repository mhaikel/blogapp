package com.afam.backendapistest.dao;

import com.afam.backendapistest.model.*;
import oracle.jdbc.OracleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {
    private final Logger logger = LoggerFactory.getLogger(CommentDaoImpl.class);

    @Autowired
    DataSource dataSource;


    @Override
    public CommentResponse createCommentResponse(CommentRequestModel commentRequestModel) {
        //logger.info("Request Model::: " + commentRequestModel);
        Connection connection = null;
        CallableStatement callableStatement = null;
        CommentResponse response = new CommentResponse();

        try {
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGCOMMENT.proc_create_comment(?,?,?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setLong(1,commentRequestModel.getPostId());
            callableStatement.setString(2,commentRequestModel.getCommentMessage());
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4,Types.VARCHAR);
            callableStatement.execute();

            response.setResponseCode(callableStatement.getString(3));
            response.setResponseMessage(callableStatement.getString(4));


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (callableStatement != null) {
                try {
                    callableStatement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return response;
    }

    @Override
    public CommentResponse showAllComments() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        CommentResponse response = null;
        List<CommentResponseModel> details = new ArrayList<>();
        CommentResponseModel commentResponseModel;

        try {
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGCOMMENT.proc_show_all_comments(?,?,?)}";
            callableStatement = connection.prepareCall(query);

            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(3);

            //logger.info("code and message ::: " + (callableStatement.getString(1)) + ", " + callableStatement.getString(2));

            while (resultSet.next()) {
                commentResponseModel = new CommentResponseModel();
                commentResponseModel.setCommentId(resultSet.getLong("COMMENT_ID"));
                commentResponseModel.setPostId(resultSet.getLong("POST_ID"));
                commentResponseModel.setCommentMessage(resultSet.getString("COMMENTS"));
                commentResponseModel.setTimeCreated(resultSet.getString("COMMENT_TIME"));

                details.add(commentResponseModel);
                //logger.info("Details::: " + details);
            }

            response = new CommentResponse();
            response.setResponseCode(callableStatement.getString(1));
            response.setResponseMessage(callableStatement.getString(2));
            response.setResponseModel(details);


        }catch (Exception exception){
            logger.error(exception.getMessage());
            exception.printStackTrace();
        }finally {
            if (connection != null){
                try {
                    connection.close();
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
            if (callableStatement != null){
                try {
                    callableStatement.close();
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }
        logger.info("Get all Posts Response ::: " + response);


        return response;
    }

    @Override
    public CommentResponse searchByCommentIDResponse(CommentIDRequestModel idRequestCommentSearch) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        CommentResponse response = null;
        List<CommentResponseModel> details = new ArrayList<>();
        CommentResponseModel commentResponseModel;



        try{
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGCOMMENT.proc_get_by_comment_id(?,?,?,?)}";
            callableStatement = connection.prepareCall(query);

            callableStatement.setLong(1, idRequestCommentSearch.getCommentId());
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(4);

            //logger.info("code and message ::: " + (callableStatement.getString(2)) + ", " + callableStatement.getString(3));

            while (resultSet.next()) {
                commentResponseModel = new CommentResponseModel();
                commentResponseModel.setCommentId(resultSet.getLong("COMMENT_ID"));
                commentResponseModel.setPostId(resultSet.getLong("POST_ID"));
                commentResponseModel.setCommentMessage(resultSet.getString("COMMENTS"));
                commentResponseModel.setTimeCreated(resultSet.getString("COMMENT_TIME"));

                details.add(commentResponseModel);
                //logger.info("Details::: " + details);
            }

            response = new CommentResponse();
            response.setResponseCode(callableStatement.getString(2));
            response.setResponseMessage(callableStatement.getString(3));
            response.setResponseModel(details);

        }catch (Exception exception){
            logger.error(exception.getMessage());
            exception.printStackTrace();
        }finally {
            if (connection != null){
                try {
                    connection.close();
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
            if (callableStatement != null){
                try {
                    callableStatement.close();
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }


        return response;
    }

    @Override
    public CommentResponse showCommentsByPost(PostIDCommentRequest postRequestCommentsSearch) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        CommentResponse response = null;
        List<CommentResponseModel> details = new ArrayList<>();
        CommentResponseModel commentResponseModel;



        try{
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGCOMMENT.proc_show_all_post_comments(?,?,?,?)}";
            callableStatement = connection.prepareCall(query);

            callableStatement.setLong(1, postRequestCommentsSearch.getPostId());
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(4);

            //logger.info("code and message ::: " + (callableStatement.getString(2)) + ", " + callableStatement.getString(3));

            while (resultSet.next()) {
                commentResponseModel = new CommentResponseModel();
                commentResponseModel.setCommentId(resultSet.getLong("COMMENT_ID"));
                commentResponseModel.setPostId(resultSet.getLong("POST_ID"));
                commentResponseModel.setCommentMessage(resultSet.getString("COMMENTS"));
                commentResponseModel.setTimeCreated(resultSet.getString("COMMENT_TIME"));

                details.add(commentResponseModel);
                //logger.info("Details::: " + details);
            }

            response = new CommentResponse();
            response.setResponseCode(callableStatement.getString(2));
            response.setResponseMessage(callableStatement.getString(3));
            response.setResponseModel(details);

        }catch (Exception exception){
            logger.error(exception.getMessage());
            exception.printStackTrace();
        }finally {
            if (connection != null){
                try {
                    connection.close();
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
            if (callableStatement != null){
                try {
                    callableStatement.close();
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }


        return response;
    }

    @Override
    public GenericResponse deleteCommentByCommentID(CommentIDRequestModel idDeleteCommentRequest) {
        //logger.info("Delete Post by ID Request ::: " + idDeleteCommentRequest);

        Connection connection = null;
        CallableStatement callableStatement = null;
        GenericResponse response = new GenericResponse();

        try{
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "call BLOGCOMMENT.proc_delete_by_comnt_id(?,?,?)";
            callableStatement = connection.prepareCall(query);
            callableStatement.setLong(1, idDeleteCommentRequest.getCommentId());
            callableStatement.registerOutParameter(2,Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.execute();

            response.setResponseCode(callableStatement.getString(2));
            response.setResponseMessage(callableStatement.getString(3));
        }catch (Exception exception){
            logger.error(exception.getMessage());
            exception.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            if (callableStatement != null) {
                try {
                    callableStatement.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        logger.info("Delete Post by ID Response ::: " + response);
        return response;
    }


/*

    public static DataSource cardOracleDataSource() {
        OracleDataSource ds = null;
        try {
            ds = new OracleDataSource();
            ds.setURL("jdbc:oracle:thin:@//localhost:1521/xe");
            ds.setUser("afam");
            ds.setPassword("afam");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }



    public static void main(String[] args) {

//        //create post tset
//        CommentDaoImpl dao = new CommentDaoImpl();
//        CommentRequestModel create = new CommentRequestModel();
//        create.setPostId(22);
//        create.setCommentMessage("2. testing from card oracle tester");
//        CommentResponse response = dao.createCommentResponse(create);
//        System.out.println("Response:::: " + response);

//        //search all posts
//        CommentDaoImpl dao = new CommentDaoImpl();
//        CommentResponse response = dao.showAllComments();
//        System.out.println(response);


//        //search post by id
//        CommentDaoImpl dao = new CommentDaoImpl();
//        CommentIDRequestModel id = new CommentIDRequestModel();
//        id.setCommentId(2);
//        CommentResponse response = dao.searchByCommentIDResponse(id);
//        System.out.println("Response::::: " + response);


//        //search posts by post id
//        CommentDaoImpl dao = new CommentDaoImpl();
//        PostIDCommentRequest id = new PostIDCommentRequest();
//        id.setPostId(32);
//        CommentResponse response = dao.showCommentsByPost(id);
//        System.out.println("Response:::: " + response);

//        //delete post by comment id
//        CommentDaoImpl dao = new CommentDaoImpl();
//        CommentIDRequestModel id = new CommentIDRequestModel();
//        id.setCommentId(17);
//        GenericResponse response = dao.deleteCommentByCommentID(id);
//        System.out.println(response);
    }

*/


}


