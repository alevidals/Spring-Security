package com.security.controller;

import com.security.dto.TodoDto;
import com.security.model.Todo;
import com.security.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoDto> createTodo(
            @RequestBody TodoDto todoDto,
            Principal connectedUser
    ) {
        Todo todo = Todo.builder().title(todoDto.getTitle()).build();
        Todo savedTodo = todoService.save(todo, connectedUser);

        return new ResponseEntity<>(
                TodoDto.builder().id(savedTodo.getId()).title(savedTodo.getTitle()).build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public List<TodoDto> getTodos(Principal connectedUser) {
        List<Todo> todos = todoService.findAll(connectedUser);

        return todos.stream()
                .map(todo -> TodoDto.builder().id(todo.getId()).title(todo.getTitle()).build())
                .toList();
    }
}
