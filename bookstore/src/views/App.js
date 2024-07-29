import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './Login';
import Register from './Register';
import Logout from './Logout';
import Author from './Author';
import Book from './Book';
import './App.scss';

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/login" component={Login} />
        <Route path="/register" component={Register} />
        <Route path="/logout" component={Logout} />
        <Route path="/authors" component={Author} />
        <Route path="/books" component={Book} />
        <Route path="/" component={Login} exact />
      </Switch>
    </Router>
  );
}

export default App;