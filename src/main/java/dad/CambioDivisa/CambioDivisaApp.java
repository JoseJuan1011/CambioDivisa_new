package dad.CambioDivisa;

import dad.CambioDivisa.resources.Divisa;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CambioDivisaApp extends Application {

	// model
	private Divisa euro;
	private Divisa libra;
	private Divisa dolar;
	private Divisa yen;

	private ObjectProperty<Divisa> fromDivisaProperty;
	private ObjectProperty<Divisa> toDivisaProperty;

	private DoubleProperty fromDivisaValueProperty;
	private DoubleProperty toDivisaValueProperty;

	// view

	private TextField fromDivisaTextField;
	private ComboBox<Divisa> fromDivisaComboBox;

	private TextField toDivisaTextField;
	private ComboBox<Divisa> toDivisaComboBox;

	private HBox fromDivisaHBox;
	private HBox toDivisaHBox;

	private Button cambiarButton;

	private VBox root;

	@Override
	public void start(Stage primaryStage) throws Exception {
		euro = new Divisa("Euro", 1.0);
		libra = new Divisa("Libra", 0.8873);
		dolar = new Divisa("Dolar", 1.2007);
		yen = new Divisa("Yen", 133.59);

		fromDivisaProperty = new SimpleObjectProperty<Divisa>();
		toDivisaProperty = new SimpleObjectProperty<Divisa>();

		fromDivisaValueProperty = new SimpleDoubleProperty();
		toDivisaValueProperty = new SimpleDoubleProperty();

		// fromDivisaHBox
		fromDivisaTextField = new TextField("0");

		fromDivisaComboBox = new ComboBox<Divisa>();
		fromDivisaComboBox.getItems().addAll(euro, libra, dolar, yen);
		fromDivisaComboBox.getSelectionModel().select(euro);

		fromDivisaHBox = new HBox(fromDivisaTextField, fromDivisaComboBox);
		fromDivisaHBox.setPadding(new Insets(2));
		fromDivisaHBox.setAlignment(Pos.CENTER);

		// toDivisaHBox
		toDivisaTextField = new TextField("0");
		toDivisaTextField.setEditable(false);

		toDivisaComboBox = new ComboBox<Divisa>();
		toDivisaComboBox.getItems().addAll(euro, libra, dolar, yen);
		toDivisaComboBox.getSelectionModel().select(euro);

		toDivisaHBox = new HBox(toDivisaTextField, toDivisaComboBox);
		toDivisaHBox.setPadding(new Insets(2));
		toDivisaHBox.setAlignment(Pos.CENTER);

		// root VBox
		cambiarButton = new Button("Cambiar");

		root = new VBox(fromDivisaHBox, toDivisaHBox, cambiarButton);
		root.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root, 420, 300);

		primaryStage.setTitle("CambioDivisa");
		primaryStage.setScene(scene);
		primaryStage.show();

		Bindings.bindBidirectional(fromDivisaTextField.textProperty(), fromDivisaValueProperty,
				new NumberStringConverter());
		Bindings.bindBidirectional(toDivisaTextField.textProperty(), toDivisaValueProperty,
				new NumberStringConverter());

		fromDivisaProperty.bind(fromDivisaComboBox.getSelectionModel().selectedItemProperty());
		toDivisaProperty.bind(toDivisaComboBox.getSelectionModel().selectedItemProperty());

		cambiarButton.setOnAction(e -> OnCambiarAction(e));
	}

	private void OnCambiarAction(ActionEvent e) {
		toDivisaValueProperty.setValue(Divisa.fromTo(fromDivisaProperty.getValue(), toDivisaProperty.getValue(),
				fromDivisaValueProperty.getValue()));
	}

	public static void main(String[] args) {
		launch(args);
	}

}
