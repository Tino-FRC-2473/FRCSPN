package stages.matches;

import general.ScoutingApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Team;
import models.event_status.EventStatus;

public class MainStandingsBox extends TableView<EventStatus.StandingsRow2018> {
	private final ObservableList<EventStatus.StandingsRow2018> data = FXCollections.observableArrayList();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MainStandingsBox() {
		for(Team t : ScoutingApp.getDatabase().getTeamsAtEvent(ScoutingApp.mStage.getEvent().key)) {
//			data.add(ScoutingApp.getDatabase().getStatusForTeamAtEvent(t.getNumber(), ScoutingApp.mStage.getEvent().key).getStandingsRow2018());
		}
		
		this.setEditable(true);
		
		TableColumn c1 = new TableColumn("Rank");
		c1.setMinWidth(100);
        c1.setCellValueFactory(new PropertyValueFactory<>("rank"));
        
        TableColumn c2 = new TableColumn("Team");
		c2.setMinWidth(100);
        c2.setCellValueFactory(new PropertyValueFactory<>("team"));
        
        TableColumn c3 = new TableColumn("Ranking Score");
		c3.setMinWidth(100);
        c3.setCellValueFactory(new PropertyValueFactory<>("rankingScore"));
        
        TableColumn c4 = new TableColumn("Park/Climb Points");
		c4.setMinWidth(100);
        c4.setCellValueFactory(new PropertyValueFactory<>("parkClimbPoints"));
        
        TableColumn c5 = new TableColumn("Auto");
		c5.setMinWidth(100);
        c5.setCellValueFactory(new PropertyValueFactory<>("auto"));
        
        TableColumn c6 = new TableColumn("Ownership");
		c6.setMinWidth(100);
        c6.setCellValueFactory(new PropertyValueFactory<>("ownership"));
        
        TableColumn c7 = new TableColumn("Vault");
		c7.setMinWidth(100);
        c7.setCellValueFactory(new PropertyValueFactory<>("vault"));
        
        TableColumn c8 = new TableColumn("Record (WLT)");
		c8.setMinWidth(100);
        c8.setCellValueFactory(new PropertyValueFactory<>("recordWLT"));
        
        TableColumn c9 = new TableColumn("DQ");
		c9.setMinWidth(100);
        c9.setCellValueFactory(new PropertyValueFactory<>("dq"));
        
        TableColumn c10 = new TableColumn("Played");
		c10.setMinWidth(100);
        c10.setCellValueFactory(new PropertyValueFactory<>("played"));
	}
}
