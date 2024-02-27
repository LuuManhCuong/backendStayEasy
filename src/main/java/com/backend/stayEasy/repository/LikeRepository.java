package com.backend.stayEasy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.stayEasy.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {

	List<Like> findByPropertyPropertyId(UUID propertyId);
}
