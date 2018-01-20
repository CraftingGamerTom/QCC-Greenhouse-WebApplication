
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
				<div class="text-center loginscreen animated fadeInDown">

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

					<a class="btn btn-social btn-google" href="/login"> <span
						class="fa fa-google"></span> Sign in with Google
					</a>

					<p class="m-t">
						<small>By using this site you agree to our <a
							href="/terms">Terms</A> and <a href="/policy">Privacy Policy</a>
						</small>
					</p>
				</div>

				<!-- END REGISTER CONTAINER -->
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
</body>

<footer>

	<!-- FOOTER -->
	<%@ include file="../../content/common/footer.jspf"%>
	<!-- END FOOTER -->

</footer>