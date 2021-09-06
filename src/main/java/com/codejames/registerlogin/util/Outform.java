package com.codejames.registerlogin.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Outform {

	public static final String ERRCODE_SUCESS = "0";
	
	String errCode;

	String errMsg;

	Object result;
	
	public static Outform success(Object result){
		Outform out = new Outform();
		out.setErrCode("0");
		out.setErrMsg("success");
		out.setResult(result);
		return out;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}