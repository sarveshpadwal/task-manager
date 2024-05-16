import React from "react";
import {Link} from "react-router-dom";
import Task from "../models/task";
import BaseService from "../service/base.service";
import * as toastr from "toastr";

function Del(Id?: string) {
    BaseService.delete("/tasks/", {
        id: Id,
    }).then((rp) => {
        if ("SUCCESS" === rp.status) {
            toastr.success("task deleted");
            window.location.reload();
        } else {
            toastr.error(JSON.stringify(rp.errors));
            console.log("errors: " + rp.errors);
        }
    });
}

interface IProps {
    task: Task;
    index: Number;
}

const TableRow: React.FunctionComponent<IProps> = (props) => {
    return (
        <tr>
            <td>{props.index.toString()}</td>
            <td>{props.task.title}</td>
            <td>{props.task.description}</td>
            <td>{props.task.status}</td>
            <td>
                <Link to={"/tasks/" + props.task.id} className="btn btn-primary">
                    Edit
                </Link>
            </td>
            <td>
                <button onClick={() => Del(props.task.id)} className="btn btn-danger">
                    Delete
                </button>
            </td>
        </tr>
    );
};
export default TableRow;
