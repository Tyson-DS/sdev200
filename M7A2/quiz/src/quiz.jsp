<%@ page import="java.util.ArrayList" session="true" contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Addition Quiz</title></head>
<body>
    <form action="results.jsp" method="post">
        <%
            ArrayList<String> questions = (ArrayList<String>) request.getAttribute("questions");
            int index = 0;
            for (String q : questions) {
        %>
            <p><%= q %> = <input type="text" name="answer<%= index %>"></p>
        <%
                index++;
            }
        %>
        <input type="submit" value="Submit Answers">
    </form>
</body>
</html>
