package com.example.connection_service.Connection_Service.repository;

import com.example.connection_service.Connection_Service.entity.ConnectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionsRepository  extends JpaRepository<ConnectionEntity, Long>{

    boolean existsByFollowerIdAndFollowingId(Long id, Long id1);

    ConnectionEntity findByFollowerIdAndFollowingId(Long id, Long id1);
}
