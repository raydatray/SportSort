<script>
    import Logo from "../../assets/logo.png";
    import Character from "../../assets/characters.png";

    let errorMsg = "";
    let email = "";
    let name = "";
    let password = "";
    let confirmPassword = "";

  function createAccount() {
      const emailRegex = /^[a-z]{3,}@[a-z]+\.[a-z]+$/;
      const lengthRegex = /^.{8,20}$/;
      const uppercaseRegex = /[A-Z]/;
      const lowercaseRegex = /[a-z]/;
      const numberRegex = /\d/;
      const specialCharRegex = /[!@#$%^&*()_+]/;

      if (email.trim() === "" || name.trim() === "" || password.trim() === "" || confirmPassword.trim() === "") {
          errorMsg = "Please fill in empty fields!"
          return;
      } else if (password !== confirmPassword) {
          errorMsg = "Passwords have to match!";
          return;
      } else if (!emailRegex.test(email)) {
          errorMsg = "The email provided is invalid!";
          return;
      } else if (!lengthRegex.test(password)) {
          errorMsg = "The password must be between 8 and 20 characters long!";
          return;
      } else if (!uppercaseRegex.test(password)) {
          errorMsg = "The password must contain an upper case letter!";
          return;
      } else if (!lowercaseRegex.test(password)) {
          errorMsg = "The password must contain a lower case letter!";
          return;
      } else if (!numberRegex.test(password)) {
          errorMsg = "The password must contain a number!";
          return;
      } else if (!specialCharRegex.test(password)) {
          errorMsg = "The password must contain a special character!";
          return;
      }

      console.log("Email:", email);
      console.log("Name:", name);
      console.log("Password:", password);
      console.log("Confirm Password:", confirmPassword);
  }
</script>

<div class="h-full create-account-page bg-secondary">
  <div class="page-title">
    <h1>CREATE AN ACCOUNT</h1>
  </div>
  <div class="page-content bg-base-100">
    <!-- Form Section -->
    <div class="create-account-form">
      <label class="flex items-center gap-2 input input-bordered">
        <svg  xmlns="http://www.w3.org/2000/svg"  width="24"  height="24"  viewBox="0 0 24 24"  fill="none"  stroke="currentColor"  stroke-width="2"  stroke-linecap="round"  stroke-linejoin="round"  class="icon icon-tabler icons-tabler-outline icon-tabler-mail"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M3 7a2 2 0 0 1 2 -2h14a2 2 0 0 1 2 2v10a2 2 0 0 1 -2 2h-14a2 2 0 0 1 -2 -2v-10z" /><path d="M3 7l9 6l9 -6" /></svg>
        <input type="text" class="grow" placeholder="Email" bind:value={email} />
      </label>
      <label class="flex items-center gap-2 input input-bordered">
        <svg  xmlns="http://www.w3.org/2000/svg"  width="24"  height="24"  viewBox="0 0 24 24"  fill="none"  stroke="currentColor"  stroke-width="2"  stroke-linecap="round"  stroke-linejoin="round"  class="icon icon-tabler icons-tabler-outline icon-tabler-user"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M8 7a4 4 0 1 0 8 0a4 4 0 0 0 -8 0" /><path d="M6 21v-2a4 4 0 0 1 4 -4h4a4 4 0 0 1 4 4v2" /></svg>
        <input type="text" class="grow" placeholder="Name" bind:value={name} />
      </label>
      <label class="flex items-center gap-2 input input-bordered">
        <svg  xmlns="http://www.w3.org/2000/svg"  width="24"  height="24"  viewBox="0 0 24 24"  fill="none"  stroke="currentColor"  stroke-width="2"  stroke-linecap="round"  stroke-linejoin="round"  class="icon icon-tabler icons-tabler-outline icon-tabler-key"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M16.555 3.843l3.602 3.602a2.877 2.877 0 0 1 0 4.069l-2.643 2.643a2.877 2.877 0 0 1 -4.069 0l-.301 -.301l-6.558 6.558a2 2 0 0 1 -1.239 .578l-.175 .008h-1.172a1 1 0 0 1 -.993 -.883l-.007 -.117v-1.172a2 2 0 0 1 .467 -1.284l.119 -.13l.414 -.414h2v-2h2v-2l2.144 -2.144l-.301 -.301a2.877 2.877 0 0 1 0 -4.069l2.643 -2.643a2.877 2.877 0 0 1 4.069 0z" /><path d="M15 9h.01" /></svg>
        <input type="password" class="grow" placeholder="Password" bind:value={password} />
      </label>
      <label class="flex items-center gap-2 input input-bordered">
        <svg  xmlns="http://www.w3.org/2000/svg"  width="24"  height="24"  viewBox="0 0 24 24"  fill="none"  stroke="currentColor"  stroke-width="2"  stroke-linecap="round"  stroke-linejoin="round"  class="icon icon-tabler icons-tabler-outline icon-tabler-key"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M16.555 3.843l3.602 3.602a2.877 2.877 0 0 1 0 4.069l-2.643 2.643a2.877 2.877 0 0 1 -4.069 0l-.301 -.301l-6.558 6.558a2 2 0 0 1 -1.239 .578l-.175 .008h-1.172a1 1 0 0 1 -.993 -.883l-.007 -.117v-1.172a2 2 0 0 1 .467 -1.284l.119 -.13l.414 -.414h2v-2h2v-2l2.144 -2.144l-.301 -.301a2.877 2.877 0 0 1 0 -4.069l2.643 -2.643a2.877 2.877 0 0 1 4.069 0z" /><path d="M15 9h.01" /></svg>
        <input type="password" class="grow" placeholder="Confirm password" bind:value={confirmPassword} />
      </label>
      <button class="btn bg-accent" on:click={createAccount}>Create Account</button>
      <p class="error-msg">{errorMsg}</p>
      <p class="sign-in-footer">Already have an account? <a href="/login" class="underline text-blue">Sign in</a></p>
    </div>
    <!-- Image/Logo Section -->
    <div class="image bg-base-100">
      <img class="char" src={Character} alt="Logo"/>
      <img class="logo" src={Logo} alt="Logo"/>
    </div>
  </div>
</div>

<style>
  /* Add any extra styles here */
  h1 {
      font-size: 3rem;
      margin-bottom: 2rem;
      letter-spacing: 0.2rem;
      font-weight: bold;
  }

  button {
      margin-top: 2.5rem;
      width: 60%;
      border-radius: 10px;
  }

  label {
      width: 100%;
      border-radius: 10px;
  }

  .create-account-page {
      border-radius: 10px;
      height: 100%;
      width: 100%;
      margin-bottom: 0.75%;
      padding: 2% 3%;
      display: flex;
      align-items: flex-start;
      justify-content: center;
      flex-direction: column;
  }

  .page-title {
      height: 20%;
      margin-bottom: -3%;
  }

  .page-content {
      display: flex;
      justify-content: center;
      align-content: center;
      width: 100%;
      height: 80%;
      border-radius: 10px;
  }

  .create-account-form {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      gap: 1rem;
      padding: 3rem;
      width: 60%;
  }

  .error-msg {
      color: #ef1414;
      height: 3%;
  }

  .image {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      gap: 20px;
      border-radius: 10px;
  }

  .logo {
      max-height: 20px;
      object-fit: cover;
  }

  .char {
      max-height: 400px;
  }

  .sign-in-footer {
      margin-top: 1rem;
  }

  .underline {
      text-decoration: underline;
  }
</style>