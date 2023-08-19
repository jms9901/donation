package com.example.donation.repository;

import com.example.donation.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // SELECT * FROM uuser WHERE email=?;
    @Query("SELECT ub FROM User ub WHERE ub.email = :email")
    public Optional<User> findByEmail(@Param("email") String email);

    // SELECT * FROM uuser WHERE password=?;
    @Query("SELECT ub FROM User ub WHERE ub.password = :password")
    public Optional<User> findByPassword(@Param("password") String password);

}
