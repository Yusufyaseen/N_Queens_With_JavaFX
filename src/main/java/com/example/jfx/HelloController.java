package com.example.jfx;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;

public class HelloController {

    @FXML
    private TextField nameField;
    public int num;
    public int[][] nn;
    private Stage stage;

    @FXML
    public void setScreen(ActionEvent event) {

        System.out.println(num);
        Group root = new Group();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, 2000, 2000);

        Button back = new Button();
        back.setMinWidth(1200);
        back.setText("Back");
        back.setLayoutX(5);
        back.setLayoutY(5);
        back.setOnAction((ActionEvent e) -> {
            try {
                backToPage(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        root.getChildren().add(back);


        LinkedList<StackPane> ls = new LinkedList<>();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().isAlive());

                Platform.runLater(

                        () -> {
                            root.getChildren().removeAll(ls);
                            ls.clear();
                            boolean a = false;
                            for (int i = 0; i < nn.length; i++) {
                                for (int j = 0; j < nn.length; j++) {
                                    Rectangle rc = new Rectangle();
                                    if (a) {
                                        rc.setFill(Color.WHITE);
                                    } else {
                                        rc.setFill(Color.LIGHTGREY);
                                    }

                                    rc.setArcHeight(15);
                                    rc.setStrokeWidth(1.5);
                                    rc.setStroke(Color.BLACK);
                                    rc.setArcWidth(15);
                                    rc.setHeight(30);
                                    rc.setWidth(40);

                                    Text txt = new Text();
                                    Font font = new Font(25);
                                    txt.setFont(font);
                                    txt.setFill(Color.BLACK);

                                    if ((nn[i][j] == 1)) {
                                        txt.setText("♛");
                                    } else {
                                        txt.setText("");
                                    }
                                    StackPane sp = new StackPane();
                                    sp.getChildren().addAll(rc, txt);
                                    sp.setLayoutX((j + 1) * 50);
                                    sp.setLayoutY((i + 1) * 50);
                                    ls.add(sp);
                                    Platform.runLater(
                                            () -> root.getChildren().add(sp)
                                    );
                                    a = !a;
                                }
                                a = (num % 2 == 0) ? !a : a;
                            }
                            System.out.println(root.getChildren().size());
                        }
                );
            }
        };

        Timer timer = new Timer();
        long delay = 10;
        long freq = 1000;
        timer.schedule(task, delay, freq);

        stage.setOnCloseRequest((e) -> {
            Platform.exit();
            System.exit(0);
        });
        scrollPane.setContent(root);

        scrollPane.setPannable(true);
        scrollPane.setMinWidth(1200);
        gridPane.getChildren().add(scrollPane);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPadding(new Insets(30));
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void backToPage(ActionEvent event) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void switchToPage(ActionEvent event) throws IOException {
        num = (nameField.getText() != "") ? Integer.valueOf(nameField.getText()) : 4;
        nn = new int[num][num];
        for (int i = 0; i < nn.length; i++) {
            for (int j = 0; j < nn.length; j++) {
                nn[i][j] = 0;
            }
        }
        setScreen(event);
        Runnable task = () -> {
            try {
                solveNQUtil(0)solveNQUtil;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };
        Thread thread = new Thread(task);

        thread.start();

    }

    boolean isSafe(int row, int col) {
        int i, j;

        /* Check this row on left side */
        for (i = 0; i < col; i++)
            if (nn[row][i] == 1)
                return false;

        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (nn[i][j] == 1)
                return false;

        for (i = row, j = col; j >= 0 && i < num; i++, j--)
            if (nn[i][j] == 1)
                return false;
        return true;
    }

    boolean solveNQUtil(int col) throws InterruptedException {

        if (col >= num)
            return true;

        for (int i = 0; i < num; i++) {

            if (isSafe(i, col)) {
                nn[i][col] = 1;
                Thread.sleep(200);

                if (solveNQUtil(col + 1))
                    return true;
                Thread.sleep(200);
                nn[i][col] = 0;
                Thread.sleep(200);

            }
        }

        return false;
    }

}