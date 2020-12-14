<%@tag description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%@attribute name="path" required="true" type="java.lang.String"%>
<%@attribute name="cssClass" required="false" type="java.lang.String"%>
<%@attribute name="cssStyle" required="false" type="java.lang.String"%>
<%@attribute name="label" required="false" type="java.lang.String"%>
<%@attribute name="required" required="false" type="java.lang.Boolean"%>
<%@attribute name="prepend" required="false" type="java.lang.String"%>
<%@attribute name="append" required="false" type="java.lang.String"%>
<%@attribute name="readonly" required="false" type="java.lang.Boolean"%>
<%@attribute name="decimal" required="false" type="java.lang.Boolean"%>
<%@attribute name="fractionDigits" required="false" type="java.lang.Integer"%>
<%@attribute name="tabindex" required="false" type="java.lang.Integer" %>

<c:if test="${fn:length(prepend) gt 1 }">
    <c:set var="icon" value='<i class="${prepend}"></i>'/>
</c:if>
<c:if test="${fn:length(append) gt 1 }">
    <c:set var="icon" value='<i class="${append}"></i>'/>
</c:if>

<s:bind path="${path}">
    <div class="control-group ${status.error ? 'error' : '' }">
        <label class="control-label" for="${path}">
            <s:message code="${label}"/>
            <c:if test="${required}">
                <span class="required"> *</span>
            </c:if>
        </label>
        <div class="controls">
            <div class="${empty prepend ? '' : 'input-prepend'} ${empty append ? '' : 'input-append'}">
                <c:if test="${not empty prepend}"><span class="add-on">${prepend}</span></c:if>
                <c:choose>
                    <c:when test="${decimal}">
                        <input id="${status.expression}" type="text" name="${status.expression}" value='<fmt:formatNumber value="${status.value}" maxFractionDigits="${fractionDigits}" minFractionDigits="${fractionDigits}"/>'  class="${empty cssClass ? 'input-xlarge' : cssClass}" tabindex="${tabindex}"/>
                    </c:when>
                    <c:otherwise>
                        <form:input path="${path}"  readonly="${readonly? 'true': 'false'}" cssClass="${empty cssClass ? 'input-xlarge' : cssClass}" tabindex="${tabindex}"/>
                    </c:otherwise>
                </c:choose>
                <c:if test="${not empty append}"><span class="add-on">${append}</span></c:if>
            </div>
            <c:if test="${status.error}">
                <span class="help-inline">${status.errorMessage}</span>
            </c:if>
        </div>
    </div>
</s:bind>

