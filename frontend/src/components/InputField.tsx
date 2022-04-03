import { FormEvent, useRef } from "react";
import "./styles.css";

interface Props {
    todo: string;
    setTodo: (todo: string) => void;
    onSubmit: (event: FormEvent) => void;
}

export function InputField({ todo, setTodo, onSubmit }: Props) {
    const inputRef = useRef<HTMLInputElement>(null);
    return (
        <form
            className="input"
            onSubmit={(event) => {
                onSubmit(event);
                inputRef.current?.blur();
            }}
        >
            <input
                ref={inputRef}
                type="input"
                placeholder="Enter a task"
                className="input__box"
                value={todo}
                onChange={(event) => setTodo(event.target.value)}
            />
            <button className="input__submit" type="submit">
                Go
            </button>
        </form>
    );
}
