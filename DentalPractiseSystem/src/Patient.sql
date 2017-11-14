/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Sam
 * Created: 10-Nov-2017
 */

CREATE TABLE IF NOT EXISTS patient(
    patientID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, 
    title VARCHAR(6),
    forname VARCHAR(15),
    surname VARCHAR(25),
    dob DATE,
    phoneNumber VARCHAR(17),
    FOREIGN KEY (houseNumber, postCode)
        REFERENCES address(nouseNumber, postCode));
