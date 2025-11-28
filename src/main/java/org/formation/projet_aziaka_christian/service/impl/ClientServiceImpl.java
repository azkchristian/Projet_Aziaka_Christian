package org.formation.projet_aziaka_christian.service.impl;


import lombok.RequiredArgsConstructor;
import org.formation.projet_aziaka_christian.model.Account;
import org.formation.projet_aziaka_christian.model.Client;
import org.formation.projet_aziaka_christian.repository.ClientRepository;
import org.formation.projet_aziaka_christian.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }


    @Override
    public Client save(Client client) {

        if (client.getId() == null) {

            List<Client> all = clientRepository.findAll();
            for (Client c : all) {
                if (c.getLastName().equalsIgnoreCase(client.getLastName())
                        && c.getFirstName().equalsIgnoreCase(client.getFirstName())) {
                    throw new RuntimeException("Client already exists : "
                            + client.getFirstName() + " " + client.getLastName());
                }
            }

            return clientRepository.save(client);
        }

        return clientRepository.save(client);
    }


    @Override
    public void deleteById(Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));

        for (Account account : client.getAccounts()) {
            if (account.getBalance().doubleValue() != 0) {
                throw new RuntimeException("Cannot delete client: account " + account.getAccountNumber()
                        + " has non-zero balance");
            }
        }

        clientRepository.deleteById(id);
    }


}