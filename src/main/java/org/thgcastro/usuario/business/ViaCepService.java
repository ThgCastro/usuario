package org.thgcastro.usuario.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thgcastro.usuario.client.ViaCepClient;
import org.thgcastro.usuario.client.ViaCepDTO;
import org.thgcastro.usuario.infrastructure.exceptions.IllegalArgumentException;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient viaCepClient;

    public ViaCepDTO buscarDadosEndereco(String cep) {
        return viaCepClient.buscarDadosEndereco(processarCep(cep));
    }

    private String processarCep(String cep) {
        String cepFormatado = cep.replaceAll("[^0-9]", "");
        if (cepFormatado.length() != 8) throw new IllegalArgumentException("Tamanho do CEP não é válido");
        return cepFormatado;
    }
}
