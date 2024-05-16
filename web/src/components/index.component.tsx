import * as React from "react";
import TableRow from "./TableRow";
import Task from "../models/task";
import BaseService from "../service/base.service";
import * as toastr from "toastr";
interface IProps {}
interface IState {
  listTasks: Array<Task>;
  isReady: Boolean;
  hasError: Boolean;
}

class Index extends React.Component<IProps, IState> {
  public state: IState = {
    listTasks: new Array<Task>(),
    isReady: false,
    hasError: false,
  };
  constructor(props: IProps) {
    super(props);
    this.state = {
      isReady: false,
      listTasks: Array<Task>(),
      hasError: false,
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
        this.setState({ isReady: true });
        this.setState({ hasError: true });
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
        toastr.error(
          "An error occurred!",
          "",
          { timeOut: 8000 }
        );
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
      return (
        <tr>
          <td colSpan={6} className="text-center">
            <div className="alert alert-danger" role="alert">
              An error occurred!
            </div>
          </td>
        </tr>
      );
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
          <thead>
            <tr>
              <th>Index</th>
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
