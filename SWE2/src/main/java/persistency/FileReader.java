package persistency;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
	private String fileName;

    public FileReader(String fileName) {
        this.fileName = fileName;
    }

    public List<Object> readFile() {
		return new ArrayList<>();
	}

	private void Deserialize(){

		File file = new File(fileName);

		try{

			if(file.createNewFile()){
                List<? extends Object> list = new ArrayList<>();
                FileWriter writer = new FileWriter();
                writer.writeFile(list); // not the fastest but new File Writer only gets created if and if only file doesnt exist, doesnt happen often!
            }


        }catch (Exception e) {
			// TODO EXCEPTION HANDLING
		}


		try {



			FileInputStream inputStream = new FileInputStream(fileName);
			ObjectInputStream objinputstream = new ObjectInputStream(inputStream);

			//" When read they need to be cast to the expected type. " https://docs.oracle.com/javase/9/docs/api/java/io/ObjectInputStream.html
            List<? extends Object> returnlist = (List<? extends Object>)objinputstream.readObject();

			inputStream.close();
			objinputstream.close();

		} catch (Exception e) {
			// TODO EXCEPTION HANDLING
		}

	}
}
