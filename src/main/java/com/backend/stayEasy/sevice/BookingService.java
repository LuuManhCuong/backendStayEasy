package com.backend.stayEasy.sevice;

import com.backend.stayEasy.convertor.BookingConverter;
import com.backend.stayEasy.dto.BookingDTO;
import com.backend.stayEasy.entity.Booking;
import com.backend.stayEasy.repository.BookingRepository;
import com.backend.stayEasy.repository.PropertyUilitisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PropertyUilitisRepository propertyRepository;

    @Autowired
    private BookingConverter bookingConverter;

    @Autowired PropertyService propertyService;

    public BookingDTO getBookingById(UUID id) {
        Booking booking;
        booking = bookingRepository.findById(id).get();
        return bookingConverter.toDTO(booking);
    }
    public Booking findById(UUID id) {
        Booking booking;
        booking = bookingRepository.findById(id).get();
        return  booking;
    }
    public List<BookingDTO> findAll() {
        return bookingRepository.findAll()
                .stream()
                .map(bookingConverter::toDTO)
                .collect(Collectors.toList());
    }
    public List<BookingDTO> returnMyBookings(UUID id) {
        return bookingRepository.findAllByUser_IdOrderByDateBookingDesc(id)
                .stream()
                .map(bookingConverter::toDTO)
                .collect(Collectors.toList());
    }
    public List<BookingDTO> returnListingBookings(UUID id) {
        return bookingRepository.findAllByPropertyPropertyIdOrderByDateBookingDesc(id)
                .stream()
                .map(bookingConverter::toDTO)
                .collect(Collectors.toList());
    }
    public void updateBookingStatus(UUID bookingId, boolean status) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (!bookingOptional.isPresent()) {
            // Handle booking not found (e.g., throw an exception, log an error, etc.)
            throw new RuntimeException("Booking with ID " + bookingId + " not found.");
        }
        Booking booking = bookingOptional.get();
        if (booking.getStatus() != status) {
            booking.setStatus(status);
            bookingRepository.save(booking);
            // Send notification (optional)
            // ...
        }
    }
    // create booking
    public BookingDTO crateBooking(Booking booking) {
        booking = bookingRepository.save(booking);
        System.out.println("Booking added or updated");
        return bookingConverter.toDTO(booking);
    }
    public BookingDTO newBooking(BookingDTO bookingDTO) {
        BookingDTO bookingDto=new BookingDTO();
        bookingDto.setPropertyId(bookingDTO.getPropertyId());
        bookingDto.setUserId(bookingDTO.getUserId());
        bookingDto.setTotal(bookingDTO.getPrice());
        bookingDto.setNumOfGuest(bookingDTO.getNumOfGuest());
        bookingDto.setDateBooking(Date.valueOf(java.time.LocalDate.now()));
        bookingDto.setCheckIn(bookingDTO.getCheckIn());
        bookingDto.setCheckOut(bookingDTO.getCheckOut());
        bookingDto.setNumberNight(bookingDTO.getNumberNight());
        bookingDto.setStatus(false);
        // đoạn này set dữ liệu payment
        return bookingDto;
    }

    public boolean isRoomAvailable( UUID id , Date checkInDate, Date checkOutDate) {
        // Logic to check room availability based on check-in and check-out dates
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(id , checkInDate, checkOutDate);
        return conflictingBookings.isEmpty();
    }

    public void deleteBooking(UUID id) {
        bookingRepository.deleteById(id);
    }

}
