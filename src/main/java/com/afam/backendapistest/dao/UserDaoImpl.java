package com.afam.backendapistest.dao;


import com.afam.backendapistest.controller.ViewConfirmationController;
import com.afam.backendapistest.model.GenericResponse;
import com.afam.backendapistest.model.SignUpResponse;
import com.afam.backendapistest.model.User;
import com.afam.backendapistest.model.UsernameDetailsModel;
import com.afam.backendapistest.util.Token;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.pool.OracleDataSource;
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
import java.util.Random;

@Repository
public class UserDaoImpl implements UserDao{
    private final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    DataSource dataSource;


    @Override
    public SignUpResponse signUp(User request) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        SignUpResponse response = new SignUpResponse();
        Token token = new Token();




        try {
            String token_ = token.generateRandomString();
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGUSER.proc_create_user(?,?,?,?,?,?,?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1,request.getUsername());
            callableStatement.setString(2,request.getPassword());
            callableStatement.setString(3,request.getFirstName());
            callableStatement.setString(4,request.getLastName());
            callableStatement.setString(5,request.getEmail());
            callableStatement.setString(6, token_);
            callableStatement.registerOutParameter(7, Types.VARCHAR);
            callableStatement.registerOutParameter(8, Types.VARCHAR);
            callableStatement.execute();

            response.setResponseCode(callableStatement.getString(7));
            response.setResponseMessage(callableStatement.getString(8));
            response.setToken(token_);


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection != null){
                try {
                    connection.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if (callableStatement != null){
                try {
                    callableStatement.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return response;

    }

    @Override
    public String findByToken(String confirmationToken) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        String token = null;


        try{
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGUSER.proc_get_vcode(?,?,?,?)}";
            callableStatement = connection.prepareCall(query);

            callableStatement.setString(1,confirmationToken);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(4);


            while (resultSet.next()){
                token = resultSet.getString("VERIFICATION_CODE");
                logger.info("code ::: " + token);
            }

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

        return token;
    }


    @Override
    public GenericResponse updateEnableFlag(String confirmationToken) {

        Connection connection = null;
        CallableStatement callableStatement = null;

        GenericResponse response = new GenericResponse();


        try {
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGUSER.proc_set_enableflag(?,?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1,confirmationToken);
            callableStatement.registerOutParameter(2,Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.execute();

            response = new GenericResponse();
            response.setResponseCode(callableStatement.getString(2));
            response.setResponseMessage(callableStatement.getString(3));



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
    public GenericResponse checkEnableFlag(String confirmationToken) {
        Connection connection = null;
        CallableStatement callableStatement = null;

        GenericResponse response = new GenericResponse();


        try {
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGUSER.proc_get_enable(?,?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1,confirmationToken);
            callableStatement.registerOutParameter(2,Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.execute();

            response = new GenericResponse();
            response.setResponseCode(callableStatement.getString(2));
            response.setResponseMessage(callableStatement.getString(3));



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


 /*   public static DataSource cardOracleDataSource() {
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
*//*

        // token confirmation test
        UserDaoImpl dao = new UserDaoImpl();
        String token = dao.findByToken("trukkd");
        System.out.println("Response ::: " + token);
*//*


        // user enable flag test
        UserDaoImpl dao = new UserDaoImpl();
        GenericResponse enable = dao.updateEnableFlag("wbvqqfwgnf");
        System.out.println("Enable flag test ::::: " + enable);

    }*/

}
