import React from 'react'
import {Route, Routes} from 'react-router-dom'

import TaskList from './TaskList';
import EditTask from "./EditTask";
import CreateTask from "./CreateTask";
import Menu from "./Menu";
import NoMatch from "./NoMatch";

const TaskBox: React.FC = () => (
    <>
        <Menu />
        <Routes>
            <Route path="/" element={<TaskList />} />
            <Route path="/tasks" element={<CreateTask />} />
            <Route path="/tasks/:id" element={<EditTask />} />
            <Route path="*" element={<NoMatch />} />
        </Routes>
    </>
);

export default TaskBox;