import java.io.IOException;
import java.util.List;

public class TemperatureSpread {
	public static void main(String...args) throws IOException {
		DataFileParser parser = new DataFileParser("weather.dat",
				"\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\*?\\s+(\\d+).*");
		List<String[]> data = parser.loadData();
		
		WeatherRecord maxSpreadRecord = null;
		
		for (String[] line : data) {
			WeatherRecord record = new WeatherRecord(line);
			int spread = record.getSpread();
			if (maxSpreadRecord == null || spread > maxSpreadRecord.getSpread()) {
				maxSpreadRecord = record;
			}
		}
		
		System.out.format("Largest spread: day %d, %d%n", maxSpreadRecord.getDay(), maxSpreadRecord.getSpread());
	}
}
