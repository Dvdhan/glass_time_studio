package David.glass_time_studio.domain.announcement.mapper;

import David.glass_time_studio.domain.announcement.dto.AnnouncementDto;
import David.glass_time_studio.domain.announcement.entity.Announcement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnnouncementMapper {

    Announcement announcementDtoPostToAnnouncement (AnnouncementDto.Post post);
    Announcement announcementDtoPatchToAnnouncement (AnnouncementDto.Patch patch);
    @Mappings({
            @Mapping(target = "created_at", source = "created_At"),
            @Mapping(target = "modified_at", source = "modified_At")
    })
    AnnouncementDto.Response announcementToAnnouncementDtoResponse (Announcement announcement);
    List<AnnouncementDto.Response> announcementsToAnnouncementDtoResponse (List<Announcement> announcements);

}
