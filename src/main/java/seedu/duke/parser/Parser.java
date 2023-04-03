package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.AddCommand;
import seedu.duke.command.ConfirmCommand;
import seedu.duke.command.UnconfirmCommand;
import seedu.duke.command.ChooseVenueCommand;
import seedu.duke.command.FindCompanyCommand;
import seedu.duke.command.FindIndustryCommand;
import seedu.duke.command.ListCompanyCommand;
import seedu.duke.command.ListUnconfirmedCommand;
import seedu.duke.command.ListVenueCommand;
import seedu.duke.command.LoadSampleCompanyCommand;
import seedu.duke.command.DeleteCommand;
import seedu.duke.command.PurgeCommand;

import seedu.duke.ui.Ui;
import seedu.duke.exception.WrongFormatException;

public interface Parser {

    /**
     * Handles the various command type and parse accordingly
     *
     * @param input the information that requires parsing
     * @return command type that would represent what to execute
     *
     * @throws WrongFormatException if error occurs when the input is in the wrong format
     * @throws NumberFormatException if error occurred due to user not providing a number where expected
     * @throws NullPointerException if error occurred due to null pointers
     * @throws IndexOutOfBoundsException if error occurred due to an index being out of bounds
     */
    static Command parse(String input) throws WrongFormatException,
            NumberFormatException, NullPointerException, IndexOutOfBoundsException {
        Ui ui = new Ui();
        String[] inputWords = input.split(" ");
        String command = inputWords[0];
        switch (command) {
        case "list":
            if (inputWords.length == 1) {
                throw new WrongFormatException();
            }
            if (inputWords[1].equals("companies")) {
                ListCompanyCommand companyCommand = new ListCompanyCommand(command + " companies");
                return companyCommand;
            } else if (inputWords[1].equals("venues")) {
                ListVenueCommand venueCommand = new ListVenueCommand(command + " venues");
                return venueCommand;
            } else if (inputWords[1].equals("unconfirmed")){
                ListUnconfirmedCommand unconfirmedCommand = new ListUnconfirmedCommand(command + " unconfirmed");
                return unconfirmedCommand;
            }
            throw new WrongFormatException();
        case "add":
            if (inputWords.length == 1) {
                throw new WrongFormatException();
            }
            input = input.replaceFirst("add", "").trim();

            if(input.indexOf("n/") != input.lastIndexOf("n/")){//Assume that multiple additions are not allowed
                ui.multipleAdditionErrorMessage();
                throw new WrongFormatException();
            }

            int indexOfName = input.indexOf("n/");
            int indexOfIndustry = input.indexOf("i/");
            int indexOfContactNumber = input.indexOf("c/");
            int indexOfContactEmail = input.indexOf("e/");
            String companyName = input.substring(indexOfName+2, indexOfIndustry).trim();
            String industry = input.substring(indexOfIndustry+2, indexOfContactNumber).trim();
            String contactNumberString = input.substring(indexOfContactNumber+2, indexOfContactEmail).trim();
            int contactNumber = Integer.parseInt(contactNumberString);
            String contactEmail = input.substring(indexOfContactEmail+2).trim();

            if(companyName.equals("")){
                ui.emptyInputErrorMessage("company name");
                throw new WrongFormatException();
            }
            if(industry.equals("")){
                ui.emptyInputErrorMessage("industry");
                throw new WrongFormatException();
            }
            if(contactNumberString.length()!=8){ //Assume valid 8-digit Singaporean Number.
                ui.invalidInputFormatErrorMessage("contact number");
                throw new WrongFormatException();
            }
            if(!contactEmail.contains("@") || contactEmail.contains(" ") || contactEmail.endsWith("@")){
                ui.invalidInputFormatErrorMessage("email address");
                throw new WrongFormatException();
            }
            AddCommand addCommand = new AddCommand(command, industry, companyName, contactNumber, contactEmail);
            return addCommand;
        case "delete":
            if (inputWords.length == 1) {
                throw new WrongFormatException();
            }
            int companyNum = Integer.parseInt(inputWords[1]) - 1;
            DeleteCommand deleteCommand = new DeleteCommand(command, companyNum);
            return deleteCommand;
        case "load":
            if (inputWords.length == 1) {
                throw new WrongFormatException();
            }
            if (inputWords[1].equals("samples")) {
                LoadSampleCompanyCommand loadSampleCompanyCommand = new LoadSampleCompanyCommand(command + " samples");
                return loadSampleCompanyCommand;
            }
            throw new WrongFormatException();
        case "purge":
            PurgeCommand purgeCommand = new PurgeCommand(command);
            return purgeCommand;
        case "choose":
            if (inputWords.length != 3) {
                throw new WrongFormatException();
            }
            if (inputWords[1].equals("venue")) {
                int venueNum = Integer.parseInt(inputWords[2]) - 1;
                ChooseVenueCommand chooseVenueCommand = new ChooseVenueCommand(command + " venue", venueNum);
                return chooseVenueCommand;
            }
            throw new WrongFormatException();
        case "confirm":
            if (inputWords.length == 1) {
                throw new WrongFormatException();
            }
            int companyConfirmNum = Integer.parseInt(inputWords[1]) - 1;
            ConfirmCommand confirmCommand = new ConfirmCommand(command, companyConfirmNum);
            return confirmCommand;
        case "unconfirm":
            if (inputWords.length == 1){
                throw new WrongFormatException();
            }
            int companyUnconfirmNum = Integer.parseInt(inputWords[1]) - 1;
            UnconfirmCommand unconfirmCommand = new UnconfirmCommand(command, companyUnconfirmNum);
            return unconfirmCommand;
        case "find":
            if(inputWords[1].equals("industry")){
                String targetIndustry = input.replace("find", "").trim();
                targetIndustry = targetIndustry.replace("industry", "").trim();
                if (targetIndustry.equals("")) {
                    ui.emptyInputErrorMessage("target industry type");
                    throw new WrongFormatException();
                }
                return new FindIndustryCommand("find industry", targetIndustry.toUpperCase());
            } else if(inputWords[1].equals("company")){
                String targetCompany = input.replace("find", "").trim();
                targetCompany = targetCompany.replace("company", "").trim();
                if (targetCompany.equals("")) {
                    ui.emptyInputErrorMessage("target company name");
                    throw new WrongFormatException();
                }
                return new FindCompanyCommand("find company", targetCompany);
            } else {
                throw new WrongFormatException();
            }
        case "help":
            ui.showGuide();
            break;
        case "exit":
            ui.showExitMessage();
            System.exit(0);
            break;
        default:
            throw new WrongFormatException();
        }
        Command defaultCommand = new Command(command);
        return defaultCommand;
    }
}
