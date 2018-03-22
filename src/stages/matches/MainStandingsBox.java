package stages.matches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import general.ScoutingApp;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.Team;
import models.event_status.EventStatus;
import models.event_status.EventStatus.StandingsRow2018;

public class MainStandingsBox extends TableView<MainStandingsBox.TableStandings> {
	private final ObservableList<TableStandings> data;
	private ArrayList<TableStandings> standings;
	private final int K = 90;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MainStandingsBox() {
		standings = new ArrayList<>();
		for(Team t : ScoutingApp.getDatabase().getTeamsAtEvent(ScoutingApp.mStage.getEvent().key)) {
			standings.add(new TableStandings(ScoutingApp.getDatabase().getStatusForTeamAtEvent(t.getNumber(), ScoutingApp.mStage.getEvent().key).getStandingsRow2018()));
		}
		addRankings();
		Collections.sort(standings, (t1,t2) -> t1.compareTo(t2));
		data = FXCollections.observableArrayList();
		for (TableStandings t : standings) {
			data.add(t);
		}
		
//		for(Team t : ScoutingApp.getDatabase().getTeamsAtEvent(ScoutingApp.mStage.getEvent().key)) {
//			data.add(ScoutingApp.getDatabase().getStatusForTeamAtEvent(t.getNumber(), ScoutingApp.mStage.getEvent().key).getStandingsRow2018());
//		}
		
		this.setEditable(true);
		TableColumn[] columns = new TableColumn[10];
		
		TableColumn c1 = new TableColumn("Rank");
		columns[0] = c1;
		c1.setMinWidth(.5*K);
        c1.setCellValueFactory(new PropertyValueFactory<>("rank"));
//        c1.setCellFactory(getCustomCellFactoryInt());
        
        TableColumn c2 = new TableColumn("Team");
        columns[1] = c2;
		c2.setMinWidth(.5*K);
        c2.setCellValueFactory(new PropertyValueFactory<>("team"));
//        c2.setCellFactory(getCustomCellFactoryInt());
        
        TableColumn c3 = new TableColumn("Ranking Score");
        columns[2] = c3;
		c3.setMinWidth(K);
        c3.setCellValueFactory(new PropertyValueFactory<>("rankingScore"));
//        c3.setCellFactory(getCustomCellFactoryDouble());
        
        TableColumn c4 = new TableColumn("Park/Climb");
        columns[3] = c4;
		c4.setMinWidth(K);
        c4.setCellValueFactory(new PropertyValueFactory<>("parkClimb"));
//        c4.setCellFactory(getCustomCellFactoryInt());
        
        TableColumn c5 = new TableColumn("Auto");
        columns[4] = c5;
		c5.setMinWidth(.75*K);
        c5.setCellValueFactory(new PropertyValueFactory<>("auto"));
//        c5.setCellFactory(getCustomCellFactoryInt());
        
        TableColumn c6 = new TableColumn("Ownership");
        columns[5] = c6;
		c6.setMinWidth(.75*K);
        c6.setCellValueFactory(new PropertyValueFactory<>("ownership"));
//        c6.setCellFactory(getCustomCellFactoryInt());
        
        TableColumn c7 = new TableColumn("Vault");
        columns[6] = c7;
		c7.setMinWidth(.75*K);
        c7.setCellValueFactory(new PropertyValueFactory<>("vault"));
//        c7.setCellFactory(getCustomCellFactoryInt());
        
        TableColumn c8 = new TableColumn("Record (WLT)");
        columns[7] = c8;
		c8.setMinWidth(K);
        c8.setCellValueFactory(new PropertyValueFactory<>("recordWLT"));
//        c8.setCellFactory(getCustomCellFactoryStr());
        
        TableColumn c9 = new TableColumn("DQ");
        columns[8] = c9;
		c9.setMinWidth(.5*K);
        c9.setCellValueFactory(new PropertyValueFactory<>("dq"));
//        c9.setCellFactory(getCustomCellFactoryInt());
        
        TableColumn c10 = new TableColumn("Played");
        columns[9] = c10;
		c10.setMinWidth(.5*K);
        c10.setCellValueFactory(new PropertyValueFactory<>("played"));
//        c10.setCellFactory(getCustomCellFactoryInt());
        
        this.setItems(data);
        this.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10);
        
        this.getColumns().addListener(new ListChangeListener() {
            public boolean suspended;

            @Override
            public void onChanged(Change change) {
                change.next();
                if (change.wasReplaced() && !suspended) {
                    this.suspended = true;
                    getInstance().getColumns().setAll(columns);
                    this.suspended = false;
                }
            }
        });
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
	public void addRankings() {
		Double[] ranking = new Double[standings.size()];
		for (int i = 0; i < ranking.length; i++) ranking[i] = standings.get(i).getStandings().getRankingScore();
		Arrays.sort(ranking,Collections.reverseOrder());
		for (TableStandings t : standings) {
			for (int i = 0; i < ranking.length; i++) {
				if (t.getStandings().getRankingScore() == ranking[i]) {
					t.setRankingScore(t.getRankingScore() + " (" + (i+1) + ")");
					break;
				}
			}
		}
		Integer[] rank = new Integer[standings.size()];
		for (int i = 0; i < rank.length; i++) rank[i] = standings.get(i).getStandings().getParkClimb();
		Arrays.sort(rank,Collections.reverseOrder());
		for (TableStandings t : standings) {
			for (int i = 0; i < rank.length; i++) {
				if (t.getStandings().getParkClimb() == rank[i]) {
					t.setParkClimb(t.getParkClimb() + " (" + (i+1) + ")");
					break;
				}
			}
		}
		rank = new Integer[standings.size()];
		for (int i = 0; i < rank.length; i++) rank[i] = standings.get(i).getStandings().getAuto();
		Arrays.sort(rank,Collections.reverseOrder());
		for (TableStandings t : standings) {
			for (int i = 0; i < rank.length; i++) {
				if (t.getStandings().getAuto() == rank[i]) {
					t.setAuto(t.getAuto() + " (" + (i+1) + ")");
					break;
				}
			}
		}
		rank = new Integer[standings.size()];
		for (int i = 0; i < rank.length; i++) rank[i] = standings.get(i).getStandings().getOwnership();
		Arrays.sort(rank,Collections.reverseOrder());
		for (TableStandings t : standings) {
			for (int i = 0; i < rank.length; i++) {
				if (t.getStandings().getOwnership() == rank[i]) {
					t.setOwnership(t.getOwnership() + " (" + (i+1) + ")");
					break;
				}
			}
		}
		rank = new Integer[standings.size()];
		for (int i = 0; i < rank.length; i++) rank[i] = standings.get(i).getStandings().getVault();
		Arrays.sort(rank,Collections.reverseOrder());
		for (TableStandings t : standings) {
			for (int i = 0; i < rank.length; i++) {
				if (t.getStandings().getVault() == rank[i]) {
					t.setVault(t.getVault() + " (" + (i+1) + ")");
					break;
				}
			}
		}
	}
	
	public MainStandingsBox getInstance() {
		return this;
	}
	public class TableStandings {
		private int rank;
		private int team;
		private String rankingScore;
		private String parkClimb;
		private String auto;
		private String ownership;
		private String vault;
		private String recordWLT;
		private int dq;
		private int played;
		private StandingsRow2018 standings;
		public TableStandings(StandingsRow2018 standings) {
			this.standings = standings;
			rank = standings.getRank();
			team = standings.getTeam();
			rankingScore = standings.getRankingScore() + "";
			parkClimb = standings.getParkClimb() + "";
			auto = standings.getAuto() + "";
			ownership = standings.getOwnership() + "";
			vault = standings.getVault() + "";
			recordWLT = standings.getRecordWLT() + "";
			dq = standings.getDq();
			played = standings.getPlayed();
		}
		public int getRank() { return rank; }
		public void setRank(int rank) { this.rank = rank; }
		public int getTeam() { return team; }
		public void setTeam(int team) { this.team = team; }
		public String getRankingScore() { return rankingScore; }
		public void setRankingScore(String rankingScore) { this.rankingScore = rankingScore; }
		public String getParkClimb() { return parkClimb; }
		public void setParkClimb(String parkClimb) { this.parkClimb = parkClimb; }
		public String getAuto() { return auto; }
		public void setAuto(String auto) { this.auto = auto; }
		public String getOwnership() { return ownership; }
		public void setOwnership(String ownership) { this.ownership = ownership; }
		public String getVault() { return vault; }
		public void setVault(String vault) { this.vault = vault; }
		public String getRecordWLT() { return recordWLT; }
		public void setRecordWLT(String recordWLT) { this.recordWLT = recordWLT; }
		public int getDQ() { return dq; }
		public void setDQ(int dq) { this.dq = dq; }
		public int getPlayed() { return played; }
		public void setPlayed(int played) { this.played = played; }
		public StandingsRow2018 getStandings() {
			return standings;
		}
		public void setStandings(StandingsRow2018 standings) {
			this.standings = standings;
		}
		public int compareTo(TableStandings t) {
			if (t.getRank() == this.getRank()) return 0;
			else if (t.getRank() > this.getRank()) return -1;
			else return 1;
		}
	}

}

