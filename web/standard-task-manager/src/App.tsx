import React from 'react';
import {Provider} from "react-redux";
import {BrowserRouter} from "react-router-dom";
import 'toastr/build/toastr.min.css';
import TaskBox from "./components/TaskBox";
import RenderOnAnonymous from "./components/RenderOnAnonymous";
import RenderOnAuthenticated from "./components/RenderOnAuthenticated";
import Welcome from "./components/Welcome";
import {Store} from "redux";

interface AppProps {
    store: Store;
}

const App: React.FC<AppProps> = ({ store }) => (
    <Provider store={store}>
        <BrowserRouter>
            <div className="container">
                <RenderOnAnonymous>
                    <Welcome />
                </RenderOnAnonymous>
                <RenderOnAuthenticated>
                    <TaskBox />
                </RenderOnAuthenticated>
            </div>
        </BrowserRouter>
    </Provider>
);

export default App;
