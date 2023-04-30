package project;

import java.util.ArrayList;
import java.util.List;

public class LiveAction extends Film {

	public List<String> actors = new ArrayList<String>();
	
	public LiveAction() {
		
	}
	
	public LiveAction(String name, int year, String director, int rating, List<String> actors) {
		this.name = name;
		this.director = director;
		this.year = year;
		this.rating = rating;
		this.actors = actors;
	}
}
