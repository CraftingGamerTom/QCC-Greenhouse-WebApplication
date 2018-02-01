
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Head starts in header.jspf -->
<%@ include file="../../content/common/header.jspf"%>

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

<!-- Date Picker -->
<link
	href="<c:url value="/resources/css/plugins/datapicker/datepicker3.css"/>"
	rel="stylesheet">
<!-- Clock Picker -->
<link
	href="<c:url value="/resources/css/plugins/clockpicker/clockpicker.css"/>"
	rel="stylesheet">


<!-- Markdown -->
<link
	href="<c:url value="/resources/css/plugins/bootstrap-markdown/bootstrap-markdown.min.css"/>"
	rel="stylesheet">

<!-- Fix clock picker in modal -->
<style>
.popover.clockpicker-popover {
	z-index: 9999;
}
</style>
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
				<!-- USER-DATA CONTAINER -->
				<%@ include file="../../content/entity/observations.jspf"%>
				<!-- END USER-DATA CONTAINER -->
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

	<!-- Custom and plugin javascript -->
	<script src="<c:url value="/resources/js/inspinia.js"/>"></script>
	<script src="<c:url value="/resources/js/plugins/pace/pace.min.js"/>"></script>

	<!-- Data picker -->
	<script
		src="<c:url value="/resources/js/plugins/datapicker/bootstrap-datepicker.js"/>"></script>

	<!-- Clock picker -->
	<script
		src="<c:url value="/resources/js/plugins/clockpicker/clockpicker.js"/>"></script>

	<!-- Date range use moment.js same as full calendar plugin -->
	<script
		src="<c:url value="/resources/js/plugins/fullcalendar/moment.min.js"/>"></script>

	<!-- Date range picker -->
	<script
		src="<c:url value="/resources/js/plugins/daterangepicker/daterangepicker.js"/>"></script>

	<!-- Bootstrap markdown -->
	<script
		src="<c:url value="/resources/js/plugins/bootstrap-markdown/bootstrap-markdown.js"/>"></script>
	<script
		src="<c:url value="/resources/js/plugins/bootstrap-markdown/markdown.js"/>"></script>

	<!-- Custom and plugin javascript -->
	<script src="<c:url value="/resources/js/inspinia.js"/>"></script>
	<script src="<c:url value="/resources/js/plugins/pace/pace.min.js"/>"></script>

	<!-- Toastr -->
	<script
		src="<c:url value="/resources/js/plugins/toastr/toastr.min.js"/>"></script>

	<!-- Data picker -->
	<script
		src="<c:url value="/resources/js/plugins/datapicker/bootstrap-datepicker.js"/>"></script>

	<!-- Jquery Validate -->
	<script
		src="<c:url value="/resources/js/plugins/validate/jquery.validate.min.js"/>"></script>

	<script>
		$(document).ready(function() {

			$('.clockpicker').clockpicker();

			$('#date_range_data .input-daterange').datepicker({
				keyboardNavigation : false,
				format : 'yyyy-mm-dd',
				forceParse : false,
				autoclose : true
			});

			$("#observation-form").validate({
				submitHandler : function(form) {
					postObservation();
				},
				rules : {
					title : {
						required : true,
						maxlength : 75,
						minlength : 1
					},
					date : {
						required : true,
						maxlength : 10,
						minlength : 10
					},
					time : {
						required : true,
						maxlength : 5,
						minlength : 5
					},
					body : {
						required : true,
						maxlength : 4000,
						minlength : 1
					}
				}
			});

			$('#calander-data .input-group.date').datepicker({
				format : 'yyyy-mm-dd',
				todayBtn : "linked",
				keyboardNavigation : false,
				forceParse : false,
				calendarWeeks : true,
				autoclose : true
			});

		});
	</script>

	<!-- To Make REST work - Prevent CSRF Attacks -->
	<script type="text/javascript">
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
	</script>

	<!-- Set the date -->
	<script type="text/javascript">
		window.onload = function() {
			setObservationDate();
			setObservationTime();
		}
	</script>

	<!-- Do the post for a new observation -->
	<script>
		function postObservation() {

			var titleD = document.querySelector('#title').value.toString();
			var dateD = document.querySelector('#date').value.toString();
			var timeD = document.querySelector('#time').value.toString();
			var priorityD = document.querySelector('#priority').value
					.toString();
			var bodyD = document.querySelector('#body').value.toString();

			$.ajax({
				url : "/api/observation",
				type : "POST",
				data : JSON.stringify({
					title : titleD,
					date : dateD,
					time : timeD,
					priority : priorityD,
					body : bodyD
				}),
				processData : false,
				contentType : 'application/json',
				error : function(xhr, error) {
					console.log(xhr);
					console.log(error);
					console.log(JSON.stringify({
						title : titleD,
						date : dateD,
						time : timeD,
						priority : priorityD,
						body : bodyD
					}));
					$('#newObservation').modal('hide');
					showErrorToast();
				},
				success : function(result) {
					console.log("POST new observation success");
					$('#newObservation').modal('hide');
					clearModal();
				}
			});
		};
	</script>

	<script>
		function getTodaysDate() {
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth() + 1; //January is 0!
			var yyyy = today.getFullYear();

			if (dd < 10) {
				dd = '0' + dd
			}

			if (mm < 10) {
				mm = '0' + mm
			}

			var todaysDate = yyyy + '-' + mm + '-' + dd
			return todaysDate;
		};

		function setObservationDate() {
			document.getElementById('date').value = getTodaysDate();
		};

		function setObservationTime() {
			var d = new Date();
			var h = d.getHours();
			var m = d.getMinutes();

			if (h < 10) {
				h = '0' + h
			}

			if (m < 10) {
				m = '0' + m
			}

			document.getElementById('time').value = h + ':' + m;
		};

		function clearModal() {
			setObservationDate();
			setObservationTime();
			document.getElementById('title').value = '';
			document.getElementById('body').value = '';
			document.getElementById('priority').selectedIndex = 0;
		}
	</script>

	<script type="text/javascript">
		function showErrorToast() {

			toastr.options = {
				"closeButton" : false,
				"debug" : false,
				"progressBar" : false,
				"preventDuplicates" : false,
				"positionClass" : "toast-bottom-right",
				"onclick" : null,
				"showDuration" : "400",
				"hideDuration" : "1000",
				"timeOut" : "7000",
				"extendedTimeOut" : "1000",
				"showEasing" : "swing",
				"hideEasing" : "linear",
				"showMethod" : "fadeIn",
				"hideMethod" : "fadeOut"
			};

			toastr["error"](
					"Your observation was not saved. An error occurred.",
					"Error!");

		};

		function showSuccessToast() {

			toastr.options = {
				"closeButton" : false,
				"debug" : false,
				"progressBar" : false,
				"preventDuplicates" : false,
				"positionClass" : "toast-bottom-right",
				"onclick" : null,
				"showDuration" : "200",
				"hideDuration" : "1000",
				"timeOut" : "7000",
				"extendedTimeOut" : "1000",
				"showEasing" : "swing",
				"hideEasing" : "linear",
				"showMethod" : "fadeIn",
				"hideMethod" : "fadeOut"
			}

			toastr["success"]("Thank you for your contribution.", "Success")

		};
	</script>

	<script>
		function openViewObservation(oId) {
			var priorityId = oId + '-priority';
			var colorId = oId + '-color';
			var titleId = oId + '-title';
			var oDateId = oId + '-odate';
			var pDateId = oId + '-pdate';
			var bodyId = oId + '-body';

			console.log(oId);
			console.log(priorityId);
			console.log(oDateId);
			console.log(document.getElementById(oDateId).value.toString());

			// Set Priority
			var elementPriority = document.getElementById("view-priority");
			elementPriority.innerHTML = document.getElementById(priorityId).value
					.toString();

			// Set badge color
			var badgeColor = 'badge badge-'
					+ document.getElementById(colorId).value.toString()
					+ " pull-left";
			if (!document.getElementById("view-priority").className
					.match(/(?:^|\s)badge badge-info pull-left(?!\S)/)) {
				document.getElementById("view-priority").className = badgeColor;
			}

			// Set Title
			var elementTitle = document.getElementById("view-title");
			elementTitle.innerHTML = document.getElementById(titleId).value
					.toString();

			// Set Observation Date
			var elementOdate = document.getElementById("view-observe-date");
			elementOdate.innerHTML = document.getElementById(oDateId).value
					.toString();

			// Set Post Date
			var elementPdate = document.getElementById("view-post-date");
			elementPdate.innerHTML = document.getElementById(pDateId).value
					.toString();

			var elementBody = document.getElementById('view-body');
			elementBody.innerHTML = document.getElementById(bodyId).value
					.toString();

			$('#viewObservation').modal('show');
		};
		function ZopenViewObservation(oId) {

			$('#viewObservation').modal('toggle');
		};
	</script>

</body>

<footer>

	<!-- FOOTER -->
	<%@ include file="../../content/common/footer.jspf"%>
	<!-- END FOOTER -->

</footer>