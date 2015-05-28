<!-- #-------------------------------------------------------------------------------
# eGov suite of products aim to improve the internal efficiency,transparency, 
#    accountability and the service delivery of the government  organizations.
# 
#     Copyright (C) <2015>  eGovernments Foundation
# 
#     The updated version of eGov suite of products as by eGovernments Foundation 
#     is available at http://www.egovernments.org
# 
#     This program is free software: you can redistribute it and/or modify
#     it under the terms of the GNU General Public License as published by
#     the Free Software Foundation, either version 3 of the License, or
#     any later version.
# 
#     This program is distributed in the hope that it will be useful,
#     but WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#     GNU General Public License for more details.
# 
#     You should have received a copy of the GNU General Public License
#     along with this program. If not, see http://www.gnu.org/licenses/ or 
#     http://www.gnu.org/licenses/gpl.html .
# 
#     In addition to the terms of the GPL license to be adhered to in using this
#     program, the following additional terms are to be complied with:
# 
# 	1) All versions of this program, verbatim or modified must carry this 
# 	   Legal Notice.
# 
# 	2) Any misrepresentation of the origin of the material is prohibited. It 
# 	   is required that all modified versions of this material be marked in 
# 	   reasonable ways as different from the original version.
# 
# 	3) This license does not grant any rights to any user of the program 
# 	   with regards to rights under trademark law for use of the trade names 
# 	   or trademarks of eGovernments Foundation.
# 
#   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
#------------------------------------------------------------------------------- -->
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<meta name="description" content="Neon Admin Panel" />
		<meta name="author" content="" />
		
		<title>eGov Urban Portal</title>
		<link rel="stylesheet" href="../../../../../../egov-egiweb/src/main/webapp/resources/global/css/bootstrap/bootstrap.css">
		<link rel="stylesheet" href="../../../../../../egov-egiweb/src/main/webapp/resources/global/css/font-icons/entypo/css/entypo.css">
		<link rel="stylesheet" href="../../../../../../egov-egiweb/src/main/webapp/resources/global/css/egov/custom.css">
		<link rel="stylesheet" href="../../../../../../egov-egiweb/src/main/webapp/resources/global/css/egov/header-custom.css">
		<script src="../../../../../../egov-egiweb/src/main/webapp/resources/global/js/jquery/jquery.js"></script>
		
		<!--[if lt IE 9]><script src="resources/js/ie8-responsive-file-warning.js"></script><![endif]-->
		
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		
		
	</head>
	<body class="page-body">
		
		<div class="page-container">
			
			<header class="navbar navbar-fixed-top"><!-- set fixed position by adding class "navbar-fixed-top" -->
				
				<nav class="navbar navbar-default navbar-custom navbar-fixed-top">
					<div class="container-fluid">
						<div class="navbar-header col-md-10 col-xs-10">
							<a class="navbar-brand" href="javascript:void(0);">
								<img src="../../../../../../egov-egiweb/src/main/webapp/resources/global/images/chennai_logo.jpg" height="60">
								<div>
									
									<span class="title2">Create Position</span>
								</div>
							</a>
						</div>
						
						<div class="nav-right-menu col-md-2 col-xs-2">
							<ul class="hr-menu text-right">
								<li class="ico-menu">
									<a href="javascript:void(0);">
										<img src="../../../../../../egov-egiweb/src/main/webapp/resources/global/images/logo@2x.png" title="Powered by eGovernments" height="20px">
									</a>
								</li>
								
							</ul>
						</div>
						
					</div>
				</nav>
				
			</header>
			
			<div class="main-content">
			
				
				<div class="row">
					<div class="col-md-12">
						
						<div class="panel panel-primary" data-collapsed="0">
							<div class="panel-heading">
								<div class="panel-title view-content">
									Create Position or Position created successfully
								</div>
								
							</div>

							<div class="panel-body">
								
								<form action="javascript:void(0);" id="addcomplaint" method="post" class="form-horizontal form-groups-bordered">
									
									<div class="form-group">
										<label class="col-sm-3 control-label">Department<span class="mandatory"></span></label>
										<div class="col-sm-6">
											<input id="com_type" type="text" class="form-control typeahead" placeholder=""/>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">Designation<span class="mandatory"></span></label>
										<div class="col-sm-6">
											<input id="com_type" type="text" class="form-control typeahead" placeholder=""/>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">Position<span class="mandatory"></span></label>
										<div class="col-sm-6">
											<input id="com_type" type="text" class="form-control" placeholder=""/>
										</div>
									</div>

									<div class="form-group">
											
											<label for="field-1" class="col-sm-3 control-label">Outsourced Posts<span class="mandatory"></span></label>
											
											<div class="col-sm-1 col-xs-12 add-margin">
												<input type="radio" name="posts" value="Yes">
												<label>Yes</label>
											</div>
											<div class="col-sm-1 col-xs-12 add-margin">
												<input type="radio" name="posts" value="No">
												<label>No</label>
											</div>
										</div>

									<div class="form-group">
										<div class="text-center">
											<button type="submit" class="btn btn-primary">Create Position</button>
											<a href="javascript:void(0);" class="btn btn-default">Cancel</a>
										</div>
									</div>
									
								</form>
								
							</div>
							
						</div>
						
					</div>
				</div>
		
				
			</div>
			
			<footer class="main">
					
					Powered by <a href="http://eGovernments.org" target="_blank">eGovernments Foundation</a>
					
				</footer>
			
			
		</div>
		
		<link rel="stylesheet" href="../../../../../../egov-egiweb/src/main/webapp/resources/global/js/jquery/plugins/datatables/responsive/css/datatables.responsive.css">
		<script src="../../../../../../egov-egiweb/src/main/webapp/resources/global/js/bootstrap/bootstrap.js"></script>
		<script src="../../../../../../egov-egiweb/src/main/webapp/resources/global/js/egov/custom.js"></script>
		<script src="../../../../../../egov-egiweb/src/main/webapp/resources/global/js/bootstrap/typeahead.bundle.js"></script>
		<script src="../../../../../../egov-egiweb/src/main/webapp/resources/global/js/jquery/plugins/jquery.validate.min.js"></script>
		<script src="../../../../../../egov-egiweb/src/main/webapp/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js"></script>
		<script src="../../../../../../egov-egiweb/src/main/webapp/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js"></script>
		<script src="../../../../../../egov-egiweb/src/main/webapp/resources/global/JS/jquery/plugins/datatables/responsive/js/datatables.responsive.js"></script>
		<script src="../js/app/positioncreate.js"></script>
	</body>
</html>