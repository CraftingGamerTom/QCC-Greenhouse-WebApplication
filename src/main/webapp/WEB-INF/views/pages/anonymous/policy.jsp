
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../../content/common/header.jspf"%>

<style>
#ppBody {
	font-size: 11pt;
	width: 100%;
	margin: 0 auto;
	text-align: justify;
}

#ppHeader {
	font-family: verdana;
	font-size: 21pt;
	width: 100%;
	margin: 0 auto;
}

.ppConsistencies {
	display: none;
}
</style>

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
			<%@ include file="../../content/common/nav-horizontal.jspf"%>
			<!-- END HORIZONTAL NAVIGATION -->

			<div class="wrapper wrapper-content">
				<!-- FEED CONTAINER -->
				<%@ include file="../../content/anonymous/policy-content.jspf"%>
				<!-- END FEED CONTAINER -->
			</div>

		</div>
	</div>

	<!-- Mainly scripts -->
	<script src="<c:url value="/resources/js/jquery-3.1.1.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	<script
		src="<c:url value="/resources/js/plugins/metisMenu/jquery.metisMenu.js"/>"></script>
	<script
		src="<c:url value="/resources/js/plugins/slimscroll/jquery.slimscroll.min.js"/>"></script>

	<!-- Toastr -->
	<script
		src="<c:url value="/resources/js/plugins/toastr/toastr.min.js"/>"></script>

	<!-- Custom and plugin javascript -->
	<script src="<c:url value="/resources/js/inspinia.js"/>"></script>
	<script src="<c:url value="/resources/js/plugins/pace/pace.min.js"/>"></script>


</body>

<footer>

	<!-- FOOTER -->
	<%@ include file="../../content/common/footer.jspf"%>
	<!-- END FOOTER -->

</footer>
