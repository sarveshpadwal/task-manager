import React, {ReactNode} from 'react';
import UserService from "../services/UserService";

interface RenderOnAnonymousProps {
    children: ReactNode;
}

const RenderOnAuthenticated: React.FC<RenderOnAnonymousProps> = ({ children }) => (
    UserService.isLoggedIn() ? <>{children}</> : null
);

export default RenderOnAuthenticated;
