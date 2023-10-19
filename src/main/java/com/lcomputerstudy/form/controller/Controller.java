package com.lcomputerstudy.form.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lcomputerstudy.form.domain.User;
import com.lcomputerstudy.form.domain.Allanswer;
import com.lcomputerstudy.form.domain.Answer;
import com.lcomputerstudy.form.domain.Options;
import com.lcomputerstudy.form.domain.Question;
import com.lcomputerstudy.form.domain.Response;
import com.lcomputerstudy.form.domain.Survey;
import com.lcomputerstudy.form.service.SurveyService;
import com.lcomputerstudy.form.service.UserService;

@org.springframework.stereotype.Controller
public class Controller {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired SurveyService surveyservice;
	@Autowired UserService userservice;
	
	@RequestMapping("/")
	public String home(Model model) {
		List<Survey> list = surveyservice.selectSurvey();
		model.addAttribute("list", list);
		logger.debug("debug");
		logger.info("info");
		logger.error("error");
		return "/index";
	}
	@RequestMapping("/beforeSignUp")
	public String beforeSingUp() {
		return "/signup";
	}
	@RequestMapping("/signup")
	public String signup(User user) {
		//비밀번호 암호화
		String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
	      
		//유저 데이터 세팅
		user.setPassword(encodedPassword);
		user.setAccountNonExpired(true);
		user.setEnabled(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setAuthorities(AuthorityUtils.createAuthorityList("ROLE_USER"));   
	      
		//유저 생성
		userservice.createUser(user);
		//유저 권한 생성
		userservice.createAuthorities(user);
	      
	      return "/login";
	}
	@RequestMapping(value="/login")
		public String beforeLogin(Model model) {
		return "/login";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/admin")
	public String admin(Model model) {
		return "/admin";
	}
	   
	@Secured({"ROLE_USER"})
	@RequestMapping(value="/user/info")
	public String userInfo(Model model) {
	      
		return "/user_info";
	}
	   
	@RequestMapping(value="/denied")
	public String denied(Model model) {
		return "/denied";
	}
	@RequestMapping("/surveylist")
	public String getsurveylist(Model model) {
		List<Survey> list = surveyservice.selectSurvey();
		model.addAttribute("list", list);
		return "/surveylist";
	}
	@RequestMapping("/resultSurvey")
	public String insquesi(@RequestParam("sIdx") int sIdx, Model model) {
		Survey survey = surveyservice.viewSurvey(sIdx);
		List<Question> question = surveyservice.viewQuestion(sIdx);
		List<Options> option = surveyservice.viewOption(sIdx);
		model.addAttribute("survey", survey);
		model.addAttribute("question", question);
		model.addAttribute("option", option);
		return "/resultSurvey";
	}
	@RequestMapping("/form-view")
	public String sum(Model model) {
		return "/form2";
	}
	
	@RequestMapping("/aj-insertanswer")
	public String insertAnswer(Model model, @RequestBody Response response) {
		surveyservice.insertResponse(response);
		System.out.println(response.getaAnswer());

		return "/surveylist";
	}
	
	/////// pie chart 보여주기
	@RequestMapping("/aj-viewAnswer")
    public String getActionData(@RequestBody Response response, Model model) {
		int sIdx = response.getsIdx();
		System.out.println(sIdx);
    //    surveyservice.selectAnswerList(sIdx, answer);
    //    model.addAttribute("answer", attributeValue);
        return "/chart";
    }
	
	@RequestMapping("/aj-chart-data")
	public String showChart(Model model, Answer answer) {
		List<Integer> a = surveyservice.selectAnswerlist(answer);
		String data = a.toString();
		System.out.println(data);
		
		List<Answer> ans = surveyservice.selectAnswerList(150, answer);
		String data2 = ans.toString();
		String aa = answer.getoOption();
		System.out.println(ans);
		System.out.println(data2);
		System.out.println("aa: " + aa);
	
		model.addAttribute("chartData", data);
		model.addAttribute("ch", data2);
		return "/chart";
	}
	
	
	
	
	//RequetBody Survey 로 받아서 값 주기
	@RequestMapping("/aj-insert")
	public String in(@RequestBody Survey survey, Model model) {
		surveyservice.insertSurvey(survey);
		return "/form";
	}
	
	//RequestBody Map 으로 받기
	@RequestMapping("/aj-insertsurvey")
	public String ins(@RequestBody Map<String, Object> pay, Survey survey, Question question, Options options) {
		String name = (String) pay.get("sTitle");
		List<Map<String, Object>> qList = (List<Map<String, Object>>) pay.get("qQuestions"); 
//		List<Map<String, Object>> oList = (List<Map<String, Object>>) pay.get("oOptions");
		
		survey.setsTitle(name);
		surveyservice.insertSurvey(survey);
		
		int sIdx = survey.getsIdx();
		
		for (Map<String, Object> qData : qList) {
            String qTitle = (String) qData.get("qQuestion");
            int qType = (int) qData.get("qType");
            List<String> optionTexts = (List<String>) qData.get("oOption");
            
            question.setqQuestion(qTitle);
            question.setqType(qType);
            question.setsIdx(sIdx);
            surveyservice.insertQuestion(question);
            
            if(question.getqType() == 2) {
	            for (String optionText : optionTexts) {
	            	
	        //        options.setoOption(optionText);
	                options.setsIdx(sIdx);
	                surveyservice.insertOption(options);
	                surveyservice.getOptionqIdx(options);
	            }
	            
            }
            
        }
		
		System.out.println(qList.toString());
		return "/index";
	}

}
