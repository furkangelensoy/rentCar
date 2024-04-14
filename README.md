# Rent A Car Project
A car rental system. You can add new vehicles, update or delete existing vehicles.
Vehicles have information such as brand, model, plate, kilometer, gear type, fuel type and searches can be made by filtering according to this information. 
Reservations can be made for available vehicles according to dates. Reservations made can be cancelled.

N layer architecture and java swing library were used in this project. This project consists of business, core, dao, entity and view packages. 
* **Business package:** This package acts as an intermediate between the View and the Dao.
* **Dao package:** This package includes database operations.
* **Entity package:** Contains classes that represent tables in the database
* **Core package:**  Db connection is made here. Helper classes are located here.
* **View package:** The layer at which users interact with the application and the final data will be visible to the users at this interface. It acts as an interface between the user and the application.

## Technologies
Project created with:
* Java Version 19
* PostgreSql Version 16

## How To Use
Clone this repository
```shell
https://github.com/furkangelensoy/rentCar.git
```

<details close>
<summary>Login</summary>
<br>

* You can login with username = "admin", password = "123". Also if you want, you can save new user on user table and you can login with new user information.

  ![](https://github.com/furkangelensoy/rentCar/blob/master/screenShots/15.PNG)

</details>

<details close>
<summary>Brand Tab Menu</summary>
<br>

* Brands appear here. You can add, update or delete a brand with Jpopupmenu.
  ![](https://github.com/furkangelensoy/rentCar/blob/master/screenShots/14.png)
* If you want to add or update a brand, you must enter a name.
  ![](https://github.com/furkangelensoy/rentCar/blob/master/screenShots/13.png)

</details>

<details close>
<summary>Model Tab Menu</summary>
<br>

* Models appear here. You can add, update or delete model with Jpopupmenu.
  ![](https://github.com/furkangelensoy/rentCar/blob/master/screenShots/2.PNG)
* If you want to add or update a model, you must fill the all fields.  
  ![](https://github.com/furkangelensoy/rentCar/blob/master/screenShots/1.png)
* You can search model according to filter.
  ![](https://github.com/furkangelensoy/rentCar/blob/master/screenShots/3.PNG)

</details>

<details close>
<summary>Car Tab Menu</summary>
<br>

* Cars appear here. You can add, update, delete a car with Jpopupmenu.
  ![](https://github.com/furkangelensoy/rentCar/blob/master/screenShots/24.png)
* If you wanto add or update a car, you must fill the all fields.
  ![](https://github.com/furkangelensoy/rentCar/blob/master/screenShots/6.png)

</details>

<details close>
<summary>Booking Tab Menu</summary>
<br>
  
* Available cars appear here. You can search booking according to (reservation date, car type, gear type, fuel type) filter.
  ![](https://github.com/furkangelensoy/rentCar/blob/master/screenShots/5.png)
* You can create a booking on available vehicles.   
  ![](https://github.com/furkangelensoy/rentCar/blob/master/screenShots/0.png)

</details>

<details close>
<summary>Rentals Tab Menu</summary>
<br>

* Bookings appear here. You can delete a booking with Jpopupmenu.
  ![](https://github.com/furkangelensoy/rentCar/blob/master/screenShots/7.png)
* You can search bookings according to plates.
  ![](https://github.com/furkangelensoy/rentCar/blob/master/screenShots/8.png)

</details>



