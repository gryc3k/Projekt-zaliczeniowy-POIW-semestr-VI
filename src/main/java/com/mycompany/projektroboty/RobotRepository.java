package com.mycompany.projektroboty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RobotRepository extends JpaRepository<Robot, Long> {
    // Spring Boot automatycznie dostarczy tu metody takie jak:
    // save(), findAll(), deleteById() itp.
}