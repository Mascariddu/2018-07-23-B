/**
 * Sample Skeleton for 'NewUfoSightings.fxml' Controller Class
 */

package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewUfoSightingsController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtxG"
	private TextField txtxG; // Value injected by FXMLLoader

	@FXML // fx:id="btnCreaGrafo"
	private Button btnCreaGrafo; // Value injected by FXMLLoader

	@FXML // fx:id="txtT1"
	private TextField txtT1; // Value injected by FXMLLoader

	@FXML // fx:id="txtT2"
	private TextField txtT2; // Value injected by FXMLLoader

	@FXML // fx:id="btnSimula"
	private Button btnSimula; // Value injected by FXMLLoader

	@FXML
	void doCreaGrafo(ActionEvent event) {
		
		txtResult.clear();

		try {
			
			int anno = Integer.parseInt(txtAnno.getText());
			int gg = Integer.parseInt(txtxG.getText());
			
			if(gg > 0 && gg < 181) {
				
				if(anno > 1905 && anno < 2015) {
					
					model.creaGrafo(anno,gg);
					for(String string : model.getPesi())
						txtResult.appendText(string+"\n");
					
				} else txtResult.appendText("L' anno deve essere compreso tra 1906 e 2014!");
				
			} else txtResult.appendText("I giorni devono essere compresi tra 1 e 180!");
			
		} catch (NumberFormatException e) {
			// TODO: handle exception
			txtResult.appendText("Inserisci valori numerici!");
		}
	}

	@FXML
	void doSimula(ActionEvent event) {
		
		txtResult.clear();

		try {
			
			int anno = Integer.parseInt(txtAnno.getText());
			int t1 = Integer.parseInt(txtT1.getText());
			int t2 = Integer.parseInt(txtT2.getText());
			
			if(t1 > 0 && t1 < 366 && t2 > 0 && t2 < 366) {
				
				model.simula(anno,t1,t2);
				for(String string : model.getAllerte())
					txtResult.appendText(string+"\n");
				
			}else txtResult.appendText("Troppi pochi o troppi giorni!");
			
		} catch (NumberFormatException e) {
			// TODO: handle exception
			txtResult.appendText("Inserisci i giorni!");
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtxG != null : "fx:id=\"txtxG\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtT2 != null : "fx:id=\"txtT2\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;

	}
}
