package org.launchcode.adventureland.models;

import java.util.ArrayList;

public class CatData {
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
            } else if (equipment.toString().toLowerCase().contains(lowerVal)){
                results.add(equipment);
            }
        }

        return results;
    }
}
