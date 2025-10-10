import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

public class numbers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Double> num = new ArrayList<>();
        ArrayList<String> questions = new ArrayList<>();

        for (int i = 0; i < 20; i += 2) {
            double n1 = Math.random() * 10;
            double n2 = Math.random() * 10;
            num.add(n1);
            num.add(n2);
            questions.add(String.format("%d + %d", (int)Math.round(n1), (int)Math.round(n2)));
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("num", num);
        request.setAttribute("questions", questions);

        RequestDispatcher dispatcher = request.getRequestDispatcher("quiz.jsp");
        dispatcher.forward(request, response);
    }
}
