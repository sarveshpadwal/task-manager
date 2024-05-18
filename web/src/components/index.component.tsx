import * as React from "react";
import TableRow from "./TableRow";
import Task from "../models/task";
import BaseService from "../service/base.service";
import * as toastr from "toastr";
import ErrorDetails from "../models/errordetails";

interface IProps {}
interface IState {
  listTasks: Array<Task>;
  isReady: Boolean;
  hasError: Boolean;
  errors: Array<ErrorDetails>;
}

class Index extends React.Component<IProps, IState> {
  public state: IState = {
    listTasks: new Array<Task>(),
    isReady: false,
    hasError: false,
    errors: new Array<ErrorDetails>()
  };
  constructor(props: IProps) {
    super(props);
    this.state = {
      listTasks: Array<Task>(),
      isReady: false,
      hasError: false,
      errors: new Array<ErrorDetails>()
    };
  }

  public componentDidMount() {
    BaseService.getAll<Task>("/tasks").then((rp) => {
      if (rp.data) {
        const listTasks = new Array<Task>();
        (rp.data || []).forEach((task: any) => {
          listTasks.push(new Task(task.id, task.title, task.description, task.status));
        });

        this.setState({ listTasks: listTasks });
        this.setState({ isReady: true });
      } else {
        const errors = new Array<ErrorDetails>();
        (rp.errors || []).forEach((err: any) => {
          errors.push(new ErrorDetails(err.code, err.message, err.target));
        });
        this.setState({ isReady: true });
        this.setState({ hasError: true });
        this.setState({ errors: errors });
        console.log("errors: " + rp.errors);
      }
    });

    setTimeout(() => {
      if (!this.state.isReady) {
        toastr.info(
          "It is possible that the service is being restarted, please wait more ...",
          "",
          { timeOut: 8000 }
        );
      }

      if (this.state.hasError) {
        (this.state.errors || []).forEach((err: any) => {
          toastr.error(
              err.displayMessage,
              "",
              { timeOut: 8000 }
          );
        });
      }
    }, 2000);
  }

  public tabRow = () => {
    if (!this.state.isReady) {
      return (
        <tr>
          <td colSpan={6} className="text-center">
            <div className="spinner-border" role="status">
              <span className="visually-hidden">Loading...</span>
            </div>
          </td>
        </tr>
      );
    }
    if (this.state.hasError) {
      return this.state.errors.map(function (err, i) {
        return (
            <tr>
              <td colSpan={6} className="text-center">
                <div className="alert alert-danger" role="alert">
                  {err.displayMessage}
                </div>
              </td>
            </tr>
        );
      });
    }

    return this.state.listTasks.map(function (object, i) {
      return <TableRow key={i} index={i + 1} task={object} />;
    });
  };

  public render(): React.ReactNode {
    return (
      <div>
        <h3 className="text-center">Tasks List</h3>
        <table className="table table-striped" style={{ marginTop: 20 }}>
          <thead style={{ position: "sticky", top: 0 }}>
            <tr>
              <th>Title</th>
              <th>Description</th>
              <th>Status</th>
              <th className="text-center" colSpan={2}>
                Action
              </th>
            </tr>
          </thead>
          <tbody>{this.tabRow()}</tbody>
        </table>
      </div>
    );
  }
}
export default Index;
