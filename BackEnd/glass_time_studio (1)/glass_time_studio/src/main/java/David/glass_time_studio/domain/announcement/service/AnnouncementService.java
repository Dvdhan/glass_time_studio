package David.glass_time_studio.domain.announcement.service;

import David.glass_time_studio.domain.announcement.entity.Announcement;
import David.glass_time_studio.domain.announcement.repository.AnnouncementRepository;
import David.glass_time_studio.global.advice.BusinessLogicException;
import David.glass_time_studio.global.advice.ExceptionCode;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Getter
public class AnnouncementService {

    private AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository){
        this.announcementRepository=announcementRepository;
    }

    @Transactional
    public Announcement postAnnouncement(Announcement announcement){
        Announcement postAnnouncement = announcementRepository.save(announcement);
        return postAnnouncement;
    }

    public Announcement findAnnouncement(Long announcement_Id){
        Optional<Announcement> findAnnouncement = announcementRepository.findById(announcement_Id);
        Announcement foundAnnouncement = findAnnouncement.orElseThrow(()->new BusinessLogicException(ExceptionCode.ANNOUNCEMENT_NOT_FOUND));
        return foundAnnouncement;
    }

    public List<Announcement> searchAnnouncementsByTitle(String keyword){
        return announcementRepository.searchAnnouncement("%" + keyword + "%");
    }

    public Page<Announcement> findAllAnnouncement(int page, int size){
        return announcementRepository.findAllAnnouncements(PageRequest.of(page, size));
    }

    public Announcement updateAnnouncement(Announcement announcement, Long announcement_Id){
        Announcement target = findAnnouncement(announcement_Id);

        String new_announcement_title = announcement.getAnnouncement_Title();
        String new_announcement_content = announcement.getAnnouncement_Content();

        boolean isUpdated = false;

        if(new_announcement_title != null){
            target.setAnnouncement_Title(new_announcement_title);
            isUpdated = true;
        }
        if(new_announcement_content != null){
            target.setAnnouncement_Content(new_announcement_content);
            isUpdated = true;
        }
        if(isUpdated){
            return announcementRepository.save(target);
        }else {
            return target;
        }
    }

    public void deleteAnnouncement (Announcement announcement){
        announcementRepository.delete(announcement);
    }
}
