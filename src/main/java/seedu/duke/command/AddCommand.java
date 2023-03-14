package seedu.duke.command;


public class AddCommand extends Command {
    protected String companyName;
    protected String contactNumber;
    protected String contactEmail;

    public AddCommand(String commandType, String companyName, String contactNumber, String contactEmail) {
        super(commandType);
        this.companyName = companyName;
        this.contactNumber = contactNumber;
        this.contactEmail = contactEmail;
    }

    @Override
    public void execute() {
        System.out.println("add command");
    }
}