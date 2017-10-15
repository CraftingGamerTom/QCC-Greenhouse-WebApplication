<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../common/header.jspf"%>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$("button").click(function() {
			$("p").hide();
		});
	});
</script>
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
		<%@ include file="../common/nav-horizontal.jspf"%>
		<!-- END HORIZONTAL NAVIGATION -->

		<div class="container">
			<spring:message code="welcome.message" />
			${name}. <a>testing this page.</a>
	
			<h2>This is a heading</h2>
	
			<p>This is a paragraph.</p>
			<p>This is another paragraph.</p>
	
			<button>Click me to hide paragraphs</button>
		</div>


		</div>
		
               
  	</div>

		<!-- TASKS NAVIGATION CONTAINER -->
		<%@ include file="../common/tasks.jspf"%>
		<!-- END TASKS NAVIGATION CONTAINER -->

	
</body>
