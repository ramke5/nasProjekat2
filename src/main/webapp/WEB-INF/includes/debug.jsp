<div class="row-fluid">
    <div class="span12">
        <div style="background-color: #fffcdd; color: #000000; padding-top: 20px;">
            <table class="table table-bordered table-condensed">

                <tr><td colspan="2"><h4>Request Attributes</h4></td></tr>
                <% java.util.Enumeration ralist = request.getAttributeNames();
                    while(ralist.hasMoreElements()){
                        String name=(String)ralist.nextElement();
//                        if (name.contains(".") || name.startsWith("_")) continue;
                        Object value = request.getAttribute(name);
                %>
                <tr><td style="color:#666"><%=name%></td><td><%=value%></td></tr>
                <% }%>

                <tr><td colspan="2"><h4>Session Attributes</h4></td></tr>
                <% java.util.Enumeration salist = session.getAttributeNames();
                    while(salist.hasMoreElements()){
                        String name=(String)salist.nextElement();
//                        if (name.startsWith("SPRING")) continue;
                        Object value = session.getAttribute(name);
                %>
                <tr><td style="color:#666"><%=name%></td><td><%=value%></td></tr>
                <% }%>
            </table>
        </div>

    </div>
</div>
