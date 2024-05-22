import * as React from "react";
import {useState} from "react";
import {useDispatch} from "react-redux";
import {useNavigate} from "react-router";
import Task from "../models/task";
import {addTask} from "../modules/tasks";
import RenderOnRole from "./RenderOnRole";

const CreateTask: React.FC = () => {
    const [title, setTitle] = useState<string>('');
    const [description, setDescription] = useState<string>('');
    const [status, setStatus] = useState<string>('To Do');

    const dispatch = useDispatch<any>();
    const navigate = useNavigate();

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (!title || !status) {
            return;
        }
        const task: Task = new Task('', title, description, status);
        dispatch(addTask(task)).then(() => navigate("/"));
    };

    return (
        <div className="row">
            <div className="col-sm-6">
                <form onSubmit={handleSubmit}>
                    <h1>Add a new task:</h1>
                    <div className="mb-3">
                        <label htmlFor="title" className="form-label">Title</label>
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Title"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="description" className="form-label">Description</label>
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Description"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="status" className="form-label">Status</label>
                        <select
                            name="status"
                            className="form-control"
                            value={status}
                            onChange={(e) => setStatus(e.target.value)}
                        >
                            <option value={"To Do"}>To Do</option>
                            <option value={"In Progress"}>In Progress</option>
                            <option value={"Done"}>Done</option>
                        </select>
                    </div>
                    <RenderOnRole roles={['user']}>
                        <button type="submit" className="btn btn-primary">Add Task</button>
                    </RenderOnRole>
                </form>
            </div>
        </div>
    );
}

export default CreateTask;