package com.project.prsystem.vo;

import java.util.Date;

public class ProfessorInfo {
	protected int 		prof_id;
	protected String 	prof_name;
	protected String 	prof_email;
	protected String 	prof_pwd;
	protected String	prof_phone;
	protected String	prof_auth;
	protected Date		prof_cre_date;
	protected Date		prof_mod_date;

	@Override
	public String toString() {
		return  "{" + "\"login\"" + ":" + "["
				+ "{" + "\"prof_id\""+":" + getProfId()  + ","
				+ "\"prof_name\""+":"+ "\"" +getProfName()+ "\"" + ","
				+ "\"prof_email\""+":" +"\"" +getProfEmail()+ "\"" + ","
				+ "\"prof_pwd\""+":" +"\"" +getProfPwd()+ "\"" + ","
				+ "\"prof_phone\""+":" +"\"" +getProfPhone()+ "\"" + ","
				+ "\"prof_auth\""+":" +"\"" +getProfAuth()+ "\"" + ","
				+ "\"prof_cre_date\""+":" +"\"" +getProfCreDate()+ "\"" + ","
				+ "\"prof_mod_date\""+":" +"\"" +getProfModDate() +"\""+ "}]}";
	}

	public int getProfId() {
		return prof_id;
	}
	public ProfessorInfo setProfId(int prof_id) {
		this.prof_id = prof_id;
		return this;
	}

	public String getProfName() {
		return prof_name;
	}
	public ProfessorInfo setProfName(String prof_name) {
		this.prof_name = prof_name;
		return this;
	}
	public String getProfEmail() {
		return prof_email;
	}
	public ProfessorInfo setProfEmail(String prof_email) {
		this.prof_email = prof_email;
		return this;
	}
	public String getProfPwd() {
		return prof_pwd;
	}
	public ProfessorInfo setProfPwd(String prof_pwd) {
		this.prof_pwd = prof_pwd;
		return this;
	}
	public String getProfPhone() {
		return prof_phone;
	}
	public ProfessorInfo setProfPhone(String prof_phone) {
		this.prof_phone = prof_phone;
		return this;
	}
	public String getProfAuth() {
		return prof_auth;
	}
	public ProfessorInfo setProfAuth(String prof_auth) {
		this.prof_auth = prof_auth;
		return this;
	}
	public Date getProfCreDate() {
		return prof_cre_date;
	}
	public ProfessorInfo setProfCreDate(Date prof_cre_date) {
		this.prof_cre_date = prof_cre_date;
		return this;
	}
	public Date getProfModDate() {
		return prof_mod_date;
	}
	public ProfessorInfo setProfModDate(Date prof_mod_date) {
		this.prof_mod_date = prof_mod_date;
		return this;
	}
}
