import * as React from "react";
import {useEffect} from "react";
import Task from "../models/task";
import {useDispatch, useSelector} from "react-redux";
import {Link, useParams} from "react-router-dom";
import {createSelector} from "reselect";
import {allTasks, deleteTask} from "../modules/tasks";
import {RootState} from "../modules";

const selectTasks = createSelector(
    (state: RootState) => state.tasks,
    (tasks) => tasks
);

const TaskList: React.FC = () => {
    const dispatch = useDispatch<any>();
    const { status } = useParams();
    const tasks = useSelector(selectTasks);

    useEffect(() => {
        const statusFilter = status || "";
        dispatch(allTasks(statusFilter));
    }, [dispatch]);

    return (
        <div className="row">
            <div className="col-sm-12">
                <h1 className="text-center">Tasks List</h1>
                <div>
                    Select status to filter tasks:
                    <select name="filter-status"
                            onChange={async (e) => {
                                dispatch(allTasks(e.target.value));
                            }}
                    >
                        <option value={""}>All</option>
                        <option value={"To Do"}>To Do</option>
                        <option value={"In Progress"}>In Progress</option>
                        <option value={"Done"}>Done</option>
                    </select>
                </div>
                <table className="table table-striped align-middle">
                    <thead style={{position: "sticky", top: 0}}>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Status</th>
                        <th className="text-center" colSpan={2}>
                            Action
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {tasks.map((task: Task) => (
                        <tr key={task.id}>
                            <td>{task.id}</td>
                            <td>{task.title}</td>
                            <td>{task.description}</td>
                            <td>{task.status}</td>
                            <td>
                                <Link className="btn btn-xs btn-danger" to={`/tasks/${task.id}`}>EDIT TASK</Link>
                            </td>
                            <td>
                                <button
                                    className="btn btn-xs btn-danger"
                                    onClick={() => dispatch(deleteTask(task))}
                                >
                                    Delete Task
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default TaskList;
