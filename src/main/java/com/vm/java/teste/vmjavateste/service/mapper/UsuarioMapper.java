package com.vm.java.teste.vmjavateste.service.mapper;

import com.vm.java.teste.vmjavateste.controller.dto.UsuarioDTO;
import com.vm.java.teste.vmjavateste.model.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario usuarioDtoParaEntidade(UsuarioDTO usuarioDTO);
    UsuarioDTO usuarioParaDto(Usuario usuario);}