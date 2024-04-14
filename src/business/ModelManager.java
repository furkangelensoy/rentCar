package business;

import core.Helper;
import dao.ModelDao;
import entity.Car;
import entity.Model;

import java.util.ArrayList;

public class ModelManager {
    private final ModelDao modelDao = new ModelDao();
    private final CarManager carManager = new CarManager();

    public Model getById(int id) {
        return this.modelDao.getById(id);
    }

    public ArrayList<Model> findAll() {
        return this.modelDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Model> modelList) {
        ArrayList<Object[]> modelObj = new ArrayList<>();
        for (Model object : modelList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = object.getId();
            rowObject[i++] = object.getBrand().getName();
            rowObject[i++] = object.getName();
            rowObject[i++] = object.getType();
            rowObject[i++] = object.getYear();
            rowObject[i++] = object.getFuel();
            rowObject[i++] = object.getGear();
            modelObj.add(rowObject);
        }
        return modelObj;
    }

    public boolean save(Model model) {
        if (this.getById(model.getId()) != null) {
            Helper.showMessage("There is an another model with this id", "Error!");
            return false;
        }
        return this.modelDao.save(model);
    }

    public boolean update(Model model) {
        if (this.getById(model.getId()) == null) {
            Helper.showMessage("Model not found", "Error!");
            return false;
        }
        return this.modelDao.update(model);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMessage("Model not found", "Error!");
            return false;
        }
        for (Car car : this.carManager.getByListModelId(id)){
            this.carManager.delete(car.getId());
        }
        return this.modelDao.delete(id);
    }

    public ArrayList<Model> getByListBrandId(int brandId) {
        return this.modelDao.getByListBrandId(brandId);
    }

    public ArrayList<Model> searchForTable(int brandId, Model.Fuel fuel, Model.Gear gear, Model.Type type) {
        String select = "SELECT * FROM public.model";
        ArrayList<String> whereList = new ArrayList<>();

        if (brandId != 0) {
            whereList.add("model_brand_id = " + brandId);
        }
        if (fuel != null) {
            whereList.add("model_fuel = '" + fuel.toString() + "'");
        }
        if (gear != null) {
            whereList.add("model_gear = '" + gear.toString() + "'");
        }
        if (type != null) {
            whereList.add("model_type = '" + type.toString() + "'");
        }
        String whereStr = String.join(" AND ", whereList);
        String query = select;
        if (whereStr.length() > 0) {
            query += " WHERE " + whereStr;
        }
        return this.modelDao.selectByQuery(query);
    }

}
