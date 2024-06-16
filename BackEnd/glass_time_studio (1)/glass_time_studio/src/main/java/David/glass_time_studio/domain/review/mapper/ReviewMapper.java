package David.glass_time_studio.domain.review.mapper;

import David.glass_time_studio.domain.review.dto.ReviewDto;
import David.glass_time_studio.domain.review.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review reviewDtoPostToReview(ReviewDto.Post post);
    Review reviewDtoPatchToReview(ReviewDto.Patch patch);
    ReviewDto.Response reviewToReviewDtoResponse(Review review);
    List<ReviewDto.Response> reviewsToReviewDtoResponse(List<Review> reviews);
}
