package stages.matches;

import general.ScoutingApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.Team;
import models.event_status.EventStatus;

public class MainStandingsBox extends TableView<EventStatus.StandingsRow2018> {
	private final ObservableList<EventStatus.StandingsRow2018> data;
	private final int K = 90;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MainStandingsBox() {
		data = FXCollections.observableArrayList();
		for(Team t : ScoutingApp.getDatabase().getTeamsAtEvent(ScoutingApp.mStage.getEvent().key)) {
//			data.add(ScoutingApp.getDatabase().getStatusForTeamAtEvent(t.getNumber(), ScoutingApp.mStage.getEvent().key).getStandingsRow2018());
		}
		
		this.setEditable(true);
		
		TableColumn c1 = new TableColumn("Rank");
		c1.setMinWidth(.5*K);
        c1.setCellValueFactory(new PropertyValueFactory<>("rank"));
        c1.setCellFactory(getCustomCellFactoryInt());
        
        TableColumn c2 = new TableColumn("Team");
		c2.setMinWidth(.5*K);
        c2.setCellValueFactory(new PropertyValueFactory<>("team"));
        c2.setCellFactory(getCustomCellFactoryInt());
        
        TableColumn c3 = new TableColumn("Ranking Score");
		c3.setMinWidth(K);
        c3.setCellValueFactory(new PropertyValueFactory<>("rankingScore"));
        c3.setCellFactory(getCustomCellFactoryDouble());
        
        TableColumn c4 = new TableColumn("Park/Climb");
		c4.setMinWidth(K);
        c4.setCellValueFactory(new PropertyValueFactory<>("parkClimb"));
        c4.setCellFactory(getCustomCellFactoryInt());
        
        TableColumn c5 = new TableColumn("Auto");
		c5.setMinWidth(.75*K);
        c5.setCellValueFactory(new PropertyValueFactory<>("auto"));
        c5.setCellFactory(getCustomCellFactoryInt());
        
        TableColumn c6 = new TableColumn("Ownership");
		c6.setMinWidth(.75*K);
        c6.setCellValueFactory(new PropertyValueFactory<>("ownership"));
        c6.setCellFactory(getCustomCellFactoryInt());
        
        TableColumn c7 = new TableColumn("Vault");
		c7.setMinWidth(.75*K);
        c7.setCellValueFactory(new PropertyValueFactory<>("vault"));
        c7.setCellFactory(getCustomCellFactoryInt());
        
        TableColumn c8 = new TableColumn("Record (WLT)");
		c8.setMinWidth(K);
        c8.setCellValueFactory(new PropertyValueFactory<>("recordWLT"));
        c8.setCellFactory(getCustomCellFactoryStr());
        
        TableColumn c9 = new TableColumn("DQ");
		c9.setMinWidth(.5*K);
        c9.setCellValueFactory(new PropertyValueFactory<>("dq"));
        c9.setCellFactory(getCustomCellFactoryInt());
        
        TableColumn c10 = new TableColumn("Played");
		c10.setMinWidth(.5*K);
        c10.setCellValueFactory(new PropertyValueFactory<>("played"));
        c10.setCellFactory(getCustomCellFactoryInt());
        
        this.setItems(data);
        this.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10);
	}
	
	@SuppressWarnings("rawtypes")
	public Callback<TableColumn, TableCell> getCustomCellFactoryStr() {
		return new Callback<TableColumn, TableCell>() {
			@Override
			public TableCell call(TableColumn param) {
                return new TableCell<EventStatus.StandingsRow2018, String>() {
                	@Override
                	public void updateItem(String item, boolean empty) {
                		super.updateItem(item, empty);
                		
                		if(!isEmpty()) {
                			setStyle("-fx-font-size: 20");
                			setText(item);
                		}
                	}
                };
            }
        };
	}
	
	@SuppressWarnings("rawtypes")
	public Callback<TableColumn, TableCell> getCustomCellFactoryInt() {
		return new Callback<TableColumn, TableCell>() {
			@Override
			public TableCell call(TableColumn param) {
                return new TableCell<EventStatus.StandingsRow2018, Integer>() {
                	@Override
                	public void updateItem(Integer item, boolean empty) {
                		super.updateItem(item, empty);
                		
                		if(!isEmpty()) {
                			setStyle("-fx-font-size: 20");
                			setText(""+item);
                		}
                	}
                };
            }
        };
	}
	
	@SuppressWarnings("rawtypes")
	public Callback<TableColumn, TableCell> getCustomCellFactoryDouble() {
		return new Callback<TableColumn, TableCell>() {
			@Override
			public TableCell call(TableColumn param) {
                return new TableCell<EventStatus.StandingsRow2018, Double>() {
                	@Override
                	public void updateItem(Double item, boolean empty) {
                		super.updateItem(item, empty);
                		
                		if(!isEmpty()) {
                			setStyle("-fx-font-size: 20");
                			setText(""+item);
                		}
                	}
                };
            }
        };
	}
}
