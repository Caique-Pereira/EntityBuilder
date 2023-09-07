package br.com.visualmix.entity.builder.process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import br.com.visualmix.entity.builder.entity.EntityClass;


public class WriterRepository{

	StringBuilder sb;
	String conteudoRepo;
	EntityClass clazz;
	String pkColumntype;
	public WriterRepository(EntityClass entity){
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
		conteudoRepo = sb.toString();
	}

	public void WriteFile(EntityClass clazz) {
		try (BufferedWriter bw = new BufferedWriter(
				new FileWriter("D:\\Estudos\\api\\vm-databsp-base\\src\\main\\java\\br\\com\\visualmix\\visualstore\\databsp\\base\\repositories\\" + clazz.getEntityRepository().getRepositoryName() + ".java"))) {
			bw.write(conteudoRepo);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void printImports() {
		sb.append("package br.com.visualmix.databsp.base.model;\n\n");
		sb.append("import org.springframework.data.jpa.repository.JpaRepository;\n");
		sb.append("import org.springframework.stereotype.Repository;\n");
	}

	private void printBody() {
		sb.append("\n");
		sb.append("@Repository\n");
		sb.append("public interface " + clazz.getEntityRepository().getRepositoryName()); 
		
		if(clazz.isMultiplePrimaryKey()) {
			sb.append(" extends JpaRepository<"+ clazz.getClassName()+ "," + clazz.getClassName()+".PrimaryKey>{\n");
		}else {
			
			 clazz.getColumnList().forEach(x -> {
				if(x.isPrimaryKey()) {
					pkColumntype = x.getColumntype();
				}	
			});
			
			sb.append(" extends JpaRepository<"+ clazz.getClassName()+ "," +pkColumntype+">{\n");
		}
	    sb.append("}");
	}

}
