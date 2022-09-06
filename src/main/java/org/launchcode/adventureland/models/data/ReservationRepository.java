package org.launchcode.adventureland.models.data;

import org.launchcode.adventureland.models.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {


}




