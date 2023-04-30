package project;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;

import project.GUI.Panel;

public class Application {

	public static void main(String[] args) {
		FilmList filmList = new FilmList();
		String databaseLoginField[];
		databaseLoginField = fetchDatabase(filmList);
		buildUI(filmList, databaseLoginField);

	}

	public static void buildUI(FilmList filmList, String[] databaseLoginField) {

		JFrame frame = new JFrame("PC2T Project Application");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().add(new Panel(filmList));
		frame.pack();
		try {
			frame.setIconImage(ImageIO.read(new File("resources/icon.png")));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Error: Program Icon not found!\n" + e, "PC2T Project Application",
					JOptionPane.ERROR_MESSAGE);
		}
		frame.setVisible(true);

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to close this window?",
						"Close Window?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (result == JOptionPane.YES_OPTION) {
					try {
						updateDatabase(filmList, databaseLoginField[0], databaseLoginField[1], databaseLoginField[2],
								databaseLoginField[3]);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frame, "Error: Database update Failed!\n" + e,
								"PC2T Project Application", JOptionPane.ERROR_MESSAGE);
					}
					System.exit(0);
				}
			}
		});
	}

	public static String[] fetchDatabase(FilmList filmList) {
		JTextField urlField = new JTextField(10);
		JTextField tableField = new JTextField(10);
		JTextField loginField = new JTextField(10);
		JPasswordField passwordField = new JPasswordField(10);
		
		urlField.setText("jdbc:sqlite:resources/database.db");
		tableField.setText("film");

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("SQL URL:"));
		myPanel.add(urlField);
		myPanel.add(Box.createHorizontalStrut(15));
		myPanel.add(new JLabel("Table name:"));
		myPanel.add(tableField);
		myPanel.add(Box.createHorizontalStrut(15));
		myPanel.add(new JLabel("Login:"));
		myPanel.add(loginField);
		myPanel.add(Box.createHorizontalStrut(15));
		myPanel.add(new JLabel("Password:"));
		myPanel.add(passwordField);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter SQL URL and Login details",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.CANCEL_OPTION)
			return null;

		String url = urlField.getText();
		String table = tableField.getText();
		String login = loginField.getText();
		String pwd = new String(passwordField.getPassword());

		

		
		connectDatabase(filmList, url, table, login, pwd);

		String[] loginInfo = { url, table, login, pwd };
		return loginInfo;
	}

	public static void connectDatabase(FilmList filmList, String url, String table, String login, String pwd) {
		Connection con;
		try {
			con = DriverManager.getConnection(url, login, pwd);

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + table);
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
			}

			con.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:\n" + e, "PC2T Project Application", JOptionPane.ERROR_MESSAGE);
			return;
		}

	}

	public static void updateDatabase(FilmList filmList, String url, String table, String login, String pwd) {
		Connection con;
		try {
			con = DriverManager.getConnection(url, login, pwd);
			HashMap<String, Film> films = filmList.getFilms();
			for (String key : filmList.getFilms().keySet()) {

				Statement stmt = con.createStatement();
				ResultSet initialRs = stmt.executeQuery("SELECT " + key + " FROM " + table);
				Film film = films.get(key);
				String type = "";
				if (film instanceof LiveAction)
					type = "LiveAction";
				else if (film instanceof Animation)
					type = "Animation";
				else
					throw new RuntimeException("Incorrect film type!");

				if (initialRs == null) {
					stmt.close();
					stmt = con.createStatement();
					if (film instanceof LiveAction) {
						stmt.executeQuery("INSERT " + " INTO " + table
								+ "(film, type, year, director, rating, age, credits) " + "VALUES (" + key + type
								+ film.year + film.director + film.rating + ((LiveAction)film).actors + ")");
					} else if (film instanceof Animation) {
						stmt.executeQuery("INSERT " + " INTO " + table
								+ "(film, type, year, director, rating, age, credits) " + "VALUES (" + key + type
								+ film.year + film.director + film.rating + ((Animation)film).age + ((Animation)film).animators + ")");
					}
					stmt.close();
				} else {
					stmt.close();
					stmt = con.createStatement();
					
					
					stmt = con.createStatement();
					if (film instanceof LiveAction) {
						stmt.executeQuery("UPDATE " + table
								+ "SET ( {key}, {type},  {film.year}, {film.director}, {film.rating}, 0,  {((LiveAction)film).actors} ) WHERE (film = " + key + ")");
					} else if (film instanceof Animation) {
						stmt.executeQuery("UPDATE " + table
								+ "SET ( {key}, {type},  {film.year}, {film.director}, {((Animation)film).age}, {film.year},  {((LiveAction)film).animators} ) WHERE (film = " + key + ")");
					}
					
					stmt.close();
				}

			}

			con.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:\n" + e, "PC2T Project Application", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

}
