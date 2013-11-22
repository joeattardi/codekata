import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFileParser {
	private Pattern linePattern;
	private String dataFile;
	
	public DataFileParser(final String dataFile, final String regexp) {
		this.dataFile = dataFile;
		this.linePattern = Pattern.compile(regexp);
	}
	
	public List<String[]> loadData() throws IOException {
		List<String[]> result = new ArrayList<String[]>();
		List<String> fileLines = Files.readAllLines(Paths.get(dataFile), Charset.defaultCharset());
		
		for (String line : fileLines) {
			Matcher m = linePattern.matcher(line);
			if (m.matches()) {
				String[] values = new String[m.groupCount()];
				for (int n = 0; n < values.length; n++) {
					values[n] = m.group(n + 1);
				}
				
				result.add(values);
			}
		}
		
		return result;
	}
}
