/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Sam
 * Created: 10-Nov-2017
 */

CREATE TABLE IF NOT EXISTS appointment(
    patientID INTEGER,
    partnerId INTEGER,
    startTime TIME,
    endTime TIME,
    appointmentDate DATE,
    PRIMARY KEY (startTime, appointmentDate, partnerId),
    FOREIGN KEY (patientID) REFERENCES patient,
    FOREIGN KEY (partnerId) REFERENCES partner);
