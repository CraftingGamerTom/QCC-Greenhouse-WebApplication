
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Head starts in header.jspf -->
<%@ include file="../../common/header.jspf"%>

<link href="<c:url value="/resources/css/animate.css"/>"
	rel="stylesheet">
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
			<%@ include file="../../common/nav-horizontal.jspf"%>
			<!-- END HORIZONTAL NAVIGATION -->

			<!-- USER-DATA CONTAINER -->
			<%@ include file="../../content/user/profile-content.jspf"%>
			<!-- END USER-DATA CONTAINER -->

			<!-- FOOTER -->
			<%@ include file="../../common/footer.jspf"%>
			<!-- END FOOTER -->

		</div>


	</div>

	<!-- TASKS NAVIGATION CONTAINER -->
	<%@ include file="../../common/tasks.jspf"%>
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

	<!-- Sparkline -->
	<script
		src="<c:url value="/resources/js/plugins/sparkline/jquery.sparkline.min.js"/>"></script>

	<script>
        $(document).ready(function() {


            $("#sparkline1").sparkline([34, 43, 43, 35, 44, 32, 44, 48], {
                type: 'line',
                width: '100%',
                height: '50',
                lineColor: '#1ab394',
                fillColor: "transparent"
            });


        });
    </script>


</body>
