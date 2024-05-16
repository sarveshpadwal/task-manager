import * as React from 'react';
import Task from '../models/task';

import {Input, Button} from '../common/components/form';

interface Props {
    task: Task;
    onChange: (fieldName: string, value: string) => void;
    onSave: () => void;
}

export const TaskForm: React.FunctionComponent<Props> = (props) => {
    return (
        <form>
            <h1>Manage Task</h1>

            <Input
                name="Title"
                label="Title"
                value={props.task.title}
                onChange={props.onChange}
            />

            <Input
                name="Description"
                label="Description"
                value={props.task.description}
                onChange={props.onChange}
            />

            <Input
                name="Status"
                label="Status"
                value={props.task.status.toString()}
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
