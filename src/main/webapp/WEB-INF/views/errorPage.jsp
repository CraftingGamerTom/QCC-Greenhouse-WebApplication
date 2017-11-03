<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>

<!-- Head starts in header.jspf -->
<%@ include file="common/header.jspf"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<!-- END Head -->


<!--
*
*  INSPINIA - Responsive Admin Theme
*  version 2.7
*
-->
<body class="top-navigation">
    <div id="wrapper">
        <div id="page-wrapper" class="gray-bg">
        
		<!-- VERTICAL NAVIGATION -->
				<!-- removed due to style conflict -->
		<!-- END VERTICAL NAVIGATION -->
        
		<!-- HORIZONTAL NAVIGATION -->
		<%@ include file="common/nav-horizontal.jspf"%>
		<!-- END HORIZONTAL NAVIGATION -->

    	<%=request.getAttribute("error-message")%>
  

		
		</div>

               
  	</div>

		<!-- TASKS NAVIGATION CONTAINER -->
		<%@ include file="common/tasks.jspf"%>
		<!-- END TASKS NAVIGATION CONTAINER -->
		
		<!-- FOOTER -->
		<%@ include file="common/footer.jspf"%>
		<!-- END FOOTER -->
		
	<!-- Mainly scripts -->
	<script src="<c:url value="../../../../resources/js/jquery-3.1.1.min.js"/>"></script>
    <script src="<c:url value="../../../../resources/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="../../../../resources/js/plugins/metisMenu/jquery.metisMenu.js"/>"></script>
    <script src="<c:url value="../../../../resources/js/plugins/slimscroll/jquery.slimscroll.min.js"/>"></script>
	

</body>
</html>