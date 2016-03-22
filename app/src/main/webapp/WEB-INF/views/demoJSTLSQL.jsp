<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
  "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>&lt;sql:dateParam&gt; Demo</title>
  </head>
  <body>
    <h1>&lt;sql:dateParam&gt; Demo</h1>
    <form name="citizenDOBForm"
    action="${pageContext.request.contextPath}/tag-types/sql/dateparam.jsp"
        method="POST">
    <table border="0">           
      <tr>
        <td>* Enter First Name:</td>
        <td><input type="text" name="firstName" />
      </tr>
      <tr>
        <td>* Enter Last Name:</td>
        <td><input type="text" name="lastName" />
      </tr>
      <tr>
        <td>* Enter Date of Birth: (yyyy/MM/dd)</td>
        <td><input type="text" name="dateOfBirth" /></td>
      </tr>           
      <tr>
        <td><input type="submit" value="Submit"/></td>
      </tr>
    </table>       
     </form>
     <br/><br/>
     
    <fmt:parseDate value="${param.dateOfBirth}" var="parsedDateOfBirth" pattern="yyyy/MM/dd" />
     
    <sql:setDataSource var="myDS" driver="com.mysql.jdbc.Driver"
        url="jdbc:mysql://localhost:3306/test" user="root" password="" />
         
    <c:choose>
    <c:when test="${not empty param.firstName
                      && not empty param.lastName
                      && not empty param.dateOfBirth}">       
        <sql:update dataSource="${myDS}" var="citizenDOB">
            INSERT INTO citizens_dob (first_name, last_name, date_of_birth) VALUES (?, ?, ?)           
            <sql:param value="${param.firstName}" />
            <sql:param value="${param.lastName}" />
            <sql:dateParam value="${parsedDateOfBirth}" type="DATE"/>           
        </sql:update>
    </c:when>
    <c:otherwise>
        <font color="#cc0000">Please enter mandatory information.</font>
    </c:otherwise>
    </c:choose>
     
    <br/><br/>
    <sql:query dataSource="${myDS}" var="citizens">
        SELECT * from citizens_dob;
    </sql:query>
    <table border="1">
        <c:forEach var="row" items="${citizens.rows}">
            <tr>               
                <td><c:out value="${row.first_name}" /></td>
                <td><c:out value="${row.last_name}" /></td>
                <td><c:out value="${row.date_of_birth}" /></td>               
            </tr>
        </c:forEach>
    </table>
    </body>
</html>