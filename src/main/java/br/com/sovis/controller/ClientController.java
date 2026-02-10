package br.com.sovis.controller;

import br.com.sovis.dao.ClientDAO;
import br.com.sovis.model.Client;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClientController {

    private final ClientDAO clientDAO = new ClientDAO();

    public void createClient(Client client, ArrayList<Long> usersForAssociated) throws SQLException {
        clientDAO.createClient(
                client.getName(),
                client.getEmail(),
                client.getPhone(),
                client.getDateRegister(),
                usersForAssociated
            );
    }

    public void updateClient(Long id, Client clientModify, ArrayList<Long> usersForAssociatedEdit) throws SQLException {
        clientDAO.updateClient(
                String.valueOf(id),
                clientModify.getName(),
                clientModify.getEmail(),
                clientModify.getPhone(),
                clientModify.getDateRegister(),
                usersForAssociatedEdit
            );
    }

    public void deleteClient(Long id) throws SQLException {
        clientDAO.deleteClient(String.valueOf(id));
    }

    public ArrayList<Client> getClients() throws SQLException {
        return clientDAO.getClients();
    }

    public ArrayList<Client> getClientsOfUser(Long idUser) throws SQLException {
        return clientDAO.getClientsOfUser(String.valueOf(idUser));
    }

    public Client getClientById(Long id) throws SQLException {
        return clientDAO.getClient(String.valueOf(id));
    }
}
