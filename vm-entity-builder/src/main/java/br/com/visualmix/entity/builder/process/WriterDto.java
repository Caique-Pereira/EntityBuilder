package br.com.visualmix.entity.builder.process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import br.com.visualmix.entity.builder.entity.Column;
import br.com.visualmix.entity.builder.entity.EntityClass;


public class WriterDto{

	StringBuilder sb;
	String conteudoDto;
	EntityClass clazz;
	
	public WriterDto(EntityClass entity){
		this.clazz = entity;
		
	}
	
	public void execute() {
		GenerateContent();
		WriteFile(clazz);
	}
	

	public void GenerateContent() {
		sb = new StringBuilder();
		printImports();
		printBody();
		printMethod();
		conteudoDto = sb.toString();
	}

	private void printImports() {
		sb.append("package br.com.visualmix.databsp.base.model;\n");
		sb.append("import java.time.LocalDateTime;\n");
		sb.append("import lombok.Data;\n");
		sb.append("import lombok.NoArgsConstructor;\n");
		sb.append("import lombok.AllArgsConstructor;\n");

	}

	private void printBody() {

		sb.append("\n");
		sb.append("@Data\n");
		sb.append("@NoArgsConstructor\n");
		sb.append("@AllArgsConstructor\n");
		sb.append("public class " + clazz.getEntityDto().getDtoClassName() + " implements Serializable  {\n\n");

		sb.append("public static final String TABLE_NAME =\"" + clazz.getTableName() + "\"; \n\n");

		for (Column column : clazz.getColumnList()) {
			sb.append("private " + column.getColumntype() + " " + column.getColumnVariableName() + ";\n");
		}
	}

	private void printMethod() {
		sb.append("\n\n");
		sb.append(" public " + clazz.getClassName() + " toEntity() {\n");
		sb.append("ModelMapper modelMapper = new ModelMapper();\n");
		sb.append("return modelMapper.map(this," + clazz.getClassName() + ".class);\n");
		sb.append("}\n");
		sb.append("\n");
		sb.append("\n\n");
		sb.append("}\n");

	}

	public void WriteFile(EntityClass clazz) {
		try (BufferedWriter bw = new BufferedWriter(
				new FileWriter("D:\\Estudos\\api\\vm-databsp-base\\src\\main\\java\\br\\com\\visualmix\\visualstore\\databsp\\base\\dtos\\" + clazz.getEntityDto().getDtoClassName() + ".java"))) {
			bw.write(conteudoDto);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
