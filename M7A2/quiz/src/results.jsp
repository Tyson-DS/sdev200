<%@ page import="java.util.ArrayList" session="true" contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Quiz Results</title></head>
<body>
<%
    HttpSession session = request.getSession(false);
    if (session == null) {
        out.println("<h3>Session expired. Please take the quiz again.</h3>");
        return;
    }

    ArrayList<Double> num = (ArrayList<Double>) session.getAttribute("num");
    int correct = 0;

    for (int i = 0; i < num.size() / 2; i++) {
        int n1 = (int) Math.round(num.get(i * 2));
        int n2 = (int) Math.round(num.get(i * 2 + 1));
        int realAnswer = n1 + n2;

        String ansStr = request.getParameter("answer" + i);
        int userAnswer = 0;
        boolean valid = true;

        try {
            userAnswer = Integer.parseInt(ansStr);
        } catch (Exception e) {
            valid = false;
        }

        out.println("<p>" + n1 + " + " + n2 + " = " + realAnswer +
                    " | Your answer: " + (valid ? userAnswer : "invalid") +
                    ((valid && userAnswer == realAnswer) ? " correct" : " wrong") +
                    "</p>");

        if (valid && userAnswer == realAnswer) correct++;
    }

    out.println("<h2>You got " + correct + " out of " + (num.size()/2) + " correct!</h2>");
%>
</body>
</html>
