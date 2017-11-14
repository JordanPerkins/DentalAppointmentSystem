/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Sam
 * Created: 10-Nov-2017
 */

CREATE TABLE IF NOT EXISTS treatment(
    treatmentName VARCHAR(30) NOT NULL PRIMARY KEY,
    cost DOUBLE(4,2));