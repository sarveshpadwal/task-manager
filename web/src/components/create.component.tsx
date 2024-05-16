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
                status: '',
            }
        }
        this.onFieldValueChange = this.onFieldValueChange.bind(this);
    }

    private onFieldValueChange(fieldName: string, value: string) {
        const nextState = {
            ...this.state,
            person: {
                ...this.state.task,
                [fieldName]: value,
            }
        };

        this.setState(nextState);
    }

    private onSave = () => {
        BaseService.create<Task>("/tasks", this.state.task).then(
            (rp) => {
                if ("SUCCESS" === rp.status) {
                    toastr.success('task created');
                    this.setState({
                        task: {
                            id: '',
                            title: '',
                            description: '',
                            status: '',
                        }
                    });

                } else {
                    toastr.error(JSON.stringify(rp.errors));
                    console.log("errors: " + rp.errors);
                }
            }
        );

    }

    render() {
        return (
            <TaskPage
                task={this.state.task}
                onChange={this.onFieldValueChange}
                onSave={this.onSave}
            />
        );
    }

}