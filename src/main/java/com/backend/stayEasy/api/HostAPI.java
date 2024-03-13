package com.backend.stayEasy.api;

import com.backend.stayEasy.dto.BookingDTO;
import com.backend.stayEasy.sevice.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stayeasy/host-manager")
public class HostAPI {
    // CRUD Property
    @Autowired
    private BookingService bookingService;
    // get all booking
    @GetMapping(value = "/{id}&{filter}")
    public ResponseEntity<List<BookingDTO>> getAllBookingOfHost(@PathVariable("id") UUID hostId, @PathVariable("filter") String Filter ){
        return ResponseEntity.ok().body(bookingService.returnAllBookingOfHost(hostId, Filter));
    }
    // update status
   
    public ResponseEntity<String> confirmBooking(@PathVariable UUID id, @PathVariable String status){
        bookingService.updateConfirmBooking(id, status);
        // khi trạng thái thay đổi thì gửi thông báo or mail
        return ResponseEntity.ok("Confirmed " + status );
    }
}
