package br.com.manell.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.manell.cursomc.domain.Categoria;
import br.com.manell.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

//	@Query("Select distinct obj from Produto obj inner join obj.categorias cat where obj.nome LIKE %:nome% and cat in :categorias")
//	Page<Produto> search(@Param("nome") String nome,@Param("categorias") List<Categoria> categoria, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categoria, Pageable pageRequest);
	
}
