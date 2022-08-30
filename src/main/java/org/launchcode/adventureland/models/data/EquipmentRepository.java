package org.launchcode.adventureland.models.data;

import org.launchcode.adventureland.models.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends CrudRepository <Equipment, Integer> {

}
