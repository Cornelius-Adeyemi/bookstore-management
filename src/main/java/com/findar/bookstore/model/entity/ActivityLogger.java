package com.findar.bookstore.model.entity;

import com.findar.bookstore.enums.Activity;
import com.findar.bookstore.model.audith.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ActivityLogger extends AuditEntity {


    @Column(nullable = false)
    private String user;

    @Enumerated(EnumType.STRING)
    private Activity activity;

    private String description;


    public ActivityLogger(String username, Activity activity){
        this.user= username;
        this.activity= activity;
        this.description = activity.getDescription();


    }
}
