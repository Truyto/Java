
package com.example.gym.controller;

import com.example.gym.dto.ApiResponse;
import com.example.gym.dto.UserLoginDTO;
import com.example.gym.dto.UserRegisterDTO;
import com.example.gym.entity.User;
import com.example.gym.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@Valid @RequestBody UserLoginDTO dto) {
        try {
            Map<String, Object> data = userService.login(dto);
            return ResponseEntity.ok(ApiResponse.success(data));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody UserRegisterDTO dto) {
        try {
            userService.register(dto);
            return ResponseEntity.ok(ApiResponse.success(null));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/info")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            String username = token.replace("Bearer ", "");
            User user = userService.getUserByUsername(username);
            Map<String, Object> data = new HashMap<>();
            data.put("username", user.getUsername());
            data.put("motto", user.getMotto());
            data.put("fitReason", user.getFitReason());
            return ResponseEntity.ok(ApiResponse.success(data));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/fit-reasons")
    public ResponseEntity<ApiResponse<List<String>>> getRandomFitReasons(@RequestParam(defaultValue = "15") int limit) {
        List<String> reasons = userService.getRandomFitReasons(limit);
        return ResponseEntity.ok(ApiResponse.success(reasons));
    }
}
