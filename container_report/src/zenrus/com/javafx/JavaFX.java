package zenrus.com.javafx;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import zenrus.com.container.exception.ApplicationException;
import zenrus.com.container.report.Report;

public class JavaFX extends Application{
	Button button;
	Button runButton;
	DirectoryChooser directoryChooser;
	
	String path = null;
	
	String fileName = "C:/report.xls";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Отчет о выполнении по поручению Клиента дополнительных транспортно-экспедиционных услуг в Брестском железнодорожном узле");
		
		final Label labelSelectedDirectory = new Label();
		
		button = new Button("Выбрать папку с входящими файлами");
		
		runButton = new Button();
		runButton.setText("Запустить");

		
		Text text = new Text();
		Text textSuccess = new Text();
		textSuccess.setFont(new Font(25));
		textSuccess.setWrappingWidth(200);
		textSuccess.setTextAlignment(TextAlignment.CENTER);
		text.setFont(new Font(20));
		text.setWrappingWidth(200);
		text.setTextAlignment(TextAlignment.JUSTIFY);
		
		VBox vBox = new VBox();
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				directoryChooser = new DirectoryChooser();
                File selectedDirectory = 
                        directoryChooser.showDialog(primaryStage);
                 
                if(selectedDirectory == null){
                	text.setText("Путь к папке с входящими файлами не вбран");
                }else{
                	vBox.getChildren().add(runButton);
                	text.setText(selectedDirectory.getAbsolutePath());
                	path = selectedDirectory.getAbsolutePath();
                }
				
			}
		});
		
		runButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Report.main(path, fileName);
					textSuccess.setText("Выполненно! Файл "+ fileName + " сохранен");
					vBox.getChildren().add(textSuccess);
				} catch (ApplicationException e) {
					textSuccess.setText("Ошибка! "+ e.getMessage());
					vBox.getChildren().add(textSuccess);
				}
				
			}
		});
		
		
		
		
	    vBox.getChildren().addAll(
	                text,
	                button);
	    
	    StackPane layout = new StackPane();
		layout.getChildren().add(vBox);
		layout.getChildren().add(labelSelectedDirectory);
		
		Scene scene = new Scene(layout, 700, 500);
		
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
