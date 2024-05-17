-- liquibase formatted sql
-- changeset sarvesh:create-table-task splitStatements:true
CREATE TABLE IF NOT EXISTS task
(
    id UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid
(
),
    title VARCHAR
(
    128
) NOT NULL,
    description TEXT,
    status task_status NOT NULL DEFAULT 'To Do',
    version INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS task_audit
(
    audit_id
    SERIAL
    NOT
    NULL
    PRIMARY
    KEY,
    id
    UUID
    NOT
    NULL,
    title
    VARCHAR
(
    128
) NOT NULL,
    description TEXT,
    status task_status NOT NULL,
    ts TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                     version INT NOT NULL
                     );