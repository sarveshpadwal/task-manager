import {ComponentType} from 'react';
import {useLocation, useNavigate, useParams} from 'react-router-dom';
import {BrowserHistory, createBrowserHistory} from "history";

export interface WithRouterProps<T = ReturnType<typeof useParams>> {
    history: BrowserHistory
    location: ReturnType<typeof useLocation>;
    match: {
        params: T;
    };
    navigate: ReturnType<typeof useNavigate>;
}

export const withRouter = <P extends object>(Component: ComponentType<P>) => {
    return (props: Omit<P, keyof WithRouterProps>) => {
        const location = useLocation();
        const match = { params: useParams() };
        const navigate = useNavigate();
        const history = createBrowserHistory();

        return (
            <Component
                history={history}
                location={location}
                match={match}
                navigate={navigate}
                {...props as P}
            />
        );
    };
};