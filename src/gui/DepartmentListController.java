package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {

	// Department service dependency
	private DepartmentService service = new DepartmentService();

	@FXML
	private Button btNew;

	@FXML
	private TableView<Department> tableViewDepartment;

	@FXML
	private TableColumn<Department, Integer> tableColumnId;

	@FXML
	private TableColumn<Department, String> tableColumnName;

	// List for save all elements to departmentList
	private ObservableList<Department> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		createDialogForm("/gui/DepartmentForm.fxml", Utils.currentStage(event));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeNodes();

	}

	private void initializeNodes() {
		// Initializable the columns compartments.
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		// Table view will have the height of the main window
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	/*
	 * it will save all departments in a temporary list then it will save that
	 * temporary list in my observable list and then it will set all those items
	 * saved inside the tableviewDepartment.
	 */
	public void updateTableView() {

		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);

	}
	// Create the window DialogForm
	private void createDialogForm(String absoluteName, Stage parentStage) {
		try {
			// Load the FXML
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			// Initialize a new stage for the new window.
			Stage dialogStage = new Stage();
			// Set a title for the window.
			dialogStage.setTitle("Enter Department data");
			// Set the scene because is a stage and he need.
			dialogStage.setScene(new Scene(pane));
			// The window will not be resized.
			dialogStage.setResizable(false);
			// Set the parent stage for our new window
			dialogStage.initOwner(parentStage);
			// The windows will be locked
			dialogStage.initModality(Modality.WINDOW_MODAL);
			// Show the window (duh)
			dialogStage.showAndWait();
			
			
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
