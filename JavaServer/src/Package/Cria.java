package Package;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cria")
public class Cria extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response) 
    		             throws ServletException, IOException {
      PrintWriter out = response.getWriter();
	out.println("<html><body>");
	out.println("<form method='post'>");
	out.println("<h4>Nome</h4><br>");
	out.println("Nome: <input type='text' name='nome'><br>");
	out.println("Nascimento: <input type='date' name='nascimento'><br>");
	out.println("Altura: <input type='number' name='altura' step='0.01'><hr>");
	out.println("<h4>Passaporte</h4><br>");
	out.println("Pais: <input type='text' name='passaporte'><br>");
	out.println("Validade: <input type='date' name='validade'><br>");
	out.println("<input type='submit' value='Submit'>");
	out.println("</form>");
	out.println("<body><html>");
    }
	
    @Override
    protected void doPost(HttpServletRequest request, 
                          HttpServletResponse response) 
    		throws ServletException, IOException {
	
    	DAO dao = new DAO();

    	Pessoas pessoa = new Pessoas();
    	Passaportes passaporte = new Passaportes();
    	pessoa.setNome(request.getParameter("nome"));   
    	pessoa.setAltura(Double.valueOf(request.getParameter("altura")));
    	
    	passaporte.setPais(request.getParameter("passaporte"));
    	
    	String validade = request.getParameter("validade");
		Date data = null;
		try {
			data = new SimpleDateFormat("MM-dd-yyyy").parse(validade);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar dataValidade = Calendar.getInstance();
		dataValidade.setTime(data);
		passaporte.setValidade(dataValidade);
		
    	
		String nascimento = request.getParameter("nascimento");
		Date data1 = null;
		try {
			data1 = new SimpleDateFormat("MM-dd-yyyy").parse(nascimento);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar dataNascimento = Calendar.getInstance();
		dataNascimento.setTime(data1);
		pessoa.setNascimento(dataNascimento);
		
    	dao.adicionaPessoa(pessoa);
    	
    	List<Pessoas> lista = dao.getLista();
    	
    	passaporte.setUserId(lista.get(lista.size() - 1).getId());
    	dao.adicionaPassaporte(passaporte);
		
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println(pessoa.getNome() + " foi adicionado ao database!");
        out.println("</body></html>");
		
	  dao.close();

    } 
}