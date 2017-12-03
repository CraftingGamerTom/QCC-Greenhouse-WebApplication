<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ include file="common/header.jspf"%>

</head>

<!--
*
*  INSPINIA - Responsive Admin Theme
*  version 2.7
*
-->
<body class="top-navigation grey-bg">
    <div id="wrapper">
        <div id="page-wrapper" class="gray-bg">
        
		<!-- VERTICAL NAVIGATION -->
				<!-- removed due to style conflict -->
		<!-- END VERTICAL NAVIGATION -->
        
		<!-- HORIZONTAL NAVIGATION -->
		<%@ include file="common/nav-horizontal.jspf"%>
		<!-- END HORIZONTAL NAVIGATION -->

		<div class="container">
			Application has encountered an error. Please contact support
			<br>This page should not be shown. An updated error page was created.
		</div>


		</div>
		
               
  	</div>

		<!-- TASKS NAVIGATION CONTAINER -->
		<%@ include file="common/tasks.jspf"%>
		<!-- END TASKS NAVIGATION CONTAINER -->


<%@ include file="common/footer.jspf"%>

    <!-- Mainly scripts -->
    <script src="<c:url value="/resources/js/jquery-3.1.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/plugins/metisMenu/jquery.metisMenu.js"/>"></script>
    <script src="<c:url value="/resources/js/plugins/slimscroll/jquery.slimscroll.min.js"/>"></script>
    
    <!-- Custom and plugin javascript -->
    <script src="<c:url value="/resources/js/inspinia.js"/>"></script>
    <script src="<c:url value="/resources/js/plugins/pace/pace.min.js"/>"></script>
    
    
