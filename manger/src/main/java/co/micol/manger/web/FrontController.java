package co.micol.manger.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.manger.command.MainCommand;
import co.micol.manger.command.MemberSelectList;
import co.micol.manger.common.Command;

//@WebServlet("/FrontController")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HashMap<String, Command> map = new HashMap<String, Command>();
    
    public FrontController() {
        super();
    }

	/**
	 * init: 서블릿 초기화하는 곳, 실행할 Command 메소드를 등록한다.
	 */
	public void init(ServletConfig config) throws ServletException {
		map.put("/main.do", new MainCommand()); // 처음 호출했을 때 보여주는 페이지
		map.put("/memberSelectList.do", new MemberSelectList());
		
	}

	/**
	 * service: 요청을 분석하고 처리하는 곳, controller 만드는 곳
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //한글 깨짐 방지
		String uri = request.getRequestURI(); //요청명령을 분석하기 위해 URI를 구한다.
		String contextPath = request.getContextPath(); //프로젝트의 루트를 찾아낸다.
		String page = uri.substring(contextPath.length()); //실제 수행해야할 호출명을 구한다.
		
		Command command = map.get(page); //command = new MainCommand(); 불러올때는 get(key)만 해도 된다. 인터페이스를 통해 명령을 호출한다.
		String viewPage = command.run(request, response); //명령을 실행하고 돌려줄 페이지(viewPage)를 받는다.
		
		
		if(!viewPage.endsWith(".do")) { //viewPage의 끝이 ".do"로 끝나지 않으면
			viewPage = "WEB-INF/views/" + viewPage + ".jsp"; //viewPage는 ""이렇게 실행한다. => view Resolve
		}
		
		//보여줄 페이지를 호출하는 부분, DAO에서 가져온 값을 담고 가는 dispatcher
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage); //db에서 받아온 값을 그대로 전달하기 위해 dispatcher 사용
		dispatcher.forward(request, response);
	}

}
