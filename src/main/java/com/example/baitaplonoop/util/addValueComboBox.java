package com.example.baitaplonoop.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.text.NumberFormat;
import java.util.Locale;

public class addValueComboBox {

    public static void addValue(ComboBox<String> comboBox){
        comboBox.getItems().addAll("None","100%", "90%", "83.33333%", "80%", "75%", "70%", "66.66667%", "60%", "50%", "40%", "33.33333%", "30%", "25%", "20%", "16.66667%", "14.28571%", "12.5%", "11.1111%", "10%", "5%", "-5%", "10%", "-11.1111%", "-12.5%", "-14.28571%", "-16.66667%", "-20%", "-25%", "-30%", "-33.33333%", "-40%", "-50%", "-60%", "-66.66667%", "-70%", "-75%", "-80%", "-83.33333%");
        comboBox.setValue("None");
    }
    public static double convertStringToDouble(ComboBox<String> stringComboBox){
        String selectedValue = stringComboBox.getValue();
        int NoneGrade = 0;
        if(selectedValue == "None") return NoneGrade;
        else {
            double percentageGrade = Double.parseDouble(selectedValue.replace("%", ""))/100.0;
            return percentageGrade;
        }
    }
}
