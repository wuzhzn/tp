package seedu.eventus.command;

import seedu.eventus.venue.VenueList;

public class DeleteCommand extends Command{
    protected int taskNum;
    public DeleteCommand(String commandType, int taskNum){
        super(commandType);
        this.taskNum = taskNum;
    }

    @Override
    public void execute(VenueList venueList) {

    }
}
