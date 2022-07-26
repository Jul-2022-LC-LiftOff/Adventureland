package org.launchcode.adventureland.models;

import java.util.ArrayList;
import java.util.function.Predicate;

public class CatData {


    public static ArrayList<Equipment> findByColumnAndValue(String column, String value, Iterable<Equipment> allEquipment) {

        ArrayList<Equipment> results = new ArrayList<>();

        if (value.toLowerCase().equals("all")){
            return (ArrayList<Equipment>) allEquipment;
        }

//        if (column.isEmpty())



        if (column.equals("all")){
            results = findByValue(value, allEquipment);
//            results.removeIf(Predicate.isEqual("Manufacturer")); // searching letters only found in that word resulted in all results, this removes undesired results
            return results;
        }
        for (Equipment equipment : allEquipment) {

            String aValue = getFieldValue(equipment, column);

            if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(equipment);
            }
        }

        return results;
    }

    public static String getFieldValue(Equipment equipment, String fieldName){
        String theValue;
        if (fieldName.equals("name")){
            theValue = equipment.getEquipmentName();
        } else if (fieldName.equals("category")){
            theValue = equipment.getCategory().toString();
        } else {
            theValue = equipment.getManufacturer();
        }

        return theValue;
    }

    public static ArrayList<Equipment> findByValue(String value, Iterable<Equipment> allEquipment){
        String lowerVal = value.toLowerCase();

        ArrayList<Equipment> results = new ArrayList<>();

        for (Equipment equipment : allEquipment) {
            if (equipment.getEquipmentName().toLowerCase().contains(lowerVal)){
                results.add(equipment);
            } else if (equipment.getCategory().toString().toLowerCase().contains(lowerVal)){
                results.add(equipment);
            } else if (equipment.getManufacturer().toLowerCase().contains(lowerVal)){
                results.add(equipment);
            }
        }

        return results;
    }
}
