package org.launchcode.adventureland.models.data;


import org.launchcode.adventureland.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    public User findByEmail(String email);
}
