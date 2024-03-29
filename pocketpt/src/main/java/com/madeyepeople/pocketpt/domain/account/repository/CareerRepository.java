package com.madeyepeople.pocketpt.domain.account.repository;

import com.madeyepeople.pocketpt.domain.account.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
    List<Career> findAllByTrainerAccountIdAndIsDeletedFalseOrderByType(Long trainerAccountId);
    Optional<Career> findByCareerIdAndIsDeletedFalse(Long careerId);
}
