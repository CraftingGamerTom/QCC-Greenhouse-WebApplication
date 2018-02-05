
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
				<%@ include
					file="../../content/anonymous/organization-members-content.jspf"%>
				<!-- END FEED CONTAINER -->
			</div>

		</div>
	</div>

	<div id="right-sidebar" class="animated">
		<div class="sidebar-container">

			<ul class="nav nav-tabs navs-3">

				<li class="active"><a data-toggle="tab" href="#tab-1">
						Notes </a></li>
				<li><a data-toggle="tab" href="#tab-2"> Projects </a></li>
				<li class=""><a data-toggle="tab" href="#tab-3"> <i
						class="fa fa-gear"></i>
				</a></li>
			</ul>

			<div class="tab-content">


				<div id="tab-1" class="tab-pane active">

					<div class="sidebar-title">
						<h3>
							<i class="fa fa-comments-o"></i> Latest Notes
						</h3>
						<small><i class="fa fa-tim"></i> You have 10 new message.</small>
					</div>

					<div>

						<div class="sidebar-message">
							<a href="#">
								<div class="pull-left text-center">
									<img alt="image" class="img-circle message-avatar"
										src="img/a1.jpg">

									<div class="m-t-xs">
										<i class="fa fa-star text-warning"></i> <i
											class="fa fa-star text-warning"></i>
									</div>
								</div>
								<div class="media-body">

									There are many variations of passages of Lorem Ipsum available.
									<br> <small class="text-muted">Today 4:21 pm</small>
								</div>
							</a>
						</div>
						<div class="sidebar-message">
							<a href="#">
								<div class="pull-left text-center">
									<img alt="image" class="img-circle message-avatar"
										src="img/a2.jpg">
								</div>
								<div class="media-body">
									The point of using Lorem Ipsum is that it has a more-or-less
									normal. <br> <small class="text-muted">Yesterday
										2:45 pm</small>
								</div>
							</a>
						</div>
						<div class="sidebar-message">
							<a href="#">
								<div class="pull-left text-center">
									<img alt="image" class="img-circle message-avatar"
										src="img/a3.jpg">

									<div class="m-t-xs">
										<i class="fa fa-star text-warning"></i> <i
											class="fa fa-star text-warning"></i> <i
											class="fa fa-star text-warning"></i>
									</div>
								</div>
								<div class="media-body">
									Mevolved over the years, sometimes by accident, sometimes on
									purpose (injected humour and the like). <br> <small
										class="text-muted">Yesterday 1:10 pm</small>
								</div>
							</a>
						</div>
						<div class="sidebar-message">
							<a href="#">
								<div class="pull-left text-center">
									<img alt="image" class="img-circle message-avatar"
										src="img/a4.jpg">
								</div>

								<div class="media-body">
									Lorem Ipsum, you need to be sure there isn't anything
									embarrassing hidden in the <br> <small class="text-muted">Monday
										8:37 pm</small>
								</div>
							</a>
						</div>
						<div class="sidebar-message">
							<a href="#">
								<div class="pull-left text-center">
									<img alt="image" class="img-circle message-avatar"
										src="img/a8.jpg">
								</div>
								<div class="media-body">

									All the Lorem Ipsum generators on the Internet tend to repeat.
									<br> <small class="text-muted">Today 4:21 pm</small>
								</div>
							</a>
						</div>
						<div class="sidebar-message">
							<a href="#">
								<div class="pull-left text-center">
									<img alt="image" class="img-circle message-avatar"
										src="img/a7.jpg">
								</div>
								<div class="media-body">
									Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor
									sit amet..", comes from a line in section 1.10.32. <br> <small
										class="text-muted">Yesterday 2:45 pm</small>
								</div>
							</a>
						</div>
						<div class="sidebar-message">
							<a href="#">
								<div class="pull-left text-center">
									<img alt="image" class="img-circle message-avatar"
										src="img/a3.jpg">

									<div class="m-t-xs">
										<i class="fa fa-star text-warning"></i> <i
											class="fa fa-star text-warning"></i> <i
											class="fa fa-star text-warning"></i>
									</div>
								</div>
								<div class="media-body">
									The standard chunk of Lorem Ipsum used since the 1500s is
									reproduced below. <br> <small class="text-muted">Yesterday
										1:10 pm</small>
								</div>
							</a>
						</div>
						<div class="sidebar-message">
							<a href="#">
								<div class="pull-left text-center">
									<img alt="image" class="img-circle message-avatar"
										src="img/a4.jpg">
								</div>
								<div class="media-body">
									Uncover many web sites still in their infancy. Various versions
									have. <br> <small class="text-muted">Monday 8:37
										pm</small>
								</div>
							</a>
						</div>
					</div>

				</div>

				<div id="tab-2" class="tab-pane">

					<div class="sidebar-title">
						<h3>
							<i class="fa fa-cube"></i> Latest projects
						</h3>
						<small><i class="fa fa-tim"></i> You have 14 projects. 10
							not completed.</small>
					</div>

					<ul class="sidebar-list">
						<li><a href="#">
								<div class="small pull-right m-t-xs">9 hours ago</div>
								<h4>Business valuation</h4> It is a long established fact that a
								reader will be distracted.

								<div class="small">Completion with: 22%</div>
								<div class="progress progress-mini">
									<div style="width: 22%;"
										class="progress-bar progress-bar-warning"></div>
								</div>
								<div class="small text-muted m-t-xs">Project end: 4:00 pm
									- 12.06.2014</div>
						</a></li>
						<li><a href="#">
								<div class="small pull-right m-t-xs">9 hours ago</div>
								<h4>Contract with Company</h4> Many desktop publishing packages
								and web page editors.

								<div class="small">Completion with: 48%</div>
								<div class="progress progress-mini">
									<div style="width: 48%;" class="progress-bar"></div>
								</div>
						</a></li>
						<li><a href="#">
								<div class="small pull-right m-t-xs">9 hours ago</div>
								<h4>Meeting</h4> By the readable content of a page when looking
								at its layout.

								<div class="small">Completion with: 14%</div>
								<div class="progress progress-mini">
									<div style="width: 14%;" class="progress-bar progress-bar-info"></div>
								</div>
						</a></li>
						<li><a href="#"> <span
								class="label label-primary pull-right">NEW</span>
								<h4>The generated</h4> There are many variations of passages of
								Lorem Ipsum available.
								<div class="small">Completion with: 22%</div>
								<div class="small text-muted m-t-xs">Project end: 4:00 pm
									- 12.06.2014</div>
						</a></li>
						<li><a href="#">
								<div class="small pull-right m-t-xs">9 hours ago</div>
								<h4>Business valuation</h4> It is a long established fact that a
								reader will be distracted.

								<div class="small">Completion with: 22%</div>
								<div class="progress progress-mini">
									<div style="width: 22%;"
										class="progress-bar progress-bar-warning"></div>
								</div>
								<div class="small text-muted m-t-xs">Project end: 4:00 pm
									- 12.06.2014</div>
						</a></li>
						<li><a href="#">
								<div class="small pull-right m-t-xs">9 hours ago</div>
								<h4>Contract with Company</h4> Many desktop publishing packages
								and web page editors.

								<div class="small">Completion with: 48%</div>
								<div class="progress progress-mini">
									<div style="width: 48%;" class="progress-bar"></div>
								</div>
						</a></li>
						<li><a href="#">
								<div class="small pull-right m-t-xs">9 hours ago</div>
								<h4>Meeting</h4> By the readable content of a page when looking
								at its layout.

								<div class="small">Completion with: 14%</div>
								<div class="progress progress-mini">
									<div style="width: 14%;" class="progress-bar progress-bar-info"></div>
								</div>
						</a></li>
						<li><a href="#"> <span
								class="label label-primary pull-right">NEW</span>
								<h4>The generated</h4> <!--<div class="small pull-right m-t-xs">9 hours ago</div>-->
								There are many variations of passages of Lorem Ipsum available.
								<div class="small">Completion with: 22%</div>
								<div class="small text-muted m-t-xs">Project end: 4:00 pm
									- 12.06.2014</div>
						</a></li>

					</ul>

				</div>

				<div id="tab-3" class="tab-pane">

					<div class="sidebar-title">
						<h3>
							<i class="fa fa-gears"></i> Settings
						</h3>
						<small><i class="fa fa-tim"></i> You have 14 projects. 10
							not completed.</small>
					</div>

					<div class="setings-item">
						<span> Show notifications </span>
						<div class="switch">
							<div class="onoffswitch">
								<input type="checkbox" name="collapsemenu"
									class="onoffswitch-checkbox" id="example"> <label
									class="onoffswitch-label" for="example"> <span
									class="onoffswitch-inner"></span> <span
									class="onoffswitch-switch"></span>
								</label>
							</div>
						</div>
					</div>
					<div class="setings-item">
						<span> Disable Chat </span>
						<div class="switch">
							<div class="onoffswitch">
								<input type="checkbox" name="collapsemenu" checked
									class="onoffswitch-checkbox" id="example2"> <label
									class="onoffswitch-label" for="example2"> <span
									class="onoffswitch-inner"></span> <span
									class="onoffswitch-switch"></span>
								</label>
							</div>
						</div>
					</div>
					<div class="setings-item">
						<span> Enable history </span>
						<div class="switch">
							<div class="onoffswitch">
								<input type="checkbox" name="collapsemenu"
									class="onoffswitch-checkbox" id="example3"> <label
									class="onoffswitch-label" for="example3"> <span
									class="onoffswitch-inner"></span> <span
									class="onoffswitch-switch"></span>
								</label>
							</div>
						</div>
					</div>
					<div class="setings-item">
						<span> Show charts </span>
						<div class="switch">
							<div class="onoffswitch">
								<input type="checkbox" name="collapsemenu"
									class="onoffswitch-checkbox" id="example4"> <label
									class="onoffswitch-label" for="example4"> <span
									class="onoffswitch-inner"></span> <span
									class="onoffswitch-switch"></span>
								</label>
							</div>
						</div>
					</div>
					<div class="setings-item">
						<span> Offline users </span>
						<div class="switch">
							<div class="onoffswitch">
								<input type="checkbox" checked name="collapsemenu"
									class="onoffswitch-checkbox" id="example5"> <label
									class="onoffswitch-label" for="example5"> <span
									class="onoffswitch-inner"></span> <span
									class="onoffswitch-switch"></span>
								</label>
							</div>
						</div>
					</div>
					<div class="setings-item">
						<span> Global search </span>
						<div class="switch">
							<div class="onoffswitch">
								<input type="checkbox" checked name="collapsemenu"
									class="onoffswitch-checkbox" id="example6"> <label
									class="onoffswitch-label" for="example6"> <span
									class="onoffswitch-inner"></span> <span
									class="onoffswitch-switch"></span>
								</label>
							</div>
						</div>
					</div>
					<div class="setings-item">
						<span> Update everyday </span>
						<div class="switch">
							<div class="onoffswitch">
								<input type="checkbox" name="collapsemenu"
									class="onoffswitch-checkbox" id="example7"> <label
									class="onoffswitch-label" for="example7"> <span
									class="onoffswitch-inner"></span> <span
									class="onoffswitch-switch"></span>
								</label>
							</div>
						</div>
					</div>

					<div class="sidebar-content">
						<h4>Settings</h4>
						<div class="small">I belive that. Lorem Ipsum is simply
							dummy text of the printing and typesetting industry. And
							typesetting industry. Lorem Ipsum has been the industry's
							standard dummy text ever since the 1500s. Over the years,
							sometimes by accident, sometimes on purpose (injected humour and
							the like).</div>
					</div>

				</div>
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


	<!-- Custom and plugin javascript -->
	<script src="<c:url value="/resources/js/inspinia.js"/>"></script>
	<script src="<c:url value="/resources/js/plugins/pace/pace.min.js"/>"></script>

	<!-- FooTable -->
	<script
		src="<c:url value="/resources/js/plugins/footable/footable.all.min.js"/>"></script>

	<!-- Page-Level Scripts -->
	<script>
		$(document).ready(function() {

			$('.footable').footable();

		});
	</script>

</body>

<footer>

	<!-- FOOTER -->
	<%@ include file="../../content/common/footer.jspf"%>
	<!-- END FOOTER -->

</footer>
