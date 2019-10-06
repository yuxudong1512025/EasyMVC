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
	NoLogin("Nologin","请先登录"),
	LoginSuccess("LoginSuccess","登录成功，欢迎您 %s"),
	NoRegister("NoRegister","抱歉 %s 未注册"),
	RegisterSuccess("RegisterSuccess","%s 注册成功，欢迎您的加入"),
	DepositSuccess("DepositSuccess","您已成功充值%s 元"),
	PaySuccess("PaySuccess","您已成功付款%s 元");


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
		return null;
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

	public static String show(String command, Object... arg){
		return ViewResolver.show(StringViewResolver.find(command).getvalue(),arg);
	}

	public static void main(String[] args) {
		System.out.println(StringViewResolver.containsCommand("paySuccess"));
		System.out.println(StringViewResolver.show(PaySuccess.value,100.11));
	}
}
