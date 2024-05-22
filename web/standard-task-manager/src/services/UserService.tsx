import Keycloak, {KeycloakInstance, KeycloakTokenParsed} from "keycloak-js";

const _kc: KeycloakInstance = new Keycloak({
    url: "http://localhost:8888",
    realm: "external",
    clientId: "external-client",
});

/**
 * Initializes Keycloak instance and calls the provided callback function if successfully authenticated.
 *
 * @param onAuthenticatedCallback
 */
const initKeycloak = (onAuthenticatedCallback: () => void): void => {
    _kc.init({
        onLoad: 'login-required',
    })
        .then((authenticated) => {
            console.log("authenticated?", authenticated);
            if (!authenticated) {
                console.log("user is not authenticated..!");
            }
            onAuthenticatedCallback();
        })
        .catch(console.error);

    _kc.onTokenExpired = () => {
        console.log('token expired');
        _kc.updateToken(30)
            .then(() => {
                console.log('token successfully refreshed');
            })
            .catch(function () {
                console.log('failed to refresh token');
                _kc.login();
            });
    }
};

const doLogin = (): Promise<void> => _kc.login();

const doLogout = (): Promise<void> => _kc.logout();

const getToken = (): string | undefined => _kc.token;

const getTokenParsed = (): KeycloakTokenParsed | undefined => _kc.tokenParsed;

const isLoggedIn = (): boolean => !!_kc.token;

const updateToken = (successCallback: () => void): void => {
    _kc.updateToken(5)
        .then(successCallback)
        .catch(doLogin);
};

const getUsername = (): string | undefined => _kc.tokenParsed?.preferred_username;

const hasRole = (roles: string[]): boolean => roles.some((role) => _kc.hasRealmRole(role));

const UserService = {
    initKeycloak,
    doLogin,
    doLogout,
    isLoggedIn,
    getToken,
    getTokenParsed,
    updateToken,
    getUsername,
    hasRole,
};

export default UserService;
