package org.thgcastro.usuario.business;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thgcastro.usuario.business.converter.UsuarioConverter;
import org.thgcastro.usuario.business.dto.EnderecoDTO;
import org.thgcastro.usuario.business.dto.TelefoneDTO;
import org.thgcastro.usuario.business.dto.UsuarioDTO;
import org.thgcastro.usuario.infrastructure.entity.Endereco;
import org.thgcastro.usuario.infrastructure.entity.Telefone;
import org.thgcastro.usuario.infrastructure.entity.Usuario;
import org.thgcastro.usuario.infrastructure.exceptions.ConflictException;
import org.thgcastro.usuario.infrastructure.exceptions.ResourceNotFoundException;
import org.thgcastro.usuario.infrastructure.repository.EnderecoRepository;
import org.thgcastro.usuario.infrastructure.repository.TelefoneRepository;
import org.thgcastro.usuario.infrastructure.repository.UsuarioRepository;
import org.thgcastro.usuario.infrastructure.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private  final TelefoneRepository telefoneRepository;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if(existe) {
                throw new ConflictException("Email já cadastrado " + email);
            }
        } catch (ConflictException e){
            throw new ConflictException("Email já cadastrado ", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado" + email))
            );
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Email nao encontrado " + email);
        }

    }

    public void deletarUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token ,UsuarioDTO usuarioDTO){
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado"));

        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado " + idEndereco));
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado " + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
