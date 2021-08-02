package com.afam.backendapistest.dao;


import com.afam.backendapistest.model.*;
import com.afam.backendapistest.util.Token;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.pool.OracleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

@Repository
public class UserDaoImpl implements UserDao{
    private final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    DataSource dataSource;

    @Autowired
    PasswordEncoder passwordEncoder;


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
            //callableStatement.setString(2,request.getPassword());  //without password encoder
            callableStatement.setString(2,passwordEncoder.encode(request.getPassword()));
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

    @Override
    public GenericResponse userLogin(UserLoginRequestModel request) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        GenericResponse response = new GenericResponse();

        try {
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGUSER.proc_login_user(?,?,?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, request.getUsername());
            callableStatement.setString(2, request.getPassword());
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.execute();

            response.setResponseCode(callableStatement.getString(3));
            response.setResponseMessage(callableStatement.getString(4));



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
    public GenericResponse verifiedUsernameCheck(String username) {
        Connection connection = null;
        CallableStatement callableStatement = null;

        GenericResponse response = new GenericResponse();


        try {
            connection = dataSource.getConnection();
            //connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGUSER.proc_verif_flag_login_status(?,?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, username);
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


//        // token confirmation test
//        UserDaoImpl dao = new UserDaoImpl();
//        String token = dao.findByToken("trukkd");
//        System.out.println("Response ::: " + token);



//        // user enable flag test
//        UserDaoImpl dao = new UserDaoImpl();
//        GenericResponse enable = dao.updateEnableFlag("wbvqqfwgnf");
//        System.out.println("Enable flag test ::::: " + enable);

//        //login user test
//        UserDaoImpl dao = new UserDaoImpl();
//        UserLoginRequestModel request = new UserLoginRequestModel("gogogo","test");
//        GenericResponse login = dao.userLogin(request);
//        System.out.println("Response:::: " + login);

//        // user enable flag test by username
//        UserDaoImpl dao = new UserDaoImpl();
//        UsernameRequestModel user = new UsernameRequestModel("gogogo00");
//        GenericResponse enable = dao.checkEnableFlagByUsername(user);
//        System.out.println("Enable flag test ::::: " + enable);


        //  verified user enable flag test by username
        UserDaoImpl dao = new UserDaoImpl();
        GenericResponse enable = dao.verifiedUsernameCheck("ggg");
        System.out.println("Enable flag test ::::: " + enable);


    }
*/

}
