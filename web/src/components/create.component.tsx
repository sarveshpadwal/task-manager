import React from 'react';
import * as toastr from 'toastr';
import Task from '../models/task';
import BaseService from '../service/base.service';
import {TaskPage} from './page.form';

interface IProps {
}

interface IState {
    task: Task
}


export default class Create extends React.Component<IProps, IState> {
    constructor(props: IProps) {
        super(props);

        this.state = {
            task: {
                id: '',
                title: '',
                description: '',
                status: 'To Do',
            }
        }
        this.onFieldValueChange = this.onFieldValueChange.bind(this);
    }

    private onFieldValueChange(fieldName: string, value: string) {
        const nextState = {
            ...this.state,
            task: {
                ...this.state.task,
                [fieldName]: value,
            }
        };

        this.setState(nextState);
    }

    private onSave = () => {
        BaseService.create<Task>("/tasks", this.state.task).then(
            (rp) => {
                console.log("create task api response:", rp);
                if ("SUCCESS" === rp.status) {
                    console.log("create task api successful");
                    toastr.success('task created');
                    this.setState({
                        task: {
                            id: '',
                            title: '',
                            description: '',
                            status: 'To Do',
                        }
                    });
                } else {
                    console.log("create task api failed");
                    (rp.errors || []).forEach((err: any) => {
                        toastr.error(
                            err.displayMessage,
                            "",
                            { timeOut: 8000 }
                        );
                    });
                    console.log("errors: " + rp.errors);
                }
            }
        );

    }

    render() {
        return (
            <TaskPage
                task={this.state.task}
                edit={false}
                onChange={this.onFieldValueChange}
                onSave={this.onSave}
            />
        );
    }

}