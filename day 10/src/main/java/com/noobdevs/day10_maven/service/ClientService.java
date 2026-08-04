package com.noobdevs.day10_maven.service;

import com.noobdevs.day10_maven.dto.ClientDTO;

import java.util.List;

public interface ClientService {

    ClientDTO createClient(ClientDTO clientDTO);

    List<ClientDTO> getAllClients();

    ClientDTO getClientById(Long id);
}
