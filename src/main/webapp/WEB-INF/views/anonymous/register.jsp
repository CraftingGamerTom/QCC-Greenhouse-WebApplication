
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../common/header.jspf"%>

    <link href="<c:url value="/resources/css/plugins/bootstrapSocial/bootstrap-social.css"/>" rel="stylesheet">

    <link href="<c:url value="/resources/css/animate.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/style.css.css"/>" rel="stylesheet">


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
		<%@ include file="../common/nav-horizontal.jspf"%>
		<!-- END HORIZONTAL NAVIGATION -->
		
		<!-- LIVE-PRIMARY CONTAINER -->
		<%@ include file="../data-containers/helper-content/register-content.jspf"%>
		<!-- END LIVE-PRIMARY CONTAINER -->

        </div>
</div>

    <!-- COMMON FOOTER -->                       
	<%@ include file="../common/footer.jspf"%>
	<!-- END COMMON FOOTER -->



    <!-- Mainly scripts -->
    <script src="<c:url value="/resources/js/jquery-3.1.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/plugins/metisMenu/jquery.metisMenu.js"/>"></script>
    <script src="<c:url value="/resources/js/plugins/slimscroll/jquery.slimscroll.min.js"/>"></script>

</body>
