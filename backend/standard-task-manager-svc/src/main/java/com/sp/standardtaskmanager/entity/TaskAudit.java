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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.UUID;

/**
 * Entity for task_audit table
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@ToString
@Entity
@Table(name = "task_audit")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TaskAudit {

    /**
     * ID of the task_audit table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id", nullable = false)
    private Long auditId;

    /**
     * id of the task
     */
    @Column(name = "id", nullable = false)
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

    @Column(name = "ts", insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private Instant ts;

}
