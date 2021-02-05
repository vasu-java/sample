package sample;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.StringTokenizer;

public class ConvertTsvToCsv {
    public static void main(String[] args) {
        System.out.println("Hello, world!");

        String[] pathnames;

        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        //File f = new File("/Users/vjilag846/Downloads/tsv_csv_files/");
        File f = new File(".");

        System.out.println("Path - "+f.getAbsolutePath());

        // This filter will only include files ending with .py
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                return name.endsWith(".prt");
            }
        };

        // Populates the array with names of files and directories
        File[] files = f.listFiles(filter);

        if(files.length == 0) {
            System.out.println("********************************");
            System.out.println("No files to process under :  "+f.getAbsolutePath());
            System.out.println("Copy .prt files to covert them into .csv files");
            System.out.println("********************************");
        } else {
            // For each pathname in the pathnames array
            for (int i = 0; i < files.length; i++) {
                System.out.println("Files : " + files[i].getName());
                System.out.println("Files : " + files[i].getAbsolutePath());

                //The input TSV File
                //String tsvFilePath = "/Users/vjilag846/Downloads/tsv_csv_files/XPQG-16-017358.prt";
                String tsvFilePath = files[i].getAbsolutePath();

                //The output CSV File
                //String csvFilePath = "/Users/vjilag846/Downloads/tsv_csv_files/XPQG-16-017358.csv";
                String csvFilePath = files[i].getAbsolutePath().replace("prt", "csv");

                try {
                    convertTSVToCSVFile(csvFilePath, tsvFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void convertTSVToCSVFile(String csvFilePath, String tsvFilePath) throws IOException {

        //TODO: If outfile does not exist, create one.

        StringTokenizer tokenizer;
        try (BufferedReader br = new BufferedReader(new FileReader(tsvFilePath));
             PrintWriter writer = new PrintWriter(new FileWriter(csvFilePath));) {

            int i = 0;
            for (String line; (line = br.readLine()) != null; ) {
                i++;
                // reading lines 10 throguh 15
                if ( (i >= 10 && i <= 15) || (i == 20) ) {
                    //System.out.println("skipping lines: " + i);
                    //continue;
                    //}
                    tokenizer = new StringTokenizer(line, "\t");

                    String csvLine = "";
                    String token;
                    int j = 0;
                    while (tokenizer.hasMoreTokens()) {
                        j++;
                        //skipping the 1st column
                        if (j == 1) {
                            token = tokenizer.nextToken();
                            continue;
                        }
                        token = tokenizer.nextToken();
                        token = token.replaceAll("\"", "");
                        token = convertScientificToString(token);
                        //csvLine += "\"" + token + "\",";
                        csvLine += token + ",";
                        j++;
                    }

                    if (csvLine.endsWith(",")) {
                        csvLine = csvLine.substring(0, csvLine.length() - 1);
                    }

                    writer.write(csvLine + System.getProperty("line.separator"));
                }
            }
        }
    }

    private static String convertToCSV(String line) {
        String csv = "";
        line = line.replaceAll("\t", ",");
        return line;
    }

    private static String convertScientificToString(String scientificNotation) {
        String decimalString;
        Double scientificDouble = Double.parseDouble(scientificNotation);
        NumberFormat nf = new DecimalFormat("################################################.###########################################");
        decimalString = nf.format(scientificDouble);
        return decimalString;
    }

}
