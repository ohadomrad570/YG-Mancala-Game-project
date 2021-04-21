package myGame;

// This class realize the creator side of the factory design pattern
// Create a product using hash map in O(1) time by using O(n) place

// for a generic product type
// At this project their are to abstract types of products:
//		1) The algorithm - the pitChooser - MiniMax or alpha beta
//		2) The evaluation function

import java.util.HashMap;
import java.util.Map;

public class GenericFactory<Product>  {
	
	private interface Creator<Product>{
		public Product create(); // no unhandled exceptions
	}
	
	private Map<String,Creator<Product>> map;
	
	public GenericFactory(){
		map=new HashMap<>();
	}
	
	public void insertProduct(String key, Class<? extends Product> c) {
		this.map.put(key, ()->{
			try {
				return c.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		});
	}	
	
	public Product getNewProduct(String key){
		if(map.containsKey(key))
			return map.get(key).create();
		return null;
	}
}
