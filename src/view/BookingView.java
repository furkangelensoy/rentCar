package view;

import business.BookManager;
import core.Helper;
import entity.Book;
import entity.Car;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingView extends Layout{
    private JPanel container;
    private JPanel pnl_booking;
    private JLabel lbl_book_header;
    private JTextField fld_book_name;
    private JTextField fld_book_idno;
    private JTextField fld_book_mpno;
    private JTextField fld_book_mail;
    private JTextField fld_book_start_date;
    private JTextField fld_book_finish_date;
    private JTextField fld_book_price;
    private JTextField fld_book_note;
    private JButton btn_book_create;
    private JLabel lbl_car_info;
    private JLabel lbl_book_name;
    private JLabel lbl_book_idno;
    private JLabel lbl_book_mpno;
    private JLabel lbl_book_mail;
    private JLabel lbl_book_start_date;
    private JLabel lbl_book_finish_date;
    private JLabel lbl_book_price;
    private JLabel lbl_book_note;
    private Car car;
    private BookManager bookManager;
    public BookingView(Car selectedCar, String start_date, String finish_date){
        this.car = selectedCar;
        this.bookManager = new BookManager();


        this.add(container);
        this.guiInitialize(350,600);

        this.lbl_car_info.setText("Car : " +
                this.car.getPlate() + " / " +
                this.car.getModel().getBrand().getName() + " / " +
                this.car.getModel().getName() + " / " +
                this.car.getModel().getFuel() + " / " +
                this.car.getModel().getGear());

        this.fld_book_start_date.setText(start_date);
        this.fld_book_finish_date.setText(finish_date);


        btn_book_create.addActionListener(e -> {
            JTextField[] checkFieldList = {
                    this.fld_book_name,
                    this.fld_book_idno,
                    this.fld_book_mail,
                    this.fld_book_mpno,
                    this.fld_book_price,
                    this.fld_book_start_date,
                    this.fld_book_finish_date
            };

            if (Helper.isFieldListEmpty(checkFieldList)){
                Helper.showMessage("Fill the all fields.","Error!");
            }else{
                Book book = new Book();
                book.setBook_case("done");
                book.setCar_id(this.car.getId());
                book.setName(this.fld_book_name.getText());
                book.setStart_date(LocalDate.parse(start_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                book.setFinish_date(LocalDate.parse(finish_date,DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                book.setIdNo(this.fld_book_idno.getText());
                book.setMpNo(this.fld_book_mpno.getText());
                book.setMail(this.fld_book_mail.getText());
                book.setNote(this.fld_book_note.getText());
                book.setPrice(Integer.parseInt(this.fld_book_price.getText()));

                if (this.bookManager.save(book)){
                    Helper.showMessage("Booking created successfully","Success!");
                    dispose();
                }else{
                    Helper.showMessage("An error occurred while creating a booking","Error!");
                }
            }
        });
    }
}
