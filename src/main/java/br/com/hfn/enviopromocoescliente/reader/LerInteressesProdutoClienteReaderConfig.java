package br.com.hfn.enviopromocoescliente.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import br.com.hfn.enviopromocoescliente.dominio.Cliente;
import br.com.hfn.enviopromocoescliente.dominio.InteresseProdutoCliente;
import br.com.hfn.enviopromocoescliente.dominio.Produto;

@Configuration
public class LerInteressesProdutoClienteReaderConfig {

	@Bean
	public JdbcCursorItemReader<InteresseProdutoCliente> lerInteressesProdutoClienteReader(
			@Qualifier("appDataSource") DataSource dataSource){
		
		return new JdbcCursorItemReaderBuilder<InteresseProdutoCliente>()
				.name("lerInteressesProdutoClienteReader")
				.dataSource(dataSource)
				.sql("select * from interesse_produto_cliente "
						+ "join cliente on (cliente = cliente.id) "
						+ "join produto on (produto = produto.id) ")
				.rowMapper(rowMapper())
				.build();
	}

	private RowMapper<InteresseProdutoCliente> rowMapper() {
		return new RowMapper<InteresseProdutoCliente>() {

			@Override
			public InteresseProdutoCliente mapRow(ResultSet rs, int rowNum) throws SQLException {
				Cliente c = new Cliente();
				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setEmail(rs.getString("email"));
				
				Produto p = new Produto();
				p.setId(rs.getInt(6));
				p.setNome(rs.getString(7));
				p.setDescricao(rs.getString("descricao"));
				p.setPreco(rs.getDouble("preco"));
				
				InteresseProdutoCliente ipc = new InteresseProdutoCliente();
				ipc.setCliente(c);
				ipc.setProduto(p);
				
				return ipc;
			}
			
		};
	}
}
