import UserService from "../services/UserService";
import Task from "../models/task";

const GET_TASK = 'GET_TASK';
const LIST_TASKS = 'LIST_TASKS';
const LIST_TASKS_SUCCESS = 'LIST_TASKS_SUCCESS';
const ADD_TASK = 'ADD_TASK';
const EDIT_TASK = 'EDIT_TASK';
const DELETE_TASK = 'DELETE_TASK';

interface Action<T = any> {
    type: string;
    payload?: T;
}

const tasksReducer = (state: Task[] = [], action: Action): Task[] => {
    console.log("taskreducer:action", action);
    console.log("taskreducer:state", state);
    switch (action.type) {
        case LIST_TASKS_SUCCESS:
            return action.payload?.data.data || [];

        case DELETE_TASK:
            return state.filter((task) => task.id !== action.payload?.task.id);

        default:
            return state;
    }
};

export default tasksReducer;

interface GetTaskAction extends Action {
    payload: {
        request: {
            url: string;
        };
    };
}

export const getTask = (id: string): GetTaskAction => ({
    type: GET_TASK,
    payload: {
        request: {
            url: `/tasks/${id}`,
        },
    },
});

interface AllTasksAction extends Action {
    payload: {
        request: {
            url: string;
        };
    };
}

export const allTasks = (statusFilter: string): AllTasksAction => ({
    type: LIST_TASKS,
    payload: {
        request: {
            url: '/tasks?status='+statusFilter,
        },
    },
});

interface AddTaskAction extends Action {
    payload: {
        request: {
            url: string;
            method: string;
            data: Task;
        };
    };
}

export const addTask = (task: Task): AddTaskAction => {
    console.log(`${UserService.getUsername()} added the task ${task.title}`);
    return {
        type: ADD_TASK,
        payload: {
            request: {
                url: '/tasks',
                method: 'POST',
                data: task,
            },
        },
    };
};

interface EditTaskAction extends Action {
    payload: {
        request: {
            url: string;
            method: string;
            data: Task;
        };
    };
}

export const editTask = (task: Task): EditTaskAction => {
    console.log(`${UserService.getUsername()} updated the task ${task.title}`);
    return {
        type: EDIT_TASK,
        payload: {
            request: {
                url: `/tasks/${task.id}`,
                method: 'PUT',
                data: task,
            },
        },
    };
};

interface DeleteTaskAction extends Action {
    payload: {
        task: Task;
        request: {
            url: string;
            method: string;
        };
    };
}

export const deleteTask = (task: Task): DeleteTaskAction => {
    console.log(`${UserService.getUsername()} deleted the task ${task.title}`);
    return {
        type: DELETE_TASK,
        payload: {
            task,
            request: {
                url: `/tasks/${task.id}`,
                method: 'DELETE',
            },
        },
    };
};