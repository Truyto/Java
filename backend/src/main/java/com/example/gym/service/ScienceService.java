
package com.example.gym.service;

import com.example.gym.entity.Science;
import com.example.gym.repository.ScienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScienceService {
    
    private final ScienceRepository scienceRepository;
    
    public List<Science> getAllScience() {
        return scienceRepository.findAll();
    }
    
    public Science getScienceById(Long id) {
        return scienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("科普文章不存在"));
    }
    
    public List<Science> searchScience(String keyword) {
        return scienceRepository.findByTitleContainingOrCategoryContaining(keyword, keyword);
    }
    
    public Science createScience(Science science) {
        return scienceRepository.save(science);
    }
    
    public Science updateScience(Long id, Science science) {
        Science existing = getScienceById(id);
        existing.setTitle(science.getTitle());
        existing.setDescription(science.getDescription());
        existing.setCategory(science.getCategory());
        existing.setReadTime(science.getReadTime());
        existing.setImgUrl(science.getImgUrl());
        return scienceRepository.save(existing);
    }
    
    public void deleteScience(Long id) {
        scienceRepository.deleteById(id);
    }
}
