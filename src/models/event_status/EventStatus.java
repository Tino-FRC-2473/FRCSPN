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
		private final SimpleIntegerProperty parkClimb;
		private final SimpleIntegerProperty auto;
		private final SimpleIntegerProperty ownership;
		private final SimpleIntegerProperty vault;
		private final SimpleStringProperty recordWLT;
		private final SimpleIntegerProperty dq;
		private final SimpleIntegerProperty played;
	    
    	private StandingsRow2018() {
	        this.rank = new SimpleIntegerProperty((int)qual.ranking.rank);
	        this.team = new SimpleIntegerProperty(Integer.parseInt(qual.ranking.team_key.substring(3)));
	        this.rankingScore = new SimpleDoubleProperty(qual.ranking.sort_orders[0]);
	        this.parkClimb = new SimpleIntegerProperty((int)qual.ranking.sort_orders[1]);
			this.auto = new SimpleIntegerProperty((int)qual.ranking.sort_orders[2]);
			this.ownership = new SimpleIntegerProperty((int)qual.ranking.sort_orders[3]);
			this.vault = new SimpleIntegerProperty((int)qual.ranking.sort_orders[4]);
			this.recordWLT = new SimpleStringProperty(qual.ranking.record.toString());
			this.dq = new SimpleIntegerProperty((int)qual.ranking.sort_orders[5]);
			this.played = new SimpleIntegerProperty(qual.ranking.matches_played);
    	}
			
		public int getRank() { return rank.get(); }
		public int getTeam() { return team.get(); }
		public double getRankingScore() { return rankingScore.get(); }
		public int getParkClimb() { return parkClimb.get(); }
		public int getAuto() { return auto.get(); }
		public int getOwnership() { return ownership.get(); }
		public int getVault() { return vault.get(); }
		public String getRecordWLT() { return recordWLT.get(); }
		public int getDq() { return dq.get(); }
		public int getPlayed() { return played.get(); }
	}
}
