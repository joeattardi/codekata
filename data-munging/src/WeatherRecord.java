public class WeatherRecord {
	private int day;
	private int maxTemp;
	private int minTemp;
	
	public WeatherRecord(String[] values) {
		this.day = Integer.parseInt(values[0]);
		this.maxTemp = Integer.parseInt(values[1]);
		this.minTemp = Integer.parseInt(values[2]);
	}
	
	public int getDay() {
		return day;
	}
	
	public int getSpread() {
		return maxTemp - minTemp;
	}
}
