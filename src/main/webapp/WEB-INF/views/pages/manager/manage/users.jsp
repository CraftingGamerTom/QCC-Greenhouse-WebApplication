
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Head starts in header.jspf -->
<%@ include file="../../../common/header.jspf"%>

<link href="<c:url value="/resources/css/animate.css"/>"
	rel="stylesheet">
<link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">

<!-- FooTable -->
<link
	href="<c:url value="/resources/css/plugins/footable/footable.core.css"/>"
	rel="stylesheet">


<!-- Sweet Alert -->
<link
	href="<c:url value="/resources/css/plugins/sweetalert/sweetalert.css"/>"
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
			<%@ include file="../../../common/nav-horizontal.jspf"%>
			<!-- END HORIZONTAL NAVIGATION -->

			<!-- USER-DATA CONTAINER -->
			<%@ include file="../../../content/manager/manage/users-chart.jspf"%>
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
	<script
		src="<c:url value="/resources/js/plugins/metisMenu/jquery.metisMenu.js"/>"></script>
	<script
		src="<c:url value="/resources/js/plugins/slimscroll/jquery.slimscroll.min.js"/>"></script>

	<!-- Custom and plugin javascript -->
	<script src="<c:url value="/resources/js/inspinia.js"/>"></script>
	<script src="<c:url value="/resources/js/plugins/pace/pace.min.js"/>"></script>

	<!-- FooTable -->
	<script
		src="<c:url value="/resources/js/plugins/footable/footable.all.min.js"/>"></script>

	<!-- Sweet alert -->
	<script
		src="<c:url value="/resources/js/plugins/sweetalert/sweetalert.min.js"/>"></script>


	<!-- Page-Level Scripts -->
	<script>
		$(document).ready(function() {

			$('.footable').footable();

		});

		$('.press-delete')
				.click(
						function() {
							swal(
									{
										title : "Are you sure?",
										text : "This can cause permanant damage and cannot be undone!",
										type : "warning",
										showCancelButton : true,
										confirmButtonColor : "#DD6B55",
										confirmButtonText : "Yes, delete it!",
										closeOnConfirm : false
									},
									function() {
										swal(
												"Blocked",
												"This function is not implemented for safety.",
												"error");
									});
						});
	</script>


</body>
