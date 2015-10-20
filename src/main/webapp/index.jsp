
<%
	response.sendRedirect(request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/admin/voice");
%>