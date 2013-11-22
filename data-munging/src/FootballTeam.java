
public class FootballTeam {
	private String name;
	private int goalsFor;
	private int goalsAgainst;
	
	public FootballTeam(final String[] values) {
		this.name = values[0];
		this.goalsFor = Integer.parseInt(values[1]);
		this.goalsAgainst = Integer.parseInt(values[2]);
	}
	
	public String getName() {
		return name;
	}
	
	public int getGoalsFor() {
		return goalsFor;
	}
	
	public int getGoalsAgainst() {
		return goalsAgainst;
	}
	
	public int getGoalDiff() {
		return Math.abs(goalsFor - goalsAgainst);
	}
}
