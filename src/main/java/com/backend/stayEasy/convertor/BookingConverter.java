package com.backend.stayEasy.convertor;

import com.backend.stayEasy.dto.BookingDTO;
import com.backend.stayEasy.entity.Booking;
import com.backend.stayEasy.sevice.PropertyService;
import com.backend.stayEasy.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter {
    @Autowired
    private PropertyService listingService;
    private static PropertyService listingServiceStatic;
    @Autowired
    private UserService userService;
    private static UserService userServiceStatic;
    @Autowired
    public void setStatic(){
        listingServiceStatic=listingService;
        userServiceStatic=userService;
    }
    public static BookingDTO convertToDto(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(booking.getBookingId());
        bookingDTO.setDateBooking(booking.getDateBooking());
        bookingDTO.setCheckIn(booking.getCheckIn());
        bookingDTO.setCheckOut(booking.getCheckOut());
        bookingDTO.setUserId(booking.getUser().getId());
        bookingDTO.setPropertyId(booking.getProperty().getPropertyId());
        bookingDTO.setPropertyName(booking.getProperty().getPropertyName());
        bookingDTO.setNumOfNight(booking.getNumNight());
        bookingDTO.setNumOfGuest(booking.getNumGuest());
        bookingDTO.setTotal(booking.getTotalPrice().doubleValue());
        bookingDTO.setStatus(booking.getStatus());
//        bookingDTO.setPropertyName(listingServiceStatic.findById(booking.getProperty().getId()).getPropertyName());
        return  bookingDTO;
    }
    public static Booking convert(BookingDTO bookingDto){
        Booking booking = new Booking();
        booking.setBookingId(bookingDto.getBookingId());
        booking.setDateBooking(bookingDto.getDateBooking());
        booking.setNumNight(bookingDto.getNumOfNight());
        booking.setCheckIn(bookingDto.getCheckIn());
        booking.setCheckOut(bookingDto.getCheckOut());
        booking.setTotalPrice(bookingDto.getTotal().floatValue());
        booking.setNumGuest(bookingDto.getNumOfGuest());
        booking.setProperty(listingServiceStatic.getById(bookingDto.getPropertyId()));
        booking.setUser(userServiceStatic.findById(bookingDto.getUserId()));
        booking.setStatus(bookingDto.getStatus());
        return  booking;
    }
}



