import java.io.IOException;
import java.util.List;


public class Football {
	
	public static void main(String...args) throws IOException {
		DataFileParser parser = new DataFileParser("football.dat",
				"\\s+\\d+\\. (\\w+)\\s+\\d+\\s+\\d+\\s+\\d+\\s+\\d+\\s+(\\d+)\\s+-\\s+(\\d+)\\s+\\d+\\s*");
		List<String[]> data = parser.loadData();
		
		FootballTeam topTeam = null;
		
		for (String[] record : data) {
			FootballTeam team = new FootballTeam(record);
			int diff = team.getGoalDiff();
			if (topTeam == null || diff < topTeam.getGoalDiff()) {
				topTeam = team;
			}
		}
		
		System.out.format("Team with least diff: %s (%d)%n", topTeam.getName(), topTeam.getGoalDiff());
	}
}
