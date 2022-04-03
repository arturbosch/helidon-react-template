import { FormEvent, useState } from "react";
import "./App.css";
import { InputField } from "./components/InputField";
import { Todo } from "./model";
import { TodoList } from "./components/TodoList";

export function App() {
    const [todo, setTodo] = useState<string>("");
    const [todos, setTodos] = useState<Todo[]>([]);
    const handleSubmit = (event: FormEvent) => {
        event.preventDefault();

        if (todo) {
            setTodos([...todos, { id: Date.now(), todo, isDone: false }]);
            setTodo("");
        }
    };
    return (
        <div className="app">
            <span className="heading">Taskify</span>
            <InputField todo={todo} setTodo={setTodo} onSubmit={handleSubmit} />
            <TodoList todos={todos} setTodos={setTodos} />
        </div>
    );
}
