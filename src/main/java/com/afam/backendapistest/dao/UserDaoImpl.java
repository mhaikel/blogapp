package com.afam.backendapistest.dao;


import com.afam.backendapistest.model.GenericResponse;
import com.afam.backendapistest.model.User;
import com.afam.backendapistest.util.Token;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.Random;

@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    DataSource dataSource;


    @Override
    public GenericResponse signUp(User request) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        GenericResponse response = new GenericResponse();
        Token token = new Token();




        try {
            connection = dataSource.getConnection();
//            connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGUSER.proc_create_user(?,?,?,?,?,?,?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1,request.getUsername());
            callableStatement.setString(2,request.getPassword());
            callableStatement.setString(3,request.getFirstName());
            callableStatement.setString(4,request.getLastName());
            callableStatement.setString(5,request.getEmail());
            callableStatement.setString(6, token.generateRandomString());
            callableStatement.registerOutParameter(7, Types.VARCHAR);
            callableStatement.registerOutParameter(8, Types.VARCHAR);
            callableStatement.execute();

            response.setResponseCode(callableStatement.getString(7));
            response.setResponseMessage(callableStatement.getString(8));


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

/*    public static void main(String[] args) {
        UserDaoImpl dao = new UserDaoImpl();
        User user = new User();
        user.setUsername("afam2");
        user.setPassword("test");
        user.setFirstName("james");
        user.setLastName("john");
        user.setEmail("12eer");
        System.out.println("User ::: " + user);
        System.out.println("Response ::: " + dao.signUp(user));
    }*/

}
