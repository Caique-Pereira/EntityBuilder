package br.com.visualmix.entity.builder.process;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import br.com.visualmix.entity.builder.entity.Column;
import br.com.visualmix.entity.builder.entity.EntityClass;


public class WriterClass {
	
	StringBuilder sb;
	String conteudoClass;
	EntityClass clazz;
	
	public void GenerateContent(EntityClass entity) {
		this.clazz = entity;
		sb = new StringBuilder();
		printImports();
		printBody();
		printMethod();
		conteudoClass = sb.toString();
		
	}
	
	
	private void printImports() {
		sb.append("package br.com.visualmix.databsp.base.model;\n");
		sb.append("import java.io.Serializable;\n");
		sb.append("import java.time.LocalDateTime;\n");
		sb.append("import org.springframework.data.annotation.Id;\n");
		sb.append("import jakarta.persistence.Temporal;\n");
		sb.append("import jakarta.persistence.TemporalType;\n");
		sb.append("import jakarta.persistence.Column;\n");
		sb.append("import jakarta.persistence.Entity;\n");
		sb.append("import jakarta.persistence.IdClass;\n");
		sb.append("import jakarta.persistence.Table;\n");
		sb.append("import lombok.Data;\n");
		sb.append("import lombok.EqualsAndHashCode;\n");
		sb.append("import lombok.NoArgsConstructor;\n");
	}
	
	private void printBody() {
		
		sb.append("\n");
		sb.append("@Data\n");
		sb.append("@Entity\n");
		sb.append("@NoArgsConstructor\n");
		sb.append("@Table(name = \"" + clazz.getTableName() + "\", schema = \"vm_databsp.dbo\") \n");
		if (clazz.isMultiplePrimaryKey())
			sb.append("@IdClass(" + clazz.getClassName() + ".PrimaryKey.class)\n");
		sb.append("public class " + clazz.getClassName() + " implements Serializable  {\n\n");
		
		sb.append("public static final String TABLE_NAME =\""+clazz.getTableName()+"\"; \n\n");
		if (clazz.isMultiplePrimaryKey()) {

			sb.append("@EqualsAndHashCode\n");
			sb.append("public static class PrimaryKey implements Serializable {\n");
			// Adicionar colunas dos atributos primary key
			for (Column column : clazz.getColumnList()) {
				if (clazz.getPrimaryKeyList().contains(column.getColumnOriginalName())) {
					sb.append("private " + column.getColumntype() + " " + column.getColumnVariableName() + ";\n");
				}
			}
			sb.append("}\n");
		}

		for (Column column : clazz.getColumnList()) {
			if (clazz.getPrimaryKeyList().contains(column.getColumnOriginalName())) sb.append("@Id\n");
			
				sb.append(column.getColumnAnnotation());
				sb.append(column.getColumnTemporalAnnotation());
				sb.append("private " + column.getColumntype() + " " + column.getColumnVariableName() + ";\n\n");
			
		}

	}
	
	
	private void printMethod() {
		
		sb.append("\n\n");
		sb.append(" public "+ clazz.getEntityDto().getDtoClassName() +" toDto() {\n");
		sb.append("ModelMapper modelMapper = new ModelMapper();\n");
		sb.append("return modelMapper.map(this,"+ clazz.getEntityDto().getDtoClassName()+".class);\n");
		sb.append("}\n");
		sb.append("\n");
		sb.append("\n\n");
		sb.append("}\n");
		
	}	
	
	  public void WriteFile(EntityClass clazz) {
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\User\\Desktop\\javaClasse\\entidade\\"+clazz.getClassName() + ".java"))) {
	            bw.write(conteudoClass);
	            bw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}
