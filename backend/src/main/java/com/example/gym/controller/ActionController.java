
package com.example.gym.controller;

import com.example.gym.dto.ApiResponse;
import com.example.gym.entity.Action;
import com.example.gym.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/action")
@RequiredArgsConstructor
public class ActionController {
    
    private final ActionService actionService;
    
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Action>>> getActionList() {
        List<Action> list = actionService.getAllActions();
        return ResponseEntity.ok(ApiResponse.success(list));
    }
    
    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse<Action>> getActionDetail(@PathVariable Long id) {
        try {
            Action action = actionService.getActionById(id);
            return ResponseEntity.ok(ApiResponse.success(action));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/part")
    public ResponseEntity<ApiResponse<List<Action>>> getActionsByPart(@RequestParam String part) {
        List<Action> list = actionService.getActionsByPart(part);
        return ResponseEntity.ok(ApiResponse.success(list));
    }
    
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Action>> createAction(@RequestBody Action action) {
        Action created = actionService.createAction(action);
        return ResponseEntity.ok(ApiResponse.success(created));
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Action>> updateAction(@PathVariable Long id, @RequestBody Action action) {
        try {
            Action updated = actionService.updateAction(id, action);
            return ResponseEntity.ok(ApiResponse.success(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAction(@PathVariable Long id) {
        try {
            actionService.deleteAction(id);
            return ResponseEntity.ok(ApiResponse.success(null));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }
}
