package net.mom.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto implements Serializable {

    private Long id;
    private String email;
    private String description;
    private boolean completed;
}
