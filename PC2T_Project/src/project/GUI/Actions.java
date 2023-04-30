package project.GUI;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import java.util.List;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import project.Animation;
import project.Credits;
import project.Film;
import project.FilmList;
import project.LiveAction;

public class Actions {

	final static String separator = " | ";

	public static void InsertCredit(String name, Credits credit, JList actor_list) {

		ArrayList<String> tempList = new ArrayList<>(Arrays.asList(name.split(separator)));
		name = tempList.get(0);

		for (int j = 1; j < tempList.size(); j++) {
			if (tempList.get(j).contains("|"))
				break;
			name += " " + tempList.get(j);
		}

		if (name.contains("|"))
			throw new RuntimeException("Forbidden symbol: |");

		DefaultListModel<String> model = new DefaultListModel();
		for (int i = 0; i < actor_list.getModel().getSize(); i++) {
			String element = actor_list.getModel().getElementAt(i).toString();
			if (element.equals(name + " | " + credit.getType()))
				continue;
			model.addElement(element);
		}
	
		model.addElement(name + " | " + credit.getType());
		actor_list.setModel(model);

	}

	public static void RemoveCredit(JList actor_list) {
		DefaultListModel<String> model = new DefaultListModel();
		String name = actor_list.getSelectedValue().toString();

		for (int i = 0; i < actor_list.getModel().getSize(); i++) {
			String element = actor_list.getModel().getElementAt(i).toString();
			model.addElement(element);
		}

		model.removeElement(actor_list.getSelectedValue());
		actor_list.setModel(model);

	}

	public static void InsertElement(Film film, JList actor_list, JList database_list, FilmList filmList, boolean isLoaded) {

		DefaultListModel<String> model = new DefaultListModel();

		for (int i = 0; i < database_list.getModel().getSize(); i++) {
			String element = database_list.getModel().getElementAt(i).toString();
			List<String> tempList = Arrays.asList(element.split(separator));

			String name = tempList.get(0);

			for (int j = 1; j < tempList.size(); j++) {
				if (tempList.get(j).contains("|"))
					break;
				name += " " + tempList.get(j);
			}

			if (name.equals(film.name))
				continue;
			model.addElement(element);
		}

		List<String> credits = new ArrayList<String>();

		for (int i = 0; i < actor_list.getModel().getSize(); i++) {
			String element = actor_list.getModel().getElementAt(i).toString();
			credits.add(element);

			HashMap<String, Film> films = filmList.getFilms();
			if(!isLoaded) {
				if (film instanceof LiveAction) {
					((LiveAction) film).actors.add(element);
				} else if (film instanceof Animation) {
					((Animation) film).animators.add(element);
				} else {
					throw new RuntimeException("Incorrect Film Type");
				}
			}

			filmList.addCredit(element, film.name);
			films.put(film.name, film);
		}

		filmList.addFilm(film);

		if (film instanceof LiveAction) {
			model.addElement(String.format("%s | %s | %d | %s | %s | %s", film.name, "Live Action", film.year,
					film.director, film.rating + "/5", credits.toString()));
			database_list.setModel(model);
		} else if (film instanceof Animation) {
			model.addElement(String.format("%s | %s | %d | %s | %s | %d | %s", film.name, "Animation", film.year,
					film.director, film.rating + "/10", ((Animation) film).age, credits.toString()));
			database_list.setModel(model);

		} else {
			throw new RuntimeException("Invalid Film Type");
		}

	}

	public static void UpdateElement(Film film, JList actor_list, JList database_list, FilmList filmList) {

		String selected = database_list.getSelectedValue().toString();
		ArrayList<String> tempList = new ArrayList<>(Arrays.asList(selected.split(separator)));
		selected = tempList.get(0);

		for (int j = 1; j < tempList.size(); j++) {
			if (tempList.get(j).contains("|"))
				break;
			selected += " " + tempList.get(j);
		}

		filmList.removeFilm(selected);
		DeleteElement(database_list, filmList);
		InsertElement(film, actor_list, database_list, filmList, false);

	}

	public static void DeleteElement(JList database_list, FilmList filmList) {

		DefaultListModel<String> model = new DefaultListModel();
		for (int i = 0; i < database_list.getModel().getSize(); i++) {
			String element = database_list.getModel().getElementAt(i).toString();
			model.addElement(element);
		}
		String selected = database_list.getSelectedValue().toString();
		ArrayList<String> tempList = new ArrayList<>(Arrays.asList(selected.split(separator)));

		String name = tempList.get(0);

		for (int j = 1; j < tempList.size(); j++) {
			if (tempList.get(j).contains("|"))
				break;
			name += " " + tempList.get(j);
		}

		filmList.removeFilm(name);

		model.removeElement(database_list.getSelectedValue());
		database_list.setModel(model);

	}

	public static Film LoadFile() {
		File file = null;
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir") + "/saves");

		int returnVal = jfc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = jfc.getSelectedFile();
		}

		JSONParser parser = new JSONParser();
		Film film;

		try {
			Object obj = parser.parse(new FileReader(file));

			JSONObject jsonObject = (JSONObject) obj;

			if (((String) jsonObject.get("type")).equals("LiveAction")) {
				film = new LiveAction();
			} else if (((String) jsonObject.get("type")).equals("Animation")) {
				film = new Animation();
			} else {
				throw new RuntimeException("Invalid film type in JSON file!");
			}
			film.name = (String) jsonObject.get("name");
			film.year = Integer.parseInt(jsonObject.get("year").toString());
			film.director = (String) jsonObject.get("director");
			film.rating = Integer.parseInt(jsonObject.get("rating").toString());

			if (film instanceof LiveAction) {
				JSONArray actors = (JSONArray) jsonObject.get("actors");

				for (int i = 0; i < actors.size(); i++) {
					((LiveAction) film).actors.add(actors.get(i).toString());
				}
			} else if (film instanceof Animation) {
				JSONArray animators = (JSONArray) jsonObject.get("animators");
				((Animation) film).age = Integer.parseInt(jsonObject.get("age").toString());

				for (int i = 0; i < animators.size(); i++) {
					((Animation) film).animators.add(animators.get(i).toString());

				}
			}

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Error:\n" + e, "PC2T Project Application", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		return film;

	}

};
