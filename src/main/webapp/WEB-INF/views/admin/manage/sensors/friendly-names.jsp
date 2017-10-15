<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<!-- Head starts in header.jspf -->
<%@ include file="../../../common/header.jspf"%>

<!-- Header for the Friendly Names Page Specifically -->
<%@ include file="../../../admin/friendlyNamesHeader.jspf"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<!-- END Head -->


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
		
		<!-- ADMIN-MANAGE-SENSORS-FRIENDLY-NAMES CONTAINER -->
		<%@ include file="../../../data-containers/admin-manage-sensors.jspf"%>
		<!-- END ADMIN-MANAGE-SENSORS-FRIENDLY-NAMES CONTAINER -->
		
		<!-- FOOTER -->
		<%@ include file="../../../common/footer.jspf"%>
		<!-- END FOOTER -->
		
		</div>

               
  	</div>

		<!-- TASKS NAVIGATION CONTAINER -->
		<%@ include file="../../../common/tasks.jspf"%>
		<!-- END TASKS NAVIGATION CONTAINER -->
		
	<script type="text/javascript">
		window.onload=function(){
	
			document.getElementById('sensorForm').value="rp1-01";

		}
	</script>
	
	<!-- Mainly scripts -->
	<script src="<c:url value="../../../../resources/js/jquery-3.1.1.min.js"/>"></script>
    <script src="<c:url value="../../../../resources/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="../../../../resources/js/plugins/metisMenu/jquery.metisMenu.js"/>"></script>
    <script src="<c:url value="../../../../resources/js/plugins/slimscroll/jquery.slimscroll.min.js"/>"></script>

	<!-- iCheck -->
    <script src="<c:url value="../../../../resources/js/plugins/iCheck/icheck.min.js"/>"></script>

        <script>
            $(document).ready(function () {
                $('.i-checks').iCheck({
                    checkboxClass: 'icheckbox_square-green',
                    radioClass: 'iradio_square-green',
                });
            });
        </script>
	

</body>
</html>