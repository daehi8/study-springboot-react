import React from "react";
import { Container } from "react-bootstrap";
import { Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Home from "./pages/book/Home";
import SaveForm from "./pages/book/SaveForm";
import Detail from "./pages/book/Detail";
import LoginForm from "./pages/user/LoginForm";
import ModifyForm from "./pages/book/ModifyForm";
import JoinForm from "./pages/user/JoinForm";

function App() {
  return (
    <div>
      <Header />
      <Container>
        <Routes>
          <Route path="/" exact={true} component={Home} />
          <Route path="/saveForm" exact={true} component={SaveForm} />
          <Route path="/book/:id" exact={true} component={Detail} />
          <Route path="/loginForm" exact={true} component={LoginForm} />
          <Route path="/joinForm" exact={true} component={JoinForm} />
          <Route path="/modifyForm" exact={true} component={ModifyForm} />
        </Routes>
      </Container>
    </div>
  );
}

export default App;
