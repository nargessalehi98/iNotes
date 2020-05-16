package ceit.utils;

import java.io.*;

/**
 * Mange files - Read and Write
 */
public class FileUtils {

    //I'm a mac user - if it doesn't work chang NOTES_PATH to ./notes
    private static final String NOTES_PATH = "./notes/";

    //It's a static initializer. It's executed when the class is loaded.
    //It's similar to constructor
    static {
        boolean isSuccessful = new File(NOTES_PATH).mkdirs();
        System.out.println("Creating " + NOTES_PATH + " directory is successful: " + isSuccessful);
        System.out.println(new File(NOTES_PATH).exists());

    }

    /**
     * get list of files
     * @return list of files
     */
    public static File[] getFilesInDirectory() {
        return new File(NOTES_PATH).listFiles();
    }


    /**
     * Read files with BufferedReader
     * @param file given file
     * @return content of file
     * @throws IOException
     */
    public static String fileReader(File file) throws IOException {
        //TODO: Phase1: read content from file:done
        StringBuilder content = new StringBuilder();
        FileReader reader = new FileReader(file);
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
            content.append(line);
            content.append("\n");
        }
        reader.close();
        in.close();
        return content.toString();
    }

    /**
     * Write given string to file with Buffered Reader
     * @param content given string
     * @throws IOException
     */
    public static void fileWriter(String content) throws IOException {
        //TODO: write content on file:done
        String fileName = getProperFileName(content);
        FileWriter writer = new FileWriter(NOTES_PATH + fileName + ".txt/");
        BufferedWriter out = new BufferedWriter(writer);
        out.write(content);
        out.flush();
        out.close();
    }

    /**
     * Read files with FileInputStream
     * @param file given file
     * @return content of file
     * @throws IOException
     */
    //TODO: Phase1: define method here for reading file with InputStream
    public static String fileReader2(File file) throws IOException {
        //TODO: Phase1: read content from file:done
        StringBuilder line = new StringBuilder();
        try (InputStream in = new FileInputStream(file)) {
            int content;
            while ((content = in.read()) != -1) {
                line.append((char) content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line.toString();
    }

    /**
     * Write to file with FileOutputStream
     * @param content given string
     * @throws IOException
     */

    //TODO: Phase1: define method here for writing file with OutputStream
    public static void fileWriter2(String content) throws IOException {
        //TODO: write content on file:done
        String fileName = getProperFileName(content);
        OutputStream out = new FileOutputStream(NOTES_PATH + fileName + ".txt/");
        out.write(content.getBytes());
        out.flush();
    }

    //TODO: Phase2: proper methods for handling serialization

    private static String getProperFileName(String content) {
        int loc = content.indexOf("\n");
        if (loc != -1) {
            return content.substring(0, loc);
        }
        if (!content.isEmpty()) {
            return content;
        }
        return System.currentTimeMillis() + "_new file.txt";
    }
}
