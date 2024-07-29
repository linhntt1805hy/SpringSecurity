import React, { Component } from 'react';
import axios from 'axios';
import { withRouter } from 'react-router-dom'; // Import withRouter để sử dụng history

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            error: ''
        };
    }

    handleSubmit = async (event) => {
        event.preventDefault();
        const { username, password } = this.state;
        try {
            const response = await axios.post('http://localhost:9090/api/login', {
                username,
                password
            });

            const { token, expiresIn } = response.data;

            localStorage.setItem('token', token);
            localStorage.setItem('expiresIn', Date.now() + expiresIn);

            // Điều hướng đến trang authors
            this.props.history.push('/authors'); // Sử dụng history từ props
        } catch (error) {
            if (error.response && error.response.data) {
                this.setState({
                    error: error.response.data.message || 'Login failed. Please try again later.'
                });
            } else {
                this.setState({
                    error: 'Login failed. Please try again later.'
                });
            }
        }
    }

    handleChange = (event) => {
        this.setState({ [event.target.id]: event.target.value });
    }

    render() {
        const { username, password, error } = this.state;

        return (
            <>
                <section className="bg-light py-3 py-md-5">
                    <div className="container">
                        <div className="row justify-content-center">
                            <div className="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5 col-xxl-4">
                                <div className="card border border-light-subtle rounded-3 shadow-sm">
                                    <div className="card-body p-3 p-md-4 p-xl-5">
                                        <h2 className="fw-normal text-center text-secondary mb-4">Sign in to your account</h2>
                                        {error && <div className="alert alert-danger">{error}</div>}
                                        <form onSubmit={this.handleSubmit}>
                                            <div className="row gy-2 overflow-hidden">
                                                <div className="col-12">
                                                    <div className="form-floating mb-3">
                                                        <input type="text" className="form-control" id="username"
                                                            value={username}
                                                            onChange={this.handleChange} placeholder="Username" required />
                                                        <label htmlFor="username" className="form-label">Username</label>
                                                    </div>
                                                </div>
                                                <div className="col-12">
                                                    <div className="form-floating mb-3">
                                                        <input type="password" className="form-control" id="password"
                                                            value={password}
                                                            onChange={this.handleChange}
                                                            placeholder="Password" required />
                                                        <label htmlFor="password" className="form-label">Password</label>
                                                    </div>
                                                </div>
                                                <div className="col-12">
                                                    <div className="d-flex gap-2 justify-content-between">
                                                        <div className="form-check">
                                                            <input className="form-check-input" type="checkbox" value="" name="rememberMe" id="rememberMe" />
                                                            <label className="form-check-label text-secondary" htmlFor="rememberMe">
                                                                Remember me
                                                            </label>
                                                        </div>
                                                        <a href="#!" className="link-primary text-decoration-none">Forgot password?</a>
                                                    </div>
                                                </div>
                                                <div className="col-12">
                                                    <div className="d-grid my-3">
                                                        <button className="btn btn-primary btn-lg" type="submit">Log in</button>
                                                    </div>
                                                </div>
                                                <div className="col-12">
                                                    <p className="m-0 text-secondary text-center">Don't have an account? <a href="/register" className="link-primary text-decoration-none">Register</a></p>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </>
        );
    }
}

export default Login; // Export với withRouter để sử dụng history
