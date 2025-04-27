

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Biblioteca biblio = new Biblioteca();

    public static void main(String[] args) {
        String menu = """
                ====== SYSBIBLIO ======
                Escolha uma das opções abaixo:
                1 - Adicionar novo livro
                2 - Pesquisar livro por título
                3 - Listar todos os livros
                4 - Remover livro por título
                5 - Listar  livros físicos
                6 - Listar livros digitais
                0 - Sair
                """;
        int opcao;
        Scanner lerTeclado = new Scanner(System.in); 
        System.out.print("\033[H\033[2J");
        System.out.flush();
        do {
            //System.out.println(menu);
            //opcao = lerTeclado.nextInt();
            //lerTeclado.nextLine(); //Limpar buffer entrada do console (teclado
            opcao = inputNumerico(lerTeclado, menu);
            switch (opcao) {
                case 1:
                    adicionar(lerTeclado);
                    break;
                case 2:
                    pesquisarPorTitulo(lerTeclado, biblio);
                    break;
                case 3:
                    pesquisarTodos();
                    break;
                case 4:
                    deletarLivro(lerTeclado, biblio);
                    break;

                case 5:
                    mostrarLivrosFisicos(lerTeclado);
                    break;

                case 6:
                    mostrarLivrosDigitais(lerTeclado);
                    break;
                case 0:
                    System.out.println("Encerrando programa ...");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        } while (opcao != 0);
    }

    private static void adicionar(Scanner lerTeclado) {

        System.out.println("Digite o título do livro:");
        String titulo = lerTeclado.nextLine();


        System.out.println("Digite o autor do livro:");
        String autor = lerTeclado.nextLine();


        int anoPublicacao = inputNumerico(lerTeclado, "Digite o ano da publicação:");


        int numeroPaginas = inputNumerico(lerTeclado, "Digite o número de páginas:");

        Livro novoLivro;

        int tipoLivro = inputNumerico(lerTeclado, "Qual o tipo do livro: 1-Físico, 2 Digital");
        if (tipoLivro == 1) {
            novoLivro = new LivroFisico();
            System.out.println("Digite as dimensões do livro:");
            String dimensoes = lerTeclado.nextLine();
            int numeroExemplares = inputNumerico(lerTeclado, "Digite o número de exemplares:");

            LivroFisico novoLivroFisico = (LivroFisico) novoLivro;
            novoLivroFisico.setDimensoes(dimensoes);
            novoLivroFisico.setNumeroExemplares(numeroExemplares);
            //((LivroFisico) novoLivro).setDimensoes(dimensoes);
        } else {
            novoLivro = new LivroDigital();
            System.out.println("Digite o formato do arquivo:");
            String formatoArquivo = lerTeclado.nextLine();

            double tamanhoArquivo = inputNumerico(lerTeclado, "Digite o tamanho do arquivo (em MB):");
            lerTeclado.nextLine();

            LivroDigital novoLivroDigital = (LivroDigital) novoLivro;
            novoLivroDigital.setFormatoArquivo(formatoArquivo);
            novoLivroDigital.setTamanhoArquivo(tamanhoArquivo);

        }

        novoLivro.setTitulo(titulo);
        novoLivro.setAutor(autor);
        novoLivro.setAnoPublicacao(anoPublicacao);
        novoLivro.setNumeroPaginas(numeroPaginas);

        try {
            biblio.adicionar(novoLivro);
            System.out.println("Livro adicionado com Sucesso!");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        System.out.println("Pressione ENTER para continuar...");
        lerTeclado.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pesquisarTodos() {
        List<Livro> livros = biblio.pesquisarTodos();
        if (livros.isEmpty()) {
            System.out.println("NENHUM LIVRO CADASTRADO");
        } else {
            System.out.println("Livros Cadastrados:");
            for (Livro livro : livros) {
                System.out.println(livro.toString());
            }
        }  

        System.out.println("Pressione ENTER para continuar...");
        @SuppressWarnings("resource")
        Scanner lerTeclado = new Scanner(System.in);
        lerTeclado.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    private static int inputNumerico(Scanner lerTeclado, String mensagem) {
        int valor = 0;
        boolean entradaValida = false;
        System.out.println(mensagem);
        do {
            String valorStr = lerTeclado.nextLine();
            try {
                valor = Integer.parseInt(valorStr);
                entradaValida = true;
            } catch (Exception e) {
                System.out.println("Erro. Por favor informe um número Inteiro");
            }
        } while (!entradaValida);
        return valor;
    }

    public static void pesquisarPorTitulo(Scanner lerTeclado, Biblioteca biblioteca) {
        System.out.println("Digite o título do livro que deseja pesquisar:");
        String titulo = lerTeclado.nextLine();

        List<Livro> resultados = biblioteca.pesquisarPorTitulo(titulo);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum livro encontrado com o título: " + titulo);
        } else {
            System.out.println("\nLivros encontrados:");
            for (Livro livro : resultados) {
                System.out.print("- " + livro.getTitulo());
                System.out.print(" | Publicado há " + calcularTempoPublicacao(livro.getAnoPublicacao()) + " anos");

                if (livro instanceof LivroDigital) {
                    LivroDigital livroDigital = (LivroDigital) livro;
                    System.out.println(" | Formato: " + livroDigital.getFormatoArquivo());
                } else {
                    System.out.println(" | Tipo: Livro Físico");
                }
            }
        }
        System.out.println("\nPressione ENTER para continuar...");
        lerTeclado.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static int calcularTempoPublicacao(int anoPublicacao) {
        int anoAtual = java.time.Year.now().getValue();
        return anoAtual - anoPublicacao;
    }

    public static void deletarLivro(Scanner lerTeclado, Biblioteca biblioteca) {
        try {
            System.out.print("Digite o título do livro que deseja deletar: ");
            String titulo = lerTeclado.nextLine();

            boolean removido = biblioteca.deletarLivro(titulo);

            if (removido) {
                System.out.println("Livro(s) com título '" + titulo + "' removido(s) com sucesso!");
            } else {
                System.out.println("Nenhum livro encontrado com o título '" + titulo + "'");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar livro: " + e.getMessage());
        }

        System.out.println("\nPressione ENTER para continuar...");
        lerTeclado.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void mostrarLivrosDigitais(Scanner lerTeclado) {
        List<LivroDigital> livroDigitals = biblio.mostrarLivroDigitals();
        if(livroDigitals.isEmpty()) {
            System.out.println("Nenhum Livro Digital Encontrado!");
        } else { 
            System.out.println("Livros Digitais Encontrados:");
            for (LivroDigital livro : livroDigitals) {
                System.out.println("- Título: " + livro.getTitulo());
                System.out.println("  Autor: " + livro.getAutor());
                System.out.println("  Publicado há " + calcularTempoPublicacao(livro.getAnoPublicacao()) + " anos");
                System.out.println("  Formato: " + livro.getFormatoArquivo());
                System.out.println("  Tamanho do arquivo: " + livro.getTamanhoArquivo() + " MB");
            }
        }
        System.out.println("Pressione ENTER para continuar...");
        lerTeclado.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void mostrarLivrosFisicos(Scanner lerTeclado) {
        List<LivroFisico> livroFisicos = biblio.mostrarLivrosFisicos();
        if(livroFisicos.isEmpty()) {
            System.out.println("Nenhum Livro Físico Encontrado!");
        } else { 
            System.out.println("Livros Físicos Encontrados:");
            for (LivroFisico livro : livroFisicos) {
                System.out.println("- Título: " + livro.getTitulo());
                System.out.println("  Autor: " + livro.getAutor());
                System.out.println("  Publicado há " + calcularTempoPublicacao(livro.getAnoPublicacao()) + " anos");
                System.out.println("  Dimensões: " + livro.getDimensoes());
                System.out.println("  Exemplares: " + livro.getNumeroExemplares());
                System.out.println();
            }
        }
        System.out.println("Pressione ENTER para continuar...");
        lerTeclado.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
