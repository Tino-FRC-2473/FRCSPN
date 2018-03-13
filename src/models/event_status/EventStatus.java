package models.event_status;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EventStatus {
	public EventStatusRank qual;
	public EventStatusAlliance alliance;
	public EventStatusPlayoff playoff;
	public String alliance_status_str;
	public String playoff_status_str;
	public String overall_status_str;
	
	public StandingsRow2018 getStandingsRow2018() {
		return new StandingsRow2018();
	}
	
	public class StandingsRow2018 {
		private final SimpleIntegerProperty rank;
		private final SimpleIntegerProperty team;
		private final SimpleDoubleProperty rankingScore;
		private final SimpleIntegerProperty parkClimbPoints;
		private final SimpleIntegerProperty auto;
		private final SimpleIntegerProperty ownership;
		private final SimpleIntegerProperty vault;
		private final SimpleStringProperty recordWLT;
		private final SimpleIntegerProperty dq;
		private final SimpleIntegerProperty played;
	    
    	private StandingsRow2018() {
	        this.rank = new SimpleIntegerProperty(Integer.parseInt(qual.sort_order_info[0].name));
	        this.team = new SimpleIntegerProperty(Integer.parseInt(qual.sort_order_info[1].name));
	        this.rankingScore = new SimpleDoubleProperty(Double.parseDouble(qual.sort_order_info[2].name));
	        this.parkClimbPoints = new SimpleIntegerProperty(Integer.parseInt(qual.sort_order_info[3].name));
			this.auto = new SimpleIntegerProperty(Integer.parseInt(qual.sort_order_info[4].name));
			this.ownership = new SimpleIntegerProperty(Integer.parseInt(qual.sort_order_info[5].name));
			this.vault = new SimpleIntegerProperty(Integer.parseInt(qual.sort_order_info[6].name));
			this.recordWLT = new SimpleStringProperty(qual.sort_order_info[7].name);
			this.dq = new SimpleIntegerProperty(Integer.parseInt(qual.sort_order_info[8].name));
			this.played = new SimpleIntegerProperty(Integer.parseInt(qual.sort_order_info[9].name));
    	}
			
		public SimpleIntegerProperty getRank() { return rank; }
		public SimpleIntegerProperty getTeam() { return team; }
		public SimpleDoubleProperty getRankingScore() { return rankingScore; }
		public SimpleIntegerProperty getParkClimbPoints() { return parkClimbPoints; }
		public SimpleIntegerProperty getAuto() { return auto; }
		public SimpleIntegerProperty getOwnership() { return ownership; }
		public SimpleIntegerProperty getVault() { return vault; }
		public SimpleStringProperty getRecordWLT() { return recordWLT; }
		public SimpleIntegerProperty getDq() { return dq; }
		public SimpleIntegerProperty getPlayed() { return played; }
	}
}
