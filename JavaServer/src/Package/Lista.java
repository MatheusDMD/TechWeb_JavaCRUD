package Package;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/lista")
public class Lista extends HttpServlet {

    protected void service (HttpServletRequest request,
                            HttpServletResponse response)
            throws  ServletException, IOException {

		DAO dao = new DAO();

		List<Pessoas> pessoas = dao.getLista();
		List<Passaportes> passaportes = dao.getListaPassaportes();

		PrintWriter out = response.getWriter();
		out.println("<html><body><form method='post'><table border='1'>");
		out.println("<tr><th colspan='4'>Dados Pessoais</th><th colspan='2'>Dados Passaporte</th><th>Other</th></tr>");
		out.println("<tr><th>ID</th><th>Nome</th>" + 
                    "<th>Nascimento</th><th>Altura</th><th>País</th>"
                    + "<th>Validade</th><th >Remove</th></tr>"); 
		for (Pessoas pessoa : pessoas) {
	        out.println("<tr><td>" + pessoa.getId() + "</td>");
	        out.println("<td>" + pessoa.getNome() + "</td>");
	        out.println("<td>" + pessoa.getNascimento().getTime() + "</td>");
	        out.println("<td>" + pessoa.getAltura() + "</td>");
			for(Passaportes passaporte : passaportes){
				if(passaporte.getUserId().toString().equals(pessoa.getId().toString())){
			        out.println("<td>" + passaporte.getPais() + "</td>");
			        out.println("<td>" + passaporte.getValidade().getTime() + "</td>");
					break;
				}
			}
			out.println("<td><input type='submit' value='"+pessoa.getId()+"' name='submit'/></td></tr>");
		}
		out.println("</table><form></body></html>");
		dao.remove(Integer.valueOf(request.getParameter("submit")));
		response.setHeader("Refresh", "0; URL=http://localhost:8080/JavaServer/lista");
		dao.close();
        
    } 
}