export default class ErrorDetails {
    public code: string;
    public message: string;
    public target: string;

    constructor(code: string, message: string, target: string) {
        this.code = code;
        this.message = message;
        this.target = target;
    }

}