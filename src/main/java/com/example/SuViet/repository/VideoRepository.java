package com.example.SuViet.repository;

import com.example.SuViet.model.Character;
import com.example.SuViet.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    public List<Video> findAllByEnabled(boolean enabled);


    public Page<Video> findAllByEnabled(boolean enabled, PageRequest pageRequest);

//    @Query("SELECT v FROM Video v WHERE v.title LIKE %:title%")
//    public List<Video> searchVideoByTitle(@Param("title") String title);

    public List<Video> findAllByTitleContainingAndEnabled(@Param("title") String title, boolean enabled);

    public Page<Video> findAllByTitleContainingAndEnabled(@Param("title") String title, boolean enabled, PageRequest pageRequest);

//    @Query(value = "select v.videoID, v.title, v.description, v.video, v.createdDate, v.enabled " +
//            "from Period p " +
//            "inner join tblPeriodVideo pv on p.periodID = pv.PeriodID " +
//            "inner join Video v on pv.videoID = v.videoID " +
//            "where cast(p.periodName as nvarchar(max)) like :periodName", nativeQuery = true)
//    public List<Video> findAllByPeriodName(@Param("periodName") String periodName, boolean enabled);
}
