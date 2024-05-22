import * as React from "react";

interface Props {
    options: string[];
    name: string;
    label: string;
    value: string;
    onChange: (fieldName: string, value: string) => void;
    error?: string;
}

export const Select: React.FunctionComponent<Props> = (props) => {
    return (
        <div className={formatWrapperClass(props)}>
            <label htmlFor={props.name}>{props.label}</label>
            <div className="field">
                <select
                        name={props.name}
                        className="form-control"
                        value={props.value}
                        onChange={onChangeInput(props)}
                >
                {props.options.map(
                    (option: string) => {
                        return (
                            <option value={option}>
                                {option}
                            </option>
                        );
                    }
                )}
                </select>
            </div>
            <div className="help-block">{props.error}</div>
        </div>
    )
};

const formatWrapperClass = (props: Props) => {
    const wrapperClass = 'form-group';

    return props.error ?
        `${wrapperClass} has-error` :
        wrapperClass;
};

const onChangeInput = (props: Props) => (e: React.ChangeEvent<HTMLSelectElement>) => {
    props.onChange(e.target.name, e.target.value);
};
