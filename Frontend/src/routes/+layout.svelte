<script>
  import "../app.css";
</script>

<div data-theme="nord" class="container">
  <ul class="menu menu-horizontal md:menu-vertical bg-base-200 rounded-box">
      <li><a>HOME</a></li>
      <li><a>ABOUT</a></li>
      <li><a>Contact</a></li>
  </ul>
  <div class="content">
      <slot></slot>
  </div>
</div>

<footer class="footer footer-center p-4 bg-base-300 text-base-content">
  <aside>
      <p>Copyright © 2024 - All right reserved by ACME Industries Ltd</p>
  </aside>
</footer>

<style lang="postcss">
  body, html {
      margin: 0;
      padding: 0;
      overflow-x: hidden; 
  }

  .container {
      display: grid;
      grid-template-columns: auto 1fr;
      min-height: 100vh; 
      overflow: hidden; 
  }

  .menu {
      grid-column: 1 / 2;
  }

  .content {
      grid-column: 2 / 3;
      padding: 0;
      margin: 0;
  }

  .footer {
      width: 100%; /* Full width */
      position: fixed; /* Fixed at the bottom */
      bottom: 0;
  }

  @media (max-width: 768px) {
      .container {
          grid-template-columns: 1fr; /* Full width column */
          grid-template-rows: auto 1fr auto; /* Specify rows for content and menu */
      }

      .menu {
          grid-row: 3; /* Position menu at the bottom */
          grid-column: 1 / -1; /* Stretch across all columns */
      }

      .content {
          grid-row: 1 / 2; /* Content on top */
          grid-column: 1 / -1; /* Stretch across all columns */
      }

      .footer {
          position: static; /* Allow it to flow in the document */
      }
  }
</style>
