package library.client.javafx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import library.client.javafx.dataprovider.DataProvider;
import library.client.javafx.dataprovider.data.BookVO;
import library.client.javafx.dataprovider.data.StateVO;
import library.client.javafx.model.BookAdd;
import library.client.javafx.model.BookSearch;
import library.client.javafx.model.State;

public class BookSearchController {

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;

	@FXML
	private TextField titleField;
	@FXML
	private TextField authorField;
	@FXML
	private ComboBox<State> stateField;
	@FXML
	private TextField titleFieldNewBook;
	@FXML
	private TextField authorFieldNewBook;
	@FXML
	private ComboBox<State> stateFieldNewBook;
	@FXML
	private Button searchButton;
	@FXML
	private Button addButton;
	@FXML
	private Button deleteButton;
	@FXML
	private TableView<BookVO> resultTable;
	@FXML
	private TableColumn<BookVO, Long> idColumn;
	@FXML
	private TableColumn<BookVO, String> titleColumn;
	@FXML
	private TableColumn<BookVO, String> authorColumn;
	@FXML
	private TableColumn<BookVO, String> stateColumn;

	private final DataProvider dataProvider = DataProvider.INSTANCE;
	
	private final BookSearch model = new BookSearch();
	private final BookAdd modelAdd = new BookAdd();
	private String idSelectedBook = "";

	public BookSearchController() {
	}

	@FXML
	private void initialize() {

		initializeStateField(stateField);
		initializeStateField(stateFieldNewBook);
		initializeResultTable();

		titleField.textProperty().bindBidirectional(model.titleProperty());
		authorField.textProperty().bindBidirectional(model.authorProperty());
		stateField.valueProperty().bindBidirectional(model.stateProperty());
		resultTable.itemsProperty().bind(model.resultProperty());

		titleFieldNewBook.textProperty().bindBidirectional(modelAdd.titleProperty());
		authorFieldNewBook.textProperty().bindBidirectional(modelAdd.authorProperty());
		stateFieldNewBook.valueProperty().bindBidirectional(modelAdd.stateProperty());

		model.setState(State.ANY);
		modelAdd.setState(State.FREE);

		addButton.disableProperty()
				.bind(titleFieldNewBook.textProperty().isEmpty().or(authorFieldNewBook.textProperty().isEmpty())
						.or(stateFieldNewBook.valueProperty().isEqualTo(State.ANY)));

		deleteButton.setVisible(false);

	}

	private void initializeStateField(ComboBox<State> stateField) {

		for (State state : State.values()) {
			stateField.getItems().add(state);
		}

		stateField.setCellFactory(new Callback<ListView<State>, ListCell<State>>() {

			@Override
			public ListCell<State> call(ListView<State> param) {
				return new ListCell<State>() {

					@Override
					protected void updateItem(State item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							return;
						}
						String text = getInternationalizedText(item);
						setText(text);
					}
				};
			}
		});

		stateField.setConverter(new StringConverter<State>() {

			@Override
			public String toString(State object) {
				return getInternationalizedText(object);
			}

			@Override
			public State fromString(String string) {
				return null;
			}
		});
	}

	private void initializeResultTable() {

		idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Long>(cellData.getValue().getId()));
		titleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
		authorColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAuthor()));

		stateColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BookVO, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BookVO, String> param) {
						StateVO state = param.getValue().getState();
						String text = getInternationalizedText(State.fromStateVO(state));
						return new ReadOnlyStringWrapper(text);
					}
				});

		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));

		resultTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BookVO>() {

			@Override
			public void changed(ObservableValue<? extends BookVO> observable, BookVO oldValue, BookVO newValue) {
				// REV: lepiej zrobic to bindem
				deleteButton.setVisible(idSelectedBook != null);

				if (newValue != null) {
					idSelectedBook = newValue.getId().toString();
					
					// REV: po co ten task skoro nic nie robi?
					Task<Void> backgroundTask = new Task<Void>() {

						@Override
						protected Void call() throws Exception {
							return null;
						}

					};
					new Thread(backgroundTask).start();
				}
			}
		});
	}

	@FXML
	private void searchBook(ActionEvent event) {
		searchButtonActionVersion();
	}

	@FXML
	private void addNewBook(ActionEvent event) {
		saveBookAction(titleFieldNewBook, authorFieldNewBook, stateFieldNewBook);
	}

	@FXML
	private void deleteBook(ActionEvent event) {
		if (idSelectedBook != null && !idSelectedBook.isEmpty()) {
			deleteBookAction(idSelectedBook);
		}
	}

	private void clearFields() {
		titleFieldNewBook.clear();
		authorFieldNewBook.clear();
		modelAdd.setState(State.FREE);
	}

	private void saveBookAction(TextField titleFieldNewBook, TextField authorFieldNewBook,
			ComboBox<State> stateFieldNewBook) {

		Task<Object> backgroundTask = new Task<Object>() {
			@Override
			protected Object call() throws Exception {

				// REV: powinienes pobrac dane z modelu
				String title = titleFieldNewBook.getText();
				String author = authorFieldNewBook.getText();
				String state = stateFieldNewBook.getValue().toString();
		
				dataProvider.addBook(title, author, state);

				return new Object();
			}
			                  
			@Override
			protected void succeeded() {
				clearFields();
				Alert alert = new Alert(AlertType.INFORMATION, "Book added successful :)", ButtonType.OK);
				alert.showAndWait();
			}
			
			@Override
			protected void failed() {
				clearFields();
				showErrorAlert("Book cannot be added. Http server connection error.");
			}
				
		};
		
		new Thread(backgroundTask).start();
	}
	
	

	private void showErrorAlert(String message){
		Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
		alert.showAndWait();
	}
	
	private void searchButtonActionVersion() {

		Task<Collection<BookVO>> backgroundTask = new Task<Collection<BookVO>>() {
			@Override
			protected Collection<BookVO> call() throws Exception {
				Collection<BookVO> result = new ArrayList<BookVO>();
				
					result = dataProvider.findBooks( 
							model.getTitle(), 
							model.getAuthor(), 
							model.getState().toStateVO());
					
				return result;
			}

			@Override
			protected void succeeded() {
				Collection<BookVO> result = getValue();
				model.setResult(new ArrayList<BookVO>(result));
				resultTable.getSortOrder().clear();
				// REV: j.w.
				deleteButton.setVisible(false);
			}
			
			@Override
			protected void failed() {
					showErrorAlert("Books cannot be searched. Http server connection error.");
			}
			
		};

		new Thread(backgroundTask).start();
	}

	private void deleteBookAction(String id) {

		Task<Object> backgroundTask = new Task<Object>() {

			@Override
			protected Object call() throws Exception {

					dataProvider.deleteBook(id);

				return new Object();
			}

			@Override
			protected void succeeded() {
				searchButtonActionVersion();
				Alert alert = new Alert(AlertType.INFORMATION, "Book deleted successful :)", ButtonType.OK);
				alert.showAndWait();
			}
			
			@Override
			protected void failed() {
					showErrorAlert("Book cannot be deleted. Http server connection error.");
			}
			
		};

		new Thread(backgroundTask).start();
	}

	private String getInternationalizedText(State state) {
		return resources.getString("state." + state.name());
	}

}
