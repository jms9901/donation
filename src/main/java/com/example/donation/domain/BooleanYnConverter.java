package com.example.donation.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BooleanYnConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        if (dbData.equalsIgnoreCase("Y")) {
            return true;
        } else if (dbData.equalsIgnoreCase("N")) {
            return false;
        }
        return false;
    }

}
