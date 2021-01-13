<%-- 
    Document   : adminViewCollege
    Created on : Jan 13, 2021, 9:10:48 PM
    Author     : Wen Jie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ page import="bean.User" %>
<%@ page import="bean.College" %>
<%@ page import="bean.Room" %>
<%@ page import="java.util.ArrayList" %>

<c:if test="${sessionScope.user == null}">
    <c:redirect url="/notAuthorized.jsp" />   
</c:if>

<c:if test="${!sessionScope.user.getUserType().equals('admin')}">
    <c:redirect url="/notAuthorized.jsp" />   
</c:if>

<jsp:useBean id="user" class="bean.User" scope="session" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin View College</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">

        
        <style>
         .bd-placeholder-img {
           font-size: 1.125rem;
           text-anchor: middle;
           -webkit-user-select: none;
           -moz-user-select: none;
           -ms-user-select: none;
           user-select: none;
         }

         @media (min-width: 768px) {
           .bd-placeholder-img-lg {
             font-size: 3.5rem;
           }
         }
      </style>
    <sql:setDataSource
        var="collegeApplicationData"
        driver="com.mysql.jdbc.Driver"
        url="jdbc:mysql://localhost/college_application?allowMultiQueries=true"
        user="root" password=""
    />
     
    <sql:query var="listCollege"   dataSource="${collegeApplicationData}">
        SELECT * FROM college;
    </sql:query>
 <link href="css/navbar-top-fixed.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
         <div class="container">
            <a class="navbar-brand" href="#">CRAS </a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
               <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="navbarCollapse">

               <ul class="navbar-nav mr-auto">
                  <li class="nav-item ">
                    <a class="nav-link active" href="adminHome.jsp">Home </a>
                  </li>
                  <li>
                     <a class="nav-link active" href="adminViewCollege.jsp">College </a>
                  </li>
               </ul>
                
                <div class="form-inline mt-2 mt-md-0">
                   <a class="btn btn-danger my-2 my-sm-0"  href="LogoutServlet"> Logout </a>
               </div>

            </div>
         </div>
      </nav>     
        <main role="main" class="container">
            <nav aria-label="breadcrumb">
               <ol class="breadcrumb">
                   <li class="breadcrumb-item"><a href="adminHome.jsp">Home</a></li>
                  <li class="breadcrumb-item active" aria-current="page">View College</li>
               </ol>
            </nav>
            
            <div class="card">
               <div class="container px-5 pt-5">
                   <h3 class="display-6">Manage College </h3>
                <table class="table table-striped">
                    <thead class="thead-dark text-center">
                        <tr>
                            <th>No.</th>
                            <th>College Name</th>
                            <th>Address</th>
                            <th>Added Date</th>
                            <th>Operation</th>
                            <th>Room</th>
                        </tr>
                    </thead>
                    <tbody class="text-center">
                        <c:set var="i" value="${1}"/>
                        <c:forEach items="${listCollege.rows}" var="college">
                        <tr>
                            <td><c:out value="${i}" /></td>
                            <td><c:out value="${college.collegeName}" /></td>
                            <td><c:out value="${college.address}" /></td>
                            <td><fmt:formatDate value="${college.addedDate}" pattern="dd-MM-yyyy" /></td>
                            <td><a href="adminEditCollege.jsp?cid=<c:out value="${college.collegeID}"/>">Update</a><br/>
                                <a href="AdminDeleteCollegeServlet?cid=<c:out value="${college.collegeID}"/>">Delete</a>
                            </td>
                            <td><a href="adminViewRoom.jsp?cid=<c:out value="${college.collegeID}"/>">View Room</a></td>
                        </tr>
                        <c:set var="i" value="${i+1}"/>
                        </c:forEach>
                    </tbody>
                </table>
                  <div class="col text-center" style='padding-bottom: 20px;'>
                  <button class="btn btn-dark" onclick="document.location='adminInsertCollege.jsp'" class="btn btn-primary align-content-center">New College</button>
                  </div>
               </div>
            </div>  
           
        </main>

      
      <script src="js/jquery-3.5.1.min.js"></script>
      <script src="js/popper.min.js"></script>      
      <script src="js/bootstrap.min.js"></script>
    </body>
</html>

