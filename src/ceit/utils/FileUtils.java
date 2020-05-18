package ceit.utils;

import ceit.model.Note;

import java.io.*;
import java.util.Date;

/**
 * Mange files - Read and Write
 */
public class FileUtils {

    //I'm a mac user - if it doesn't work chang NOTES_PATH to ./notes
    private static final String NOTES_PATH = "./notes/";

    //It's a static initializer. It's executed when the class is loaded.
    //It's similar to constructor
    static {
        //creat a directory in given address once
        boolean isSuccessful = new File(NOTES_PATH).mkdirs();
        System.out.println("Creating " + NOTES_PATH + " directory is successful: " + isSuccessful);
        System.out.println(new File(NOTES_PATH).exists());
    }

    /**
     * get list of files
     *
     * @return list of files
     */
    public static File[] getFilesInDirectory() {
        return new File(NOTES_PATH).listFiles();
    }


    /**
     * Read files with BufferedReader
     *
     * @param file given file
     * @return content of file
     * @throws IOException
     */
    public static String fileReader(File file) throws IOException {
        //TODO: Phase1: read content from file:done
        StringBuilder content = new StringBuilder(); // creat a string builder to build lines into single string
        FileReader reader = new FileReader(file); // creat a file reader to read file
        BufferedReader in = new BufferedReader(reader); //creat a buffer reader to read from buffer
        String line; //creat a string to read each line and assign to line
        while ((line = in.readLine()) != null) { //check if its end of file or not
            content.append(line); // add each line to string
            content.append("\n"); // go to next line
        }
        reader.close(); //close reader
        in.close(); //close buffer reader
        return content.toString(); // return file content
    }

    /**
     * Write given string to file with Buffered Reader
     *
     * @param content given string
     * @throws IOException
     */
    public static void fileWriter(String content) throws IOException {
        //TODO: write content on file:done
        String fileName = getProperFileName(content); //get name of file
        //I'm a mac user - if it doesn't work chang NOTES_PATH to ./notes/fileName.txt
        FileWriter writer = new FileWriter(NOTES_PATH + fileName + ".txt/"); //creat a writer to given address
        BufferedWriter out = new BufferedWriter(writer); // creat a buffer reader
        out.write(content); //write given content to file
        out.flush();
    }

    /**
     * Read files with FileInputStream
     *
     * @param file given file
     * @return content of file
     * @throws IOException
     */
    //TODO: Phase1: define method here for reading file with InputStream
    public static String fileReader2(File file) throws IOException {
        //TODO: Phase1: read content from file:done
        StringBuilder line = new StringBuilder();
        try (InputStream in = new FileInputStream(file)) { //creat a input stream to read from file
            int content;
            while ((content = in.read()) != -1) { //check if its end of file or not
                line.append((char) content); //cast byte to char and append it to content
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line.toString(); // return file value as a string
    }

    /**
     * Write to file with FileOutputStream
     *
     * @param content given string
     * @throws IOException
     */

    //TODO: Phase1: define method here for writing file with OutputStream
    public static void fileWriter2(String content) throws IOException {
        //TODO: write content on file:done
        String fileName = getProperFileName(content);
        //I'm a mac user - if it doesn't work chang NOTES_PATH to ./notes/fileName.txt
        OutputStream out = new FileOutputStream(NOTES_PATH + fileName + ".txt/"); //creat an output stream to write on file
        out.write(content.getBytes()); // write given string as byte into file
        out.flush();
    }

    //TODO: Phase2: proper methods for handling serialization:done

    /**
     * read object
     * @param file given object as a file
     * @return content of file
     * @throws IOException
     */
    public static String readObject(File file) throws IOException {
        Note note = null;
        try (FileInputStream of = new FileInputStream(file)) { //creat a stream for given file
            ObjectInputStream os = new ObjectInputStream(of);
            note= (Note) os.readObject(); //read from file and set it to an object
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return note.toString(); //return content of note as a string
    }

    /**
     * write given string as objects to file
     * @param content given string
     * @throws IOException
     */
    public static void writeObject(String content) throws IOException {
        Date date = new Date(); //creat present date
        String fileName = getProperFileName(content); //get name of file
        //I'm a mac user - if it doesn't work chang NOTES_PATH to ./notes/fileName
        String path = "./notes/" + fileName + "/"; //creat path of file
        if(!new File(path).exists()) { //if file already doesn't exists
            Note note = new Note(fileName, content, date.toString());  //creat a file in given path
            try (FileOutputStream fi = new FileOutputStream(path)) { //creat a stream to write on path
                ObjectOutputStream os = new ObjectOutputStream(fi); //creat a object stream to write on object
                os.writeObject(note); //write on object
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{ //if file already exist
            String oldContent; //creat a string to keep old content of file
            Note note = null; //creat a null object to rewrite object
            try (FileInputStream of = new FileInputStream(new File(path))) { //creat streams as before
                ObjectInputStream os = new ObjectInputStream(of);
                note= (Note) os.readObject(); // read object from given path and put it in note object
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            oldContent=note.getContent(); //get old content of note
            String newContent;
            int loc=content.indexOf('}');
            newContent=content.substring(loc+1); //get new content
            Note note2 = new Note(fileName,oldContent+newContent, date.toString());//update object
            try (FileOutputStream fi = new FileOutputStream(path)) { //write on object
                ObjectOutputStream os = new ObjectOutputStream(fi);
                os.writeObject(note2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Find name of file based on its content
     * @param content given string to write on file
     * @return name of file
     */
    private static String getProperFileName(String content) {
        if(content.contains("Note{")){ // in cases writing object
            int loc2=content.indexOf(",");
            return content.substring(11,loc2);
        }
        int loc = content.indexOf("\n");
        if (loc != -1) { // in other cases
            return content.substring(0, loc);
        }
        if (!content.isEmpty()) {
            return content;
        }
        return System.currentTimeMillis() + "_new file.txt";
    }
}
