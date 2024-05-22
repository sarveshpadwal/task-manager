import * as React from "react";
import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router";
import Task from "../models/task";
import {editTask, getTask} from "../modules/tasks";
import RenderOnRole from "./RenderOnRole";
import {createSelector} from "reselect";
import {RootState} from "../modules";
import {useParams} from "react-router-dom";

const selectTasks = createSelector(
    (state: RootState) => state.tasks,
    (tasks) => tasks
);

const EditTask: React.FC = () => {
    const { id } = useParams();
    const taskId = id || "";
    const dispatch = useDispatch<any>();
    const navigate = useNavigate();
    const tasks = useSelector(selectTasks);
    const task = tasks[0];
    const [description, setDescription] = useState<string>(task.description);
    const [status, setStatus] = useState<string>(task.status);

    useEffect(() => {
        dispatch(getTask(taskId));
    }, [dispatch]);

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const updatedTask: Task = new Task(taskId, task.title, description, status);
        dispatch(editTask(updatedTask)).then(() => navigate("/"));
    };

    return (
        <div className="row">
            <div className="col-sm-6">
                <form onSubmit={handleSubmit}>
                    <h5>Edit Task: {task.id}</h5>
                    <div className="mb-3">
                        <label htmlFor="title" className="form-label">Title</label>
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Title"
                            value={task.title}
                            readOnly={true}
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
                        <button type="submit" className="btn btn-primary">Edit Task</button>
                    </RenderOnRole>
                </form>
            </div>
        </div>
    );
}

export default EditTask;