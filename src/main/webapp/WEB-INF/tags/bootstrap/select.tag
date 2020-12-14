<%@tag description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%@attribute name="path" required="true" type="java.lang.String" %>
<%@attribute name="cssClass" required="false" type="java.lang.String" %>
<%@attribute name="label" required="false" type="java.lang.String" %>
<%@attribute name="required" required="false" type="java.lang.Boolean" %>
<%@attribute name="items" required="false" type="java.util.Collection" %>
<%@attribute name="itemLabel" required="false" type="java.lang.String" %>
<%@attribute name="itemValue" required="false" type="java.lang.String" %>
<%@attribute name="tabindex" required="false" type="java.lang.Integer" %>

<s:bind path="${path}">
    <div class="control-group ${status.error ? 'error' : '' }">
        <label class="control-label" for="${path}">
            <s:message code="${label}"/>
            <c:if test="${required}">
                <span class="required"> *</span>
            </c:if>
        </label>
        <div class="controls">
            <c:choose>
                <c:when test="${empty items}">
                    <form:select path="${path}" cssClass="${empty cssClass ? 'input-xlarge' : cssClass}" tabindex="${tabindex}">
                        <jsp:doBody/>
                    </form:select>
                </c:when>
                <c:otherwise>
                    <form:select path="${path}" items="${items}" itemLabel="${itemLabel}" itemValue="${itemValue}" cssClass="${empty cssClass ? 'input-xlarge' : cssClass}" tabindex="${tabindex}">
                        <jsp:doBody/>
                    </form:select>
                </c:otherwise>
            </c:choose>

            <c:if test="${status.error}">
                <span class="help-inline">${status.errorMessage}</span>
            </c:if>
        </div>
    </div>
</s:bind>