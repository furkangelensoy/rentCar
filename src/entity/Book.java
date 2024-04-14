package entity;

import java.time.LocalDate;

public class Book {
    private int id;
    private int car_id;
    private  Car car;
    private String name;
    private String idNo;
    private String mpNo;
    private String mail;
    private LocalDate start_date;
    private LocalDate finish_date;
    private String book_case;
    private String note;
    private int price;

    public Book(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMpNo() {
        return mpNo;
    }

    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(LocalDate finish_date) {
        this.finish_date = finish_date;
    }

    public String getBook_case() {
        return book_case;
    }

    public void setBook_case(String book_case) {
        this.book_case = book_case;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", car_id=" + car_id +
                ", car=" + car +
                ", name='" + name + '\'' +
                ", idNo='" + idNo + '\'' +
                ", mpNo='" + mpNo + '\'' +
                ", mail='" + mail + '\'' +
                ", start_date=" + start_date +
                ", finish_date=" + finish_date +
                ", book_case='" + book_case + '\'' +
                ", note='" + note + '\'' +
                ", price=" + price +
                '}';
    }
}
