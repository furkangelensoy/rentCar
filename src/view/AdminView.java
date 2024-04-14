package view;

import business.BookManager;
import business.BrandManager;
import business.CarManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane pnl_booking;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scrl_brand;
    private JTable tbl_brand;
    private JPanel pnl_model;
    private JScrollPane scrl_model;
    private JTable tbl_model;
    private JTable tbl_car;
    private JComboBox<ComboItem> cmb_filter_model_brand;
    private JComboBox<Model.Type> cmb_filter_model_type;
    private JComboBox<Model.Fuel> cmb_filter_model_fuel;
    private JComboBox<Model.Gear> cmb_filter_model_gear;
    private JButton btn_model_search;
    private JPanel pnl_filter_model;
    private JLabel lbl_filter_model_type;
    private JLabel lbl_filter_model_fuel;
    private JLabel lbl_filter_model_gear;
    private JLabel lbl_filter_model_brand;
    private JButton btn_model_reset;
    private JScrollPane scrl_car;
    private JPanel pnl_cars;
    private JScrollPane scrl_booking;
    private JPanel pnl_booking_search;
    private JComboBox<Model.Gear> cmb_booking_gear;
    private JComboBox<Model.Fuel> cmb_booking_fuel;
    private JComboBox<Model.Type> cmb_booking_type;
    private JFormattedTextField fld_booking_start_date;
    private JFormattedTextField fld_booking_finish_date;
    private JButton btn_booking_search;
    private JTable tbl_booking;
    private JLabel lbl_booking_type;
    private JLabel lbl_booking_fuel;
    private JLabel lbl_booking_gear;
    private JLabel lbl_booking_finish_date;
    private JLabel lbl_booking_start_date;
    private JButton btn_booking_clear;
    private JPanel pnl_rentals;
    private JPanel pnl_book;
    private JLabel lbl_rentals_head;
    private JComboBox<String> cmb_rentals_plate;
    private JButton btn_rentals_clear;
    private JButton btn_rentals_search;
    private JTable tbl_rentals;
    private JScrollPane scrl_rentals;
    private JPanel pnl_rentals_search;
    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private DefaultTableModel tmdl_car = new DefaultTableModel();
    private DefaultTableModel tmdl_booking = new DefaultTableModel();
    private DefaultTableModel tmdl_rentals = new DefaultTableModel();
    private BrandManager brandManager;
    private ModelManager modelManager;
    private CarManager carManager;
    private BookManager bookManager;
    private JPopupMenu brand_popup;
    private JPopupMenu model_popup;
    private JPopupMenu car_popup;
    private JPopupMenu booking_popup;
    private JPopupMenu rentals_popup;
    private Object[] col_model;
    private Object[] col_car;
    private Object[] col_rentals_list;

    public AdminView(User user) {
        this.brandManager = new BrandManager();
        this.modelManager = new ModelManager();
        this.carManager = new CarManager();
        this.bookManager = new BookManager();
        this.add(container);
        this.guiInitialize(1200, 500);
        this.user = user;

        if (this.user == null) {
            dispose();
        }

        this.lbl_welcome.setText("Welcome " + this.user.getUserName() + " !");

        loadComponent();

        loadBrandTable();
        loadBrandComponent();

        loadModelTable(null);
        loadModelComponent();
        loadModelFilter();

        loadCarTable();
        loadCarComponent();

        loadBookingTable(null);
        loadBookingComponent();
        loadBookingFilter();

        loadRentalsTable(null);
        loadRentalsComponent();
        loadRentalsFilter();

    }

    private void loadComponent(){
        this.btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginView loginView = new LoginView();
            }
        });
    }

    public void loadModelComponent() {
        tableRowSelect(this.tbl_model);
        this.model_popup = new JPopupMenu();

        this.model_popup.add("Add").addActionListener(e -> {
            ModelView modelView = new ModelView(new Model());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                }
            });
        });

        this.model_popup.add("Update").addActionListener(e -> {
            int selectedModelId = this.getTableSelectedRow(this.tbl_model, 0);
            ModelView modelView = new ModelView(this.modelManager.getById(selectedModelId));
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                    loadCarTable();
                    loadRentalsTable(null);
                }
            });
        });

        this.model_popup.add("Delete").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectedModelId = this.getTableSelectedRow(this.tbl_model, 0);
                if (this.modelManager.delete(selectedModelId)) {
                    Helper.showMessage("The model has been successfully deleted.", "Success!");
                    loadModelTable(null);
                    loadCarTable();
                } else {
                    Helper.showMessage("An error occurred while deleting the model.", "Error!");
                }
            }
        });

        this.tbl_model.setComponentPopupMenu(model_popup);

        this.btn_model_search.addActionListener(e -> {
            ComboItem selectedBrand = (ComboItem) this.cmb_filter_model_brand.getSelectedItem();
            int brandId = 0;
            if (selectedBrand != null) {
                brandId = selectedBrand.getKey();
            }
            ArrayList<Model> modelListBySearch = this.modelManager.searchForTable(
                    brandId,
                    (Model.Fuel) this.cmb_filter_model_fuel.getSelectedItem(),
                    (Model.Gear) this.cmb_filter_model_gear.getSelectedItem(),
                    (Model.Type) this.cmb_filter_model_type.getSelectedItem()
            );
            ArrayList<Object[]> modelRowListBySearch = this.modelManager.getForTable(this.col_model.length, modelListBySearch);
            loadModelTable(modelRowListBySearch);
        });

        this.btn_model_reset.addActionListener(e -> {
            this.cmb_filter_model_brand.setSelectedItem(null);
            this.cmb_filter_model_type.setSelectedItem(null);
            this.cmb_filter_model_gear.setSelectedItem(null);
            this.cmb_filter_model_fuel.setSelectedItem(null);
            loadModelTable(null);
        });
    }

    public void loadBrandComponent() {
        tableRowSelect(this.tbl_brand);

        this.brand_popup = new JPopupMenu();
        this.brand_popup.add("Add").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                }
            });
        });
        this.brand_popup.add("Update").addActionListener(e -> {
            int selectedId = this.getTableSelectedRow(this.tbl_brand, 0);
            BrandView brandView = new BrandView(this.brandManager.getById(selectedId));
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                    loadCarTable();
                    loadRentalsTable(null);
                }
            });
        });
        this.brand_popup.add("Delete").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectedId = this.getTableSelectedRow(this.tbl_brand, 0);
                if (this.brandManager.delete(selectedId)) {
                    Helper.showMessage("The brand has been successfully deleted.", "Success!");
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                    loadCarTable();
                } else {
                    Helper.showMessage("An error occurred while deleting the brand.", "Error!");
                }
            }

        });

        this.tbl_brand.setComponentPopupMenu(this.brand_popup);
    }

    public void loadBrandTable() {
        Object[] col_brand = {"Brand Id", "Brand Name"};
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);

        createTable(this.tmdl_brand, this.tbl_brand, col_brand, brandList);
    }

    public void loadModelTable(ArrayList<Object[]> modelList) {
        this.col_model = new Object[]{"Model ID", "Brand", "Model Name", "Type", "Year", "Fuel", "Gear"};
        if (modelList == null) {
            modelList = this.modelManager.getForTable(this.col_model.length, this.modelManager.findAll());
        }
        createTable(this.tmdl_model, this.tbl_model, this.col_model, modelList);
    }

    public void loadModelFilter() {
        this.cmb_filter_model_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_filter_model_type.setSelectedItem(null);
        this.cmb_filter_model_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_filter_model_gear.setSelectedItem(null);
        this.cmb_filter_model_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_filter_model_fuel.setSelectedItem(null);
        loadModelFilterBrand();
    }

    public void loadModelFilterBrand() {
        this.cmb_filter_model_brand.removeAllItems();
        for (Brand object : this.brandManager.findAll()) {
            this.cmb_filter_model_brand.addItem(new ComboItem(object.getId(), object.getName()));
        }
        this.cmb_filter_model_brand.setSelectedItem(null);
    }

    public void loadCarTable() {
        this.col_car = new Object[]{"ID", "Brand", "Model", "Plate", "Color", "Kilometer", "Year", "Type", "Fuel Type", "Gear"};
        ArrayList<Object[]> carList = this.carManager.getForTable(col_car.length, this.carManager.findAll());
        createTable(this.tmdl_car, this.tbl_car, this.col_car, carList);
    }

    public void loadCarComponent() {
        tableRowSelect(this.tbl_car);
        this.car_popup = new JPopupMenu();
        this.car_popup.add("Add").addActionListener(e -> {
            CarView carView = new CarView(new Car());
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });
        });

        this.car_popup.add("Update").addActionListener(e -> {
            int selectedCar = this.getTableSelectedRow(this.tbl_car, 0);
            CarView carView = new CarView(this.carManager.getById(selectedCar));
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                    loadRentalsTable(null);
                }
            });
        });

        this.car_popup.add("Delete").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectCarId = this.getTableSelectedRow(this.tbl_car, 0);
                if (this.carManager.delete(selectCarId)) {
                    Helper.showMessage("The car has been successfully deleted.", "Success!");
                    loadCarTable();
                } else {
                    Helper.showMessage("An error occurred while deleting the car.", "Error!");
                }
            }
        });

        this.tbl_car.setComponentPopupMenu(this.car_popup);
    }

    public void loadBookingComponent() {
        tableRowSelect(this.tbl_booking);
        this.booking_popup = new JPopupMenu();
        this.booking_popup.add("Create booking").addActionListener(e -> {
            int selectedCarId = this.getTableSelectedRow(this.tbl_booking, 0);
            BookingView bookingView = new BookingView(
                    this.carManager.getById(selectedCarId),
                    this.fld_booking_start_date.getText(),
                    this.fld_booking_finish_date.getText()
            );

            bookingView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBookingTable(null);
                    loadBookingFilter();
                }
            });
        });

        btn_booking_search.addActionListener(e -> {
            ArrayList<Car> carList = this.carManager.searchForBooking(
                    fld_booking_start_date.getText(),
                    fld_booking_finish_date.getText(),
                    (Model.Type) cmb_booking_type.getSelectedItem(),
                    (Model.Gear) cmb_booking_gear.getSelectedItem(),
                    (Model.Fuel) cmb_booking_fuel.getSelectedItem()

            );

            ArrayList<Object[]> carBookingRow = this.carManager.getForTable(this.col_car.length, carList);
            loadBookingTable(carBookingRow);

        });
        btn_booking_clear.addActionListener(e -> {
            loadBookingFilter();
        });

        this.tbl_booking.setComponentPopupMenu(this.booking_popup);
    }

    public void loadBookingTable(ArrayList<Object[]> carList) {
        Object[] col_booking_list = {"ID", "Brand", "Model", "Plate", "Color", "Kilometer", "Year", "Type", "Fuel Type", "Gear"};
        createTable(this.tmdl_booking, this.tbl_booking, col_booking_list, carList);
    }

    public void loadBookingFilter() {
        this.cmb_booking_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_booking_type.setSelectedItem(null);
        this.cmb_booking_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_booking_gear.setSelectedItem(null);
        this.cmb_booking_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_booking_fuel.setSelectedItem(null);
    }


    public void loadRentalsComponent() {
        tableRowSelect(this.tbl_rentals);
        this.rentals_popup = new JPopupMenu();
        this.rentals_popup.add("Delete").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectedRental = this.getTableSelectedRow(this.tbl_rentals, 0);
                if (this.bookManager.delete(selectedRental)) {
                    Helper.showMessage("The booking has been successfully deleted.", "Success!");
                    loadRentalsTable(null);
                    loadBookingTable(null);
                } else {
                    Helper.showMessage("An error occurred while deleting the booking.", "Error!");
                }
            }

        });


        this.btn_rentals_search.addActionListener(e -> {
            if (this.cmb_rentals_plate.getSelectedItem() == null){
                ArrayList<Book> allRentals = this.bookManager.findAll();
                ArrayList<Object[]> result = this.bookManager.getFortable(this.col_rentals_list.length,allRentals);
                loadRentalsTable(result);
            }else{
                String selectedPlate = this.cmb_rentals_plate.getSelectedItem().toString();

                int carId = 0;
                for (Book book : this.bookManager.findAll()){
                    if (book.getCar().getPlate().equals(selectedPlate)){
                        carId = book.getCar_id();
                    }
                }
                ArrayList<Book> searchedRantel = this.bookManager.searchForTable(carId);
                ArrayList<Object[]> result = this.bookManager.getFortable(this.col_rentals_list.length,searchedRantel);
                loadRentalsTable(result);
            }


        });

        this.btn_rentals_clear.addActionListener(e -> {
            loadRentalsFilter();
        });

        this.tbl_rentals.setComponentPopupMenu(this.rentals_popup);


    }

    public void loadRentalsTable(ArrayList<Object[]> rentalList) {
        this.col_rentals_list = new Object[]{"ID", "Plate", "Brand", "Model", "Customer", "Mobile Phone", "Mail", "Customer ID", "Start Date", "Finish Date", "Price"};
        if (rentalList == null){
            rentalList = this.bookManager.getFortable(col_rentals_list.length,this.bookManager.findAll());
        }
        createTable(this.tmdl_rentals, this.tbl_rentals, col_rentals_list, rentalList);
    }

    public void loadRentalsFilter() {
        this.cmb_rentals_plate.setModel(new DefaultComboBoxModel<>(this.bookManager.getRentalplates()));
        this.cmb_rentals_plate.setSelectedItem(null);
    }

    private void createUIComponents() throws ParseException {
        this.fld_booking_start_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_booking_start_date.setText("09/04/2024");
        this.fld_booking_finish_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_booking_finish_date.setText("16/04/2024");
    }

}
