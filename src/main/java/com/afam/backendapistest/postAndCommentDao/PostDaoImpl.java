package com.afam.backendapistest.postAndCommentDao;


import com.afam.backendapistest.model.GenericResponse;
import com.afam.backendapistest.postModel.*;
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
public class PostDaoImpl implements PostDao {
    private final Logger logger = LoggerFactory.getLogger(PostDaoImpl.class);

    @Autowired
    DataSource dataSource;

    @Override
    public PostResponse createPostResponse(PostRequestModel postRequestModel) {
        logger.info("Request Model::: " + postRequestModel);
        Connection connection = null;
        CallableStatement callableStatement = null;
        PostResponse response = new PostResponse();

        try {
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGPOST.proc_create_post(?,?,?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1,postRequestModel.getUsername());//**** get username from authentication
            callableStatement.setString(2,postRequestModel.getPostMessage());
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
    public PostResponse showAllPosts() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        PostResponse response = null;
        List<PostModel> details = new ArrayList<>();
        PostModel postModel;

        try {
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGPOST.proc_show_all_posts(?,?,?)}";
            callableStatement = connection.prepareCall(query);

            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(3);

            logger.info("code and message ::: " + (callableStatement.getString(1)) + ", " + callableStatement.getString(2));

            while (resultSet.next()) {
                postModel = new PostModel();
                postModel.setId(resultSet.getLong("ID"));
                postModel.setUsername(resultSet.getString("USERNAME"));
                postModel.setPostMessage(resultSet.getString("POST"));
                postModel.setDateCreated(resultSet.getString("DATE_TIME_CREATED"));

                details.add(postModel);
                logger.info("Details::: " + details);
            }

            response = new PostResponse();
            response.setResponseCode(callableStatement.getString(1));
            response.setResponseMessage(callableStatement.getString(2));
            response.setPostModel(details);

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
    public PostResponse searchPostByIDResponse(IDModel idRequestPostSearch) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        PostResponse response = null;
        List<PostModel> details = new ArrayList<>();
        PostModel postModel;

        try {
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGPOST.proc_show_post_by_id(?,?,?,?)}";
            callableStatement = connection.prepareCall(query);

            callableStatement.setLong(1, idRequestPostSearch.getId());
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(4);

            logger.info("code and message ::: " + (callableStatement.getString(2)) + ", " + callableStatement.getString(3));

            while (resultSet.next()) {
                postModel = new PostModel();
                postModel.setId(resultSet.getLong("ID"));
                postModel.setUsername(resultSet.getString("USERNAME"));
                postModel.setPostMessage(resultSet.getString("POST"));
                postModel.setDateCreated(resultSet.getString("DATE_TIME_CREATED"));

                details.add(postModel);
                logger.info("Details::: " + details);
            }

            response = new PostResponse();
            response.setResponseCode(callableStatement.getString(2));
            response.setResponseMessage(callableStatement.getString(3));
            response.setPostModel(details);

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
    public PostResponse showPostsByUser(UsernamePostRequest userRequestPostsSearch) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        PostResponse response = null;
        List<PostModel> details = new ArrayList<>();
        PostModel postModel;

        try {
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGPOST.proc_show_user_posts(?,?,?,?)}";
            callableStatement = connection.prepareCall(query);

            callableStatement.setString(1, userRequestPostsSearch.getUsername());
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(4);

            logger.info("code and message ::: " + (callableStatement.getString(2)) + ", " + callableStatement.getString(3));

            while (resultSet.next()) {
                postModel = new PostModel();
                postModel.setId(resultSet.getLong("ID"));
                postModel.setUsername(resultSet.getString("USERNAME"));
                postModel.setPostMessage(resultSet.getString("POST"));
                postModel.setDateCreated(resultSet.getString("DATE_TIME_CREATED"));

                details.add(postModel);
                logger.info("Details::: " + details);
            }

            response = new PostResponse();
            response.setResponseCode(callableStatement.getString(2));
            response.setResponseMessage(callableStatement.getString(3));
            response.setPostModel(details);

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
    public GenericResponse deletePostByID(IDModel idDeletePostRequest) {

        //logger.info("Delete Post by ID Request ::: " + idDeletePostRequest);

        Connection connection = null;
        CallableStatement callableStatement = null;
        GenericResponse response = new GenericResponse();

        try{
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "call BLOGPOST.proc_delete_post_by_id(?,?,?)";
            callableStatement = connection.prepareCall(query);
            callableStatement.setLong(1, idDeletePostRequest.getId());
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
//        PostDaoImpl dao = new PostDaoImpl();
//        PostRequestModel create = new PostRequestModel();
//        create.setUsername("afam");
//        create.setPostMessage("testing from card oracle tester");
//        PostResponse response = dao.createPostResponse(create);
//        System.out.println("Response:::: " + response);

//        //search all posts
//        PostDaoImpl dao = new PostDaoImpl();
//        PostResponse response = dao.showAllPosts();
//        System.out.println(response);


//        //search post by id
//        PostDaoImpl dao = new PostDaoImpl();
//        int id = 22;
//        PostResponse response = dao.searchPostByIDResponse(id);
//        System.out.println("Response::::: " + response);
//

//        //search posts by user
//        PostDaoImpl dao = new PostDaoImpl();
//        String user = "aaa";
//        PostResponse response = dao.showPostsByUser(user);
//        System.out.println("Response:::: " + response);

//        //delete post by id
//        PostDaoImpl dao = new PostDaoImpl();
//        long id = 29;
//        GenericResponse response = dao.deletePostByID(id);
//        System.out.println(response);


    }
*/

}
