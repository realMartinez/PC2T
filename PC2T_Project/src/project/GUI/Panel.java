package project.GUI;

import project.Animation;
import project.Application;
import project.Credits;
import project.Film;
import project.FilmList;
import project.LiveAction;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.io.FileWriter;
import java.io.IOException;

public class Panel extends JPanel {
	private JList database_Contents;
	private JButton insert_Button;
	private JButton edit_Button;
	private JButton delete_Button;
	private JButton save_Button;
	private JButton load_Button;
	private JButton displayCredits_button;
	private JTextField film_name_input_field;
	private JLabel jcomp6;
	private JLabel jcomp7;
	private JLabel jcomp8;
	private JLabel jcomp9;
	private JLabel jcomp10;
	private JComboBox film_type_combo;
	private JTextField age_input_field;
	private JComboBox rating_combo;
	private JTextField actors_input_field;
	private JButton jcomp15;
	private JList actor_List;
	private JLabel jcomp19;
	private JLabel jcomp20;
	private JLabel jcomp21;
	private JTextField director_input_field;
	private JTextField year_input_field;
	private JButton jcomp24;
	private JButton jcomp25;

	final static String separator = " | ";
	
	public Panel(FilmList filmList) {
		// construct preComponents
		String[] database_ContentsItems = {};
		String[] film_type_comboItems = { "Live Action", "Animation" };
		String[] rating_comboItems = { "1/5", "2/5", "3/5", "4/5", "5/5" };
		String[] actor_ListItems = {};

		// construct components
		database_Contents = new JList(database_ContentsItems);
		insert_Button = new JButton("Insert");
		edit_Button = new JButton("Update");
		delete_Button = new JButton("Delete");
		save_Button = new JButton("Save");
		load_Button = new JButton("Load");
		displayCredits_button = new JButton("Credits");
		film_name_input_field = new JTextField(5);
		jcomp6 = new JLabel("Name:");
		jcomp7 = new JLabel("Type:");
		jcomp8 = new JLabel("Rating");
		jcomp9 = new JLabel("Credits");
		jcomp10 = new JLabel("Age");
		film_type_combo = new JComboBox(film_type_comboItems);
		age_input_field = new JTextField(5);
		rating_combo = new JComboBox(rating_comboItems);
		actors_input_field = new JTextField(5);
		jcomp15 = new JButton("Insert Credit");
		actor_List = new JList(actor_ListItems);
		jcomp19 = new JLabel("Film Database:");
		jcomp20 = new JLabel("Director");
		jcomp21 = new JLabel("Year");
		director_input_field = new JTextField(5);
		year_input_field = new JTextField(5);
		jcomp24 = new JButton("Remove Credit");
		jcomp25 = new JButton("Reset");

		// set components properties
		database_Contents.setToolTipText("List of all database film entries");
		insert_Button.setToolTipText("Inserts new film");
		delete_Button.setToolTipText("Deletes film entry from database");

		// adjust size and set layout
		setPreferredSize(new Dimension(614, 638));
		setLayout(null);

		// add components
		add(database_Contents);
		add(insert_Button);
		add(edit_Button);
		add(delete_Button);
		add(save_Button);
		add(load_Button);
		add(displayCredits_button);
		add(film_name_input_field);
		add(jcomp6);
		add(jcomp7);
		add(jcomp8);
		add(jcomp9);
		add(jcomp10);
		add(film_type_combo);
		add(age_input_field);
		add(rating_combo);
		add(actors_input_field);
		add(jcomp15);
		add(actor_List);
		add(jcomp19);
		add(jcomp20);
		add(jcomp21);
		add(director_input_field);
		add(year_input_field);
		add(jcomp24);
		add(jcomp25);

		// set component bounds (only needed by Absolute Positioning)
		database_Contents.setBounds(5, 390, 605, 245);
		insert_Button.setBounds(5, 310, 105, 45);
		edit_Button.setBounds(225, 310, 105, 45);
		delete_Button.setBounds(115, 310, 105, 45);
		save_Button.setBounds(345, 310, 105, 45);
		load_Button.setBounds(455, 310, 105, 45);
		displayCredits_button.setBounds(520, 360, 90, 30);
		film_name_input_field.setBounds(5, 35, 140, 30);
		jcomp6.setBounds(5, 10, 100, 25);
		jcomp7.setBounds(150, 10, 100, 25);
		jcomp8.setBounds(450, 10, 60, 25);
		jcomp9.setBounds(5, 85, 100, 25);
		jcomp10.setBounds(555, 10, 55, 25);
		film_type_combo.setBounds(150, 35, 105, 30);
		age_input_field.setBounds(555, 35, 55, 30);
		rating_combo.setBounds(450, 35, 100, 30);
		actors_input_field.setBounds(5, 110, 105, 30);
		jcomp15.setBounds(5, 145, 105, 25);
		actor_List.setBounds(5, 175, 605, 85);
		jcomp19.setBounds(5, 365, 100, 25);
		jcomp20.setBounds(340, 10, 100, 25);
		jcomp21.setBounds(265, 10, 100, 25);
		director_input_field.setBounds(340, 35, 105, 30);
		year_input_field.setBounds(265, 35, 70, 30);
		jcomp24.setBounds(115, 145, 120, 25);
		jcomp25.setBounds(535, 135, 70, 35);

		jcomp10.setVisible(false);
		age_input_field.setVisible(false);

		JScrollPane databaseScrollPane = new JScrollPane();
		databaseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		databaseScrollPane.add(database_Contents);
		add(databaseScrollPane);
		databaseScrollPane.setBounds(5, 390, 605, 245);
		databaseScrollPane.setViewportView(database_Contents);

		JScrollPane actorScrollPane = new JScrollPane();
		actorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		actorScrollPane.add(actor_List);
		add(actorScrollPane);
		actorScrollPane.setBounds(5, 175, 605, 85);
		actorScrollPane.setViewportView(actor_List);

		jcomp25.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				clearInputs();
			}
		});
		
	
		

		database_Contents.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {

				clearInputs();

				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {

					String selected = database_Contents.getSelectedValue().toString();
					if (selected == null)
						return;
					ArrayList<String> tempList = new ArrayList<>(Arrays.asList(selected.split(separator)));
					selected = tempList.get(0);
					
					for(int j = 1; j < tempList.size(); j++) {
						if(tempList.get(j).contains("|")) break;
						selected += " " + tempList.get(j);
					}
					
					System.out.println(selected);
					Film film = filmList.getFilm(selected);

					film_name_input_field.setText(film.name);
					year_input_field.setText(String.valueOf(film.year));
					director_input_field.setText(film.director);

					DefaultListModel<String> model = new DefaultListModel();
					if (film instanceof LiveAction) {
						rating_combo.setSelectedItem(film.rating + "/5");

						for (int i = 0; i < ((LiveAction) film).actors.size(); i++) {
							String name = ((LiveAction) film).actors.get(i);
							model.addElement(name);
							film_type_combo.setSelectedIndex(0);
						}
					} else if (film instanceof Animation) {
						rating_combo.setSelectedItem(film.rating + "/10");
						age_input_field.setText(String.valueOf(((Animation) film).age));

						for (int i = 0; i < ((Animation) film).animators.size(); i++) {
							String name = ((Animation) film).animators.get(i);
							model.addElement(name);
							film_type_combo.setSelectedIndex(1);
						}

					} else {
						throw new RuntimeException("Invalid Film Type");
					}
					actor_List.setModel(model);
				}

			}
		});

		displayCredits_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				final JFrame parent = new JFrame("Credits List");

				String[] columnNames = { "Name", "Type", "Films", };
				

				HashMap<String, List<String>> credits = filmList.getCredits();
				String dataValues[][] = new String[credits.size()][3];
				int i = 0;
				for (String key : credits.keySet()) {
					
					String element = key.toString();
					ArrayList<String> tempList = new ArrayList<>(Arrays.asList(element.split(separator)));
				
					String name = tempList.get(0);
					int j = 1;
					for( j = 1; j < tempList.size(); j++) {
						if(tempList.get(j).contains("|")) break;
						name += " " + tempList.get(j);
					}
					
					String type = tempList.get(j+1);
					
			
					
					dataValues[i][0] = name;
					dataValues[i][1] = type;
					for(String film : credits.get(key)) {
						if(dataValues[i][2] == null) {
							dataValues[i][2] = film;
						}else {
							dataValues[i][2] = dataValues[i][2] + ", " + film;
						}
						
					}
					i++;
				}
				
				JTable table = new JTable(dataValues, columnNames);
				JScrollPane scrollPane = new JScrollPane(table);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				scrollPane.add(table);
				scrollPane.setViewportView(table);
				
				
				
				table.setFillsViewportHeight(true);
				table.setBounds(30, 40, 600, 250);
				parent.add(scrollPane);
				parent.setSize(700, 300);
				parent.setVisible(true);
			}
		});

		film_type_combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (film_type_combo.getSelectedItem().toString() == "Live Action") {
					String[] rating_comboItems = { "1/5", "2/5", "3/5", "4/5", "5/5" };
					DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(rating_comboItems);
					rating_combo.setModel(model);
					jcomp10.setVisible(false);
					age_input_field.setVisible(false);
				} else if (film_type_combo.getSelectedItem().toString() == "Animation") {
					String[] rating_comboItems = { "1/10", "2/10", "3/10", "4/10", "5/10", "6/10", "7/10", "8/10",
							"9/10", "10/10" };
					DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(rating_comboItems);
					rating_combo.setModel(model);
					jcomp10.setVisible(true);
					age_input_field.setVisible(true);

				} else {
					throw new RuntimeException("Incorrect Credits type.");
				}

				if (actor_List.getModel().getSize() <= 0)
					return;

				DefaultListModel listModel = (DefaultListModel) actor_List.getModel();
				listModel.removeAllElements();
				actor_List.clearSelection();
			}
		});

		jcomp15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Credits type;
				String name;
				name = actors_input_field.getText();

				if (name.equals(""))
					return;

				if (film_type_combo.getSelectedItem().toString() == "Live Action") {
					type = Credits.ACTOR;
				} else if (film_type_combo.getSelectedItem().toString() == "Animation") {
					type = Credits.ANIMATOR;
				} else {
					throw new RuntimeException("Incorrect Credits type.");
				}
				Actions.InsertCredit(name, type, actor_List);
				actors_input_field.setText("");
			}
		});

		jcomp24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (actor_List.getSelectedIndex() == -1)
					return;
				Actions.RemoveCredit(actor_List);

			}
		});

		insert_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				Film film;

				try {
					String type = film_type_combo.getSelectedItem().toString();

					if (type == "Live Action") {
						film = new LiveAction();
					} else if (type == "Animation") {
						film = new Animation();
					} else {
						throw new RuntimeException("Invalid Movie Type");
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error:\n" + e, "PC2T Project Application",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					checkInputValidity(film);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error:\n" + e, "PC2T Project Application",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				Actions.InsertElement(film, actor_List, database_Contents, filmList, false);
				try {

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error:\n" + e, "PC2T Project Application",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				clearInputs();

			}

		});

		edit_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (database_Contents.getSelectedIndex() == -1) {
					return;
				}

				Film film = new LiveAction();

				try {
					String type = film_type_combo.getSelectedItem().toString();

					if (type == "Live Action") {
						film = new LiveAction();
					} else if (type == "Animation") {
						film = new Animation();
					} else {
						throw new RuntimeException("Invalid Movie Type");
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error:\n" + e, "PC2T Project Application",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					checkInputValidity(film);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error:\n" + e, "PC2T Project Application",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					Actions.UpdateElement(film, actor_List, database_Contents, filmList);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error:\n" + e, "PC2T Project Application",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				clearInputs();
			}
		});

		delete_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (database_Contents.getSelectedIndex() == -1) {
					return;
				}

				try {
					Actions.DeleteElement(database_Contents, filmList);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error:\n" + e, "PC2T Project Application",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				clearInputs();
			}
		});

		save_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (database_Contents.getSelectedIndex() == -1) {
					return;
				}

				try {
					String name = database_Contents.getSelectedValue().toString();
					ArrayList<String> tempList = new ArrayList<>(Arrays.asList(name.split(separator)));
					name = tempList.get(0);
					
					for(int j = 1; j < tempList.size(); j++) {
						if(tempList.get(j).contains("|")) break;
						name += " " + tempList.get(j);
					}
					
					Film film = filmList.getFilm(name);

					JSONObject obj = new JSONObject();

					obj.put("name", film.name);

					obj.put("year", film.year);
					obj.put("director", film.director);
					obj.put("rating", film.rating);

					JSONArray credits = new JSONArray();

					if (film instanceof LiveAction) {
						obj.put("type", "LiveAction");
						for (int i = 0; i < ((LiveAction) film).actors.size(); i++) {
							credits.add(((LiveAction) film).actors.get(i));
						}
						obj.put("actors", credits);

					} else if (film instanceof Animation) {
						obj.put("type", "Animation");
						obj.put("age", ((Animation) film).age);
						for (int i = 0; i < ((Animation) film).animators.size(); i++) {
							credits.add(((Animation) film).animators.get(i));
						}
						obj.put("animators", credits);
					} else {
						throw new RuntimeException("Invalid Movie Type");
					}

					FileWriter file = new FileWriter("saves/" + film.name + ".txt");
					try {
						file.write(obj.toJSONString());
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							file.flush();
							file.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error:\n" + e, "PC2T Project Application",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

			}
		});

		load_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Film film = Actions.LoadFile();

				if (film == null)
					return;
				
				clearInputs();
				
				if (film instanceof LiveAction) {
					for (int i = 0; i < ((LiveAction) film).actors.size(); i++) {
						Actions.InsertCredit(((LiveAction) film).actors.get(i), Credits.ACTOR, actor_List);
					}
				} else if (film instanceof Animation) {
					for (int i = 0; i < ((Animation) film).animators.size(); i++) {
						Actions.InsertCredit(((Animation) film).animators.get(i), Credits.ANIMATOR, actor_List);
					}
				}
				Actions.InsertElement(film, actor_List, database_Contents, filmList, true);
				
				clearInputs();
			}
		});

	}

	public void clearInputs() {
		film_name_input_field.setText("");
		year_input_field.setText("");
		director_input_field.setText("");
		age_input_field.setText("");
		actors_input_field.setText("");
		actor_List.clearSelection();
		film_type_combo.setSelectedIndex(0);
		rating_combo.removeAllItems();
		rating_combo.addItem(actor_List);
		String[] rating_comboItems = { "1/5", "2/5", "3/5", "4/5", "5/5" };
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(rating_comboItems);
		rating_combo.setModel(model);
		rating_combo.setSelectedIndex(0);

		if (actor_List.getModel().getSize() <= 0)
			return;
		DefaultListModel listModel = (DefaultListModel) actor_List.getModel();
		listModel.removeAllElements();

	}

	public void checkInputValidity(Film film) {

		String name = film_name_input_field.getText();
		if (name.contains("|"))
			throw new RuntimeException("Forbidden symbol: |");

		if (name.equals(""))
			throw new RuntimeException("Name Not Set");

		int year = Integer.valueOf(year_input_field.getText());
		try {
			year = Integer.valueOf(year_input_field.getText());
		} catch (NumberFormatException e) {
			throw new RuntimeException("year Not Set");
		}

		String director = director_input_field.getText();
		if (director.contains("|"))
			throw new RuntimeException("Forbidden symbol: |");
		if (director.equals(""))
			throw new RuntimeException("Director Not Set");

		String str = rating_combo.getSelectedItem().toString();
		ArrayList<String> tempList = new ArrayList<>(Arrays.asList(str.split("/")));
		int rating = 0;
		try {
			rating = Integer.valueOf(tempList.get(0));
		} catch (NumberFormatException e) {
			throw new RuntimeException("Rating Not Set");
		} finally {
			if (film instanceof LiveAction) {
				if (rating < 0 || rating > 5) {
					throw new RuntimeException("Incorrect Rating Format");
				}
			} else if (film instanceof Animation) {
				if (rating < 0 || rating > 10) {
					throw new RuntimeException("Incorrect Rating Format");
				}
			}
		}
		int age = 0;
		if (film instanceof Animation) {
			try {
				age = Integer.valueOf(age_input_field.getText());
			} catch (NumberFormatException e) {
				if (!film_type_combo.getSelectedItem().toString().equals("Live Action")) {
					throw new RuntimeException("Age Not Set");
				}
			}
		}

		if (film instanceof LiveAction) {

		} else if (film instanceof Animation) {
			((Animation) film).age = age;
		} else {
			throw new RuntimeException("Invalid Movie Type");
		}

		film.name = name;
		film.year = year;
		film.director = director;
		film.rating = rating;

	}
}