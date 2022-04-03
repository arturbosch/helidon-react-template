import { useEffect } from "react";
import { createRoot } from "react-dom/client";
import { App } from "./App";

function AppWithCallbackAfterRender() {
    useEffect(() => {
        console.log("rendered");
    });

    return <App />;
}

const container = document.getElementById("app")!;
const root = createRoot(container);
root.render(<AppWithCallbackAfterRender />);
