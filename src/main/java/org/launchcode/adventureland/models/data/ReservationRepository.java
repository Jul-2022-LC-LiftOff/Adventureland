package org.launchcode.adventureland.models.data;

import org.launchcode.adventureland.models.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Transactional
@Repository("reservationRepository")
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    @Query(value = "SELECT sum(equipmentQuantity) FROM Reservation")
    public Long sumQuantities();

    @Query(value = "SELECT sum(equipmentQuantity * unitPrice) FROM Reservation")
    public BigDecimal total();
}