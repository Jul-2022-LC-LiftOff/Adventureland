package org.launchcode.adventureland.models.data;

import org.launchcode.adventureland.models.Reservation;
import org.launchcode.adventureland.models.Reserved;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservedRepository extends CrudRepository<Reserved, Integer> {
    Optional<Reservation> findAllById(Integer reservedId);

    @Query(value = "select * from Reserved where user_id = :user_id and res_status = 'Pending-Checkout'", nativeQuery = true)
    Optional<Reserved> findByUserId(@Param("user_id") Long userId);

}