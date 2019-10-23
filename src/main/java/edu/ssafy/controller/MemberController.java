package edu.ssafy.controller;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.ssafy.dto.MemDTO;
import edu.ssafy.service.MemberService;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	MemberService ser;
	
	@RequestMapping("/memregpage")
	public String memRegPage(){
		return "/member/memreg";
	}
	
	@RequestMapping("/memreg")
	public ModelAndView memReg(HttpServletRequest req, ModelAndView mv){
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		String name  = req.getParameter("name");
		String tel = req.getParameter("tel");
		ser.insert(id, pw, name, tel);
		
		mv.setViewName("redirect:memlist");
		return mv;
	}
	
	@RequestMapping("/memlist")
	public ModelAndView memList(ModelAndView mv){
		List<MemDTO> list = ser.selectList();
		mv.addObject("mems", list);
		mv.setViewName("/member/memlist");
		return mv;
	}
	
	@RequestMapping("/memview")
	public ModelAndView memView(@RequestParam("id") String id, ModelAndView mv){
//		String id = req.getParameter("id");
		MemDTO mem = ser.selectOne(id);
		mv.addObject("mem", mem);
		mv.setViewName("/member/memview");
		return mv;
	}
	
	@RequestMapping("/memdelete")
	public ModelAndView memDelete(@RequestParam("id") String id, ModelAndView mv){
		ser.delete(id);
		mv.setViewName("redirect:memlist");
		return mv;
	}
	
	@RequestMapping("/memupdate")
	public ModelAndView memUpdate(MemDTO mem, ModelAndView mv){
		ser.update(mem.getId(), mem.getPw(), mem.getName(), mem.getTel());
		mv.setViewName("redirect:memlist");
		return mv;
	}
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest req, @RequestParam("id") String id, @RequestParam("pw") String pw, ModelAndView mv) {
		if(ser.isLogin(id, pw)) {
			mv.addObject("islogin", "1");
			req.getSession().setAttribute("userid", id);
		}
		else {
			mv.addObject("islogin", "0");
		}
		
		mv.setViewName("redirect:/");
		
		return mv;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest req, ModelAndView mv) {
		req.getSession().invalidate();
		mv.setViewName("redirect:/");
		
		return mv;
	}
}