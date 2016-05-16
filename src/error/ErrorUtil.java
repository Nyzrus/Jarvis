package error;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ErrorUtil {

	public static String parseErrorStream(InputStream errorStream){
		BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
			while ((line = reader.readLine()) != null) {
			    sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

        return sb.toString();
	}
}
