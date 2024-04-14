package business;

import core.Helper;
import dao.BookDao;
import dao.CarDao;
import entity.Book;
import entity.Car;
import entity.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CarManager {
    private final CarDao carDao;
    private final BookDao bookDao;

    public CarManager() {
        this.carDao = new CarDao();
        this.bookDao = new BookDao();
    }

    public Car getById(int id) {
        return this.carDao.getById(id);
    }

    public ArrayList<Car> getByListModelId(int modelId) {
        return this.carDao.getByListModelId(modelId);
    }

    public ArrayList<Car> findAll() {
        return this.carDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Car> cars) {
        ArrayList<Object[]> carList = new ArrayList<>();

        for (Car object : cars) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = object.getId();
            rowObject[i++] = object.getModel().getBrand().getName();
            rowObject[i++] = object.getModel().getName();
            rowObject[i++] = object.getPlate();
            rowObject[i++] = object.getColor();
            rowObject[i++] = object.getKm();
            rowObject[i++] = object.getModel().getYear();
            rowObject[i++] = object.getModel().getType();
            rowObject[i++] = object.getModel().getFuel();
            rowObject[i++] = object.getModel().getGear();
            carList.add(rowObject);
        }
        return carList;
    }

    public boolean save(Car car) {
        if (this.getById(car.getId()) != null) {
            Helper.showMessage("Operation failed.", "Error!");
            return false;
        }
        return this.carDao.save(car);
    }

    public boolean update(Car car) {
        if (this.getById(car.getId()) == null) {
            Helper.showMessage("Registered car not found with this id: " + car.getId(), "Error!");
            return false;
        }
        return this.carDao.update(car);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMessage("Registered car not found with this id: " + id, "Error!");
            return false;
        }
        return this.carDao.delete(id);
    }

    public ArrayList<Car> searchForBooking(String start_date, String finish_date, Model.Type type, Model.Gear gear, Model.Fuel fuel) {
        String query = "SELECT * FROM public.car as c LEFT JOIN public.model as m";

        ArrayList<String> where = new ArrayList<>();
        ArrayList<String> joinWhere = new ArrayList<>();
        ArrayList<String> bookOrWhere = new ArrayList<>();

        joinWhere.add("c.car_model_id = m.model_id");

        start_date = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
        finish_date = LocalDate.parse(finish_date,DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();

        if (fuel != null) where.add("m.model_fuel = '" + fuel.toString() + "'");
        if (type != null) where.add("m.model_type = '" + type.toString() + "'");
        if (gear != null) where.add("m.model_gear = '" + gear.toString() + "'");

        String whereStr = String.join(" AND ", where);
        String joinStr = String.join(" AND ", joinWhere);

        if (joinStr.length() > 0){
            query += " ON " + joinStr;
        }

        if (whereStr.length() > 0){
            query += " WHERE " + whereStr;
        }

        ArrayList<Car> searchedCarList = this.carDao.selectByQuery(query);

        bookOrWhere.add("('" + start_date + "' BETWEEN book_start_date AND book_finish_date)");
        bookOrWhere.add("('" + finish_date + "' BETWEEN book_start_date AND book_finish_date)");
        bookOrWhere.add("(book_start_date BETWEEN '" + start_date + "' AND '" + finish_date + "')");
        bookOrWhere.add("(book_finish_date BETWEEN '" + start_date + "' AND '" + finish_date + "')");

        String bookOrWhereStr = String.join(" OR ", bookOrWhere);
        String bookQuery = "SELECT * FROM public.book WHERE " + bookOrWhereStr;

        ArrayList<Book> bookList = this.bookDao.selectByQuery(bookQuery);
        ArrayList<Integer> busyCarId = new ArrayList<>();
        for (Book book : bookList){
            busyCarId.add(book.getCar_id());
        }
        searchedCarList.removeIf(car -> busyCarId.contains(car.getId()));

        return searchedCarList;
    }
}
