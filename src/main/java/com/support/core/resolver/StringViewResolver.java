package com.support.core.resolver;

import com.publicgroup.util.log.LogFactory;



/**
 * @author
 */
interface ViewResolver{
	static String show(String command, Object... arg) {
		return String.format(command,arg);
	}

}

public enum  StringViewResolver implements ViewResolver{
	NOLOGIN("Nologin","请先登录"),
	LOGINSUCCESS("LoginSuccess","登录成功，欢迎您 %s"),
	NOREGISTER("NoRegister","抱歉 %s 未注册"),
	ERRORREGISTER("ErrorRegister","该用户已注册"),
	REGISTERSUCCESS("RegisterSuccess","%s 注册成功，欢迎您的加入"),
	DEPOSITSUCCESS("DepositSuccess","您已成功充值%s 元"),
	PAYSUCCESS("PaySuccess","您已成功付款%s 元"),
	PAYFAIL("PayFail","付款失败，您的余额不足 %s 元"),
	ERRORPASSWORD("ErrorPassword","密码错误"),
	ERROR("Error","Command Not Found");




	private String key;
	private String value;

	public String getKey(){
		return key;
	}
	public String getvalue(){
		return value;
	}
	public static StringViewResolver find(String key){
		for(StringViewResolver s:StringViewResolver.values()){
			if(s.getKey().equals(key)){
				return s;
			}
		}
		return StringViewResolver.ERROR;
	}

	StringViewResolver(String key, String value) {
		this.key=key;
		this.value=value;
	}

	public static boolean containsCommand(String command){
		for (StringViewResolver s:StringViewResolver.values()){
			if(s.getKey().equals(command)){
				return true;
			}
		}
		return false;
	}

	public static String show(StringViewResolver stringViewResolver, Object... arg){
		return  ViewResolver.show(stringViewResolver.getvalue(),arg);
	}

	public static void main(String[] args) {
		String out=ViewResolver.show(PAYSUCCESS.value,100.11);
		String Sout=StringViewResolver.show(PAYSUCCESS,100.11);
		LogFactory.getGlobalLog().info(Sout);
		LogFactory.getGlobalLog().info(out);
	}
}
