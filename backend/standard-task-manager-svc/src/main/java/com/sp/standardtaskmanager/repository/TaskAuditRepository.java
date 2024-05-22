package com.sp.standardtaskmanager.repository;

import com.sp.standardtaskmanager.entity.TaskAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Repository
public interface TaskAuditRepository extends JpaRepository<TaskAudit, Long> {
}
