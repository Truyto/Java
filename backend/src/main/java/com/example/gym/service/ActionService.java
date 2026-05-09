
package com.example.gym.service;

import com.example.gym.entity.Action;
import com.example.gym.repository.ActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActionService {
    
    private final ActionRepository actionRepository;
    
    public List<Action> getAllActions() {
        return actionRepository.findAll();
    }
    
    public Action getActionById(Long id) {
        return actionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("动作不存在"));
    }
    
    public List<Action> getActionsByPart(String part) {
        return actionRepository.findByPart(part);
    }
    
    public Action createAction(Action action) {
        return actionRepository.save(action);
    }
    
    public Action updateAction(Long id, Action action) {
        Action existing = getActionById(id);
        existing.setName(action.getName());
        existing.setPart(action.getPart());
        existing.setLevel(action.getLevel());
        existing.setContent(action.getContent());
        existing.setImgUrl(action.getImgUrl());
        return actionRepository.save(existing);
    }
    
    public void deleteAction(Long id) {
        actionRepository.deleteById(id);
    }
}
