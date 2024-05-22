import {Link} from "react-router-dom";
import UserService from "../services/UserService";

const Menu = () => (
    <nav className="navbar navbar-expand bg-body-tertiary py-3 mb-5">
        <div className="container-fluid">
            <Link className="navbar-brand" to="/">TaskBox!</Link>
            <div className="collapse navbar-collapse">
                <ul className="navbar-nav me-auto">
                    <li className="nav-item"><Link to="/" className="nav-link">List</Link></li>
                    <li className="nav-item"><Link to="/tasks" className="nav-link">New Task</Link></li>
                    <li className="nav-item"><Link to="/foo" className="nav-link">404!</Link></li>
                </ul>
                <div className="d-flex align-items-center">
                    <div className="navbar-text mx-1">
                        Signed in as <b>{UserService.getUsername()}</b>
                    </div>
                    <button className="btn btn-sm btn-success ms-1" onClick={() => UserService.doLogout()}>
                        Logout
                    </button>
                </div>
            </div>
        </div>
    </nav>
)

export default Menu