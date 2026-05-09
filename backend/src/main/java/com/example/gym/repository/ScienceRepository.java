
package com.example.gym.repository;

import com.example.gym.entity.Science;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScienceRepository extends JpaRepository<Science, Long> {
    List<Science> findByTitleContainingOrCategoryContaining(String title, String category);
}
