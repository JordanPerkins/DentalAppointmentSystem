/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Sam
 * Created: 10-Nov-2017
 */

CREATE TABLE IF NOT EXISTS address(
    houseNumber INTEGER,
    postCode VARCHAR(7),
    streetname VARCHAR(30),
    district VARCHAR(30),
    city VARCHAR(30)
    PRIMARY KEY (houseNumber, postCode));

