package org.launchcode.adventureland.models.data;

import org.launchcode.adventureland.models.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;


@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    Object reservation = new ArrayList<>();
    @Query(value = "select * from Reservation where equipment_name = :equipment_name", nativeQuery = true)
    ArrayList<Object> findAllByEquipmentId(@Param("equipment_name") String equipName);

}
