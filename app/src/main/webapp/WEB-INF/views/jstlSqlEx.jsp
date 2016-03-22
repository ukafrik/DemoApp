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
  	<!-- http://www.codejava.net/java-ee/jstl/jstl-sql-tag-dateparam -->
    <h1>&lt;sql:dateParam&gt; Demo</h1>
    <form name="citizenDOBForm"
    action="${pageContext.request.contextPath}/demo2.jsp"
        method="POST">
    <table border="0">           
      <tr>
        <td>* Enter Name:</td>
        <td><input type="text" name="name" />
      </tr>
      <tr>
        <td>* Enter Salary:</td>
        <td><input type="text" name="salary" />
      </tr>
      <tr>
        <td>* Enter Date Joined: (yyyy/MM/dd)</td>
        <td><input type="text" name="joinDate" /></td>
      </tr>           
      <tr>
        <td>* Enter SSN:</td>
        <td><input type="text" name="ssn" />
      </tr>
      <tr>
        <td><input type="submit" value="Submit"/></td>
      </tr>
    </table>       
     </form>
     <br/><br/>
     
    <fmt:parseDate value="${param.joinDate}" var="joinDate" pattern="yyyy/MM/dd" />
     
     <!-- 
    <sql:setDataSource var="myDS" driver="com.mysql.jdbc.Driver"
        url="jdbc:mysql://localhost:3306/test" user="root" password="" />
      -->
         
    <sql:setDataSource var="myDS" dataSource="jdbc/macMySqlDs"/>
         
    <c:choose>
    <c:when test="${not empty param.name && not empty param.ssn
                      && not empty param.salary
                      && not empty param.joinDate}">       
        <sql:update dataSource="${myDS}" var="empTbl">
            INSERT INTO employee (name, salary, ssn, joining_date) VALUES (?, ?, ?, ?)           
            <sql:param value="${param.name}" />
            <sql:param value="${param.salary}" />
            <sql:param value="${param.ssn}" />
            <sql:dateParam value="${joinDate}" type="DATE"/>           
        </sql:update>
    </c:when>
    <c:otherwise>
        <font color="#cc0000">Please enter mandatory information.</font>
    </c:otherwise>
    </c:choose>
     
    <br/><br/>
    <sql:query dataSource="${myDS}" var="emps">
        SELECT * from employee;
    </sql:query>
    <table border="1">
        <c:forEach var="row" items="${emps.rows}">
            <tr>               
                <td><c:out value="${row.name}" /></td>
                <td><c:out value="${row.salary}" /></td>
                <td><c:out value="${row.joining_date}" /></td>               
            </tr>
        </c:forEach>
    </table>
    </body>
</html>