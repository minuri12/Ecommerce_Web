<%@page import="com.mycompany.mycart.entities.User" %>

<%
User user = (User) session.getAttribute("current_user");
%>

<nav class="navbar navbar-expand-lg bg-body-tertiary custom-navbar" style="border-bottom: 1px black solid">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Nexo</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#">Home</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Categories
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="#">Action</a></li>
            <li><a class="dropdown-item" href="#">Another action</a></li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" href="#">Something else here</a></li>
          </ul>
        </li>
      </ul>
      <ul class="navbar-nav">
        <% if (user == null) { %>
          <li class="nav-item">
            <a class="nav-link" href="login.jsp">Login</a>
          </li>
        <% } else { %>
          <li class="nav-item">
            <a class="nav-link" href="LogOutServlet">Logout</a> <!-- Assuming logout.jsp handles session invalidation -->
          </li>
          
        <% } %>
      </ul>
    </div>
  </div>
</nav>