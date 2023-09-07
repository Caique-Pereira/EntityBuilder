package br.com.visualmix.entity.builder.process;
public class CamelCaseFormatter {

    public static String formatStringToCamelCase(String input) {
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

    public static void main(String[] args) {
        String[] examples = {"mel", "Mel", "mel_azedo", "MEl_azedo", "caique", "CAique_Pereira", "caique_PEREIRA", "data", "ultimaData", "NovaData", "DataAnterior", "controleremoto", "TErremoto"};

        for (String example : examples) {
            String formatted = formatStringToCamelCase(example);
            System.out.println("Original: " + example + " -> CamelCase: " + formatted);
        }
    }
}
