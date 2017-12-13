
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Head starts in header.jspf -->
<%@ include file="../../content/common/header.jspf"%>

<!-- Datepicker -->
<link
	href="<c:url value="/resources/css/plugins/datapicker/datepicker3.css"/>"
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
			<div class="wrapper wrapper-content">
					<!-- VIEW-DATA-GRAPH CONTAINER -->
					<%@ include file="../../content/view/view-data-graph.jspf"%>
					<!-- END VIEW-DATA-GRAPH CONTAINER -->
			</div>

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

	<!-- Flot -->
	<script
		src="<c:url value="/resources/js/plugins/flot/jquery.flot.js"/>"></script>
	<script
		src="<c:url value="/resources/js/plugins/flot/jquery.flot.tooltip.min.js"/>"></script>
	<script
		src="<c:url value="/resources/js/plugins/flot/jquery.flot.spline.js"/>"></script>
	<script
		src="<c:url value="/resources/js/plugins/flot/jquery.flot.resize.js"/>"></script>
	<script
		src="<c:url value="/resources/js/plugins/flot/jquery.flot.pie.js"/>"></script>

	<!-- Peity -->
	<script
		src="<c:url value="/resources/js/plugins/peity/jquery.peity.min.js"/>"></script>
	<script src="<c:url value="/resources/js/demo/peity-demo.js"/>"></script>

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

	<!-- Sparkline demo data  -->
	<script src="<c:url value="/resources/js/demo/sparkline-demo.js"/>"></script>

	<!-- ChartJS-->
	<script
		src="<c:url value="/resources/js/plugins/chartJs/Chart.min.js"/>"></script>

	<!-- Toastr -->
	<script
		src="<c:url value="/resources/js/plugins/toastr/toastr.min.js"/>"></script>

	<!-- Data picker -->
	<script
		src="<c:url value="/resources/js/plugins/datapicker/bootstrap-datepicker.js"/>"></script>

	<!-- Date range use moment.js same as full calendar plugin -->
	<script
		src="<c:url value="/resources/js/plugins/fullcalendar/moment.min.js"/>"></script>

	<!-- Date range picker -->
	<script
		src="<c:url value="/resources/js/plugins/daterangepicker/daterangepicker.js"/>"></script>

	<!-- DATA FOR THE VIEW-DATA-GRAPH SCRIPT -->
	<%@ include file="../../content/view/view-data-graph-data.jspf"%>
	<!-- END DATA FOR THE VIEW-DATA-GRAPH SCRIPT -->


</body>

<footer>

			<!-- FOOTER -->
			<%@ include file="../../content/common/footer.jspf"%>
			<!-- END FOOTER -->

</footer>