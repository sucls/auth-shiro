<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <title>出错了</title>

    <link rel='stylesheet' href='<%=request.getContextPath()%>/webjars/bootstrap/css/bootstrap.min.css'/>

    <!-- Favicon -->
    <link rel="icon" href="<%=request.getContextPath()%>/assets/img/brand/favicon.png" type="image/png">
    <!-- Font Awesome -->
    <!-- Quick CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/quick-website.css" id="stylesheet">

    <style>
        .docs .main-content {
            display: flex;
            min-width: 0;
            max-width: 100%;
            height: 100vh;
            margin: 0;
            flex: 1 1 auto;
            padding: 0 200px;
        }

        .container-docs {
          display: flex;
          flex: 1;
          align-items: stretch;
        }

        .docs-content {
          margin-top: 80px;
          padding: 0 100px;
        }

        .docs-title {
          padding: 2.5rem;
          margin: 2.5rem 0px;
          border: 2px solid #e2e8f0;
          border-radius: 0.5rem;
        }
    </style>
  </head>

  <body class="docs loaded">

    <div class="main-content row position-relative pb-5">
      <div class="col-xl-12 docs-content pb-5">
        <!-- Docs title -->
        <div class="docs-title">
          <h1>
            <span class="avatar bg-primary text-white">5</span>
            <span class="avatar bg-warning text-white">0</span>
            <span class="avatar bg-dark text-white">0</span>
          </h1>
          <p class="lead mb-0"> 系统故障。 </p>
        </div>
        <!-- Docs content -->
      </div>
    </div>

  </body>

</html>
