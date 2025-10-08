import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class loanServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        double amount = Double.parseDouble(request.getParameter("amount"));
        double rate = Double.parseDouble(request.getParameter("rate"));
        int years = Integer.parseInt(request.getParameter("years"));

        double monthlyRate = rate / 1200;
        double monthlyPayment = amount * monthlyRate / 
                (1 - Math.pow(1 + monthlyRate, -years * 12));
        double totalPayment = monthlyPayment * years * 12;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Loan Payment Results</h2>");
        out.println("Monthly Payment: $" + String.format("%.2f", monthlyPayment) + "<br>");
        out.println("Total Payment: $" + String.format("%.2f", totalPayment) + "<br>");
        out.println("</body></html>");
    }
}
