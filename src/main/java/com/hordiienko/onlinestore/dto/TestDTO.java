package com.hordiienko.onlinestore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.GregorianCalendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDTO {
    private String name;
    private GregorianCalendar date;
}
