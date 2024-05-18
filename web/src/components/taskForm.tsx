import * as React from 'react';
import Task from '../models/task';

import {Button, Input, Select} from '../common/components/form';

interface Props {
    task: Task;
    edit: boolean;
    onChange: (fieldName: string, value: string) => void;
    onSave: () => void;
}

export const TaskForm: React.FunctionComponent<Props> = (props) => {
    const statuses = ['To Do', 'In Progress', 'Done'];
    return (
        <form>
            <h1>Manage Task</h1>

            <Input
                name="title"
                label="Title"
                value={props.task.title}
                readonly={props.edit}
                onChange={props.onChange}
            />

            <Input
                name="description"
                label="Description"
                value={props.task.description}
                readonly={props.edit}
                onChange={props.onChange}
            />

            <Select
                name="status"
                label="Status"
                value={props.task.status}
                options={statuses}
                onChange={props.onChange}
            />

            <Button
                label="Save"
                className="btn btn-success mt-2"
                onClick={props.onSave}
            />
        </form>
    );
};
