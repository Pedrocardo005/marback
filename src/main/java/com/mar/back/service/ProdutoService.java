package com.mar.back.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mar.back.model.Produto;
import com.mar.back.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    public ArrayList<Produto> findAll() {
        ArrayList<Produto> produtos = (ArrayList<Produto>) produtoRepository.findAll();
        return produtos;
    }

    public Produto create(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto update(Produto produto, Long id) throws Exception {
        Optional<Produto> produto2 = produtoRepository.findById(id);

        if (produto2.isPresent()) {
            return produtoRepository.save(produto);
        } else {
            throw new Exception("Não encontrado");
        }
    }

    public void delete(Long id) throws Exception {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isPresent()) {
            produtoRepository.delete(produto.get());
        } else {
            throw new Exception("Não encontrado");
        }
    }
}
