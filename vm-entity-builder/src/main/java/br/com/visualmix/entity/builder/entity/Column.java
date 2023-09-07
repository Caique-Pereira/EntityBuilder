package br.com.visualmix.entity.builder.entity;


import lombok.Data;
public class Column {
	
	boolean primaryKey = false;
	boolean temporal = false;
	String columnVariableName;
	String ColumnOriginalName;
	String Columntype;
	int columSize;
	
	public void loadinfos(String columnName, String TypeName, int columnSize, String IsNullable, String ColumnDef, String isAtoIncrement,
			boolean isPrimaryKey) {

		this.ColumnOriginalName =  columnName;
		this.columnVariableName = formatVariableName(columnName);
		this.Columntype = toJavaType(TypeName);
		this.columSize = columnSize;
		this.primaryKey = isPrimaryKey;
	}
	
	
	   private String formatVariableName(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split("[^a-zA-Z0-9]+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                if (result.length() == 0) {
                    // A primeira palavra começa com letra minúscula
                    result.append(Character.toLowerCase(word.charAt(0)));
                } else {
                    // As palavras subsequentes começam com letra maiúscula
                    result.append(Character.toUpperCase(word.charAt(0)));
                }

                if (word.length() > 1) {
                    // Adicione o restante da palavra em minúsculas
                    result.append(word.substring(1).toLowerCase());
                }
            }
        }

        return result.toString();
    }
		
	
	
	private String toJavaType(String TypeName) {

		switch (TypeName) {
		case "float":return "Double";
		case "int":return "Integer";
		case "tinyint":return"Byte";
		case "varchar":return  "String";
		case "smallint": return "Short";
		case "datetime":
		    temporal = true;
		    return"LocalDateTime";
		default:return "String";	
		}

	
	}
	
	public String getColumnOriginalName() {
       return this.ColumnOriginalName;		
	}
	
	public String getColumntype() {
		return this.Columntype;
	}
	
	public String getColumnVariableName() {
		return this.columnVariableName;
	}
	
	public String getColumnAnnotation() {
	   if(this.ColumnOriginalName.toUpperCase().equals("COMPANY_ID")) {
		   return "@Column(name = \"COMPANY_ID\", nullable = true, columnDefinition = \"int default 1\")\n";
	   }else {	   
		   return String.format("@Column(name = \"%s\") \n", this.ColumnOriginalName);
	   }	
	}
	
	public String getColumnTemporalAnnotation() {
		
		if(this.Columntype.equals("DATE")) {
		  return "@Temporal(TemporalType.TIMESTAMP)";
		}else {
			return "";
		}
	}
	
	

	
		
	
}
