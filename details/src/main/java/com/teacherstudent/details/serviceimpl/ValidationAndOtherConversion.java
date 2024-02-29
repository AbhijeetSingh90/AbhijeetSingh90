package com.teacherstudent.details.serviceimpl;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Component
public class ValidationAndOtherConversion {

    public int calculateAge(Date birthDate) {
        LocalDate localBirthDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(localBirthDate, currentDate);
        return period.getYears();
    }

}
