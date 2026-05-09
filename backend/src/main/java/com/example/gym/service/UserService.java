
package com.example.gym.service;

import com.example.gym.dto.UserLoginDTO;
import com.example.gym.dto.UserRegisterDTO;
import com.example.gym.entity.User;
import com.example.gym.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    public Map<String, Object> login(UserLoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));
        
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        String token = jwtService.generateToken(user.getUsername());
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("username", user.getUsername());
        result.put("motto", user.getMotto());
        result.put("fitReason", user.getFitReason());
        result.put("level", user.getLevel());
        result.put("exp", user.getExp());
        return result;
    }
    
    public void register(UserRegisterDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFitReason(dto.getFitReason());
        user.setMotto("自律给我自由");
        
        userRepository.save(user);
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }
    
    public List<String> getRandomFitReasons(int limit) {
        return userRepository.findAll().stream()
                .map(User::getFitReason)
                .filter(reason -> reason != null && !reason.isEmpty() && !"未设置".equals(reason))
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());
    }
}
