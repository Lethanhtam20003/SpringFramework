package com.thanhtam.ecommerce.identity.infrastructure.persistence;

import com.thanhtam.ecommerce.identity.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IClientRepository extends JpaRepository<Client, UUID> {
    boolean existsByEmail(String email);
    Optional<Client> findByEmail(String email);

    boolean existsByClientName(String clientName);

    Optional<Client> findByClientName(String clientName);
}