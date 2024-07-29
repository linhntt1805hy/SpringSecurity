import React, { Component } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { Link } from "react-router-dom/cjs/react-router-dom";

class Book extends Component {
    constructor(props) {
        super(props);
        this.state = {
            books: [],
            authors: [],
            error: '',
            isLibrarian: false,
            isBorrower: false,
            newBook: { code: '', title: '', description: '', authorCodes: [] },
            selectedBook: null
        }
    }

    componentDidMount() {
        this.fetchBooks();
        this.fetchAuthors();
    }

    fetchBooks = async () => {
        try {
            const token = localStorage.getItem('token');
            if (token) {
                const decodedToken = jwtDecode(token);
                this.setState({
                    isLibrarian: decodedToken.role.includes('Librarian'),
                    isBorrower: decodedToken.role.includes('Borrower')
                });

                const response = await axios.get('http://localhost:9090/api/books', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                this.setState({ books: response.data });
            }
        } catch (error) {

        }
    }

    fetchAuthors = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get('http://localhost:9090/api/authors', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            this.setState({ authors: response.data });
        } catch (error) {
            this.setState({ error: 'Failed to fetch authors. Please try again later.' });
        }
    }

    handleInputChange = (e) => {
        const { name, value } = e.target;
        this.setState(prevState => ({
            newBook: {
                ...prevState.newBook,
                [name]: value
            }
        }));
    }

    handleSelectChange = (event) => {
        const options = event.target.options;
        const selectedValues = [];
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                selectedValues.push(options[i].value);
            }
        }
        this.setState(prevState => ({
            newBook: {
                ...prevState.newBook,
                authorCodes: selectedValues
            }
        }));
    };

    handleCheckboxChange = (title) => {
        this.setState(prevState => ({
            selectedBook: prevState.selectedBook === title ? null : title
        }));
    }

    handleBorrow = async () => {
        if (!this.state.selectedBook) return;

        try {
            const token = localStorage.getItem('token');
            await axios.post('http://localhost:9090/api/borrow', { title: this.state.selectedBook }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            alert('Book borrowed successfully!');
        } catch (error) {
            this.setState({ error: 'Failed to borrow book. Please try again later.' });
        }
    }


    handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            await axios.post('http://localhost:9090/api/books', this.state.newBook, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            // Refresh authors list
            const response = await axios.get('http://localhost:9090/api/books', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            this.setState({
                books: response.data,
                newBook: { code: '', title: '', description: '', authorCodes: [] }
            });
        } catch (err) {
            this.setState({ error: 'Failed to create author. Please try again later.' });
        }
    }

    render() {
        const { books, error, newBook, isLibrarian, isBorrower, authors, selectedBook } = this.state;
        return (
            <>
                <div className="container">
                    <h2>Books</h2>
                    {error && <div className="alert alert-danger">{error}</div>}

                    <div className="row">
                        {isLibrarian && (
                            <div className="col-md-4">
                                <h3 className="mb-4">Create New Book</h3>
                                <form onSubmit={this.handleSubmit}>
                                    <div className="mb-3">
                                        <label htmlFor="code" className="form-label">Code:</label>
                                        <input
                                            type="text"
                                            id="code"
                                            name="code"
                                            className="form-control"
                                            value={newBook.code}
                                            onChange={this.handleInputChange}
                                            required
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="title" className="form-label">Title:</label>
                                        <input
                                            type="text"
                                            id="title"
                                            name="title"
                                            className="form-control"
                                            value={newBook.title}
                                            onChange={this.handleInputChange}
                                            required
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="description" className="form-label">Description:</label>
                                        <input
                                            type="text"
                                            id="description"
                                            name="description"
                                            className="form-control"
                                            value={newBook.description}
                                            onChange={this.handleInputChange}
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="authors" className="form-label">Authors:</label>
                                        <select
                                            id="authors"
                                            name="authorCodes"
                                            className="form-select"
                                            multiple
                                            value={newBook.authorCodes}
                                            onChange={this.handleSelectChange}
                                        >
                                            {authors.map((author) => (
                                                <option key={author.code} value={author.code}>
                                                    {author.code} - {author.fullName}
                                                </option>
                                            ))}
                                        </select>
                                    </div>
                                    <button type="submit" className="btn btn-primary">Create Book</button>
                                </form>
                            </div>
                        )}

                        <div className={isLibrarian ? "col-md-8" : "col-md-12"}>
                            <div className="table-responsive">
                                <table className="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Code</th>
                                            <th>Title</th>
                                            <th>Description</th>
                                            {/* <th>Author</th> */}
                                            {isBorrower && <th>Select</th>}
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {books.length > 0 ? (
                                            books.map((book) => (
                                                <tr key={book.code}>
                                                    <td>{book.code}</td>
                                                    <td>{book.title}</td>
                                                    <td>{book.description}</td>
                                                    {/* <td>
                                                        {book.authors && book.authors.length > 0 ? (
                                                            book.authors.map((author, index) => (
                                                                <span key={index}>
                                                                    {author.code} - {author.fullName}
                                                                    {index < book.authors.length - 1 ? ', ' : ''}
                                                                </span>
                                                            ))
                                                        ) : 'No author available'}
                                                    </td> */}
                                                    {isBorrower && (
                                                        <td>
                                                            <input
                                                                type="checkbox"
                                                                checked={selectedBook === book.title}
                                                                onChange={() => this.handleCheckboxChange(book.title)}
                                                                disabled={book.quantity <= 0}
                                                            />
                                                        </td>
                                                    )}
                                                </tr>
                                            ))
                                        ) : (
                                            <tr>
                                                <td colSpan={isBorrower ? "5" : "4"}>No books available</td>
                                            </tr>
                                        )}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <Link to="/authors">Go to Authors</Link>
                    <br />
                    <Link to="/logout">Logout</Link>
                </div>

            </>
        );
    }


}

export default Book;