 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../tmp/header.jsp"></jsp:include>

	<form class="editForm" action="createContact" method="post">
		<p>Абонент:
		<c:forEach items="${addresses}" var = "address">
				<c:choose>
					<c:when test="${address.getId()==addressId}">
						<span id="org_name">${address.getName()}</span>
					</c:when>
				</c:choose>
		</c:forEach>
		<c:choose>
			<c:when test="${addressId==null}">
				<span id="org_name">${addresses.get(0).getName()}</span>
			</c:when>
		</c:choose>
	</p>
	
	<p>Адрес:
		<select id="address" name="addressId">
    	<c:forEach items="${addresses}" var = "address">
    		<option value="${address.getId()}" ${addressId == address.getId()? 'selected' : ''}>${address.getAddress()}</option>
    	</c:forEach>
    </select>
	</p>
	
		<p>Должность:<input type="text" name="post"></p>
		<p>ФИО:<input type="text" name="fio"></p>
		<p>Тел.:<input type="tel" name="phone"></p>
		<p>Email:<input type="email" name="email"></p>
		<p>Квартира:<input type="text" name="appartments"></p>
		<p><input type="submit"></p>
	</form>
	
	<script type="text/javascript">
var addresses = Array();
<c:forEach items="${addresses}" var = "address">
	addresses.push({
		id:'${address.getId()}',
		address:'${address.getAddress()}',
		name:'${address.getName()}'
	});
</c:forEach>

$("#address").on("change",
        function(){
            var a = $(this).find("option:selected").val();
            for(var i = 0; i<addresses.length; i++){
            	if(addresses[i].id==a){
            		$("#org_name").text(addresses[i].name);		
            	}
            }
});
</script>

<jsp:include page="../tmp/footer.jsp"></jsp:include>