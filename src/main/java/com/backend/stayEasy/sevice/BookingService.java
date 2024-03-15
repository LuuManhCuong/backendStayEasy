package com.backend.stayEasy.sevice;

import com.backend.stayEasy.convertor.BookingConverter;
import com.backend.stayEasy.dto.BookingDTO;
import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.Booking;
import com.backend.stayEasy.enums.Confirmation;
import com.backend.stayEasy.repository.BookingRepository;
import com.backend.stayEasy.repository.PropertyUilitisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

	@Autowired
	PropertyService propertyService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PropertyUilitisRepository propertyRepository;
    @Autowired
    private BookingConverter bookingConverter;


	public BookingDTO getBookingById(UUID id) {
		Booking booking;
		booking = bookingRepository.findById(id).get();
		return bookingConverter.toDTO(booking);
	}

	public Booking findById(UUID id) {
		Booking booking;
		booking = bookingRepository.findById(id).get();
		return booking;
	}

	public List<BookingDTO> findAll() {
		return bookingRepository.findAll().stream().map(bookingConverter::toDTO).collect(Collectors.toList());
	}


    public List<BookingDTO> returnMyBookings(UUID userId) {
        return bookingRepository.findAllByUser_IdOrderByDateBookingDesc(userId)
                .stream()
                .map(bookingConverter::toDTO)
                .collect(Collectors.toList());
    }

	public List<BookingDTO> returnListingBookings(UUID id) {
		return bookingRepository.findAllByPropertyPropertyIdOrderByDateBookingDesc(id).stream()
                .filter(Booking::getStatus)
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
            // send Email
			// ...
		}
	}
    public void updateBookingCancel(UUID bookingId, boolean status, boolean CancelBooking) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            // Handle booking not found (e.g., throw an exception, log an error, etc.)
            throw new RuntimeException("Booking with ID " + bookingId + " not found.");
        }
            Booking booking = bookingOptional.get();
            booking.setCancel(status);
            booking.setStatus(CancelBooking);
            booking.setConfirmation(Confirmation.valueOf(Confirmation.REJECTED.name()));
            bookingRepository.save(booking);
            // Send notification (optional)
            // send Email
            // ...
    }

	// create booking
	public BookingDTO crateBooking(Booking booking) {
		booking = bookingRepository.save(booking);
		System.out.println("Booking added or updated");
		return bookingConverter.toDTO(booking);
	}
    public BookingDTO newBooking(BookingDTO bookingDTO) {
        BookingDTO bookingDto = new BookingDTO();
        bookingDto.setPropertyId(bookingDTO.getPropertyId());
        bookingDto.setUserId(bookingDTO.getUserId());
        bookingDto.setTotal(bookingDTO.getPrice());
        bookingDto.setNumOfGuest(bookingDTO.getNumOfGuest());
        bookingDto.setDateBooking(Date.valueOf(java.time.LocalDate.now()));
        bookingDto.setCheckIn(bookingDTO.getCheckIn());
        bookingDto.setCheckOut(bookingDTO.getCheckOut());
        bookingDto.setNumberNight(bookingDTO.getNumberNight());
        bookingDto.setStatus(false);
        bookingDto.setConfirmation(Confirmation.PENDING.name());
        return bookingDto;
    }
    public boolean isRoomAvailable(UUID id, Date checkInDate, Date checkOutDate) {
        // Logic to check room availability based on check-in and check-out dates
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(id, checkInDate, checkOutDate);
        return conflictingBookings.isEmpty();
    }

    public void deleteBooking(UUID bookingId) {
        // set du lieu lai thanh false  khong xoa khoi database
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if(bookingOptional.isPresent()){
            Booking booking = bookingOptional.get();
            booking.setCancel(false);
            booking.setConfirmation(Confirmation.REJECTED);
        } else {
            throw new RuntimeException("Booking with ID " + bookingId + " not found.");
        }
    }


    // Service for host
    public List<BookingDTO> returnAllBookingOfHost(UUID hostId, String filter) {
        Confirmation confirmation = Enum.valueOf(Confirmation.class, filter);
        if(filter.isEmpty()){
            confirmation = Confirmation.PENDING;
        }
        // Lấy danh sách các properties của host
        List<PropertyDTO> properties = propertyService.findAllPropertiesByHostId(hostId);
        // Lấy ra các propertyId của các properties
        List<UUID> propertyIds = properties.stream().map(PropertyDTO::getPropertyId).toList();
        // loc và trả về danh sách booking
        List<BookingDTO> bookings = new ArrayList<>();
        for (UUID propertyId : propertyIds) {
            List<Booking> propertyBookings = bookingRepository.findAllByPropertyPropertyIdAndConfirmationOrderByDateBookingDesc(propertyId, confirmation);
            List<BookingDTO> propertyBookingDTOs = propertyBookings.stream().map(bookingConverter::toDTO).toList();
            bookings.addAll(propertyBookingDTOs);
        }
        // result after filter
        return bookings;
    }
    public void updateConfirmBooking(UUID bookingId, String status) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            throw new RuntimeException("Booking with ID " + bookingId + " not found.");
        }
        try {
            Booking booking = bookingOptional.get();
            Confirmation confirmation = Enum.valueOf(Confirmation.class, status);
            if (!booking.getConfirmation().equals(confirmation)) {
                booking.setConfirmation(confirmation);
                bookingRepository.save(booking);
            }
        } catch (IllegalArgumentException e) {
            // Handle invalid status value (e.g., throw an exception, log an error, etc.)
            throw new IllegalArgumentException("Invalid status value: " + status);
        }
    }

}
