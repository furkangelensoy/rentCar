package business;

import core.Helper;
import dao.BrandDao;
import entity.Brand;
import entity.Model;

import java.util.ArrayList;

public class BrandManager {
    private final BrandDao brandDao;
    private final ModelManager modelManager;

    public BrandManager() {
        this.brandDao = new BrandDao();
        this.modelManager = new ModelManager();

    }

    public ArrayList<Brand> findAll() {
        return this.brandDao.findAll();
    }

    public boolean save(Brand brand) {
        if (brand.getId() != 0) {
            Helper.showMessage("There is an another brand with this id", "Error!");
        }
        return this.brandDao.save(brand);
    }

    public boolean update(Brand brand) {
        if (this.getById(brand.getId()) == null) {
            Helper.showMessage("Brand not found.", "Error!");
        }
        return this.brandDao.update(brand);
    }

    public Brand getById(int id) {
        return this.brandDao.getById(id);
    }

    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> brandRowList = new ArrayList<>();
        for (Brand brand : findAll()) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = brand.getId();
            rowObject[i++] = brand.getName();
            brandRowList.add(rowObject);

        }
        return brandRowList;
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMessage("Brand not found.", "Error!");
            return false;
        }

        for (Model model : this.modelManager.getByListBrandId(id)) {
            this.modelManager.delete(model.getId());
        }
        return this.brandDao.delete(id);
    }
}
