
package com.example.gym.controller;

import com.example.gym.dto.ApiResponse;
import com.example.gym.entity.Science;
import com.example.gym.service.ScienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/science")
@RequiredArgsConstructor
public class ScienceController {
    
    private final ScienceService scienceService;
    
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Science>>> getScienceList() {
        List<Science> list = scienceService.getAllScience();
        return ResponseEntity.ok(ApiResponse.success(list));
    }
    
    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse<Science>> getScienceDetail(@PathVariable Long id) {
        try {
            Science science = scienceService.getScienceById(id);
            return ResponseEntity.ok(ApiResponse.success(science));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Science>>> searchScience(@RequestParam String keyword) {
        List<Science> list = scienceService.searchScience(keyword);
        return ResponseEntity.ok(ApiResponse.success(list));
    }
    
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Science>> createScience(@RequestBody Science science) {
        Science created = scienceService.createScience(science);
        return ResponseEntity.ok(ApiResponse.success(created));
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Science>> updateScience(@PathVariable Long id, @RequestBody Science science) {
        try {
            Science updated = scienceService.updateScience(id, science);
            return ResponseEntity.ok(ApiResponse.success(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteScience(@PathVariable Long id) {
        try {
            scienceService.deleteScience(id);
            return ResponseEntity.ok(ApiResponse.success(null));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }
}
