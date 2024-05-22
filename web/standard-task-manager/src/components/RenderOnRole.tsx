import React, {ReactNode} from 'react';
import UserService from "../services/UserService";
import NotAllowed from "./NotAllowed";

interface RenderOnRoleProps {
  roles: string[];
  showNotAllowed?: boolean;
  children: ReactNode;
}

const RenderOnRole: React.FC<RenderOnRoleProps> = ({ roles, showNotAllowed, children }) => (
    UserService.hasRole(roles) ? <>{children}</> : (showNotAllowed ? <NotAllowed /> : null)
);

export default RenderOnRole;
