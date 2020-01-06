package com.forezp.controller;

/**
 * 返回给前台的成功提示
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午5:05:22
 */
public class SuccessTip extends Tip {
	
	public SuccessTip(){
		super.code = 200;
		super.message = "操作成功";
	}

	public SuccessTip(Object data){
		super.code = 200;
		super.data=data;
	}

	public SuccessTip(Object data, String message){
		super.code = 200;
		super.message = message;
		super.data=data;
	}

	public SuccessTip(int code, Object data, String message){
		super.code = code;
		super.message = message;
		super.data=data;
	}
}
