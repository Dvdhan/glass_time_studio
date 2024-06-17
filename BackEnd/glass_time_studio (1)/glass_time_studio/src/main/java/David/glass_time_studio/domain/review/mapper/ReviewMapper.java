package David.glass_time_studio.domain.review.mapper;

import David.glass_time_studio.domain.booking.dto.BookingDto;
import David.glass_time_studio.domain.booking.entity.Booking;
import David.glass_time_studio.domain.review.dto.ReviewDto;
import David.glass_time_studio.domain.review.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review reviewDtoPostToReview(ReviewDto.Post post);
    Review reviewDtoPatchToReview(ReviewDto.Patch patch);
    @Mappings({
            @Mapping(target = "created_at", source = "created_At"),
            @Mapping(target = "modified_at", source = "modified_At")
    })
    ReviewDto.Response reviewToReviewDtoResponse(Review review);
    List<ReviewDto.Response> reviewsToReviewDtoResponse(List<Review> reviews);
}
