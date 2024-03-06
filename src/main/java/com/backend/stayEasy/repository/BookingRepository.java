package com.backend.stayEasy.repository;

import com.backend.stayEasy.entity.Booking;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findAllByUser_IdOrderByDateBookingDesc(UUID id);
    List<Booking> findAllByPropertyPropertyIdOrderByDateBookingDesc(UUID id);
    @Query("SELECT b FROM Booking b " +
            "WHERE b.property.propertyId = :propertyID AND (b.checkIn<= :checkOutDate) AND (b.checkOut >= :checkInDate)")
    List<Booking> findConflictingBookings(@Param("propertyID") UUID propertyID, @Param("checkInDate") Date checkInDate,
                                          @Param("checkOutDate") Date checkOutDate);
    
    
}
