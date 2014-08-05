<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglib.jsp"%>
<c:set var="paramStr" value="${param.otherParam}"/>
<c:if test="${ empty param.displayPageNum }">
	<c:set var="displayPageNum" value="5"/>
</c:if>
<c:if test="${not empty param.displayPageNum}">
	<c:set var="displayPageNum" value="${param.displayPageNum}"/>
</c:if>
<fmt:parseNumber value="${param.currentPage}" type="number" var="currentPage"></fmt:parseNumber>
<fmt:parseNumber value="${param.maxPage}" type="number" var="maxPage"></fmt:parseNumber>
<fmt:parseNumber value="${param.totalCount}" type="number" var="totalCount"></fmt:parseNumber>
<fmt:parseNumber value="${displayPageNum}" type="number" var="displayPageNum"></fmt:parseNumber>
<fmt:parseNumber value="${starpage}" type="number" var="starpage"></fmt:parseNumber>
<fmt:parseNumber value="${starpage}" type="number" var="starpage"></fmt:parseNumber>

<c:if test="${not empty paramStr}">
<script>
$(function(){
	$("#pages>a").click(function(event){
		$("#pager_info_form").attr("action", $(this).attr("href"));
		$("#pager_info_form").submit();
		event.preventDefault(); 
	});
});
</script>
<form  method="post" id="pager_info_form">
  <c:forTokens items="${paramStr}" delims="&" var="params">
	     <c:set var="paramName"/>
	     <c:set var="paramValue"/>
	     <c:forTokens items="${params}" delims="=" var="p" varStatus="status">
	        <c:if test="${status.first}"><c:set var="paramName" value="${p}"/></c:if>
	        <c:if test="${status.last}"><c:set var="paramValue" value="${p}"/></c:if>
	     </c:forTokens>
	     <c:if test="${paramName != paramValue}">
	     <input type="hidden" name="${paramName}" value="${paramValue}"/>
	     </c:if>
  </c:forTokens>
</form>
</c:if>

<div class="modal-footer">
	<div class="pull-left" data-dismiss="modal">
		总记录数${totalCount}
	</div>
	<div class="pagination pull-right no-margin"> 
		<c:if test="${maxPage>1}">
		<ul>
			<c:if test="${currentPage <= 1}">
				<li class="disabled"><a>首页</a></li>
				<li class="disabled"><a>上一页</a></li>
				<li class="active"><a>1</a></li>
			</c:if>
			<c:if test="${currentPage > 1}">
				<li><a href="${param.pageUrl}">首页</a></li>
				<li>
					<c:if test="${currentPage-1==1}">
						<a href="${param.pageUrl}">上一页</a>
					</c:if>
					<c:if test="${currentPage-1!=1}">
						<a href="${param.pageUrl}?start=${currentPage-1}">上一页</a>
					</c:if>
				</li>
			</c:if>
			
			<c:if test="${currentPage<displayPageNum}">
   				 	<c:set var="starpage" value="1"/>
   				 	<c:set var="endpage" value="5"/>
   			</c:if>
   			<c:if test="${currentPage>=displayPageNum}">
   				 	<c:set var="starpage" value="${currentPage-2}"/>
   				 	<c:set var="endpage" value="${currentPage+2}"/>
   			</c:if>
   			
  			<c:if test="${starpage<1}">
   				 	<c:set var="starpage" value="1"/>
   			</c:if>
   			<c:if test="${endpage > maxPage}">
   				 	<c:set var="endpage" value="${maxPage}"/>
   			</c:if>
   			
			<c:forEach begin="${starpage}" end="${endpage}" var="num">
			 <c:if test="${num == currentPage && currentPage != 1}">
			   <li class="active"><a>${num}</a></li>
			 </c:if>
			 <c:if test="${num != currentPage}">
		     <li><a href="${param.pageUrl}<c:if test="${num!=1}">?start=${num}</c:if>">${num}</a></li>
		   </c:if>
			</c:forEach>
			<c:if test="${currentPage == maxPage}">
		      <li class="disabled"><a>下一页</a></li>
		      <li class="disabled"><a>尾页</a></li>
		  </c:if>
		  <c:if test="${currentPage != maxPage}">
		      <li><a href="${param.pageUrl}?start=${currentPage+1}">下一页</a></li>
		      <li><a href="${param.pageUrl}?start=${param.maxPage}">尾页</a></li>
		  </c:if>
		</ul>
		</c:if>
	</div>
</div>