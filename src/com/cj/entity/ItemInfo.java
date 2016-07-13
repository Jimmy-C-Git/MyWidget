package com.cj.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ItemInfo extends Properties{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6605699808074178416L;

	@Override
	public Object setProperty(String name, String value) {
		// 
		return super.setProperty(name, value == null ? "" : value);
	}
	
	@Override
	public String getProperty(String name) {
		String value = super.getProperty(name);
		return value == null ? "" : value;
	}
	
	public void parse(Node mInfoNode) 
	{
		if(!mInfoNode.hasChildNodes())return ;
		NodeList nodeList=mInfoNode.getChildNodes();
		for(int i=0;i<nodeList.getLength();i++)
		{
			Node node =nodeList.item(i);
			this.setProperty(node.getNodeName(), node.getTextContent());
		}
	}
	
	public ArrayList<String> getKeys(){
		ArrayList<String>ans =new ArrayList<String>();
		Iterator<Entry<Object, Object>> it = entrySet().iterator(); 
		while (it.hasNext()) {  
            Entry<Object, Object> entry = it.next();  
            Object key = entry.getKey();  
            ans.add(key.toString());
		}
		return null;
		
	} 
}
