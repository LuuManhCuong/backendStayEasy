package com.backend.stayEasy.api;

import com.backend.stayEasy.dto.BookingDTO;
import com.backend.stayEasy.dto.PayoutDTO;
import com.backend.stayEasy.sevice.BookingService;
import com.backend.stayEasy.sevice.PaymentBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stayeasy/host-manager")
public class HostAPI {
    // CRUD Property
    @Autowired
    private BookingService bookingService;
    @Autowired
    private PaymentBillService paymentBillService;
    @Scheduled(cron = "0 */5 * * * *") // Chạy mỗi 5 phút
    public void scheduleBookingStatusUpdate() throws IOException {
        List<UUID> listUpdate = bookingService.updateBookingStatusWithSchedule();
        for (UUID id: listUpdate) {
            System.out.println(id);
            PayoutDTO payoutDTO = paymentBillService.createPerformPayout(id);
            paymentBillService.performPayout(payoutDTO);
        }

    }

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
