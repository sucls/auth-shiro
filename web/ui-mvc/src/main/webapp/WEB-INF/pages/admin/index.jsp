<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="Webpixels">
  <title>ADMIN</title>
  <!-- Preloader -->

  <!-- Favicon -->
  <link rel="icon" href="<%=request.getContextPath()%>/assets/img/brand/favicon.png" type="image/png"><!-- Font Awesome -->
<%--  <link rel="stylesheet" href="assets/libs/@fortawesome/fontawesome-free/css/all.min.css">--%>
  <!-- Quick CSS -->
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/quick-website.css" id="stylesheet">

  <style>
      .sidenav {
          position: fixed;
          top: 0;
          height: 100vh;
          overflow: auto;
          margin-bottom: 0;
          border: 0;
          z-index: 900;
          border-radius: 0;
          transition: all 0.2s ease;
          width: 180px;
      }
      .main-content {
          display: flex;
          flex: 1 1 auto;
          padding: 0;
          margin: 0;
          min-width: 0;
          max-width: 100%;
          height: 100vh;
      }
  </style>
</head>

<body>

  <header class="header" id="header-main">
    <!-- Main navbar -->
    <nav class="navbar navbar-main navbar-expand-lg fixed-top navbar-shadow navbar-light bg-white border-bottom" id="navbar-main">
      <div class="container-fluid justify-content-between">
        <!-- Navbar brand -->
        <a class="navbar-brand" href="/index.html">
          <img alt="Image placeholder" src="<%=request.getContextPath()%>/assets/img/brand/dark.svg" id="navbar-logo">
          <sup class="text-muted text-xs text-uppercase">Docs</sup>
        </a>
        <!-- Navbar nav -->
        <div class="collapse navbar-collapse" id="navbar-main-collapse">
          <!-- Right menu -->
          <ul class="navbar-nav align-items-center mx-auto">
            <li class="nav-item">
              <a class="nav-link" href="">面板</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="">系统管理</a>
            </li>
          </ul>
        </div>
        <div class="dropdown">
          <button class="btn btn-primary dropdown-toggle btn-sm " type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Admin
          </button>
          <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <a class="dropdown-item" href="#">Action</a>
            <a class="dropdown-item" href="#">Another action</a>
            <a class="dropdown-item" href="#">Something else here</a>
          </div>
        </div>
      </div>
    </nav>
  </header>
  <div class="container-fluid container-docs">
    <!-- Sidenav -->
    <nav class="sidenav navbar navbar-vertical navbar-expand-xs navbar-light bg-white" id="sidenav-main">
      <div class="scrollbar-inner px-4">
        <!-- Navigation -->
        <div class="docs-sidebar">
          <h6 class="mt-4">面板</h6>
          <ul class="nav flex-column">
            <li class="nav-item">
              <a href="" class="nav-link">Quick start</a>
            </li>
          </ul>
          <h6 class="mt-4">系统管理</h6>
          <ul class="nav flex-column">
            <li class="nav-item">
              <a href="" class="nav-link">用户管理</a>
            </li>
            <li class="nav-item">
              <a href="" class="nav-link">角色管理</a>
            </li>
            <li class="nav-item">
              <a href="" class="nav-link">权限管理</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <!-- Main content -->
    <div class="main-content row position-relative pb-5">
      <%-- TODO --%>
        <table class="table">
          <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">First</th>
            <th scope="col">Last</th>
            <th scope="col">Handle</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <th scope="row">1</th>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
          </tr>
          <tr>
            <th scope="row">2</th>
            <td>Jacob</td>
            <td>Thornton</td>
            <td>@fat</td>
          </tr>
          <tr>
            <th scope="row">3</th>
            <td>Larry</td>
            <td>the Bird</td>
            <td>@twitter</td>
          </tr>
          </tbody>
        </table>

    </div>
  </div>

  <!-- Core JS  -->
  <script type="text/javascript" src="<%=request.getContextPath()%>/webjars/jquery/jquery.min.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/webjars/bootstrap/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/webjars/svg-injector/dist/svg-injector.min.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/webjars/feather-icons/dist/feather.min.js"></script>
  <!-- Quick JS -->
  <script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/quick-website.js"></script>

</body>

</html>
