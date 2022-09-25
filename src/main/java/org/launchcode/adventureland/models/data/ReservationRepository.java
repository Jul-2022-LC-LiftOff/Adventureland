package org.launchcode.adventureland.models.data;


import org.launchcode.adventureland.models.Reservation;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//import static org.launchcode.adventureland.controllers.EquipmentController.equipment;


@Transactional
@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {


    Reservation findById(Long id);

    Object reservation = new ArrayList<>();

    @Query(value = "select * from Reservation where equipment_id = :equipment_id and reservation_status <> 'Archived'", nativeQuery = true)
    ArrayList<Object> findAllByEquipmentId(@Param("equipment_id") Integer equipmentId);

    @Query(value = "select * from Reservation where reserved_id = :reserved_id", nativeQuery = true)
    List<Reservation> findAllByReservedId(@Param("reserved_id") Integer reservedId);

    @Modifying
    @Query(value = "update Reservation set Reservation.reservationStatus = :reservation_status where Reservation.id = :reservation_id", nativeQuery = true)
    void updateReservationStatus(@Param("reservation_status") String reservation_status, @Param("reservation_id") Integer reservation_id);

    Optional<Reservation> findAllById(Integer reservedId);
}
