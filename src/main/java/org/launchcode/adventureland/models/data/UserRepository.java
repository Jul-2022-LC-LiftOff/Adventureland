package org.launchcode.adventureland.models.data;

import org.launchcode.adventureland.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    //public List<User> findByEmail(String email);
}
