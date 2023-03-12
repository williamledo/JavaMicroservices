package br.com.ufood.pagamentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ufood.pagamentos.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

}
