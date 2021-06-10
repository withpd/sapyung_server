package kr.co.simba.impl;

public abstract class I_DataSelector extends Thread{
	public abstract int getList(int size, int start);
	public abstract String getBrand();
	public abstract void run();
}
