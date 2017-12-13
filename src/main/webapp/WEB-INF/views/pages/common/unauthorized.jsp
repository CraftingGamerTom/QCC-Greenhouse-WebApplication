<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Head starts in header.jspf -->
<%@ include file="../../content/common/header.jspf"%>

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

			<!--
*
*  INSPINIA - Responsive Admin Theme
*  version 2.7
*
-->
			<body class="top-navigation">
				<div id="wrapper">
					<div id="page-wrapper" class="gray-bg">

						<div class="wrapper wrapper-content">
							<div class="middle-box text-center animated fadeInDown">
								<h3>Unauthorized</h3>
								<h1 class="logo-name">
									<i class="fa fa-lock"></i>
								</h1>
								<div class="error-desc">
									<h4 class="font-bold">Curiosity killed the cat</h4>
									<h5>It's best you enter through the front door in the
										future.</h5>
								</div>
							</div>
						</div>
					</div>


				</div>

			</body>

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



</body>

<footer>

			<!-- FOOTER -->
			<%@ include file="../../content/common/footer.jspf"%>
			<!-- END FOOTER -->

</footer>
