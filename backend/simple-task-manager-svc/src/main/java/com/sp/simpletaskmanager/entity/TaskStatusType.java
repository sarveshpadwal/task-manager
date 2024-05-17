package com.sp.simpletaskmanager.entity;

import com.sp.simpletaskmanager.constant.TaskStatus;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

/**
 * Custom UserType for TaskStatus
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
public class TaskStatusType implements UserType<TaskStatus> {

    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<TaskStatus> returnedClass() {
        return TaskStatus.class;
    }

    @Override
    public boolean equals(TaskStatus x, TaskStatus y) {
        return x.getValue().equals(y.getValue());
    }

    @Override
    public int hashCode(TaskStatus x) {
        return Objects.hashCode(x);
    }

    @Override
    public TaskStatus nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner)
            throws SQLException {
        String columnValue = (String) rs.getObject(position);
        if (rs.wasNull()) {
            columnValue = null;
        }
        return TaskStatus.fromStatus(columnValue);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, TaskStatus taskStatus, int index,
                            SharedSessionContractImplementor session) throws SQLException {
        if (taskStatus == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, taskStatus.getValue(), Types.OTHER);
        }
    }

    @Override
    public TaskStatus deepCopy(TaskStatus taskStatus) {
        return taskStatus == null ? null : TaskStatus.fromStatus(taskStatus.getValue());
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(TaskStatus value) {
        return deepCopy(value);
    }

    @Override
    public TaskStatus assemble(Serializable cached, Object owner) {
        return deepCopy((TaskStatus) cached);
    }

    @Override
    public TaskStatus replace(TaskStatus detached, TaskStatus managed, Object owner) {
        return deepCopy(detached);
    }
}
