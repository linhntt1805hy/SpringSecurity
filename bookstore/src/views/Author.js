import React, { Component } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import { Link } from 'react-router-dom/cjs/react-router-dom';

class Author extends Component {
    constructor(props) {
        super(props);
        this.state = {
            authors: [],
            error: '',
            isLibrarian: false,
            newAuthor: { fullName: '', code: '', biography: '' }
        };
    }

    componentDidMount() {
        this.fetchAuthors();
    }

    fetchAuthors = async () => {
        try {
            const token = localStorage.getItem('token');

            // Kiểm tra quyền từ token
            if (token) {
                const decodedToken = jwtDecode(token);
                this.setState({ isLibrarian: decodedToken.role.includes('Librarian') });
            }

            const response = await axios.get('http://localhost:9090/api/authors', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            this.setState({ authors: response.data });
        } catch (error) {
            if (error.response && error.response.data) {
                this.setState({
                    error: error.response.data.message
                });
            } else {
                this.setState({ error: 'Failed to fetch authors. Please try again later.' });
            }
        }
    }

    handleInputChange = (e) => {
        const { name, value } = e.target;
        this.setState(prevState => ({
            newAuthor: {
                ...prevState.newAuthor,
                [name]: value
            }
        }));
    }

    handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            await axios.post('http://localhost:9090/api/authors', this.state.newAuthor, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            // Refresh authors list
            const response = await axios.get('http://localhost:9090/api/authors', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            this.setState({
                authors: response.data,
                newAuthor: { fullName: '', code: '', biography: '' }
            });
        } catch (err) {
            this.setState({ error: 'Failed to create author. Please try again later.' });
        }
    }

    render() {
        const { authors, isLibrarian, error, newAuthor } = this.state;

        return (
            <>
                <div className="container">
                    <h2>Authors</h2>
                    {error && <div className="alert alert-danger">{error}</div>}

                    <div className="row">
                        {isLibrarian && (
                            <div className="col-md-4">
                                <h3 className="mb-4">Create New Author</h3>
                                <form onSubmit={this.handleSubmit}>
                                    <div className="mb-3">
                                        <label htmlFor="code" className="form-label">Code:</label>
                                        <input
                                            type="text"
                                            id="code"
                                            name="code"
                                            className="form-control"
                                            value={newAuthor.code}
                                            onChange={this.handleInputChange}
                                            required
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="fullName" className="form-label">Full Name:</label>
                                        <input
                                            type="text"
                                            id="fullName"
                                            name="fullName"
                                            className="form-control"
                                            value={newAuthor.fullName}
                                            onChange={this.handleInputChange}
                                            required
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="biography" className="form-label">Biography:</label>
                                        <input
                                            type="text"
                                            id="biography"
                                            name="biography"
                                            className="form-control"
                                            value={newAuthor.biography}
                                            onChange={this.handleInputChange}
                                        />
                                    </div>
                                    <button type="submit" className="btn btn-primary">Create Author</button>
                                </form>
                            </div>
                        )}

                        <div className={isLibrarian ? "col-md-8" : "col-md-12"}>
                            <div className="table-responsive">
                                <table className="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Code</th>
                                            <th>Biography</th>
                                            {/* <th>Books</th> */}
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {authors.length > 0 ? (
                                            authors.map((author) => (
                                                <tr key={author.code}>
                                                    <td>{author.fullName}</td>
                                                    <td>{author.code}</td>
                                                    <td>{author.biography}</td>
                                                    {/* <td>{author.books ? author.books.join(', ') : 'No books available'}</td> */}
                                                </tr>
                                            ))
                                        ) : (
                                            <tr>
                                                <td colSpan="4">No authors available</td>
                                            </tr>
                                        )}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <Link to="/books">Go to Books</Link>
                    <br />
                    <Link to="/logout">Logout</Link>
                </div>

            </>
        );
    }
}

export default Author;