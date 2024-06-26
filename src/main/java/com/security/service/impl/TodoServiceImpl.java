package com.security.service.impl;

import com.security.model.Role;
import com.security.model.Todo;
import com.security.model.User;
import com.security.repository.TodoRepository;
import com.security.service.TodoService;
import com.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public Todo save(Todo todo, Principal connectedUser) {
        User currentUser = SecurityUtils.getCurrentUser(connectedUser);
        todo.setUser(currentUser);
        return todoRepository.save(todo);
    }

    @Override
    public List<Todo> findAll(Principal connectedUser) {
        User currentUser = SecurityUtils.getCurrentUser(connectedUser);

        if (currentUser.getRole().equals(Role.ADMIN)) {
            return todoRepository.findAll();
        } else {
            return todoRepository.findByUserId(currentUser.getId());
        }
    }
}