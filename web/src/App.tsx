import * as React from 'react';
import {BrowserRouter, Link, Route, Routes} from "react-router-dom";

import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'toastr/build/toastr.min.css';
import Index from './components/index.component';
import Edit from './components/edit.component';
import Create from './components/create.component';

const App: React.FC = () => {
    return (
        <BrowserRouter>
            <div className="container">
                <nav className="navbar navbar-expand-lg navbar-light bg-light">
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav mr-auto">
                            <li className="nav-item">
                                <Link to={'/'} className="nav-link">Home</Link>
                            </li>
                            <li className="nav-item">
                                <Link to={'/tasks'} className="nav-link">Create Task</Link>
                            </li>
                        </ul>
                    </div>
                </nav>
                <br/>

                <Routes>
                    <Route path='/tasks' element={<Create/>}/>
                    <Route path='/tasks/:id' element={<Edit/>}/>
                    <Route path='/' element={<Index/>}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;
