package br.com.ufood.pagamentos.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ufood.pagamentos.dto.PagamentoDto;
import br.com.ufood.pagamentos.model.Pagamento;
import br.com.ufood.pagamentos.model.Status;
import br.com.ufood.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public Page<PagamentoDto> obterTodos(Pageable paginacao) {
		return repository.findAll(paginacao).map(p -> modelMapper.map(p, PagamentoDto.class));
	}
	
	
	public PagamentoDto obterPorId(Long id) {
		Pagamento pagamento = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		
		return modelMapper.map(pagamento, PagamentoDto.class);
		
	}
	
	
	public PagamentoDto criarPagamento(PagamentoDto dto) {
		Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
		pagamento.setStatus(Status.CRIADO);
		repository.save(pagamento);
		
		return modelMapper.map(pagamento, PagamentoDto.class);
	}
	
	
	public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto) {
		
		Pagamento pagamento = getOrElseThrow(id);
		
		pagamento.setValor(dto.getValor());
        pagamento.setNome(dto.getNome());
        pagamento.setNumero(dto.getNumero());
        pagamento.setExpiracao(dto.getExpiracao());
        pagamento.setCodigo(dto.getCodigo());
        pagamento.setStatus(dto.getStatus());
        pagamento.setPedidoId(dto.getPedidoId());
        pagamento.setFormaDePagamentoId(dto.getFormaDePagamentoId());
        
		repository.save(pagamento);
		
		return modelMapper.map(pagamento, PagamentoDto.class);
		
	}
	
	private Pagamento getOrElseThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
    }
	
	public void excluirPagamento(Long id) {
		repository.deleteById(id);
	}
	
	
}
