package com.noobdevs.day10_maven.service.impl;

import com.noobdevs.day10_maven.dto.ClientDTO;
import com.noobdevs.day10_maven.model.Client;
import com.noobdevs.day10_maven.repository.ClientRepository;
import com.noobdevs.day10_maven.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        String hashedPassword = passwordEncoder.encode(clientDTO.getPassword());
        Client client = new Client(clientDTO.getName(), clientDTO.getEmail(), hashedPassword, clientDTO.getCompany());
        return toDTO(clientRepository.save(client));
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found: " + id));
        return toDTO(client);
    }

    private ClientDTO toDTO(Client client) {
        return new ClientDTO(client.getId(), client.getName(), client.getEmail(), "CLIENT", null, client.getCompany());
    }
}
