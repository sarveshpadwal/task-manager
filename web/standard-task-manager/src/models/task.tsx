export default class Task {
    id?: string;
    title: string;
    description: string;
    status: string;

    constructor(id: string, title: string, description: string, status: string) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }
}