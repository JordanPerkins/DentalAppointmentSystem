/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Sam
 * Created: 10-Nov-2017
 */

CREATE TABLE IF NOT EXISTS plan(
    planName VARCHAR(50) NOT NULL PRIMARY KEY,
    price DOUBLE(4,2),
    repairs INTEGER,
    checkups INTEGER,
    visits INTEGER);