package com.intuit.developer.helloworld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class TestClass {
	
	Data aNode = new Data(1, "hello", 0);
	Data bNode = new Data(2, "hello", 1);

	Data cNode = new Data(3, "hello", 2);

	Data dNode = new Data(4, "hello", 2);

	Data eNode = new Data(5, "hello", 0);
	
	public static void main(String[] args) {
		TestClass testClass = new TestClass();
		testClass.getResult();
	}
	
	public DataFinal getResult() {
	
		List<Data> inputData = new ArrayList<>();
		inputData.addAll(Arrays.asList(aNode, bNode, cNode, dNode, eNode));

		
		Map<Integer, DataFinal> map = new HashMap<Integer, DataFinal>();
		for (Data data : inputData) {
			if(data.getParentId()==0) {
				DataFinal dataFinal = new DataFinal();
				dataFinal.setId(data.getId());
				dataFinal.setTitle(data.getTitle());
				dataFinal.setParentId(data.getParentId());
				dataFinal.setChildernList(Collections.EMPTY_LIST);
				map.put(data.getId(), dataFinal);
			} else {
				DataFinal dataFinal = new DataFinal();
				dataFinal.setId(data.getId());
				dataFinal.setTitle(data.getTitle());
				dataFinal.setParentId(data.getParentId());
				dataFinal.setChildernList(Collections.EMPTY_LIST);
				map.get(data.getParentId()).getChildernList().add(dataFinal);
				map.put(data.getId(), dataFinal);
				}
		}
		System.out.println(map);
		return null;
	}
	
	public class Data {
		
		int id;
		String title;
		int parentId;
		public Data() {};
		public Data(int id, String title, int parent) {
			Data data=new Data();
			data.setId(id);
			data.setParentId(parent);
			data.setTitle(title);
			
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public int getParentId() {
			return parentId;
		}
		public void setParentId(int parentId) {
			this.parentId = parentId;
		}
		
		
	}
	
	
public class DataFinal {
		
		int id;
		String title;
		int parentId;
		List<DataFinal> childernList;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public int getParentId() {
			return parentId;
		}
		public void setParentId(int parentId) {
			this.parentId = parentId;
		}
		public List<DataFinal> getChildernList() {
			return childernList;
		}
		public void setChildernList(List<DataFinal> childernList) {
			this.childernList = childernList;
		}
		
		
		
		
	}
}
