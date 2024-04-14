package view;


import business.BrandManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entity.Brand;
import entity.Model;

import javax.swing.*;

public class ModelView extends Layout {
    private JPanel container;
    private JLabel lbl_heading;
    private JLabel lbl_brand;
    private JComboBox<ComboItem> cmb_brand;
    private JLabel lbl_model_name;
    private JTextField fld_model_name;
    private JLabel lbl_model_year;
    private JTextField fld_model_year;
    private JLabel lbl_model_type;
    private JComboBox<Model.Type> cmb_model_type;
    private JComboBox<Model.Fuel> cmb_model_fuel;
    private JComboBox<Model.Gear> cmb_model_gear;
    private JLabel lbl_model_fuel;
    private JLabel lbl_model_gear;
    private JButton btn_model_save;
    private Model model;
    private ModelManager modelManager;
    private BrandManager brandManager;

    public ModelView(Model model) {
        this.add(container);
        this.model = model;
        this.brandManager = new BrandManager();
        this.modelManager = new ModelManager();
        this.guiInitialize(300, 500);

        for (Brand brand : this.brandManager.findAll()) {
            this.cmb_brand.addItem(new ComboItem(brand.getId(), brand.getName()));
        }

        this.cmb_model_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_model_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_model_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));

        if (this.model.getId() != 0) {
            this.fld_model_year.setText(this.model.getYear());
            this.fld_model_name.setText(this.model.getName());
            this.cmb_model_fuel.getModel().setSelectedItem(this.model.getFuel());
            this.cmb_model_type.getModel().setSelectedItem(this.model.getType());
            this.cmb_model_gear.getModel().setSelectedItem(this.model.getGear());
            ComboItem defaultBrand = new ComboItem(this.model.getBrand().getId(), this.model.getBrand().getName());
            this.cmb_brand.getModel().setSelectedItem(defaultBrand);
        }

        this.btn_model_save.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_model_name, this.fld_model_year})) {
                Helper.showMessage("Fill the all field.", "Error!");
            } else {
                boolean result;
                int operation;
                ComboItem selectedBrand = (ComboItem) this.cmb_brand.getSelectedItem();

                this.model.setYear(this.fld_model_year.getText());
                this.model.setName(this.fld_model_name.getText());
                this.model.setBrand_id(selectedBrand.getKey());
                this.model.setType((Model.Type) this.cmb_model_type.getSelectedItem());
                this.model.setFuel((Model.Fuel) this.cmb_model_fuel.getSelectedItem());
                this.model.setGear((Model.Gear) this.cmb_model_gear.getSelectedItem());

                if (this.model.getId() != 0) {
                    result = this.modelManager.update(this.model);
                    operation = 1;
                } else {
                    result = this.modelManager.save(this.model);
                    operation = 2;
                }

                if (result) {
                    if (operation == 1){
                        Helper.showMessage("The model has successfully updated.", "Success!");
                        dispose();
                    }else {
                        Helper.showMessage("The model has successfully registered.", "Success!");
                        dispose();
                    }
                } else {
                    Helper.showMessage("Fill the all fields.", "Error!");
                }
            }
        });

    }
}
