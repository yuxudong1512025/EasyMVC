package com.support.core.resolver;


/**
 * @author
 */
interface ViewResolver{
	static String show(String command, Object... arg) {
		return String.format(command,arg);
	}

}

public enum  StringViewResolver implements ViewResolver{
	/**
	 * 未登录
	 */
	NOLOGIN("Nologin","请先登录"),

	/**
	 * 登陆成功
	 */
	LOGINSUCCESS("LoginSuccess","登录成功，欢迎您 %s"),

	/**
	 * 用户没注册
	 */
	NOREGISTER("NoRegister","抱歉 %s 未注册"),

	/**
	 * 注册时输入空的用户名或者密码
	 */
	EMPTYPNAMEORPASSWORD("EmptyNameOrPassword","用户名或密码不能为空"),

	/**
	 * 用户已注册
	 */
	ERRORREGISTER("ErrorRegister","该用户已注册"),

	/**
	 * 用户注册成功
	 */
	REGISTERSUCCESS("RegisterSuccess","%s 注册成功，欢迎您的加入"),

	/**
	 * 充值成功
	 */
	DEPOSITSUCCESS("DepositSuccess","您已成功充值%s 元"),

	/**
	 * 付款成功
	 */
	PAYSUCCESS("PaySuccess","您已成功付款%s 元"),

	/**
	 * 余额不足
	 */
	PAYFAIL("PayFail","付款失败，您的余额不足 %s 元"),

	/**
	 * 密码错误
	 */
	ERRORPASSWORD("ErrorPassword","密码错误"),

	/**
	 * 错误命令
	 */
	ERROR("Error","Command Not Found");


	private String key;
	private String value;

	public String getKey(){
		return key;
	}
	public String getValue(){
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
		return  ViewResolver.show(stringViewResolver.getValue(),arg);
	}

}
