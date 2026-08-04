package com.noobdevs.day10_maven.controller;

import com.noobdevs.day10_maven.dto.ClientDTO;
import com.noobdevs.day10_maven.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Client-only endpoints — SecurityConfig maps "/api/clients/**" to
 * hasRole("CLIENT"), so an ADMIN-role JWT gets 403 here. createClient is
 * also reachable, unauthenticated, via POST /auth/register for self-signup.
 */
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ClientDTO createClient(@RequestBody ClientDTO clientDTO) {
        return clientService.createClient(clientDTO);
    }

    @GetMapping
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }
}
