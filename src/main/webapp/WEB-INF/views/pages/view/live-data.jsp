<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Head starts in header.jspf -->
<%@ include file="../../content/common/header.jspf"%>

<!-- FooTable -->
<link
	href="<c:url value="/resources/css/plugins/footable/footable.core.css"/>"
	rel="stylesheet">

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
			<%@ include file="../../content/common/nav-horizontal.jspf"%>
			<!-- END HORIZONTAL NAVIGATION -->


			<!-- LIVE-PRIMARY CONTAINER -->
			<%@ include file="../../content/view/live-data-chart.jspf"%>
			<!-- END LIVE-PRIMARY CONTAINER -->

			<!-- FOOTER -->
			<%@ include file="../../content/common/footer.jspf"%>
			<!-- END FOOTER -->

		</div>


	</div>

	<!-- TASKS NAVIGATION CONTAINER -->
	<%@ include file="../../content/common/tasks.jspf"%>
	<!-- END TASKS NAVIGATION CONTAINER -->



	<!-- Mainly scripts -->
	<script src="<c:url value="/resources/js/jquery-3.1.1.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	<script
		src="<c:url value="/resources/js/plugins/metisMenu/jquery.metisMenu.js"/>"></script>
	<script
		src="<c:url value="/resources/js/plugins/slimscroll/jquery.slimscroll.min.js"/>"></script>

	<!-- Custom and plugin javascript -->
	<script src="<c:url value="/resources/js/inspinia.js"/>"></script>
	<script src="<c:url value="/resources/js/plugins/pace/pace.min.js"/>"></script>

	<!-- jQuery UI -->
	<script
		src="<c:url value="/resources/js/plugins/jquery-ui/jquery-ui.min.js"/>"></script>

	<!-- GITTER -->
	<script
		src="<c:url value="/resources/js/plugins/gritter/jquery.gritter.min.js"/>"></script>

	<!-- Sparkline -->
	<script
		src="<c:url value="/resources/js/plugins/sparkline/jquery.sparkline.min.js"/>"></script>


	<!-- Toastr -->
	<script
		src="<c:url value="/resources/js/plugins/toastr/toastr.min.js"/>"></script>

	<!-- FooTable -->
	<script
		src="<c:url value="/resources/js/plugins/footable/footable.all.min.js"/>"></script>

	<!-- Page-Level Scripts -->
	<script>
        $(document).ready(function() {

            $('.footable').footable();
            $('.footable2').footable();

        });

    </script>


</body>
