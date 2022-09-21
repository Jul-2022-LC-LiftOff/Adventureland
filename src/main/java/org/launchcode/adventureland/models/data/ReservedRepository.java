package org.launchcode.adventureland.models.data;

import org.launchcode.adventureland.models.Reservation;
import org.launchcode.adventureland.models.Reserved;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservedRepository extends CrudRepository<Reserved, Integer> {
    Optional<Reservation> findAllById(Integer reservedId);
}
