package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilmList {

	static HashMap<String, Film> films;
	static HashMap<String, List<String>> credits;

	public FilmList() {
		films = new HashMap<String, Film>();
		credits = new HashMap<String, List<String>>();
	}

	public void addFilm(Film film) {
		films.put(film.name, film);
	}

	public Film getFilm(String name) {
		return films.get(name);
	}

	public HashMap<String, Film> getFilms() {
		return films;
	}

	public void removeFilm(String name) {

		if (films.get(name) instanceof LiveAction) {
			for (int i = 0; i < ((LiveAction) films.get(name)).actors.size(); i++) {
				removeCreditsFilm(((LiveAction) films.get(name)).actors.get(i), name);
			}
		} else if (films.get(name) instanceof Animation) {
			for (int i = 0; i < ((Animation) films.get(name)).animators.size(); i++) {
				removeCreditsFilm(((Animation) films.get(name)).animators.get(i), name);
			}
		}

		films.remove(name);
	}

	public void addCredit(String creditName, String filmName) {
		List<String> list = credits.get(creditName);
		
		if(list == null) list = new ArrayList<String>();
		
		list.add(filmName);
		credits.put(creditName, list);
	}

	public List<String> getCredit(String name) {
		return credits.get(name);
	}

	public void removeCredit(String name) {
		credits.remove(name);
	}

	public void removeCreditsFilm(String creditName, String filmName) {
		List<String> list = credits.get(creditName);
		if(list == null) return;
		
		list.remove(filmName);
		credits.put(creditName, list);

	}

	public HashMap<String, List<String>> getCredits() {
		return credits;
	}

}
