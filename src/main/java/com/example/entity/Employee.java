package com.example.entity;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name="Employee")
public class Employee {
    private String name;
    private String position;
    private String department;
    private int salary;
}
