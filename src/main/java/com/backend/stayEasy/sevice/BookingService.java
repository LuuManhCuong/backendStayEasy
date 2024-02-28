package com.backend.stayEasy.sevice;

import com.backend.stayEasy.convertor.BookingConverter;
import com.backend.stayEasy.dto.BookingDTO;
import com.backend.stayEasy.dto.BookingParam;
import com.backend.stayEasy.entity.Booking;
import com.backend.stayEasy.repository.BookingRepository;
import com.backend.stayEasy.repository.PropertyUilitisRepository;
import com.backend.stayEasy.repository.UserRepository;
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
    private UserRepository userRepository;
    @Autowired
    private PropertyUilitisRepository propertyRepository;

    @Autowired PropertyService propertyService;

    public BookingDTO getBookingById(UUID id) {
        Booking booking;
        booking = bookingRepository.findById(id).get();
        return BookingConverter.convertToDto(booking);
    }
    public Booking findById(UUID id) {
        Booking booking;
        booking = bookingRepository.findById(id).get();
        return  booking;
    }
    public List<BookingDTO> findAll() {
        return bookingRepository.findAll()
                .stream()
                .map(BookingConverter::convertToDto)
                .collect(Collectors.toList());
    }
    public List<BookingDTO> returnMyBookings(UUID id) {
        return bookingRepository.findAllByUser_IdOrderByDateBookingDesc(id)
                .stream()
                .map(BookingConverter::convertToDto)
                .collect(Collectors.toList());
    }
    public List<BookingDTO> returnListingBookings(UUID id) {
        return bookingRepository.findAllByPropertyPropertyIdOrderByDateBookingDesc(id)
                .stream()
                .map(BookingConverter::convertToDto)
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
    public BookingDTO crateBooking(BookingDTO bookingDto) {
        Booking booking = BookingConverter.convert(bookingDto);
        booking = bookingRepository.save(booking);
        System.out.println("Booking added or updated");
        return BookingConverter.convertToDto(booking);
    }
    public BookingDTO newBooking(BookingParam bookingParam) {
        BookingDTO bookingDto=new BookingDTO();
        bookingDto.setPropertyId(bookingParam.getPropertyId());
        bookingDto.setUserId(bookingParam.getUserId());
        bookingDto.setTotal(bookingParam.getPrice());
        bookingDto.setNumOfGuest(countGuest(bookingParam));
        bookingDto.setDateBooking(Date.valueOf(java.time.LocalDate.now()));
        bookingDto.setCheckIn(bookingParam.getCheckIn());
        bookingDto.setCheckOut(bookingParam.getCheckOut());
        bookingDto.setNumOfNight(bookingParam.getNumberNight());
        bookingDto.setStatus(false);
        // đoạn này set dữ liệu payment
        return bookingDto;
    }
    public int countGuest(BookingParam bookingParam){
        int adult = bookingParam.getNumberOfAdult();
        int children = bookingParam.getNumberOfChildren();
        int fant =  bookingParam.getNumberOfInfants();
        int sumGuest = adult + children + fant ;
        return sumGuest;
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
