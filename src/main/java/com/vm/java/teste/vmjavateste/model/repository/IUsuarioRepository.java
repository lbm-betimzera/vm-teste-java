package com.vm.java.teste.vmjavateste.model.repository;

import com.vm.java.teste.vmjavateste.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findById(Long id);

    boolean existsByEmail(String email);

    Page<Usuario> findByNomeContainingAndEmailContaining(String nome, String email, Pageable pageable);
}
