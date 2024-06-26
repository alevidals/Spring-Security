package com.security.service;

import com.security.model.Todo;

import java.security.Principal;
import java.util.List;

public interface TodoService {
    Todo save(Todo todo, Principal connectedUser);

    List<Todo> findAll(Principal connectedUser);
}
