package br.com.manell.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.manell.cursomc.domain.Categoria;
import br.com.manell.cursomc.domain.Cidade;
import br.com.manell.cursomc.domain.Cliente;
import br.com.manell.cursomc.domain.Endereco;
import br.com.manell.cursomc.domain.Estado;
import br.com.manell.cursomc.domain.ItemPedido;
import br.com.manell.cursomc.domain.Pagamento;
import br.com.manell.cursomc.domain.PagamentoComBoleto;
import br.com.manell.cursomc.domain.PagamentoComCartao;
import br.com.manell.cursomc.domain.Pedido;
import br.com.manell.cursomc.domain.Produto;
import br.com.manell.cursomc.domain.enums.EstadoPagamento;
import br.com.manell.cursomc.domain.enums.TipoCliente;
import br.com.manell.cursomc.repositories.CategoriaRepository;
import br.com.manell.cursomc.repositories.CidadeRepository;
import br.com.manell.cursomc.repositories.ClienteRepository;
import br.com.manell.cursomc.repositories.EnderecoRepository;
import br.com.manell.cursomc.repositories.EstadoRepository;
import br.com.manell.cursomc.repositories.ItemPedidoRepository;
import br.com.manell.cursomc.repositories.PagamentoRepository;
import br.com.manell.cursomc.repositories.PedidoRepository;
import br.com.manell.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// CATEGORIA - PRODUTO
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.0);
		Produto p2 = new Produto(null, "Impressora", 800.0);
		Produto p3 = new Produto(null, "Mouse", 80.0);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
			
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		// ESTADO - CIDADE 
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		// CLIENTE - ENDERECO
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "052.556.589-78", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("61 9 8459-6789", "61 9 9264-7587"));
		
		Endereco e1 = new Endereco(null, "Rua Valdomira Pimenta", "25", "casa 25", "Chácaras Rancho Alegre", "38412-685", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Sertão de Canudos", "101", "apt 101", "Vila Perus", "05208-010", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		// PEDIDO - PAGAMENTO - CLIENTE_PEDIDO
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2018 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2018 23:14"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2018 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		// ITEMPEDIDO_PEDIDO_PRODUTO
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(ped1, p2, 100.0, 1, 800.0);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}

}
