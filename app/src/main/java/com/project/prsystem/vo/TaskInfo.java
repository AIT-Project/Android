package com.project.prsystem.vo;

import java.sql.Date;

public class TaskInfo {
	
	protected int 		task_number;
	protected String 	task_content;
	protected String 	task_name;
	protected Date 		task_start;
	protected Date 		task_end;
	protected String 	subj_code_div;
	protected String 	task_group;
	protected int 		task_group_num;

	@Override
	public String toString() {
		return  "{" + "\"task\"" + ":" + "["
				+ "{" + "\"task_number\""+":" + getTask_number()  + ","
				+ "\"task_content\""+":"+ "\"" +getTask_content()+ "\"" + ","
				+ "\"task_name\""+":" +"\"" +getTask_name()+ "\"" + ","
				+ "\"task_start\""+":" +"\"" +getTask_start()+ "\"" + ","
				+ "\"task_end\""+":" +"\"" +getTask_end()+ "\"" + ","
				+ "\"subj_code_div\""+":" +"\"" +getSubj_code_div()+ "\"" + ","
				+ "\"task_group\""+":" +"\"" +getTask_group()+ "\"" + ","
				+ "\"task_group_num\""+":" +getTask_group_num() + "}]}";
	}

	public int getTask_group_num() {
		return task_group_num;
	}
	public TaskInfo setTask_group_num(int task_group_num) {
		this.task_group_num = task_group_num;
		return this;
	}
	public int getTask_number() {
		return task_number;
	}
	public TaskInfo setTask_number(int task_number) {
		this.task_number = task_number;
		return this;
	}
	public String getTask_content() {
		return task_content;
	}
	public TaskInfo setTask_content(String task_content) {
		this.task_content = task_content;
		return this;
	}
	public String getTask_name() {
		return task_name;
	}
	public TaskInfo setTask_name(String task_name) {
		this.task_name = task_name;
		return this;
	}
	public Date getTask_start() {
		return task_start;
	}
	public TaskInfo setTask_start(Date task_start) {
		this.task_start = task_start;
		return this;
	}
	public Date getTask_end() {
		return task_end;
	}
	public TaskInfo setTask_end(Date task_end) {
		this.task_end = task_end;
		return this;
	}
	public String getSubj_code_div() {
		return subj_code_div;
	}
	public TaskInfo setSubj_code_div(String subj_code_div) {
		this.subj_code_div = subj_code_div;
		return this;
	}
	public String getTask_group() {
		return task_group;
	}
	public TaskInfo setTask_group(String task_group) {
		this.task_group = task_group;
		return this;
	}
}
