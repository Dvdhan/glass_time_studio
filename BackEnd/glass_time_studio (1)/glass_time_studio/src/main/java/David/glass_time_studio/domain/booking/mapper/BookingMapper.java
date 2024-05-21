package David.glass_time_studio.domain.booking.mapper;

import David.glass_time_studio.domain.booking.dto.BookingDto;
import David.glass_time_studio.domain.booking.entity.Booking;
import David.glass_time_studio.domain.lecture.entity.Lecture;
import David.glass_time_studio.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking bookingDtoPostToBooking (BookingDto.Post post);
    Booking bookingDtoPatchToBooking (BookingDto.Patch patch);

    @Mappings({
            @Mapping(target = "created_at", source = "created_At"),
            @Mapping(target = "modified_at", source = "modified_At"),
            @Mapping(source = "member.memberId", target = "memberId")
    })
    BookingDto.Response bookingToBookingDtoResponse (Booking booking);

    List<BookingDto.Response> bookingsToBookingDtoResponse (List<Booking> bookings);
}
