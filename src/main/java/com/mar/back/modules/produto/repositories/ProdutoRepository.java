package com.mar.back.modules.produto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mar.back.modules.produto.models.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
