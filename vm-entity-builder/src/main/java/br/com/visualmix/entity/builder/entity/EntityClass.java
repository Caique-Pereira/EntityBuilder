package br.com.visualmix.entity.builder.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.visualmix.entity.builder.process.WriterClass;
import lombok.Data;


@Data
public class EntityClass {

	String className;
	String tableName;
	String schema;
	List<Column> columnList = new ArrayList();
	List<String> primaryKeyList = new ArrayList();
	EntityDtoClass entityDto;
	EntityRepositoryClass entityRepository;
	
	boolean isMultiplePrimaryKey = false;

	public EntityClass generateColumns(ResultSet metaData, ResultSet primaryKeys) throws SQLException {
		Column column;
		boolean isPrimaryKey;

		while (primaryKeys.next()) {
			primaryKeyList.add(primaryKeys.getString("COLUMN_NAME"));
		}
		

		while (metaData.next()) {
			column = new Column();

			isPrimaryKey = primaryKeyList.contains(metaData.getString("COLUMN_NAME"));

			column.loadinfos(metaData.getString("COLUMN_NAME"), metaData.getString("TYPE_NAME"),
					metaData.getInt("COLUMN_SIZE"), metaData.getString("IS_NULLABLE"), metaData.getString("COLUMN_DEF"),
					metaData.getString("IS_AUTOINCREMENT"), isPrimaryKey);

			columnList.add(column);
		}

		return this;
	}

	public EntityClass loadClassInfo(String tableName) {
		entityDto = new EntityDtoClass();
		entityRepository = new EntityRepositoryClass();
		
		
		this.tableName = tableName;
		String baseName = GenerateClassName(tableName);
		this.className = baseName;
		entityDto.setDtoClassName(baseName+"DTO");
		ValidatePrimaryKey();
		return this;

	}

	public void build() {		
      WriterClass writerClass = new WriterClass();
      writerClass.GenerateContent(this);
      writerClass.WriteFile(this);
	}
	
	private void ValidatePrimaryKey() {
	  if(primaryKeyList.size() > 1) isMultiplePrimaryKey = true;
	}

	
	private String GenerateClassName(String tableName) {
	    // Dividir a string com base nos underscores
	    String[] words = tableName.split("_");
	    StringBuilder newTableName = new StringBuilder();

	    for (String word : words) {
	        if (!word.isEmpty()) { // Verifique se a palavra não está vazia
	            // Converta a primeira letra da palavra para maiúscula e o restante para minúscula
	            newTableName.append(Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase());
	        }
	    }

	    return newTableName.toString();
	}


}
