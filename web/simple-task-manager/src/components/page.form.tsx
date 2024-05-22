import * as React from 'react';
import Task from '../models/task';
import {TaskForm} from './taskForm';

interface IProps {
    task: Task;
    edit: boolean;
    onChange: (fieldName: string, value: string) => void;
    onSave: () => void;
}

export const TaskPage: React.FunctionComponent<IProps> = (props: IProps) => {
    return (
        <TaskForm
            task={props.task}
            edit={props.edit}
            onChange={props.onChange}
            onSave={props.onSave}
        />
    );
}
