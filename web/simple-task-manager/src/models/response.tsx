import ErrorDetails from "./errordetails";

export default class Response {
    public status: string;
    public data: any;
    public errors: ErrorDetails[];

    constructor(status: string, data: any, errors: ErrorDetails[]) {
        this.status = status;
        this.data = data;
        this.errors = errors;
    }

}