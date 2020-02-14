import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Converter {

    public static JSONObject processPII(String inputFilePath){
        ArrayList<JSONObject> entries = new ArrayList<>();
        ArrayList<Integer> errors = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            String line = reader.readLine();
            int currentIndex = 0;
            while (line != null) {
                processInputLine(line, entries, errors, currentIndex);
                line = reader.readLine();
                currentIndex+=1;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Please enter a valid input file path");
        }
        return createJSONOutput(entries, errors);
    }

    public static void processInputLine(String inputLine, ArrayList<JSONObject> entries, ArrayList<Integer> errors, int currentIndex){
        String[] inputs = inputLine.split(",");
        if(inputs.length < 4) {
            errors.add(currentIndex);
            return;
        }

        JSONObject entry = new JSONObject();
        if(!processColor(Arrays.copyOfRange(inputs, inputs.length-3, inputs.length), entry)) {
            errors.add(currentIndex);
            return;
        }

        if(!processNames(Arrays.copyOfRange(inputs, 0, inputs.length-3), entry)) {
            errors.add(currentIndex);
            return;
        }

        if(!processPhone(Arrays.copyOfRange(inputs, inputs.length-3, inputs.length), entry)) {
            errors.add(currentIndex);
            return;
        }

        if(!processZipcode(Arrays.copyOfRange(inputs, inputs.length-3, inputs.length), entry)) {
            errors.add(currentIndex);
            return;
        }

        entries.add(entry);
    }

    public static boolean processColor(String[] contactInfoWithoutName, JSONObject entry){
        /**
         * Assumption: colors contain only alphabetical characters or hyphens
         */
        for(String info: contactInfoWithoutName) {
            info = info.trim();
            if (info.matches("[a-zA-Z\\s-]+")) {
                try {
                    entry.put("color", info);
                    return true;
                } catch(JSONException e){
                    System.out.println(e);
                }
            }
        }
        return false;
    }

    public static boolean processNames(String[] names, JSONObject entry){
        /**
         * Case: four attributes and the name is one string
         * Assumption: the person has one last name
         */
        if(names.length == 1){
            String name = names[0].trim();
            String[] separateNames = name.split(" ");
            String firstname = new String();
            for(int i=0; i<separateNames.length-1;i++){
                firstname+= " " + separateNames[i];
            }
            try {
                entry.put("firstname", firstname);
                entry.put("lastname", separateNames[separateNames.length-1]);
                return true;
            } catch(JSONException e){
                System.out.println(e);
                return false;
            }
        }
        /**
         * Case: five attributes and the name is two strings separated by a comma
         * Assumption: the last name always comes before the first name
         */
        else if(names.length == 2) {
            try {
                entry.put("firstname", names[1].trim());
                entry.put("lastname", names[0].trim());
                return true;
            } catch(JSONException e) {
                System.out.println(e);
            }
        }
        System.out.println("Failed at: processNames");
        return false;
    }

    public static boolean processPhone(String[] contactInfoWithoutName, JSONObject entry){
        /**
         * Makes use of Google's libphonenumber library
         * Assumption: only numbers deemed valid by the library will be considered valid in this program
         * meaning that only connected, functioning US phone numbers will be accepted
         */
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        for(String info: contactInfoWithoutName) {
            Phonenumber.PhoneNumber phoneNumber;
            try {
                phoneNumber = util.parse(info, "US");
                if(util.isValidNumber(phoneNumber)){
                    try {
                        entry.put("phonenumber", info);
                        return true;
                    } catch(JSONException e){
                        System.out.println(e);
                        return false;
                    }
                }
            } catch (NumberParseException err){
                System.out.println("Failed at: processPhone phone number form not readable - " + info +"\n"+ err);
            }

        }
        return false;
    }

    public static boolean processZipcode(String[] contactInfoWithoutName, JSONObject entry){
        for(String info: contactInfoWithoutName) {
            info = info.trim();
            if(info.length() == 5 && info.matches("^[0-9]{5}(?:-[0-9]{4})?$")){
                try {
                    entry.put("zipcode", info);
                    return true;
                } catch (JSONException e){
                    System.out.println(e);
                    return false;
                }
            }
        }
        return false;
    }

    public static JSONObject createJSONOutput(ArrayList<JSONObject> entries, ArrayList<Integer> errors){
        JSONObject output = new JSONObject();
        try {
            output.put("entries", entries);
            output.put("errors", errors);
        } catch (JSONException e){
            System.out.println(e);
        }

        return output;
    }

    public static void createOutputFile(String filename, JSONObject output) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(output.toString(2));
            fileWriter.flush();
        } catch (IOException | JSONException e ) {
            System.out.println("Please enter a valid output file path");
        }
    }

    public static void main(String[] args) {
        if(args.length!=2) {
            System.out.println("Please enter exactly two arguments, the input file path and output file path");
            return;
        }

        JSONObject output = processPII(args[0]);
        createOutputFile(args[1], output);
    }
}
