//package David.glass_time_studio.domain.permit.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//public interface PermitRepository extends JpaRepository<Permit, Long> {
//
//    @Query(value = "SELECT * FROM MEMBER_PERMIT WHERE member_member_id = :memberId", nativeQuery = true)
//    public String findPermit(Long memberId);
//}
