import React from 'react';
import * as toastr from 'toastr';
import Task from '../models/task';
import BaseService from '../service/base.service';
import {TaskPage} from './page.form';
import {withRouter, WithRouterProps} from "./withRouter";

interface Params {
    id: string;
}

type IProps = WithRouterProps<Params>;

interface IState {
    task: Task
}

class Edit extends React.Component<IProps, IState> {

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
            task: {
                ...this.state.task,
                [fieldName]: value,
            }
        };

        this.setState(nextState);
    }

    public componentDidMount() {
        BaseService.get<Task>('/tasks/', this.props.match.params.id).then(
            (rp) => {
                if (rp.data) {
                    const task = rp.data;
                    this.setState({task: new Task(task.id, task.title, task.description, task.status)});
                } else {
                    toastr.error(JSON.stringify(rp.errors));
                    console.log("errors: " + rp.errors);
                }
            }
        );
    }


    private onSave = () => {
        BaseService.update<Task>("/tasks/", this.props.match.params.id, this.state.task).then(
            (rp) => {
                if ("SUCCESS" === rp.status) {
                    toastr.success('task updated');
                    this.props.history.back();
                } else {
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
                edit={true}
                onChange={this.onFieldValueChange}
                onSave={this.onSave}
            />
        );
    }
}

export default withRouter(Edit);