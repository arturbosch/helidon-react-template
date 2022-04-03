import {
    Dispatch,
    FormEvent,
    SetStateAction,
    useEffect,
    useRef,
    useState,
} from "react";
import { Todo } from "../model";
import { AiFillEdit, AiFillDelete } from "react-icons/ai";
import { MdDone } from "react-icons/md";
import "./styles.css";

interface Props {
    todos: Todo[];
    setTodos: Dispatch<SetStateAction<Todo[]>>;
}

type TodoIdFunc = (id: number) => void;

function SingleTodo({
    todo,
    onEdit,
    onDelete,
    onDone,
}: {
    todo: Todo;
    onEdit: (id: number, newTodo: string) => void;
    onDelete: TodoIdFunc;
    onDone: TodoIdFunc;
}) {
    const editInputRef = useRef<HTMLInputElement>(null);
    const [isEditMode, setEditMode] = useState<boolean>(false);
    const [editedTodoText, setEditedText] = useState<string>(todo.todo);

    const handleEdit = (event: FormEvent) => {
        event.preventDefault();
        onEdit(todo.id, editedTodoText);
        setEditMode(false);
    };

    useEffect(() => {
        editInputRef.current?.focus();
    }, [isEditMode]);
    return (
        <form className="todos__single" onSubmit={(event) => handleEdit(event)}>
            {isEditMode ? (
                <input
                    ref={editInputRef}
                    className="todos__single--text"
                    value={editedTodoText}
                    onChange={(event) => setEditedText(event.target.value)}
                />
            ) : todo.isDone ? (
                <s className="todos__single--text">{todo.todo}</s>
            ) : (
                <span className="todos__single--text">{todo.todo}</span>
            )}

            <div>
                <span
                    className="icon"
                    onClick={() => {
                        if (!isEditMode && !todo.isDone) {
                            setEditMode(!isEditMode);
                        }
                    }}
                >
                    <AiFillEdit />
                </span>
                <span className="icon" onClick={() => onDelete(todo.id)}>
                    <AiFillDelete />
                </span>
                <span className="icon" onClick={() => onDone(todo.id)}>
                    <MdDone />
                </span>
            </div>
        </form>
    );
}

export function TodoList({ todos, setTodos }: Props) {
    const onEdit = (id: number, newTodo: string) => {
        const newTodos = todos.map((todo) =>
            todo.id === id ? { ...todo, todo: newTodo } : todo
        );
        setTodos(newTodos);
    };
    const onDelete = (id: number) => {
        const newTodos = todos.filter((todo) => todo.id !== id);
        setTodos(newTodos);
    };
    const onDone = (id: number) => {
        const newTodos = todos.map((todo) =>
            todo.id === id ? { ...todo, isDone: !todo.isDone } : todo
        );
        setTodos(newTodos);
    };
    return (
        <div className="todos">
            {todos.map((todo) => (
                <SingleTodo
                    key={todo.id}
                    todo={todo}
                    onEdit={onEdit}
                    onDelete={onDelete}
                    onDone={onDone}
                />
            ))}
        </div>
    );
}
