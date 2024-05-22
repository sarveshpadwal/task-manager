import Response from "../models/response";
import axios from "axios";
import ErrorDetails from "../models/errordetails";


export default class BaseService {
    private static baseURL: string = "http://localhost:8080/api/v1";

    public static async getAll<T>(url: string, params: any): Promise<Response> {
        let errorDetails:ErrorDetails[] = [];
        let res = await axios.get<Array<T>>(this.baseURL + url, {params})
            .then((response: any) => {
                const apiResponse = response.data;
                if (apiResponse?.errors?.length > 0) {
                    errorDetails = apiResponse.errors.map((err: { code: string; message: string; target: string; }) =>
                        new ErrorDetails(err.code, err.message, err.target));
                }
                return new Response(apiResponse.status, apiResponse.data as Array<T>, errorDetails);
            })
            .catch(function (error: any) {
                if (error.response?.data?.errors?.length > 0) {
                    errorDetails = error.response.data.errors.map((err: { code: string; message: string; target: string; }) =>
                        new ErrorDetails(err.code, err.message, err.target));
                }
                return new Response("FAIL", null, errorDetails);
            });
        return res;
    }

    public static get<T>(url: string, param: any): Promise<Response> {
        let errorDetails:ErrorDetails[] = [];
        let res = axios.get<T>(this.baseURL + url + param)
            .then((response: any) => {
                const apiResponse = response.data;
                if (apiResponse?.errors?.length > 0) {
                    errorDetails = apiResponse.errors.map((err: { code: string; message: string; target: string; }) =>
                        new ErrorDetails(err.code, err.message, err.target));
                }
                return new Response(apiResponse.status, apiResponse.data, errorDetails);
            })
            .catch(function (error: any) {
                if (error.response?.data?.errors?.length > 0) {
                    errorDetails = error.response.data.errors.map((err: { code: string; message: string; target: string; }) =>
                        new ErrorDetails(err.code, err.message, err.target));
                }
                return new Response("FAIL", null, errorDetails);
            });
        return res;
    }

    public static delete(url: string, param: any): Promise<Response> {
        let errorDetails:ErrorDetails[] = [];
        let res = axios.delete(this.baseURL + url + param)
            .then((response: any) => {
                const apiResponse = response.data;
                if (apiResponse?.errors?.length > 0) {
                    errorDetails = apiResponse.errors.map((err: { code: string; message: string; target: string; }) =>
                        new ErrorDetails(err.code, err.message, err.target));
                }
                return new Response(apiResponse.status, apiResponse.data, errorDetails);
            })
            .catch(function (error: any) {
                if (error.response?.data?.errors?.length > 0) {
                    errorDetails = error.response.data.errors.map((err: { code: string; message: string; target: string; }) =>
                        new ErrorDetails(err.code, err.message, err.target));
                }
                return new Response("FAIL", null, errorDetails);
            });
        return res;
    }

    public static create<T>(url: string, obj: T): Promise<Response> {
        let errorDetails:ErrorDetails[] = [];
        let res = axios.post(this.baseURL + url, obj)
            .then((response: any) => {
                const apiResponse = response.data;
                if (apiResponse?.errors?.length > 0) {
                    errorDetails = apiResponse.errors.map((err: { code: string; message: string; target: string; }) =>
                        new ErrorDetails(err.code, err.message, err.target));
                }
                return new Response(apiResponse.status, apiResponse.data, errorDetails);
            })
            .catch(function (error: any) {
                if (error.response?.data?.errors?.length > 0) {
                    errorDetails = error.response.data.errors.map((err: { code: string; message: string; target: string; }) =>
                        new ErrorDetails(err.code, err.message, err.target));
                }
                return new Response("FAIL", null, errorDetails);
            });
        return res;
    }

    public static update<T>(url: string, param: any, obj: T): Promise<Response> {
        let errorDetails:ErrorDetails[] = [];
        let res = axios.put(this.baseURL + url + param, obj)
            .then((response: any) => {
                const apiResponse = response.data;
                if (apiResponse?.errors?.length > 0) {
                    errorDetails = apiResponse.errors.map((err: { code: string; message: string; target: string; }) =>
                        new ErrorDetails(err.code, err.message, err.target));
                }
                return new Response(apiResponse.status, apiResponse.data, errorDetails);
            })
            .catch(function (error: any) {
                if (error.response?.data?.errors?.length > 0) {
                    errorDetails = error.response.data.errors.map((err: { code: string; message: string; target: string; }) =>
                        new ErrorDetails(err.code, err.message, err.target));
                }
                return new Response("FAIL", null, errorDetails);
            });
        return res;
    }
}