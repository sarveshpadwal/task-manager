-- liquibase formatted sql
-- changeset sarvesh:create-type-task_status splitStatements:true
CREATE TYPE task_status AS ENUM ('All', 'To Do', 'In Progress', 'Done');