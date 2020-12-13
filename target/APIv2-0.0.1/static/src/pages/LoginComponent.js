export const LoginComponent = () => `
<div id="login-background-img">

        <section id="room-form-main">
            <div class="form-wrap">
                <h1>Login</h1>
                <p>Type in your ID and password</p>
                <form>
                    <div class="form-group">
                        <!-- User name input -->
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username">
                    </div>
                    <div class="form-group">
                        <!-- User name input -->
                        <label for="password">Password</label>
                        <input type="text" id="password" name="password">
                    </div>

                    <button id="user-submit" class="btn">Login</button>
                </form>
            </div>
        </section>
    </div>
`;