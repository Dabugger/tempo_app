package com.tempo.testapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer query_id;
    private String endpoint;
    private String user_name;
    private Integer number1;
    private Integer number2;
}
