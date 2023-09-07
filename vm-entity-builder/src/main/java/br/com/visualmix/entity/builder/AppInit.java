package br.com.visualmix.entity.builder;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import br.com.visualmix.entity.builder.entity.EntityClass;
import br.com.visualmix.entity.builder.entity.EntityDtoClass;
import br.com.visualmix.entity.builder.entity.EntityRepositoryClass;

public class AppInit {

	EntityClass entityClass;
	EntityDtoClass entityDtoClass;
	EntityRepositoryClass entityRepositoryClass;
	String catalog = "vm_databsp"; // Nome do seu banco de dados
	String schemaPattern = null; // Padrão do esquema, pode ser null
	String tableNamePattern = null; // Padrão do nome da tabela, pode ser null
	String[] types = { "TABLE" }; // Estamos interessados apenas em tabelas

	public String execute(DatabaseMetaData databaseMetaData) throws SQLException {
	
		ResultSet tables = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);
		while (tables.next()) {
			entityClass = new EntityClass();
			ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(null, null, tables.getString("TABLE_NAME"));
			ResultSet metadata = databaseMetaData.getColumns(null, null, tables.getString("TABLE_NAME"), null);
			System.out.println("Processando Tabela : " + tables.getString("TABLE_NAME")  );
			entityClass.generateColumns(metadata, primaryKeys)
						.loadClassInfo(tables.getString("TABLE_NAME"))
						.build();
		}
		
		return "ok";
	

		

		

	}

}
