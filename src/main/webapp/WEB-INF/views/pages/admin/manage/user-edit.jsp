
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Head starts in header.jspf -->
<%@ include file="../../../common/header.jspf"%>

<link href="<c:url value="/resources/css/animate.css"/>"
	rel="stylesheet">
<link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">

<!-- Select 2 -->
    <link href="<c:url value="/resources/css/plugins/select2/select2.min.css"/>" rel="stylesheet">


<!-- Sweet Alert -->
<link
	href="<c:url value="/resources/css/plugins/sweetalert/sweetalert.css"/>"
	rel="stylesheet">
	

    <link href="<c:url value="/resources/css/animate.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
	

</head>


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
			<%@ include file="../../../common/nav-horizontal.jspf"%>
			<!-- END HORIZONTAL NAVIGATION -->

			<!-- USER-DATA CONTAINER -->
			<%@ include file="../../../content/manager/manage/user-metadata.jspf"%>
			<!-- END USER-DATA CONTAINER -->

			<!-- FOOTER -->
			<%@ include file="../../../common/footer.jspf"%>
			<!-- END FOOTER -->

		</div>


	</div>

	<!-- TASKS NAVIGATION CONTAINER -->
	<%@ include file="../../../common/tasks.jspf"%>
	<!-- END TASKS NAVIGATION CONTAINER -->



	<!-- Mainly scripts -->
	<script src="<c:url value="/resources/js/jquery-3.1.1.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

	<!-- Custom and plugin javascript -->
	<script src="<c:url value="/resources/js/inspinia.js"/>"></script>
	<script src="<c:url value="/resources/js/plugins/pace/pace.min.js"/>"></script>

	<!-- Sweet alert -->
	<script
		src="<c:url value="/resources/js/plugins/sweetalert/sweetalert.min.js"/>"></script>

	<!-- Custom and plugin javascript -->
	<script
		src="<c:url value="/resources/js/plugins/slimscroll/jquery.slimscroll.min.js"/>"></script>

	<!-- Input Mask-->
	<script src="<c:url value="/resources/js/plugins/jasny/jasny-bootstrap.min.js"/>"></script>

    <!-- Select2 -->
    <script src="<c:url value="/resources/js/plugins/select2/select2.full.min.js"/>"></script>

	<!-- Puts the current authority level in as a place marker -->
	<script>
    $(".auth_level_select").select2({
    	placeholder: "<%=request.getAttribute("authority_key")%>",
    	allowClear: true
    });

    </script>

</body>
