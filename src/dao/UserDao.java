package dao;

import core.Db;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    private final Connection connection;

    public UserDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<User> findAll(){
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM public.user";
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()){
                userList.add(this.match(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userList;
    }

    public User findByLogin(String userName, String password) {
        User object = null;
        String query = "SELECT * FROM public.user WHERE user_name = ? AND user_password = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                object = new User();
                object = this.match(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return object;

    }

    public User match(ResultSet resultSet) throws SQLException {
        User object = new User();
        object.setId(resultSet.getInt("user_id"));
        object.setUserName(resultSet.getString("user_name"));
        object.setPassword(resultSet.getString("user_password"));
        object.setRole(resultSet.getString("user_role"));
        return object;

    }


}
