

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    //BD em memória
    private List<Livro> acervo = new ArrayList<>();
    final  int anoPublicacaoMinimo = 1400;

    public Livro adicionar(Livro novoLivro) throws Exception {
        if (novoLivro.getTitulo() == null || novoLivro.getTitulo().isEmpty())
            throw new Exception("Campo Título Não Pode Ser Vazio");
        else if (novoLivro.getAutor() == null || novoLivro.getAutor().isEmpty()){
            throw new Exception("Campo Autor Não Pode Ser Vazio!");
        } else if (novoLivro.getAnoPublicacao() <= anoPublicacaoMinimo) {
            throw new Exception("Campo Ano de Publicação Não Pode ser Menor que 1400");
        } else if (novoLivro.getNumeroPaginas() <= 0) {
            throw new Exception("Campo Numero de Páginas Não Pode Ser Negativo ou Igual a Zero");
        }

        for (Livro livro : acervo) {
            if (livro.getTitulo().equalsIgnoreCase(novoLivro.getTitulo())) {
                throw new Exception("Já existe um livro com este título no acervo!");
            }
        }


        acervo.add(novoLivro);

        return novoLivro;
    }


    public List<Livro> pesquisarPorTitulo(String titulo) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                livrosEncontrados.add(livro);
            }
        }
        return livrosEncontrados;
    }

    public List<Livro> pesquisarTodos() {
        return acervo;
    }

    public boolean deletarLivro(String titulo) throws Exception {
        if (titulo == null || titulo.isEmpty()) {
            throw new Exception("Título inválido para exclusão!");
        }

        List<Livro> livrosParaRemover = new ArrayList<>();


        for (Livro livro : acervo) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                livrosParaRemover.add(livro);
            }
        }

        boolean removido = acervo.removeAll(livrosParaRemover);

        return removido;
    }

    public List<LivroDigital> mostrarLivroDigitals() {
        List<LivroDigital> livrosDigitais = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro instanceof LivroDigital) {
                livrosDigitais.add((LivroDigital) livro);
            }
        }
        return livrosDigitais;
    }

    public List<LivroFisico> mostrarLivrosFisicos() {
        List<LivroFisico> livrosFisicos = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro instanceof LivroFisico) {
                livrosFisicos.add((LivroFisico) livro);
            }
        }
        return livrosFisicos;
    }

}
