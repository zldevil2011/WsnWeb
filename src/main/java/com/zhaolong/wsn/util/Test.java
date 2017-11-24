package com.zhaolong.wsn.util;

class Father{
	public String type = "Person";
	public void sayType(){
		System.out.println(this.type);
	}
	private String speak = "I am person";
	protected String pt = "I am protect";
}
public class Test extends Father{
	public Test(){
		System.out.println("create test object");
	}
	public void sayType(){
		System.out.println("I am child");
	}
	public static void main(String[] args) {
		Test test = new Test();
		System.out.println(test instanceof Father);
 		System.out.println(test instanceof Test);
		test.sayType();
		System.out.println(test.getInt());
		new Thread(){
			public void run(){
				System.out.println("Threading run");
				for(int i = 0; i < 1000000; ++i){
					System.out.println("Thread: " + i);
				}
			}
		}.start();
		for(int i = 0; i < 100000; ++i){
			System.out.println("I am yes:" + i);
		}
	}
	public int getInt(){
		try{
			return 1;
		}finally {
			return 2;
		}
	}
}
