package conCreteCommand;

import command.CommandAddClients;
import controller.ControllerManager;
import model.Client;

public class AddNewClient implements CommandAddClients {
    ControllerManager controllerManager;

    public AddNewClient(ControllerManager controllerManager) {
        this.controllerManager = controllerManager;
    }

    @Override
    public void execute(Client client) {
        controllerManager.addNewClient(client);
    }
}