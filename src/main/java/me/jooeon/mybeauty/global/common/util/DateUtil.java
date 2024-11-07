package me.jooeon.mybeauty.global.common.util;

import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@NoArgsConstructor
public class DateUtil {


    public static LocalDate convertStringToDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException();
        }
    }
    public static int calculateAge(LocalDate birthDate) {
        if(birthDate == null) {
            throw new IllegalArgumentException();
        }
        LocalDate currentDate = LocalDate.now();
        if(birthDate.isAfter(currentDate)) {
            throw new IllegalArgumentException();
        }

        return Period.between(birthDate, currentDate).getYears();
    }
}
