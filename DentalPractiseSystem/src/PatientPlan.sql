/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Sam
 * Created: 10-Nov-2017
 */

CREATE TABLE IF NOT EXISTS patientPlan(
    patientID INTEGER,
    planName VARCHAR(50),
    planStartDate DATE,
    remainingrepairs INTEGER,
    remainingCheckups INTEGER,
    remainingVisits INTEGER,
    PRIMARY KEY (patientID,patientName),
    FOREIGN KEY (patientID) REFERENCES patient,
    FOREIGN KEY (planName) REFERENCES plan);
