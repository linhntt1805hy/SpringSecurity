import React, { Component } from 'react';
import axios from 'axios';
import { withRouter } from 'react-router-dom';

class Logout extends Component {
    handleLogout = async () => {
        try {
            // Lấy token từ localStorage
            const token = localStorage.getItem('token');

            if (token) {
                // Gọi API logout
                // await axios.post('http://localhost:9090/api/logout', token, {
                //     headers: {
                //         'Content-Type': 'text/plain',
                //         'Authorization': `Bearer ${token}`
                //     }
                // });

                // Xóa token khỏi localStorage
                localStorage.removeItem('token');
                localStorage.removeItem('expiresIn');

                // Điều hướng người dùng đến trang đăng nhập
                this.props.history.push('/login');
            }
        } catch (error) {
            console.error('Logout failed:', error);
        }
    };

    componentDidMount() {
        this.handleLogout();
    }

    render() {
        return (
            <div>
                <p>Logging out...</p>
            </div>
        );
    }
}

export default withRouter(Logout);
