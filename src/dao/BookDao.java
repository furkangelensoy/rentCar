package dao;

import core.Db;
import entity.Book;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookDao {
    private Connection connection;
    private final CarDao carDao;

    public BookDao() {
        this.connection = Db.getInstance();
        this.carDao = new CarDao();
    }

    public ArrayList<Book> findAll() {
        return this.selectByQuery("SELECT * FROM public.book ORDER BY book_id ASC");
    }

    public ArrayList<Book> selectByQuery(String query) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                books.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public Book getById(int id){
        Book book = null;
        String query = "SELECT * FROM public.book WHERE book_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) book = this.match(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return book;
    }

    public boolean delete(int id){
        String query = "DELETE FROM public.book WHERE book_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate() != -1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean save(Book book) {
        String query = "INSERT INTO public.book " +
                "(" +
                "book_car_id," +
                "book_name," +
                "book_idno," +
                "book_mpno," +
                "book_mail," +
                "book_start_date," +
                "book_finish_date," +
                "book_price," +
                "book_case," +
                "book_note" +
                ")" +
                " VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, book.getCar_id());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setString(3, book.getIdNo());
            preparedStatement.setString(4, book.getMpNo());
            preparedStatement.setString(5, book.getMail());
            preparedStatement.setDate(6, Date.valueOf(book.getStart_date()));
            preparedStatement.setDate(7, Date.valueOf(book.getFinish_date()));
            preparedStatement.setInt(8, book.getPrice());
            preparedStatement.setString(9, book.getBook_case());
            preparedStatement.setString(10, book.getNote());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Book match(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("book_id"));
        book.setName(resultSet.getString("book_name"));
        book.setIdNo(resultSet.getString("book_idno"));
        book.setMpNo(resultSet.getString("book_mpno"));
        book.setMail(resultSet.getString("book_mail"));
        book.setStart_date(LocalDate.parse(resultSet.getString("book_start_date")));
        book.setFinish_date(LocalDate.parse(resultSet.getString("book_finish_date")));
        book.setCar_id(resultSet.getInt("book_car_id"));
        book.setCar(this.carDao.getById(resultSet.getInt("book_car_id")));
        book.setBook_case(resultSet.getString("book_case"));
        book.setNote(resultSet.getString("book_note"));
        book.setPrice(resultSet.getInt("book_price"));
        return book;
    }
}
