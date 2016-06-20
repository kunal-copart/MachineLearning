import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/* Author : Prashant Prakash
 * created_date: 26th Jan 2015 
 * Description: This class loads data from CSV Files  
 */
public class Input {

	// varibale to store all the attributes with last element as class
	private List<String> attributeNames = new ArrayList<String>();
	// varible to store number of attrbites which will be used across the
	// program
	private int number_of_attributes;

	/*public class DataPoint {
		public String[] attributeValues;

		public DataPoint(int n) {
			attributeValues = new String[n];
		}
	}  */

	List<DataPoint> dataPoints = new ArrayList<DataPoint>();

	
	
	public List<String> getAttributeNames() {
		return attributeNames;
	}



	public void setAttributeNames(List<String> attributeNames) {
		this.attributeNames = attributeNames;
	}



	public int getNumber_of_attributes() {
		return number_of_attributes;
	}



	public void setNumber_of_attributes(int number_of_attributes) {
		this.number_of_attributes = number_of_attributes;
	}



	public List<DataPoint> loadData(String fileName) throws Exception {
		FileInputStream in = null;
		try {
			//Path path = Paths.get(fileName);
			//System.out.println(path.toAbsolutePath());
			File inputFile = new File(fileName);
			in = new FileInputStream(inputFile);
		} catch (Exception ex) {
			System.err.println("Unable to open File");
			return null;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		// first Line in the file is for all the attribute names
		String firstLine = br.readLine();
		// checking for data present in file or not
		if (firstLine == null || firstLine.length() == 0) {
			System.out.println("No data is found in the file");
			br.close();
			in.close();
			return null;
		}

		StringTokenizer tokens = new StringTokenizer(firstLine, ",");
		number_of_attributes = tokens.countTokens();
		//attributeNames = new String[number_of_attributes];
		// get all attribute names
		// copy all the tokens to attribute names
		for (int i = 0; i < number_of_attributes; i++) {
			//attributeNames[i] = tokens.nextToken();
			// System.out.println(attributeNames[i]);
			attributeNames.add(tokens.nextToken());
		}

		String nextLine;
		while ((nextLine = br.readLine()) != null) {
			//System.out.println(nextLine);
			tokens = new StringTokenizer(nextLine, ",");
			DataPoint dp = new DataPoint(number_of_attributes);
			for (int i = 0; i < number_of_attributes; i++) {
				dp.attributeValues[i] = tokens.nextToken();
				// System.out.println(attributeNames[i]);
			}
			dataPoints.add(dp);
			

		}

		br.close();
		in.close();
		return dataPoints;

	}
}
