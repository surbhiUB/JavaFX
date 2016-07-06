/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author surbhibh3
 */
@SuppressWarnings("unchecked")
public class MakeThisWork extends Application {

    Stage window;
    BorderPane layout;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Menu");

        Menu edit = new Menu("Kind of Data");

        MenuItem properties1 = new MenuItem("ALL");
        MenuItem properties2 = new MenuItem("P(X)");
        MenuItem properties3 = new MenuItem("P(Y)");
        MenuItem properties4 = new MenuItem("P(Y|Y)");
        MenuItem properties5 = new MenuItem("P(Y|X)");
        MenuItem properties6 = new MenuItem("P(X|Y)");
        MenuItem properties7 = new MenuItem("P(X|X)");
        MenuItem properties8 = new MenuItem("LR(Y|Y)");
        MenuItem properties9 = new MenuItem("LR(Y|X)");
        MenuItem properties10 = new MenuItem("LR(X|Y)");
        MenuItem properties11 = new MenuItem("LR(X|X)");

        edit.getItems().addAll(properties1, properties2, properties3, properties4, properties5, properties6, properties7, properties8, properties9, properties10, properties11);

        properties1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                int[] countLR1 = new int[9];
                int[] countP1 = new int[9];
                int[] counthash1 = new int[9];
                int[] countT1 = new int[9];

                String users[] = {"ashlee", "catherine", "chris", "jason", "joyce", "mark", "surbhi", "tim", "vinay"};

                String homeDir = System.getProperty("user.home");
                File folder = new File(homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
                String fileName = "master_" + ".txt";
                String fileDir = homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData";
                File f = new File(fileDir, fileName);
                File[] listOfFiles = folder.listFiles();

                for (int j = 0; j < users.length; j++) {

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                inputStream = new BufferedReader(
                                        new FileReader(file));
                                String line;

                                while ((line = inputStream.readLine()) != null) {

                                    if (line.contains("Unknown")) {
                                        continue;
                                    } else {
                                        if (line.contains("LR(")) {

                                            countLR1[j]++;

                                        }
                                        if (line.contains("P(")) {

                                            countP1[j]++;

                                        }
                                        if (line.contains("?#")) {

                                            counthash1[j]++;

                                        }

                                    }
                                    countT1[j] = countLR1[j] + countP1[j] + counthash1[j];
                                }

                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        }

                    }
                    System.out.println(countT1[j]);

                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis(0d, 15000d, 10);
                    final BarChart<String, Number> bc
                            = new BarChart<String, Number>(xAxis, yAxis);
                    bc.setTitle("LRs, Ps");
                    xAxis.setLabel("User");
                    yAxis.setLabel("Data Entered(Normalized)");
                    XYChart.Series series1 = new XYChart.Series();

                    series1.setName("ALL");
                    series1.getData().add(new XYChart.Data(users[0], countT1[0]));
                    series1.getData().add(new XYChart.Data(users[1], countT1[1]));
                    series1.getData().add(new XYChart.Data(users[2], countT1[2]));
                    series1.getData().add(new XYChart.Data(users[3], countT1[3]));
                    series1.getData().add(new XYChart.Data(users[4], countT1[4]));
                    series1.getData().add(new XYChart.Data(users[5], countT1[5]));
                    series1.getData().add(new XYChart.Data(users[6], countT1[6]));
                    series1.getData().add(new XYChart.Data(users[7], countT1[7]));
                    series1.getData().add(new XYChart.Data(users[8], countT1[8]));

                    bc.getData().addAll(series1);
                    layout.setCenter(bc);
                }
            }
        });
        properties2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                int[] countP1 = new int[9];
                int[] countT1 = new int[9];

                String users[] = {"ashlee", "catherine", "chris", "jason", "joyce", "mark", "surbhi", "tim", "vinay"};

                String homeDir = System.getProperty("user.home");
                File folder = new File(homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData");

                String fileName = "master_" + ".txt";
                String fileDir = homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData";
                File f = new File(fileDir, fileName);
                File[] listOfFiles = folder.listFiles();

                for (int j = 0; j < users.length; j++) {

                    System.out.println("Entered by: " + users[j]);

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                inputStream = new BufferedReader(
                                        new FileReader(file));
                                String line;

                                while ((line = inputStream.readLine()) != null) {

                                    if (line.contains("Unknown")) {
                                        continue;
                                    } else if (line.contains("P(")) {
                                        if (line.contains(" | ")) {
                                            continue;
                                        }
                                        if (line.contains("_1)")) {
                                            countP1[j]++;
                                            System.out.println(line);
                                        }
                                    }
                                    countT1[j] = countP1[j];
                                }

                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        }

                    }
                    System.out.println(countT1[j]);

                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis(0d, 1000d, 10);
                    final BarChart<String, Number> bc
                            = new BarChart<String, Number>(xAxis, yAxis);
                    bc.setTitle("Probabilities of Diseases");
                    xAxis.setLabel("User");
                    yAxis.setLabel("Data Entered(Normalized)");
                    XYChart.Series series1 = new XYChart.Series();

                    series1.setName("P(X)");
                    series1.getData().add(new XYChart.Data(users[0], countT1[0]));
                    series1.getData().add(new XYChart.Data(users[1], countT1[1]));
                    series1.getData().add(new XYChart.Data(users[2], countT1[2]));
                    series1.getData().add(new XYChart.Data(users[3], countT1[3]));
                    series1.getData().add(new XYChart.Data(users[4], countT1[4]));
                    series1.getData().add(new XYChart.Data(users[5], countT1[5]));
                    series1.getData().add(new XYChart.Data(users[6], countT1[6]));
                    series1.getData().add(new XYChart.Data(users[7], countT1[7]));
                    series1.getData().add(new XYChart.Data(users[8], countT1[8]));

                    bc.getData().addAll(series1);
                    layout.setCenter(bc);
                }
            }
        });

        properties3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                int[] countP1 = new int[9];
                int[] countT1 = new int[9];

                String users[] = {"ashlee", "catherine", "chris", "jason", "joyce", "mark", "surbhi", "tim", "vinay"};

                String homeDir = System.getProperty("user.home");
                File folder = new File(homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData");

                String fileName = "master_" + ".txt";
                String fileDir = homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData";
                File f = new File(fileDir, fileName);
                File[] listOfFiles = folder.listFiles();

                for (int j = 0; j < users.length; j++) {

                    System.out.println("Entered by: " + users[j]);

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                inputStream = new BufferedReader(
                                        new FileReader(file));
                                String line;

                                while ((line = inputStream.readLine()) != null) {

                                    if (line.contains("Unknown")) {
                                        continue;
                                    } else if (line.contains("P(")) {
                                        if (line.contains(" | ")) {
                                            continue;
                                        }
                                        if (line.contains("ICD)")) {
                                            countP1[j]++;
                                            System.out.println(line);
                                        }
                                    }
                                    countT1[j] = countP1[j];
                                }

                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        }

                    }
                    System.out.println(countT1[j]);

                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis(0d, 1000d, 10);
                    final BarChart<String, Number> bc
                            = new BarChart<String, Number>(xAxis, yAxis);
                    bc.setTitle("Probabilities of ICDs");
                    xAxis.setLabel("User");
                    yAxis.setLabel("Data Entered(Normalized)");
                    XYChart.Series series1 = new XYChart.Series();

                    series1.setName("P(Y)");
                    series1.getData().add(new XYChart.Data(users[0], countT1[0]));
                    series1.getData().add(new XYChart.Data(users[1], countT1[1]));
                    series1.getData().add(new XYChart.Data(users[2], countT1[2]));
                    series1.getData().add(new XYChart.Data(users[3], countT1[3]));
                    series1.getData().add(new XYChart.Data(users[4], countT1[4]));
                    series1.getData().add(new XYChart.Data(users[5], countT1[5]));
                    series1.getData().add(new XYChart.Data(users[6], countT1[6]));
                    series1.getData().add(new XYChart.Data(users[7], countT1[7]));
                    series1.getData().add(new XYChart.Data(users[8], countT1[8]));

                    bc.getData().addAll(series1);
                    layout.setCenter(bc);
                }
            }
        });
        properties4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                int[] countLR1 = new int[9];
                int[] countP1XX = new int[9];
                int[] counthash1 = new int[9];
                int[] countT1 = new int[9];

                String users[] = {"ashlee", "catherine", "chris", "jason", "joyce", "mark", "surbhi", "tim", "vinay"};

                String homeDir = System.getProperty("user.home");
                File folder = new File(homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
                String fileName = "master_" + ".txt";
                String fileDir = homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData";
                File f = new File(fileDir, fileName);
                File[] listOfFiles = folder.listFiles();


                for (int j = 0; j < users.length; j++) {
                    System.out.println("Entered by: "+users[j]);

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                inputStream = new BufferedReader(
                                        new FileReader(file));
                                String line;

                                while ((line = inputStream.readLine()) != null) {

                                    if (line.contains("Unknown")) {
                                        continue;
                                    } else {

                                        if (line.contains(" | ")) {
                                            String[] parts = line.split(" | ");
                                            //System.out.println(parts[0]);
                                            //System.out.println(parts[2]);

                                            if (parts[0].contains("ICD") && parts[2].contains("ICD")) {
                                                System.out.println(" " + line);
                                                countP1XX[j]++;
                                            }
                                        }
                                        countT1[j] = countP1XX[j];
                                    }
                                }
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(countT1[j]);

                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis(0d, 1000d, 10);
                    final BarChart<String, Number> bc
                            = new BarChart<String, Number>(xAxis, yAxis);
                    bc.setTitle("Probabilities P(Y|Y)");
                    xAxis.setLabel("User");
                    yAxis.setLabel("Data Entered(Normalized)");
                    XYChart.Series series1 = new XYChart.Series();

                    series1.setName("P(Y|Y)");
                    series1.getData().add(new XYChart.Data(users[0], countT1[0]));
                    series1.getData().add(new XYChart.Data(users[1], countT1[1]));
                    series1.getData().add(new XYChart.Data(users[2], countT1[2]));
                    series1.getData().add(new XYChart.Data(users[3], countT1[3]));
                    series1.getData().add(new XYChart.Data(users[4], countT1[4]));
                    series1.getData().add(new XYChart.Data(users[5], countT1[5]));
                    series1.getData().add(new XYChart.Data(users[6], countT1[6]));
                    series1.getData().add(new XYChart.Data(users[7], countT1[7]));
                    series1.getData().add(new XYChart.Data(users[8], countT1[8]));

                    bc.getData().addAll(series1);
                    layout.setCenter(bc);
                }
            }
        });
        properties5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                int[] countLR1 = new int[9];
                int[] countP1XX = new int[9];
                int[] counthash1 = new int[9];
                int[] countT1 = new int[9];

                String users[] = {"ashlee", "catherine", "chris", "jason", "joyce", "mark", "surbhi", "tim", "vinay"};

                String homeDir = System.getProperty("user.home");
                File folder = new File(homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
                String fileName = "master_" + ".txt";
                String fileDir = homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData";
                File f = new File(fileDir, fileName);
                File[] listOfFiles = folder.listFiles();


                for (int j = 0; j < users.length; j++) {
                    System.out.println("Entered by: "+users[j]);

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                inputStream = new BufferedReader(
                                        new FileReader(file));
                                String line;

                                while ((line = inputStream.readLine()) != null) {

                                    if (line.contains("Unknown")) {
                                        continue;
                                    } else {

                                        if (line.contains(" | ")) {
                                            String[] parts = line.split(" | ");
                                            //System.out.println(parts[0]);
                                            //System.out.println(parts[2]);

                                            if (parts[0].contains("ICD") && parts[2].contains("_")) {
                                                System.out.println(line);
                                                countP1XX[j]++;
                                            }
                                        }
                                        countT1[j] = countP1XX[j];
                                    }
                                }
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(countT1[j]);

                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis(0d, 1000d, 10);
                    final BarChart<String, Number> bc
                            = new BarChart<String, Number>(xAxis, yAxis);
                    bc.setTitle("Probabilities P(Y|X)");
                    xAxis.setLabel("User");
                    yAxis.setLabel("Data Entered(Normalized)");
                    XYChart.Series series1 = new XYChart.Series();

                    series1.setName("P(Y|X)");
                    series1.getData().add(new XYChart.Data(users[0], countT1[0]));
                    series1.getData().add(new XYChart.Data(users[1], countT1[1]));
                    series1.getData().add(new XYChart.Data(users[2], countT1[2]));
                    series1.getData().add(new XYChart.Data(users[3], countT1[3]));
                    series1.getData().add(new XYChart.Data(users[4], countT1[4]));
                    series1.getData().add(new XYChart.Data(users[5], countT1[5]));
                    series1.getData().add(new XYChart.Data(users[6], countT1[6]));
                    series1.getData().add(new XYChart.Data(users[7], countT1[7]));
                    series1.getData().add(new XYChart.Data(users[8], countT1[8]));

                    bc.getData().addAll(series1);
                    layout.setCenter(bc);
                }
            }
        });
        properties6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                int[] countLR1 = new int[9];
                int[] countP1XX = new int[9];
                int[] counthash1 = new int[9];
                int[] countT1 = new int[9];

                String users[] = {"ashlee", "catherine", "chris", "jason", "joyce", "mark", "surbhi", "tim", "vinay"};

                String homeDir = System.getProperty("user.home");
                File folder = new File(homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
                String fileName = "master_" + ".txt";
                String fileDir = homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData";
                File f = new File(fileDir, fileName);
                File[] listOfFiles = folder.listFiles();


                for (int j = 0; j < users.length; j++) {
                    System.out.println("Entered by: "+users[j]);

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                inputStream = new BufferedReader(
                                        new FileReader(file));
                                String line;

                                while ((line = inputStream.readLine()) != null) {

                                    if (line.contains("Unknown")) {
                                        continue;
                                    } else {
                                            if(line.contains("LR("))
                                                continue;
                                        if (line.contains(" | ")) {
                                            String[] parts = line.split(" | ");
                                            //System.out.println(parts[0]);
                                            //System.out.println(parts[2]);

                                            if (parts[0].contains("_") && parts[2].contains("ICD")) {
                                                System.out.println(line);
                                                countP1XX[j]++;
                                            }
                                        }
                                        countT1[j] = countP1XX[j];
                                    }
                                }
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(countT1[j]);

                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis(0d, 1000d, 10);
                    final BarChart<String, Number> bc
                            = new BarChart<String, Number>(xAxis, yAxis);
                    bc.setTitle("Probabilities P(X|Y)");
                    xAxis.setLabel("User");
                    yAxis.setLabel("Data Entered(Normalized)");
                    XYChart.Series series1 = new XYChart.Series();

                    series1.setName("P(X|Y)");
                    series1.getData().add(new XYChart.Data(users[0], countT1[0]));
                    series1.getData().add(new XYChart.Data(users[1], countT1[1]));
                    series1.getData().add(new XYChart.Data(users[2], countT1[2]));
                    series1.getData().add(new XYChart.Data(users[3], countT1[3]));
                    series1.getData().add(new XYChart.Data(users[4], countT1[4]));
                    series1.getData().add(new XYChart.Data(users[5], countT1[5]));
                    series1.getData().add(new XYChart.Data(users[6], countT1[6]));
                    series1.getData().add(new XYChart.Data(users[7], countT1[7]));
                    series1.getData().add(new XYChart.Data(users[8], countT1[8]));

                    bc.getData().addAll(series1);
                    layout.setCenter(bc);
                }
            }
        });

        properties7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                int[] countLR1 = new int[9];
                int[] countP1XX = new int[9];
                int[] counthash1 = new int[9];
                int[] countT1 = new int[9];

                String users[] = {"ashlee", "catherine", "chris", "jason", "joyce", "mark", "surbhi", "tim", "vinay"};
                //int[] da=new int[9];
                //int countT=0;
                String homeDir = System.getProperty("user.home");
                File folder = new File(homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
                String fileName = "master_" + ".txt";
                String fileDir = homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData";
                File f = new File(fileDir, fileName);
                File[] listOfFiles = folder.listFiles();
                //String date=dateFormat.format(calendar.getTime());
                //String date1="2016_05_";
                //String date2="2016_06_";

                for (int j = 0; j < users.length; j++) {

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                inputStream = new BufferedReader(
                                        new FileReader(file));
                                String line;

                                while ((line = inputStream.readLine()) != null) {

                                    if (line.contains("Unknown")) {
                                        continue;
                                    } else {

                                        if (line.contains(" | ")) {
                                            String[] parts = line.split(" | ");
                                            //System.out.println(parts[0]);
                                            //System.out.println(parts[2]);

                                            if (parts[0].contains("_") && parts[2].contains("_")) {
                                                System.out.println(line);
                                                countP1XX[j]++;
                                            }
                                        }
                                        countT1[j] = countP1XX[j];
                                    }
                                }
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(countT1[j]);

                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis(0d, 1000d, 10);
                    final BarChart<String, Number> bc
                            = new BarChart<String, Number>(xAxis, yAxis);
                    bc.setTitle("Probabilities P(X|X)");
                    xAxis.setLabel("User");
                    yAxis.setLabel("Data Entered(Normalized)");
                    XYChart.Series series1 = new XYChart.Series();

                    series1.setName("P(X|X");
                    series1.getData().add(new XYChart.Data(users[0], countT1[0]));
                    series1.getData().add(new XYChart.Data(users[1], countT1[1]));
                    series1.getData().add(new XYChart.Data(users[2], countT1[2]));
                    series1.getData().add(new XYChart.Data(users[3], countT1[3]));
                    series1.getData().add(new XYChart.Data(users[4], countT1[4]));
                    series1.getData().add(new XYChart.Data(users[5], countT1[5]));
                    series1.getData().add(new XYChart.Data(users[6], countT1[6]));
                    series1.getData().add(new XYChart.Data(users[7], countT1[7]));
                    series1.getData().add(new XYChart.Data(users[8], countT1[8]));

                    bc.getData().addAll(series1);
                    layout.setCenter(bc);
                }
            }
        });
        properties8.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                int[] countLR1 = new int[9];
                int[] countP1XX = new int[9];
                int[] counthash1 = new int[9];
                int[] countT1 = new int[9];

                String users[] = {"ashlee", "catherine", "chris", "jason", "joyce", "mark", "surbhi", "tim", "vinay"};

                String homeDir = System.getProperty("user.home");
                File folder = new File(homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
                String fileName = "master_" + ".txt";
                String fileDir = homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData";
                File f = new File(fileDir, fileName);
                File[] listOfFiles = folder.listFiles();


                for (int j = 0; j < users.length; j++) {
                    System.out.println("Entered by: "+users[j]);

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                inputStream = new BufferedReader(
                                        new FileReader(file));
                                String line;

                                while ((line = inputStream.readLine()) != null) {

                                    if (line.contains("Unknown")) {
                                        continue;
                                    } else {
                                            if(line.contains("P("))
                                                continue;
                                        if (line.contains(" | ")) {
                                            String[] parts = line.split(" | ");
                                            //System.out.println(parts[0]);
                                            //System.out.println(parts[2]);

                                            if (parts[0].contains("ICD") && parts[2].contains("ICD")) {
                                                System.out.println(line);
                                                countP1XX[j]++;
                                            }
                                        }
                                        countT1[j] = countP1XX[j];
                                    }
                                }
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(countT1[j]);

                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis(0d, 1000d, 10);
                    final BarChart<String, Number> bc
                            = new BarChart<String, Number>(xAxis, yAxis);
                    bc.setTitle("Link LR(Y|Y)");
                    xAxis.setLabel("User");
                    yAxis.setLabel("Data Entered(Normalized)");
                    XYChart.Series series1 = new XYChart.Series();

                    series1.setName("LR(Y|Y)");
                    series1.getData().add(new XYChart.Data(users[0], countT1[0]));
                    series1.getData().add(new XYChart.Data(users[1], countT1[1]));
                    series1.getData().add(new XYChart.Data(users[2], countT1[2]));
                    series1.getData().add(new XYChart.Data(users[3], countT1[3]));
                    series1.getData().add(new XYChart.Data(users[4], countT1[4]));
                    series1.getData().add(new XYChart.Data(users[5], countT1[5]));
                    series1.getData().add(new XYChart.Data(users[6], countT1[6]));
                    series1.getData().add(new XYChart.Data(users[7], countT1[7]));
                    series1.getData().add(new XYChart.Data(users[8], countT1[8]));

                    bc.getData().addAll(series1);
                    layout.setCenter(bc);
                }
            }
        });
         properties9.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                int[] countLR1 = new int[9];
                int[] countP1XX = new int[9];
                int[] counthash1 = new int[9];
                int[] countT1 = new int[9];

                String users[] = {"ashlee", "catherine", "chris", "jason", "joyce", "mark", "surbhi", "tim", "vinay"};

                String homeDir = System.getProperty("user.home");
                File folder = new File(homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
                String fileName = "master_" + ".txt";
                String fileDir = homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData";
                File f = new File(fileDir, fileName);
                File[] listOfFiles = folder.listFiles();


                for (int j = 0; j < users.length; j++) {
                    System.out.println("Entered by: "+users[j]);

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                inputStream = new BufferedReader(
                                        new FileReader(file));
                                String line;

                                while ((line = inputStream.readLine()) != null) {

                                    if (line.contains("Unknown")) {
                                        continue;
                                    } else {
                                            if(line.contains("P("))
                                                continue;
                                        if (line.contains(" | ")) {
                                            String[] parts = line.split(" | ");
                                            //System.out.println(parts[0]);
                                            //System.out.println(parts[2]);

                                            if (parts[0].contains("ICD") && parts[2].contains("_")) {
                                                System.out.println(line);
                                                countP1XX[j]++;
                                            }
                                        }
                                        countT1[j] = countP1XX[j];
                                    }
                                }
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(countT1[j]);

                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis(0d, 1000d, 10);
                    final BarChart<String, Number> bc
                            = new BarChart<String, Number>(xAxis, yAxis);
                    bc.setTitle("Link LR(Y|X)");
                    xAxis.setLabel("User");
                    yAxis.setLabel("Data Entered(Normalized)");
                    XYChart.Series series1 = new XYChart.Series();

                    series1.setName("LR(Y|X)");
                    series1.getData().add(new XYChart.Data(users[0], countT1[0]));
                    series1.getData().add(new XYChart.Data(users[1], countT1[1]));
                    series1.getData().add(new XYChart.Data(users[2], countT1[2]));
                    series1.getData().add(new XYChart.Data(users[3], countT1[3]));
                    series1.getData().add(new XYChart.Data(users[4], countT1[4]));
                    series1.getData().add(new XYChart.Data(users[5], countT1[5]));
                    series1.getData().add(new XYChart.Data(users[6], countT1[6]));
                    series1.getData().add(new XYChart.Data(users[7], countT1[7]));
                    series1.getData().add(new XYChart.Data(users[8], countT1[8]));

                    bc.getData().addAll(series1);
                    layout.setCenter(bc);
                }
            }
        });
        properties10.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                int[] countLR1 = new int[9];
                int[] countP1XX = new int[9];
                int[] counthash1 = new int[9];
                int[] countT1 = new int[9];

                String users[] = {"ashlee", "catherine", "chris", "jason", "joyce", "mark", "surbhi", "tim", "vinay"};

                String homeDir = System.getProperty("user.home");
                File folder = new File(homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
                String fileName = "master_" + ".txt";
                String fileDir = homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData";
                File f = new File(fileDir, fileName);
                File[] listOfFiles = folder.listFiles();


                for (int j = 0; j < users.length; j++) {
                    System.out.println("Entered by: "+users[j]);

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                inputStream = new BufferedReader(
                                        new FileReader(file));
                                String line;

                                while ((line = inputStream.readLine()) != null) {

                                    if (line.contains("Unknown")) {
                                        continue;
                                    } else {
                                            if(line.contains("P("))
                                                continue;
                                        if (line.contains(" | ")) {
                                            String[] parts = line.split(" | ");
                                            //System.out.println(parts[0]);
                                            //System.out.println(parts[2]);

                                            if (parts[0].contains("_") && parts[2].contains("ICD")) {
                                                System.out.println(line);
                                                countP1XX[j]++;
                                            }
                                        }
                                        countT1[j] = countP1XX[j];
                                    }
                                }
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(countT1[j]);

                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis(0d, 15000d, 10);
                    final BarChart<String, Number> bc
                            = new BarChart<String, Number>(xAxis, yAxis);
                    bc.setTitle("Link LR(X|Y)");
                    xAxis.setLabel("User");
                    yAxis.setLabel("Data Entered(Normalized)");
                    XYChart.Series series1 = new XYChart.Series();

                    series1.setName("LR(X|Y)");
                    series1.getData().add(new XYChart.Data(users[0], countT1[0]));
                    series1.getData().add(new XYChart.Data(users[1], countT1[1]));
                    series1.getData().add(new XYChart.Data(users[2], countT1[2]));
                    series1.getData().add(new XYChart.Data(users[3], countT1[3]));
                    series1.getData().add(new XYChart.Data(users[4], countT1[4]));
                    series1.getData().add(new XYChart.Data(users[5], countT1[5]));
                    series1.getData().add(new XYChart.Data(users[6], countT1[6]));
                    series1.getData().add(new XYChart.Data(users[7], countT1[7]));
                    series1.getData().add(new XYChart.Data(users[8], countT1[8]));

                    bc.getData().addAll(series1);
                    layout.setCenter(bc);
                }
            }
        });
        properties11.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                int[] countLR1XX = new int[9];
                int[] countT1 = new int[9];

                String users[] = {"ashlee", "catherine", "chris", "jason", "joyce", "mark", "surbhi", "tim", "vinay"};

                String homeDir = System.getProperty("user.home");
                File folder = new File(homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
                String fileName = "master_" + ".txt";
                String fileDir = homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData";
                File f = new File(fileDir, fileName);
                File[] listOfFiles = folder.listFiles();

                for (int j = 0; j < users.length; j++) {
                    System.out.println("Entered by: " + users[j]);

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                inputStream = new BufferedReader(
                                        new FileReader(file));
                                String line;

                                while ((line = inputStream.readLine()) != null) {

                                    if (line.contains("Unknown")) {
                                        continue;
                                    } else {
                                        if (line.contains("LR(")) {
                                            if (line.contains(" | ")) {
                                                String[] parts = line.split(" | ");
                                                //System.out.println(parts[0]);
                                                //System.out.println(parts[2]);

                                                if (parts[0].contains("_") && parts[2].contains("_")) {
                                                    System.out.println(line);
                                                    countLR1XX[j]++;
                                                }
                                            }
                                        }
                                        countT1[j] = countLR1XX[j];
                                    }
                                }
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(countT1[j]);

                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis(0d, 1000d, 10);
                    final BarChart<String, Number> bc
                            = new BarChart<String, Number>(xAxis, yAxis);
                    bc.setTitle("Link LR(X|X)");
                    xAxis.setLabel("User");
                    yAxis.setLabel("Data Entered(Normalized)");
                    XYChart.Series series1 = new XYChart.Series();

                    series1.setName("LR(X|X");
                    series1.getData().add(new XYChart.Data(users[0], countT1[0]));
                    series1.getData().add(new XYChart.Data(users[1], countT1[1]));
                    series1.getData().add(new XYChart.Data(users[2], countT1[2]));
                    series1.getData().add(new XYChart.Data(users[3], countT1[3]));
                    series1.getData().add(new XYChart.Data(users[4], countT1[4]));
                    series1.getData().add(new XYChart.Data(users[5], countT1[5]));
                    series1.getData().add(new XYChart.Data(users[6], countT1[6]));
                    series1.getData().add(new XYChart.Data(users[7], countT1[7]));
                    series1.getData().add(new XYChart.Data(users[8], countT1[8]));

                    bc.getData().addAll(series1);
                    layout.setCenter(bc);
                }
            }
        });

        Menu DAMenu = new Menu("Data Analysis");

        MenuItem Daily = new MenuItem("Daily");
        MenuItem Weekly = new MenuItem("Weekly");
        MenuItem Monthly = new MenuItem("Monthly");

        DAMenu.getItems().add(Daily);
        DAMenu.getItems().add(Weekly);
        DAMenu.getItems().add(Monthly);

        Daily.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                //System.out.println("Daily Clicked");
                ObservableList<PieChart.Data> pieChartData = null;

                int[] countLR = new int[9];
                int[] countP = new int[9];
                int[] counthash = new int[9];
                int[] countT = new int[9];
                String users[] = {"ashlee", "catherine", "chris", "jason", "joyce", "mark", "surbhi", "tim", "vinay"};
                //int[] da=new int[9];
                //int countT=0;

                String homeDir = System.getProperty("user.home");

                File folder = new File(homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
                String date = dateFormat.format(calendar.getTime());

                for (int j = 0; j < users.length; j++) {

                    String fileName = "master_" + users[j] + ".txt";
                    String fileDir = homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData";
                    File f = new File(fileDir, fileName);
                    File[] listOfFiles = folder.listFiles();

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().toString().contains(date) && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                try {
                                    inputStream = new BufferedReader(
                                            new FileReader(file));
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                String line;

                                try {
                                    while ((line = inputStream.readLine()) != null) {

                                        if (line.contains("Unknown")) {
                                            continue;
                                        } else {
                                            if (line.contains("LR(")) {

                                                countLR[j]++;

                                            }
                                            if (line.contains("P(")) {

                                                countP[j]++;

                                            }
                                            if (line.contains("?#")) {

                                                counthash[j]++;

                                            }

                                        }
                                        countT[j] = countLR[j] + countP[j] + counthash[j];
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        }

                    }

                    pieChartData = FXCollections.observableArrayList(
                            new PieChart.Data("Ashlee's Collection", countT[0]),
                            new PieChart.Data("Catherine's Collection", countT[1]),
                            new PieChart.Data("Chris's Collection", countT[2]),
                            new PieChart.Data("Jason's Collection", countT[3]),
                            new PieChart.Data("Joyce's Collection", countT[4]),
                            new PieChart.Data("Mark's Collection", countT[5]),
                            new PieChart.Data("Surbhi's Collection", countT[6]),
                            new PieChart.Data("Tim's Collection", countT[7]),
                            new PieChart.Data("Vinay's Collection", countT[8]));

                    PieChart chart = new PieChart(pieChartData);
                    chart.setTitle("Probablity Statistics for : " + date);
                    layout.setCenter(chart);
                    System.out.println(countT[j]);

                }

            }

        });

        Weekly.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                //System.out.println("Weekly Clicked");

                int[] countLR1 = new int[9];
                int[] countP1 = new int[9];
                int[] counthash1 = new int[9];
                int[] countT1 = new int[9];

                int[] countLR2 = new int[9];
                int[] countP2 = new int[9];
                int[] counthash2 = new int[9];
                int[] countT2 = new int[9];

                int[] countLR3 = new int[9];
                int[] countP3 = new int[9];
                int[] counthash3 = new int[9];
                int[] countT3 = new int[9];

                int[] countLR4 = new int[9];
                int[] countP4 = new int[9];
                int[] counthash4 = new int[9];
                int[] countT4 = new int[9];
                
                int[] countLR5 = new int[9];
                int[] countP5 = new int[9];
                int[] counthash5 = new int[9];
                int[] countT5 = new int[9];
                String users[] = {"ashlee", "catherine", "chris", "jason", "joyce", "mark", "surbhi", "tim", "vinay"};
                //int[] da=new int[9];
                //int countT=0;
                String homeDir = System.getProperty("user.home");
                File folder = new File(homeDir + "/Dropbox/Pharos-Shared/GUI/MasterFiles/Master_Weekly");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
                String date = dateFormat.format(calendar.getTime());
                String date1 = "2016_05_23";
                String date2 = "2016_05_30";
                String date3 = "2016_06_06";
                String date4 = "2016_06_13";
                String date5 = "2016_06_20";
                for (int j = 0; j < users.length; j++) {

                    String fileName = "master_" + users[j] + ".txt";
                    String fileDir = homeDir + "/Dropbox/Pharos-Shared/GUI/MasterFiles/Master_Weekly";
                    File f = new File(fileDir, fileName);
                    File[] listOfFiles = folder.listFiles();

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().toString().contains(date1) && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                try {
                                    inputStream = new BufferedReader(
                                            new FileReader(file));
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                String line;

                                try {
                                    while ((line = inputStream.readLine()) != null) {

                                        if (line.contains("Unknown")) {
                                            continue;
                                        } else {
                                            if (line.contains("LR(")) {

                                                countLR1[j]++;

                                            }
                                            if (line.contains("P(")) {

                                                countP1[j]++;

                                            }
                                            if (line.contains("?#")) {

                                                counthash1[j]++;

                                            }

                                        }
                                        countT1[j] = countLR1[j] + countP1[j] + counthash1[j];
                                    }

                                } catch (IOException ex) {
                                    Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        }

                    }
                    //System.out.println("Counts  " + countT1[j]);

                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis(0d, 4000d, 10);
                    final BarChart<String, Number> bc
                            = new BarChart<String, Number>(xAxis, yAxis);
                    bc.setTitle("Weekly Analysis");
                    xAxis.setLabel("User");
                    yAxis.setLabel("Data Entered(Normalized)");

                    XYChart.Series series1 = new XYChart.Series();
                    series1.setName("23rd May-27th May");
                    series1.getData().add(new XYChart.Data(users[0], countT1[0]));
                    series1.getData().add(new XYChart.Data(users[1], countT1[1]));
                    series1.getData().add(new XYChart.Data(users[2], countT1[2]));
                    series1.getData().add(new XYChart.Data(users[3], countT1[3]));
                    series1.getData().add(new XYChart.Data(users[4], countT1[4]));
                    series1.getData().add(new XYChart.Data(users[5], countT1[5]));
                    series1.getData().add(new XYChart.Data(users[6], countT1[6]));
                    series1.getData().add(new XYChart.Data(users[7], countT1[7]));
                    series1.getData().add(new XYChart.Data(users[8], countT1[8]));

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().toString().contains(date2) && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                try {
                                    inputStream = new BufferedReader(
                                            new FileReader(file));
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                String line;

                                try {
                                    while ((line = inputStream.readLine()) != null) {

                                        if (line.contains("Unknown")) {
                                            continue;
                                        } else {
                                            if (line.contains("LR(")) {

                                                countLR2[j]++;

                                            }
                                            if (line.contains("P(")) {

                                                countP2[j]++;
                                            }
                                            if (line.contains("?#")) {

                                                counthash2[j]++;
                                            }
                                        }
                                        countT2[j] = countLR2[j] + countP2[j] + counthash2[j];
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }

                    XYChart.Series series2 = new XYChart.Series();
                    series2.setName("30th May-3rd June");
                    series2.getData().add(new XYChart.Data(users[0], countT2[0]));
                    series2.getData().add(new XYChart.Data(users[1], countT2[1]));
                    series2.getData().add(new XYChart.Data(users[2], countT2[2]));
                    series2.getData().add(new XYChart.Data(users[3], countT2[3]));
                    series2.getData().add(new XYChart.Data(users[4], countT2[4]));
                    series2.getData().add(new XYChart.Data(users[5], countT2[5]));
                    series2.getData().add(new XYChart.Data(users[6], countT2[6]));
                    series2.getData().add(new XYChart.Data(users[7], countT2[7]));
                    series2.getData().add(new XYChart.Data(users[8], countT2[8]));
                    //System.out.println("Counts 2  " + countT2[j]);

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().toString().contains(date3) && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                try {
                                    inputStream = new BufferedReader(
                                            new FileReader(file));
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                String line;

                                try {
                                    while ((line = inputStream.readLine()) != null) {

                                        if (line.contains("Unknown")) {
                                            continue;
                                        } else {
                                            if (line.contains("LR(")) {

                                                countLR3[j]++;

                                            }
                                            if (line.contains("P(")) {

                                                countP3[j]++;

                                            }
                                            if (line.contains("?#")) {

                                                counthash3[j]++;
                                            }
                                        }
                                        countT3[j] = countLR3[j] + countP3[j] + counthash3[j];
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }
                   //System.out.println("Counts 2  " + countT3[j]);
                    XYChart.Series series3 = new XYChart.Series();
                    series3.setName("6th June-10th June");
                    series3.getData().add(new XYChart.Data(users[0], countT3[0]));
                    series3.getData().add(new XYChart.Data(users[1], countT3[1]));
                    series3.getData().add(new XYChart.Data(users[2], countT3[2]));
                    series3.getData().add(new XYChart.Data(users[3], countT3[3]));
                    series3.getData().add(new XYChart.Data(users[4], countT3[4]));
                    series3.getData().add(new XYChart.Data(users[5], countT3[5]));
                    series3.getData().add(new XYChart.Data(users[6], countT3[6]));
                    series3.getData().add(new XYChart.Data(users[7], countT3[7]));
                    series3.getData().add(new XYChart.Data(users[8], countT3[8]));

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().toString().contains(date4) && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                try {
                                    inputStream = new BufferedReader(
                                            new FileReader(file));
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                String line;

                                try {
                                    while ((line = inputStream.readLine()) != null) {

                                        if (line.contains("Unknown")) {
                                            continue;
                                        } else {
                                            if (line.contains("LR(")) {

                                                countLR4[j]++;

                                            }
                                            if (line.contains("P(")) {

                                                countP4[j]++;

                                            }
                                            if (line.contains("?#")) {

                                                counthash4[j]++;

                                            }

                                        }
                                        countT4[j] = countLR4[j] + countP4[j] + counthash4[j];
                                    }

                                } catch (IOException ex) {
                                    Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        }

                    }
                    //System.out.println("Counts 4  " + countT4[j]);
                    XYChart.Series series4 = new XYChart.Series();
                    series4.setName("13th June-17th June");
                    series4.getData().add(new XYChart.Data(users[0], countT4[0]));
                    series4.getData().add(new XYChart.Data(users[1], countT4[1]));
                    series4.getData().add(new XYChart.Data(users[2], countT4[2]));
                    series4.getData().add(new XYChart.Data(users[3], countT4[3]));
                    series4.getData().add(new XYChart.Data(users[4], countT4[4]));
                    series4.getData().add(new XYChart.Data(users[5], countT4[5]));
                    series4.getData().add(new XYChart.Data(users[6], countT4[6]));
                    series4.getData().add(new XYChart.Data(users[7], countT4[7]));
                    series4.getData().add(new XYChart.Data(users[8], countT4[8]));
                    
                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().toString().contains(date5) && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                try {
                                    inputStream = new BufferedReader(
                                            new FileReader(file));
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                String line;

                                try {
                                    while ((line = inputStream.readLine()) != null) {

                                        if (line.contains("Unknown")) {
                                            continue;
                                        } else {
                                            if (line.contains("LR(")) {

                                                countLR5[j]++;

                                            }
                                            if (line.contains("P(")) {

                                                countP5[j]++;

                                            }
                                            if (line.contains("?#")) {

                                                counthash5[j]++;

                                            }

                                        }
                                        countT5[j] = countLR5[j] + countP5[j] + counthash5[j];
                                    }

                                } catch (IOException ex) {
                                    Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        }

                    }

                    XYChart.Series series5 = new XYChart.Series();
                    series5.setName("20th June-24th June");
                    series5.getData().add(new XYChart.Data(users[0], countT5[0]));
                    series5.getData().add(new XYChart.Data(users[1], countT5[1]));
                    series5.getData().add(new XYChart.Data(users[2], countT5[2]));
                    series5.getData().add(new XYChart.Data(users[3], countT5[3]));
                    series5.getData().add(new XYChart.Data(users[4], countT5[4]));
                    series5.getData().add(new XYChart.Data(users[5], countT5[5]));
                    series5.getData().add(new XYChart.Data(users[6], countT5[6]));
                    series5.getData().add(new XYChart.Data(users[7], countT5[7]));
                    series5.getData().add(new XYChart.Data(users[8], countT5[8]));
                    //System.out.println("Counts 2  " + countT2[j]);

                    bc.getData().addAll(series1, series2, series3, series4, series5);
                    layout.setCenter(bc);

                }

            }
        });
        Monthly.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                int[] countLR1 = new int[9];
                int[] countP1 = new int[9];
                int[] counthash1 = new int[9];
                int[] countT1 = new int[9];

                int[] countLR2 = new int[9];
                int[] countP2 = new int[9];
                int[] counthash2 = new int[9];
                int[] countT2 = new int[9];
                String users[] = {"ashlee", "catherine", "chris", "jason", "joyce", "mark", "surbhi", "tim", "vinay"};
                //int[] da=new int[9];
                //int countT=0;
                String homeDir = System.getProperty("user.home");
                File folder = new File(homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
                String fileName = "master_" + ".txt";
                String fileDir = homeDir + "/Dropbox/Pharos-Shared/GUI/CollectedData";
                File f = new File(fileDir, fileName);
                File[] listOfFiles = folder.listFiles();
                //String date=dateFormat.format(calendar.getTime());
                String date1 = "2016_05_";
                String date2 = "2016_06_";

                for (int j = 0; j < users.length; j++) {

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().toString().contains(date1) && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                inputStream = new BufferedReader(
                                        new FileReader(file));
                                String line;

                                while ((line = inputStream.readLine()) != null) {

                                    if (line.contains("Unknown")) {
                                        continue;
                                    } else {
                                        if (line.contains("LR(")) {

                                            countLR1[j]++;

                                        }
                                        if (line.contains("P(")) {

                                            countP1[j]++;

                                        }
                                        if (line.contains("?#")) {

                                            counthash1[j]++;

                                        }

                                    }
                                    countT1[j] = countLR1[j] + countP1[j] + counthash1[j];
                                }

                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        }

                    }
                    System.out.println(countT1[j]);

                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis(0d, 10000d, 10);
                    final BarChart<String, Number> bc
                            = new BarChart<String, Number>(xAxis, yAxis);
                    bc.setTitle("Weekly Analysis");
                    xAxis.setLabel("User");
                    yAxis.setLabel("Data Entered(Normalized)");
                    XYChart.Series series1 = new XYChart.Series();

                    series1.setName("May 2016");
                    series1.getData().add(new XYChart.Data(users[0], countT1[0]));
                    series1.getData().add(new XYChart.Data(users[1], countT1[1]));
                    series1.getData().add(new XYChart.Data(users[2], countT1[2]));
                    series1.getData().add(new XYChart.Data(users[3], countT1[3]));
                    series1.getData().add(new XYChart.Data(users[4], countT1[4]));
                    series1.getData().add(new XYChart.Data(users[5], countT1[5]));
                    series1.getData().add(new XYChart.Data(users[6], countT1[6]));
                    series1.getData().add(new XYChart.Data(users[7], countT1[7]));
                    series1.getData().add(new XYChart.Data(users[8], countT1[8]));

                    for (File file : listOfFiles) {

                        if (file.isFile() && file.getName().toString().contains(date2) && file.getName().contains(users[j])) {

                            BufferedReader inputStream = null;

                            try {
                                inputStream = new BufferedReader(
                                        new FileReader(file));
                                String line;

                                while ((line = inputStream.readLine()) != null) {

                                    if (line.contains("Unknown")) {
                                        continue;
                                    } else {
                                        if (line.contains("LR(")) {

                                            countLR2[j]++;

                                        }
                                        if (line.contains("P(")) {

                                            countP2[j]++;

                                        }
                                        if (line.contains("?#")) {

                                            counthash2[j]++;

                                        }

                                    }
                                    countT2[j] = countLR2[j] + countP2[j] + counthash2[j];
                                }

                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MakeThisWork.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        }

                    }
                    System.out.println(countT2[j]);
                    XYChart.Series series2 = new XYChart.Series();

                    series2.setName("June 2016");
                    series2.getData().add(new XYChart.Data(users[0], countT2[0]));
                    series2.getData().add(new XYChart.Data(users[1], countT2[1]));
                    series2.getData().add(new XYChart.Data(users[2], countT2[2]));
                    series2.getData().add(new XYChart.Data(users[3], countT2[3]));
                    series2.getData().add(new XYChart.Data(users[4], countT2[4]));
                    series2.getData().add(new XYChart.Data(users[5], countT2[5]));
                    series2.getData().add(new XYChart.Data(users[6], countT2[6]));
                    series2.getData().add(new XYChart.Data(users[7], countT2[7]));
                    series2.getData().add(new XYChart.Data(users[8], countT2[8]));

                    bc.getData().addAll(series1, series2);
                    layout.setCenter(bc);
                }
            }
        });

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(DAMenu, edit);
        layout = new BorderPane();
        layout.setTop(menuBar);

        Scene scene = new Scene(layout, 1000, 700);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
