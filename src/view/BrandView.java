package view;

import business.BrandManager;
import core.Helper;
import entity.Brand;

import javax.swing.*;

public class BrandView extends Layout {
    private JPanel container;
    private JLabel lbl_brand;
    private JLabel lbl_brand_name;
    private JTextField fld_brand_name;
    private JButton btn_brand_save;
    private Brand brand;
    private BrandManager brandManager;

    public BrandView(Brand brand) {
        this.brandManager = new BrandManager();

        this.add(container);
        this.guiInitialize(300, 200);
        this.brand = brand;

        if (brand != null){
            this.fld_brand_name.setText(brand.getName());
        }

        btn_brand_save.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.fld_brand_name)) {
                Helper.showMessage("Fill the all fields.", "Error!");
            } else {
                int operationNumber = 0;
                boolean result;
                if (this.brand == null){
                    result = this.brandManager.save(new Brand(fld_brand_name.getText()));
                    operationNumber = 1;
                }else{
                    this.brand.setName(fld_brand_name.getText());
                    result = this.brandManager.update(this.brand);
                    operationNumber = 2;
                }

                if (result){
                    if (operationNumber == 1){
                        Helper.showMessage("The brand has been successfully registered.","Success!");
                        dispose();
                    }else{
                        Helper.showMessage("The brand has been succesfully updated.","Success!");
                        dispose();
                    }
                }else{
                    Helper.showMessage("An error occurred while registering the brand","Error!");
                }
            }
        });
    }
}
