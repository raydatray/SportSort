<script>
    function login() {
        console.log("Clicked");
    }
</script>

<div class="login-page" data-theme="nord">
    <div class="login-form">
        <h1>
            LOGIN
        </h1>
        <label class="input input-bordered flex items-center gap-2">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4 opacity-70"><path d="M2.5 3A1.5 1.5 0 0 0 1 4.5v.793c.026.009.051.02.076.032L7.674 8.51c.206.1.446.1.652 0l6.598-3.185A.755.755 0 0 1 15 5.293V4.5A1.5 1.5 0 0 0 13.5 3h-11Z" /><path d="M15 6.954 8.978 9.86a2.25 2.25 0 0 1-1.956 0L1 6.954V11.5A1.5 1.5 0 0 0 2.5 13h11a1.5 1.5 0 0 0 1.5-1.5V6.954Z" /></svg>
            <input type="text" class="grow" placeholder="Email" />
        </label>
        <label class="input input-bordered flex items-center gap-2">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4 opacity-70"><path fill-rule="evenodd" d="M14 6a4 4 0 0 1-4.899 3.899l-1.955 1.955a.5.5 0 0 1-.353.146H5v1.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-2.293a.5.5 0 0 1 .146-.353l3.955-3.955A4 4 0 1 1 14 6Zm-4-2a.75.75 0 0 0 0 1.5.5.5 0 0 1 .5.5.75.75 0 0 0 1.5 0 2 2 0 0 0-2-2Z" clip-rule="evenodd" /></svg>
            <input type="password" class="grow" placeholder="Password" />
        </label>
        <label class="input input-bordered flex items-center gap-2">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4 opacity-70"><path fill-rule="evenodd" d="M14 6a4 4 0 0 1-4.899 3.899l-1.955 1.955a.5.5 0 0 1-.353.146H5v1.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-2.293a.5.5 0 0 1 .146-.353l3.955-3.955A4 4 0 1 1 14 6Zm-4-2a.75.75 0 0 0 0 1.5.5.5 0 0 1 .5.5.75.75 0 0 0 1.5 0 2 2 0 0 0-2-2Z" clip-rule="evenodd" /></svg>
            <input type="password" class="grow" placeholder="Password" />
            </label>
        <button class="btn" on:click={login}>Login</button>
        <p class="sign-up-footer">Don't have an account? <a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ" class="underline">Sign up</a></p>
    </div>
</div>

<style>
    /* Add any extra styles here */
    h1 {
        font-size: 42px;
        margin-bottom: 50px;
        letter-spacing: 3px;
        font-weight: bold;
    }

    button {
        width: 100%;
    }

    label {
        width: 100%;
    }

    .login-page {
        width: 100%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .login-form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        gap: 22px;
        padding: 50px;
    }

    .sign-up-footer {
        margin-top: 50px;
    }
</style>