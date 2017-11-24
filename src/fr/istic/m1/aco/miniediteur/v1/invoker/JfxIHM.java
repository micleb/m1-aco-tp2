package fr.istic.m1.aco.miniediteur.v1.invoker;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class JfxIHM {
	
	@FXML
	private TextField textField;

	public static class Laucher extends Application {

		public static void main(String[] args) {
	        Application.launch(Laucher.class, args);
	    }
	    
	    @Override
	    public void start(Stage stage) throws Exception {
	        Parent root = FXMLLoader.load(this.getClass().getResource("/istic/m1/aco/miniediteur/v1/gui.xml"));
	     
	        stage.setTitle("Minieditor");
	        stage.setScene(new Scene(root, 950, 500));
	        stage.setResizable(false);
	        stage.show();
	    }
	}
}

