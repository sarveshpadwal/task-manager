import axios from "axios";
import {createRoot} from "react-dom/client";
import {applyMiddleware, createStore, Store} from "redux";
import axiosMiddleware from "redux-axios-middleware";
import {thunk} from 'redux-thunk';
import React from "react";
import App from "./App";
import rootReducer from "./modules";
import UserService from "./services/UserService";

// HTTP

const _axios = axios.create({ baseURL: 'http://localhost:8080/api/v1' });

_axios.interceptors.request.use(async (config: any) => {
    if (UserService.isLoggedIn()) {
        config.headers.Authorization =  `Bearer ${UserService.getToken()}`;
        UserService.updateToken(() => {});
    }
    return config;
});

_axios.interceptors.response.use(
    response => response,
    error => {
        if (error.response && error.response.status === 401) {
            try {

            } catch (refreshError) {
                return Promise.reject(refreshError);
            }
        }
        return Promise.reject(error);
    },
)

// REDUX STORE

const _middleware = applyMiddleware(thunk, axiosMiddleware(_axios));
// @ts-ignore
const store: Store = createStore(rootReducer, _middleware);

// APP

const renderApp = () => {
    const rootElement = document.getElementById("root");
    if (rootElement) {
        createRoot(rootElement).render(<App store={store} />);
    }
};

UserService.initKeycloak(renderApp);
