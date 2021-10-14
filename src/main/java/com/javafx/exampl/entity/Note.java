package com.javafx.exampl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
//@RequiredArgsConstructor
@AllArgsConstructor
public class Note {

    private Integer id;
    private String description;
    private LocalDateTime createdTime;

    public Note(){
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
