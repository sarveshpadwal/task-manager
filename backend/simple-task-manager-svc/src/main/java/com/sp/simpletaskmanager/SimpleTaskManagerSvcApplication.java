package com.sp.simpletaskmanager;

import com.sp.simpletaskmanager.constant.TaskStatus;
import com.sp.simpletaskmanager.entity.Task;
import com.sp.simpletaskmanager.entity.TaskAudit;
import com.sp.simpletaskmanager.modelmapper.TaskMapper;
import com.sp.simpletaskmanager.repository.TaskAuditRepository;
import com.sp.simpletaskmanager.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
@SpringBootApplication
public class SimpleTaskManagerSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleTaskManagerSvcApplication.class, args);
    }

    @Autowired
    private TaskRepository repository;

    @Autowired
    private TaskAuditRepository auditRepository;

    @Autowired
    private TaskMapper taskMapper;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        Task task1 = repository.save(new Task("title1", null, TaskStatus.TODO));
        Task task2 = repository.save(new Task("title2", null, TaskStatus.TODO));
        Task task3 = repository.save(new Task("title3", null, TaskStatus.INPROGRESS));
        log.info("task1 {}", taskMapper.mapToDto(task1));
        log.info("task2 {}", taskMapper.mapToDto(task2));
        log.info("task3 {}", taskMapper.mapToDto(task3));
        TaskAudit taskAudit1 = auditRepository.save(taskMapper.mapToAudit(task1));
        TaskAudit taskAudit2 = auditRepository.save(taskMapper.mapToAudit(task2));
        TaskAudit taskAudit3 = auditRepository.save(taskMapper.mapToAudit(task3));
        log.info("taskAudit1 {}", taskAudit1);
        log.info("taskAudit2 {}", taskAudit2);
        log.info("taskAudit3 {}", taskAudit3);


    }

}
