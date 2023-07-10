
package com.example.thetelephoneappbe.repository;

import com.example.thetelephoneappbe.model.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}

