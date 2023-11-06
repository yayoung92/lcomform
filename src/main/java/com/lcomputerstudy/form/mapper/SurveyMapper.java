package com.lcomputerstudy.form.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.lcomputerstudy.form.domain.Allanswer;
import com.lcomputerstudy.form.domain.Answer;
import com.lcomputerstudy.form.domain.Options;
import com.lcomputerstudy.form.domain.Question;
import com.lcomputerstudy.form.domain.Survey;

@Mapper
public interface SurveyMapper {
	public List<Survey> selectSurvey();
	
	public void insertSurvey(Survey survey);
	
	public void insert(Question question);
	
	public void insertQuestion(Question question);
	
	public void insertOption(Options options);
	
	public void getOptionqIdx(Options options);
	
	public void get(Options options);
	
	public void insertQuestionData(int sIdx, List<Map<String, Object>> qList);
	
	public void insertOptionData(int sIdx, List<Map<String, Object>> oList);
	
	public Survey viewSurvey(int sIdx);
	
	public List<Question> viewQuestion(int sIdx);
	
	public List<Options> viewOption(int sIdx);
	
	public List<Answer> viewAnswer(int sIdx);
	
	public void insertAnswer(Answer answer);
	
	public void insertallAnswer(Allanswer allanswer);
	
	public void updateoId(Answer answer);
	
	public List<Options> selectOptionList(int sIdx);
	
	public List<Answer> selectAnswerList(int sIdx);
	
	public List<Allanswer> getAllanswerList(int sIdx);
	
	public void updateSurvey(Survey survey);
	
	public void updateQuestion(Question question);
	
	public void updateOption(Options options);
	
	public void deleteSurvey(int sIdx);
	
	public void deleteQuestion(int sIdx);
	
	public void deleteOption(int sIdx);
	
}
