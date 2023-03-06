package sistemaBiblioteca;

public class Livro {
    private String titulo, autor, nota, disponivel;
    
    public Livro(String titulo, String autor, String nota, String disponivel) {
        this.titulo = titulo;
        this.autor = autor;
        this.nota = nota;
        this.disponivel = disponivel;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getNota() {
        return nota;
    }

    public String getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(String disponivel) {
        this.disponivel = disponivel;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
