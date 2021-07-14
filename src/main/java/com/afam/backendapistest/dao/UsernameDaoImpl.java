package com.afam.backendapistest.dao;

import com.afam.backendapistest.model.UsernameDetailsModel;
import com.afam.backendapistest.model.UsernameRequestModel;
import com.afam.backendapistest.model.UsernameResponse;
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

@Repository
public class UsernameDaoImpl implements UsernameDao {

    @Autowired
    DataSource dataSource;

    private final Logger logger = LoggerFactory.getLogger(UsernameDaoImpl.class);
    @Override
    public UsernameDetailsModel usernameResponse(UsernameRequestModel usernameRequestModel) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        UsernameDetailsModel usernameDetailsModel = null;

        try{
            connection = dataSource.getConnection();
//            connection = cardOracleDataSource().getConnection();
            String query = "{call BLOGUSER.proc_enter_username(?,?,?,?)}";
            callableStatement = connection.prepareCall(query);

            callableStatement.setString(1,usernameRequestModel.getUsername());
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3,Types.VARCHAR);
            callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
            callableStatement.execute();

            resultSet = (ResultSet) callableStatement.getObject(4);

            logger.info("code and message ::: " + (callableStatement.getString(2)) + ", " + callableStatement.getString(3));

            while (resultSet.next()) {
                usernameDetailsModel = new UsernameDetailsModel();
                usernameDetailsModel.setUsername(resultSet.getString("USERNAME"));
                usernameDetailsModel.setPassword(resultSet.getString("PASSWORD"));



                logger.info("Details::: " + usernameDetailsModel);
            }




        }catch (Exception exception){
            logger.error(exception.getMessage());
            exception.printStackTrace();
        }finally {
            if(connection != null){
                try {
                    connection.close();
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }

        return usernameDetailsModel;
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

 /*   public static void main(String[] args) {
        UsernameDaoImpl dao = new UsernameDaoImpl();
        UsernameRequestModel usernameRequestModel = new UsernameRequestModel();
        usernameRequestModel.setUsername("LEX");
        System.out.println("Response ::: " + dao.usernameResponse(usernameRequestModel));
    }
*/
}
