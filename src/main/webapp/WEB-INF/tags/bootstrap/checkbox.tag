<%@tag description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%@attribute name="path" required="true" type="java.lang.String" %>
<%@attribute name="cssClass" required="false" type="java.lang.String" %>
<%@attribute name="label" required="false" type="java.lang.String" %>
<%@attribute name="required" required="false" type="java.lang.Boolean" %>

<s:bind path="${path}">
    <div class="control-group ${status.error ? 'error' : '' }">
        <div class="controls">
                <%--<label class="checkbox">--%>
                    <form:checkbox path="${path}" cssClass="${empty cssClass ? 'checkbox' : cssClass}"/>
                    <s:message code="${label}"/>
                    <c:if test="${required}">
                        <span class="required"> *</span>
                    </c:if>
                <%--</label>--%>
                <c:if test="${status.error}">
                    <span class="help-inline">${status.errorMessage}</span>
                </c:if>
        </div>
    </div>
</s:bind>
