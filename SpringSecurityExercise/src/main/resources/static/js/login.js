document.addEventListener('DOMContentLoaded', (event) => {
    const form = document.getElementById('loginForm');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch('/api/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                const data = await response.json();
                console.log("Data token", data)
                const token = data.token;
                const expiresIn = data.expiresIn;

                // Lưu token và thời gian hết hạn vào localStorage
                localStorage.setItem('token', token);
                localStorage.setItem('expiresIn', Date.now() + expiresIn);

                console.log('Login successful!');

                window.location.href = '/api/author';
            } else {
                // Xử lý khi đăng nhập thất bại
                const error = await response.json();
                console.log(`Login failed: ${error.message}`);
                alert(`Login failed: ${error.message}`);
            }
        } catch (error) {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        }
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            // Đăng nhập
            const loginResponse = await fetch('/api/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            if (loginResponse.ok) {
                // Lưu token vào localStorage
                const data = await loginResponse.json();
                console.log("Data token", data)
                const token = data.token;
                const expiresIn = data.expiresIn;
                localStorage.setItem('token', token);

                // Gọi /api/authors
                const authorsResponse = await fetch('/api/authors', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });

                if (authorsResponse.ok) {
                    const authorsData = await authorsResponse.json();
                    console.log('Authors:', authorsData);

                    // Xử lý dữ liệu nếu cần

                    // Điều hướng bằng thẻ <a>
                    const redirectLink = document.getElementById('redirectLink');
                    redirectLink.click();
                    // Chuyển trang
                    // window.location.href = 'view/author'; // Hoặc trang mà bạn muốn điều hướng tới
                } else {
                    const error = await authorsResponse.json();
                    console.log(`Failed to fetch authors: ${error.message}`);
                }
            } else {
                const error = await loginResponse.json();
                console.log(`Login failed: ${error.message}`);
            }
        } catch (error) {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        }
    });
});
