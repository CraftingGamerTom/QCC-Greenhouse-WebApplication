
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Head starts in header.jspf -->
<%@ include file="../../../content/common/header.jspf"%>

<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />



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
			<%@ include file="../../../content/common/nav-horizontal.jspf"%>
			<!-- END HORIZONTAL NAVIGATION -->

			<div class="wrapper wrapper-content">
				<!-- USER-DATA CONTAINER -->
				<%@ include file="../../../content/manager/manage/users-chart.jspf"%>
				<!-- END USER-DATA CONTAINER -->
			</div>
			
		</div>


	</div>

	<!-- TASKS NAVIGATION CONTAINER -->
	<%@ include file="../../../content/common/tasks.jspf"%>
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
	</script>
	<!-- For deleting user -->
	<script type="text/javascript">
		function delete_onclick(id) {

			var form = this;

			swal(
					{
						title : "Are you sure?",
						text : "This cannot be reversed. There could be serious problems.",
						type : "warning",
						showCancelButton : true,
						confirmButtonColor : '#DD6B55',
						confirmButtonText : 'Yes, I am sure!',
						cancelButtonText : "No, cancel it!",
						closeOnConfirm : false,
						closeOnCancel : false
					},
					function(isConfirm) {
						if (isConfirm) {

							$
									.ajax(
											{
												url : "/api/user/"
														+ id,
												type : "DELETE"
											})
									.done(
											function(data) {
												swal(
														{
															title : "Deleted!",
															text : "User "
																	+ id
																	+ " was successfully deleted!",
															type : "success",
															showCancelButton : false
														},
														function() {
															// Redirect the user
															window.location.href = "/manager/manage/users";
														});
											})
									.error(
											function(data) {
												swal("Oops", "Error: "
														+ data.message, "error");
											});

						} else {
							swal("Cancelled", "Phew, that was a close one! :)",
									"error");
						}
					});
		}
	</script>

	<!-- To Make REST work - Prevent CSRF Attacks -->
	<script type="text/javascript">
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
	</script>


</body>

<footer>

			<!-- FOOTER -->
			<%@ include file="../../../content/common/footer.jspf"%>
			<!-- END FOOTER -->

</footer>