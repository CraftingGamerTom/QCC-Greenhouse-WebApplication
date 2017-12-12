
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../../content/common/header.jspf"%>

<link
	href="<c:url value="/resources/css/plugins/bootstrapSocial/bootstrap-social.css"/>"
	rel="stylesheet">

<link href="<c:url value="/resources/css/animate.css"/>"
	rel="stylesheet">
<link href="<c:url value="/resources/css/style.css.css"/>"
	rel="stylesheet">


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
				<!-- REGISTER CONTAINER -->
				<div class="middle-box text-center loginscreen animated fadeInDown">
					<div>
						<div>

							<h1 class="logo-name">PTK</h1>

						</div>
						<h3>QCC Greenhouse Data Analytics</h3>
						<p>
							We use the hard work of other companies to keep the sweat off our
							eyebrow and to keep one less password out of your memory.
							<!--Continually expanded and constantly improved Inspinia Admin Them (IN+)-->
						</p>
						<p>Sign in with a Google account below.</p>
						<a class="btn btn-block btn-social btn-google" href="/login">
							<span class="fa fa-google"></span> Sign in with Google
						</a> <br /> <a class="btn btn-block btn-outline btn-primary" href="/">Take
							me back Home</a>
						<p class="m-t">
							<small>By using our web application you agree to our <a
								href="/terms">Terms</a>.
							</small>
						</p>
					</div>

					<!-- END REGISTER CONTAINER -->
				</div>

			</div>
		</div>

		<!-- COMMON FOOTER -->
		<%@ include file="../../content/common/footer.jspf"%>
		<!-- END COMMON FOOTER -->



		<!-- Mainly scripts -->
		<script src="<c:url value="/resources/js/jquery-3.1.1.min.js"/>"></script>
		<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
		<script
			src="<c:url value="/resources/js/plugins/metisMenu/jquery.metisMenu.js"/>"></script>
		<script
			src="<c:url value="/resources/js/plugins/slimscroll/jquery.slimscroll.min.js"/>"></script>
</body>
