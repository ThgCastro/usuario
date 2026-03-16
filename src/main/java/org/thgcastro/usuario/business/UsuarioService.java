package org.thgcastro.usuario.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thgcastro.usuario.business.converter.UsuarioConverter;
import org.thgcastro.usuario.business.dto.UsuarioDTO;
import org.thgcastro.usuario.infrastructure.entity.Usuario;
import org.thgcastro.usuario.infrastructure.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

}
