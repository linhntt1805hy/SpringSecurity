import React, { Component } from "react";
import axios from "axios";

class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
            newUser: { fullName: '', username: '', password: '' },
            error: ''
        }
    }

    handleInputChange = (e) => {
        const { id, value } = e.target;
        this.setState(prevState => ({
            newUser: {
                ...prevState.newUser,
                [id]: value
            }
        }));
    }

    handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:9090/api/register', this.state.newUser, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            this.setState({
                newUser: { fullName: '', username: '', password: '' }
            });
            this.props.history.push('/login');
        } catch (error) {
            if (error.response && error.response.data) {
                this.setState({
                    error: error.response.data.message || 'Register failed. Please try again later.'
                });
            } else {
                this.setState({
                    error: 'Register failed. Please try again later.'
                });
            }
        }
    }

    render() {
        const { newUser, error } = this.state;
        return (
            <>
                <section className="bg-light py-3 py-md-5">
                    <div className="container">
                        <div className="row justify-content-center">
                            <div className="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5 col-xxl-4">
                                <div className="card border border-light-subtle rounded-3 shadow-sm">
                                    <div className="card-body p-3 p-md-4 p-xl-5">
                                        <h2 className="fw-normal text-center text-secondary mb-4">Register</h2>
                                        {error && <div className="alert alert-danger">{error}</div>}
                                        <form onSubmit={this.handleSubmit}>
                                            <div className="row gy-2 overflow-hidden">
                                                <div className="col-12">
                                                    <div className="form-floating mb-3">
                                                        <input type="text" className="form-control" id="fullName"
                                                            value={newUser.fullName}
                                                            onChange={this.handleInputChange} placeholder="Fullname" required />
                                                        <label htmlFor="fullName" className="form-label">Fullname</label>
                                                    </div>
                                                </div>
                                                <div className="col-12">
                                                    <div className="form-floating mb-3">
                                                        <input type="text" className="form-control" id="username"
                                                            value={newUser.username}
                                                            onChange={this.handleInputChange} placeholder="Username" required />
                                                        <label htmlFor="username" className="form-label">Username</label>
                                                    </div>
                                                </div>
                                                <div className="col-12">
                                                    <div className="form-floating mb-3">
                                                        <input type="password" className="form-control" id="password"
                                                            value={newUser.password}
                                                            onChange={this.handleInputChange}
                                                            placeholder="Password" required />
                                                        <label htmlFor="password" className="form-label">Password</label>
                                                    </div>
                                                </div>
                                                <div className="col-12">
                                                    <div className="d-grid my-3">
                                                        <button className="btn btn-primary btn-lg" type="submit">Register</button>
                                                    </div>
                                                </div>
                                                <div className="col-12">
                                                    <p className="m-0 text-secondary text-center">Already have account? <a href="/login" className="link-primary text-decoration-none">Sign up</a></p>
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

export default Register;