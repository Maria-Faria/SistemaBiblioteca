package sistemaBiblioteca;


public class Emprestimo {
    private String nome, cpf, titulo;
    private String devolucao;
    
    public Emprestimo(String nome, String cpf, String titulo, String devolucao) {
        this.nome = nome;
        this.cpf = cpf;
        this.titulo = titulo;
        this.devolucao = devolucao;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDevolucao() {
        return devolucao;
    }

}
