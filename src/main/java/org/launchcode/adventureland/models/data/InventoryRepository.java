package org.launchcode.adventureland.models.data;

import org.launchcode.adventureland.models.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends CrudRepository <Inventory, Integer> {
}
