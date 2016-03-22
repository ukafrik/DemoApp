<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	
	<body>
	
		<sql:setDataSource var="mySqlDS" dataSource="jdbc/macMySqlDs"/>
		
		<!-- c:if test="${empty userName})" -->
		
		<sql:query dataSource="${mySqlDS}" sql="select distinct name from hiberdemo.employee;" var="userName" />
		<sql:query dataSource="${mySqlDS}" sql="select distinct ssn from hiberdemo.employee;" var="userSSN" />
		
		<!--/c:if -->
		
		<form action="#" method="post">
			<table border="0">
				<thead>
					<tr>
						<th width="200">
							Name:
							<select>
								<c:forEach items="${userName.rows}" var="row">
									<option id="userNameVal" value="${row.name}">${row.name}</option>
								</c:forEach>
							</select>
						</th>
						<th width="200">
							Salary:
							<select>
								<c:forEach items="${userSSN.rows}" var="ssn">
									<option id="userSSN" value="${ssn.ssn}">${ssn.ssn}</option>
								</c:forEach>
							</select>
						</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td><input type="submit" value="Submit"/></td>
					</tr>
				</tbody>
			</table>
		</form>
		
    	<br/><br/>
    	
		<c:choose>
		    <c:when test="${not empty param.userNameVal && not empty param.userSSN}">       
		        <sql:query dataSource="${mySqlDS}" var="users">
		        	select * from hiberdemo.employee where name=? and ssn=?
		        	<sql:param value="${param.userNameVal}" />
            		<sql:param value="${param.userSSN}" />
		        </sql:query>
		    </c:when>
		    <c:otherwise>
		        <font color="#cc0000">Name and SSN are mandatory. Please select.</font>
		    </c:otherwise>
    	</c:choose>
     
    	<br/><br/>
    	
	     <table border="1">
	        <c:forEach var="row" items="${users.rows}">
	            <tr>
	                <td><c:out value="${row.id}" /></td>
	                <td><c:out value="${row.name}" /></td>
	                <td><c:out value="${row.joining_date}" /></td>
	                <td><c:out value="${row.salary}" /></td>
	                <td><c:out value="${row.ssn}" /></td>
	            </tr>
	        </c:forEach>
	    </table>   
		
	</body>
</html>