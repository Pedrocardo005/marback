package com.mar.back.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mar.back.model.Categoria;
import com.mar.back.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ArrayList<Categoria> findAll() {
        return (ArrayList<Categoria>) categoriaRepository.findAll();
    }

    public Categoria create(Categoria categoria) {
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
