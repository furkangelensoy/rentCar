package business;

import core.Helper;
import dao.BookDao;
import entity.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class BookManager {
    private final BookDao bookDao;

    public BookManager(){
        this.bookDao = new BookDao();
    }

    public Book getById(int id){
        return this.bookDao.getById(id);
    }

    public ArrayList<Book> findAll(){
        return this.bookDao.findAll();
    }

    public boolean save(Book book){
        return this.bookDao.save(book);
    }

    public boolean delete(int id){
        if (getById(id) == null){
            Helper.showMessage("An error occurred while deleting the booking.","Error!");
            return false;
        }
        return this.bookDao.delete(id);
    }

    public ArrayList<Object[]> getFortable(int size, ArrayList<Book> rentals){
        ArrayList<Object[]> rentalList = new ArrayList<>();
        for (Book object : rentals){
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = object.getId();
            rowObject[i++] = object.getCar().getPlate();
            rowObject[i++] = object.getCar().getModel().getBrand().getName();
            rowObject[i++] = object.getCar().getModel().getName();
            rowObject[i++] = object.getName();
            rowObject[i++] = object.getMpNo();
            rowObject[i++] = object.getMail();
            rowObject[i++] = object.getIdNo();
            rowObject[i++] = object.getStart_date().toString();
            rowObject[i++] = object.getFinish_date().toString();
            rowObject[i++] = object.getPrice();
            rentalList.add(rowObject);
        }
        return rentalList;
    }

    public ArrayList<Book> searchForTable(int carId){
        String query = "SELECT * FROM public.book WHERE book_car_id = " + carId;
        return this.bookDao.selectByQuery(query);
    }

    public String[] getRentalplates(){
        ArrayList<Book> books = findAll();
        String[] plates = new String[books.size()];
        int i = 0;
        for (Book book : books){
            plates[i] = book.getCar().getPlate();
            i++;
        }
        plates = new HashSet<>(Arrays.asList(plates)).toArray(new String[0]);
        return plates;
    }

}
