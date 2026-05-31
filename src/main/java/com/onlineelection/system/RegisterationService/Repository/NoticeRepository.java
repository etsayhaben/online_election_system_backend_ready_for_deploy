package com.onlineelection.system.RegisterationService.Repository;

import com.onlineelection.system.RegisterationService.Entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {


    // Custom query to get notices by author ID
    @Query("SELECT n FROM Notice n WHERE n.author.id = :authorId")
    List<Notice> findByAuthorId(@Param("authorId") Long authorId);

    // Custom query to get recent notices (you can specify a limit for recent notices)
    @Query("SELECT n FROM Notice n ORDER BY n.date DESC")
    List<Notice> findAllNoticesOrderedByDate();
       }
