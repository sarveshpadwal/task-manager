package com.sp.standardtaskmanager.entity;

import com.sp.standardtaskmanager.constant.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import java.util.UUID;

/**
 * Entity for task table
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@ToString
@Entity
@Table(name = "task")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Task {
    /**
     * id of the task
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    /**
     * title of the task
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * description of the task
     */
    @Column(name = "description")
    private String description;

    /**
     * status of the task
     */
    @Enumerated(EnumType.STRING)
    @Type(TaskStatusType.class)
    @Column(name = "status", columnDefinition = "task_status", nullable = false)
    private TaskStatus status;

    /**
     * Version number of the database record
     */
    @Version
    @Column(name = "version")
    private Integer version;

    public Task(String title, String description, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }
}
