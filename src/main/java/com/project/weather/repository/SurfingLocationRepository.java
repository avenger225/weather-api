package com.project.weather.repository;

import com.project.weather.model.SurfingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurfingLocationRepository extends JpaRepository<SurfingLocation, Long> {
}
