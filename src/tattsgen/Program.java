package tattsgen;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class Program extends Application {

    TableView result = new TableView();
    ObservableList<Draw> data = FXCollections.observableArrayList();
    static final ForkJoinPool pool = new ForkJoinPool(1);
    
   // private static NumberFormat numberFormatter = NumberFormat.getNumberInstance();
    
    Button buttonGenerate = new Button("Generate combinations");
    Label tattsArchiveFileLabel;    
    Alert notTattsArchiveSet;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TattsGen v1.0");
        Scene scene = new Scene(new Group());
        VBox vbox = addVBox();
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY), new Runnable() {
    @Override
    public void run() {
       
            ObservableList<TablePosition> selectedCells = result.getSelectionModel().getSelectedCells();
            StringBuilder tabSeparateString = new StringBuilder();
            selectedCells.stream().map((position) -> {
                int row = position.getRow();
                Draw draw_row = data.get(row);
                return draw_row;
            }).forEach((drow_row) -> {
                if (tabSeparateString.length() != 0) {
                     tabSeparateString.append("\n");
                }
                tabSeparateString
                        .append(drow_row.getFirstBall())
                        .append("\t")
                        .append(drow_row.getSecondBall())
                        .append("\t")
                        .append(drow_row.getThirdBall())
                        .append("\t")
                        .append(drow_row.getFourthBall())
                        .append("\t")
                        .append(drow_row.getFifthBall())
                        .append("\t")
                        .append(drow_row.getSixthBall());
            });
            ClipboardContent content = new ClipboardContent();
            content.putString(tabSeparateString.toString());
            Clipboard.getSystemClipboard().setContent(content);
        }
    });
            
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(8);
        vbox.setStyle("-fx-background-color: #336699;");

        TableColumn firstBallCol = new TableColumn("Num1");
        firstBallCol.setCellValueFactory(new PropertyValueFactory<>("firstBall"));
        TableColumn secondBallCol = new TableColumn("Num2");
        secondBallCol.setCellValueFactory(new PropertyValueFactory<>("secondBall"));
        TableColumn thirdBallCol = new TableColumn("Num3");
        thirdBallCol.setCellValueFactory(new PropertyValueFactory<>("thirdBall"));
        TableColumn fourthBallCol = new TableColumn("Num4");
        fourthBallCol.setCellValueFactory(new PropertyValueFactory<>("fourthBall"));
        TableColumn fifthBallCol = new TableColumn("Num5");
        fifthBallCol.setCellValueFactory(new PropertyValueFactory<>("fifthBall"));
        TableColumn sixthBallCol = new TableColumn("Num6");
        sixthBallCol.setCellValueFactory(new PropertyValueFactory<>("sixthBall"));
        
        
        
        result.getColumns().addAll(firstBallCol, secondBallCol, thirdBallCol, fourthBallCol, fifthBallCol, sixthBallCol);
        result.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //result.getSelectionModel().setCellSelectionEnabled(true);
        result.setItems(data);
        
        vbox.getChildren().addAll(addGridPane(), result);
        return vbox;
    }

    public GridPane addGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        tattsArchiveFileLabel = new Label();
        grid.add(tattsArchiveFileLabel, 1, 1);

        FileChooser getTattsArchive = new FileChooser();
        getTattsArchive.setTitle("Open Tatts Archive csv file");
        Button buttonGetFile = new Button("Get tatts archive file");
        buttonGetFile.setPrefSize(200, 20);
        buttonGetFile.setOnAction((ActionEvent actionEvent) -> {
            getTattsArchive.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            File tattsArchiveFile = getTattsArchive.showOpenDialog(new Stage());
            tattsArchiveFileLabel.setText(tattsArchiveFile.getAbsolutePath());
        });
        grid.add(buttonGetFile, 0, 1);

        TextField numbersTextFiled = new TextField();
        numbersTextFiled.setPrefWidth(600);
        numbersTextFiled.setPromptText("Input 22 numbers(not working now)");
        grid.add(numbersTextFiled, 0, 2, 2, 1);

        buttonGenerate.setPrefSize(200, 20);
        buttonGenerate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (tattsArchiveFileLabel.getText()=="") {
                    notTattsArchiveSet = new Alert(AlertType.ERROR);
                    notTattsArchiveSet.setTitle("Attention!");
                    notTattsArchiveSet.setHeaderText(null);
                    notTattsArchiveSet.setContentText("Not selected archive file!");
                    notTattsArchiveSet.showAndWait();
                } else {
                    List<GenDraw> tasks = new LinkedList<>();
                    for (int i = 0; i<700; i++){
                        GenDraw task = new GenDraw();
                        task.fork();
                        tasks.add(task);
                    }
                    
                    tasks.stream().forEach((task) -> {
                        data.add(task.join());
                    });
                    
                }
            }
        });
        grid.add(buttonGenerate, 0, 3);
        
        return grid;
    }

    //int[] usernumbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45};
    int[] usernumbers = { 1, 2, 4, 5, 7, 8, 10, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 35, 36, 39, 41, 42, 43};
    
    class GenDraw extends RecursiveTask<Draw> {

        @Override
        protected Draw compute() {
            int z = 0;
            Draw nDraw = null;
            TattsArchive ta = new TattsArchive(tattsArchiveFileLabel.getText());
            while (z == 0) {
                int[] result = new int[6];
                int n = usernumbers.length;
                int[] usernumbersTemp = Arrays.copyOf(usernumbers, usernumbers.length);
                for (int i = 0; i < result.length; i++) {
                    int d = 0, r = 0;
              //      while (d == 0) {
              //          int v = 0;
                       r = (int) (Math.random() * n);
             //          for (int j = 0; j < 7; j++) {
             //               try {
             //                   if (usernumbersTemp[r] == ta.getColumnDraw(i, 7)[j]) {
             //                       v = v + 1;
             //                   }
            //                } catch (IOException ex) {
           //                     Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            //                }

            //            }
            //            if (v == 0) {
            //                d = 1;
            //            }
            //        }
                    result[i] = usernumbersTemp[r];
                    usernumbersTemp[r] = usernumbersTemp[n - 1];
                    n--;
                }
//                Arrays.sort(result);
                nDraw = new Draw(new Ball(result[0]), new Ball(result[1]), new Ball(result[2]), new Ball(result[3]), new Ball(result[4]), new Ball(result[5]));

                if (nDraw.oddEvenCheck()) {
                    try {
                        if (ta.checkWithArchiveDraws(nDraw.getNumbers())) {
                            if (nDraw.getSum() > 106 && nDraw.getSum() < 170) {
                                if (ta.twoNum(4, nDraw.getNumbers())) {
                                    if (ta.threeNum(13, nDraw.getNumbers())) {
                                      //  if (ta.checkWithLastDraw(nDraw.getNumbers())) {
                                            z = 1;
                                      //  }
                                    }
                                }
                            }
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return nDraw;
        }
    }
}
