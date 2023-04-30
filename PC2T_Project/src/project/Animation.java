package project;

import java.util.List;
import java.util.ArrayList;

public class Animation extends Film{

	public int age;
	public List<String> animators = new ArrayList<String>();
	
	public Animation() {
		
	}
	
	public Animation(String name, int year, String director, int rating, List<String> animators,  int age) {
		this.name = name;
		this.director = director;
		this.year = year;
		this.rating = rating;
		this.age = age;
		this.animators = animators;
	
	}
	
}
