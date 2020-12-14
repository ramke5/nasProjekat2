
<c:if test="${! empty messageSuccess}">
    <div class="alert alert-success" >
        <button type="button" class="close" data-dismiss="alert"></button>
        ${messageSuccess}
    </div>
</c:if>
<c:if test="${! empty messageInfo}">
    <div class="alert alert-info" >
        <button type="button" class="close" data-dismiss="alert"></button>
        ${messageInfo}
    </div>
</c:if>

<c:if test="${! empty messageError}">
    <div class="alert alert-error" >
        <button type="button" class="close" data-dismiss="alert"></button>
            ${messageError}
    </div>
</c:if>

<c:if test="${! empty messageImportant}">
    <div class="alert alert-important" >
        <button type="button" class="close" data-dismiss="alert"></button>
            ${messageImportant}
    </div>
</c:if>