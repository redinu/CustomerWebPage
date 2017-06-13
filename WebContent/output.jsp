<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>Customer Number: ${customer.customerId}</p>
<p>${customer.title} ${customer.firstName} ${customer.lastName}</p>
<p>${customer.streetAddress}</p>
<p>${customer.city}, ${customer.state} ${customer.zipcode}</p>
<p>${customer.emailAddress}</p>
<p>${customer.position} at ${customer.company}</p>



</body>
</html>

