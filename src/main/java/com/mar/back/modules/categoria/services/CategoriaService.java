package com.mar.back.modules.categoria.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mar.back.modules.categoria.dtos.CategoriaPostDTO;
import com.mar.back.modules.categoria.models.Categoria;
import com.mar.back.modules.categoria.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ArrayList<Categoria> findAll() {
        return (ArrayList<Categoria>) categoriaRepository.findAll();
    }

    public Categoria create(CategoriaPostDTO categoriaDto) {
        Categoria categoria = new Categoria(categoriaDto.nome());
        return categoriaRepository.save(categoria);
    }

    public Categoria update(Categoria categoria, Long id) throws Exception {
        Optional<Categoria> categoria2 = categoriaRepository.findById(id);

        if (categoria2.isPresent()) {
            return categoriaRepository.save(categoria);
        } else {
            throw new Exception("Não encontrado");
        }
    }

    public void delete(Long id) throws Exception {
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        if (categoria.isPresent()) {
            categoriaRepository.delete(categoria.get());
        } else {
            throw new Exception("Não encontrado");
        }
    }
}
