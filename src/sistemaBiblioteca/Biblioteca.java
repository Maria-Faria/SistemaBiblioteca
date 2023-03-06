package sistemaBiblioteca;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;

public class Biblioteca {
    public static void main(String[] args) throws ParseException {
        ArrayList<String> titulos = new ArrayList<String>();
        ArrayList<String> autores = new ArrayList<String>();
        ArrayList<String> notas = new ArrayList<String>();
        ArrayList<String> disp = new ArrayList<String>();
        ArrayList<String> nomes = new ArrayList<String>();
        ArrayList<String> emprestado = new ArrayList<String>();
        ArrayList<String> cpfs = new ArrayList<String>();
        ArrayList<String> data = new ArrayList<String>();

        ArrayList<Livro> livros = new ArrayList<Livro>();
        ArrayList<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

        try {
            File f = criarArquivo("livros.csv");
            lerArquivo(f, titulos, autores, notas, disp);
            gerarDados(livros, titulos, autores, notas, disp);

        }catch(IOException e) {
            e.printStackTrace();
        }

        //--------------------------------------------------------

        int op, i, ct = 0;
        String op1[] = {"Ver todos os livros", "Buscar livro", "Buscar autor", "Livros disponíveis", "Empréstimo", "Devolução", "Funções do administrador"};

        op = JOptionPane.showOptionDialog(null, "O que você deseja fazer?", "Selecione uma opção", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, op1, 0);
        
        switch(op) {
            case 0:
                System.out.println("Título \t\t\t\t\t      Autor \t\t\t\t\t    Nota \t\t\t\t       Disponível");
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
                for(i = 0; i < livros.size(); i++) {
                    System.out.println(livros.get(i).getTitulo() + espacamento(livros.get(i).getTitulo(), 6) + livros.get(i).getAutor() + espacamento(livros.get(i).getAutor(), 5) + livros.get(i).getNota() + espacamento(livros.get(i).getNota(), 4) + livros.get(i).getDisponivel());
                }

                break;
            
            case 1:
                String titulo;
                titulo = JOptionPane.showInputDialog(null, "Digite o nome do livro que deseja buscar");
                
                System.out.println("Título \t\t\t\t\t      Autor \t\t\t\t\t    Nota \t\t\t\t       Disponível");
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
                for(i = 0; i < livros.size(); i++) {
                    if(livros.get(i).getTitulo().toLowerCase().startsWith(titulo.toLowerCase())) {
                        ct = 1;
                        System.out.println(livros.get(i).getTitulo() + espacamento(livros.get(i).getTitulo(), 6) + livros.get(i).getAutor() + espacamento(livros.get(i).getAutor(), 5) + livros.get(i).getNota() + espacamento(livros.get(i).getNota(), 4) + livros.get(i).getDisponivel());
                    }
                }

                if(ct == 0) {
                    JOptionPane.showMessageDialog(null, "Livro não encontrado!");
                }

                break;

            case 2:
                String autor = JOptionPane.showInputDialog(null, "Digite o nome do autor que deseja buscar");

                System.out.println("Título \t\t\t\t\t      Autor \t\t\t\t\t    Nota \t\t\t\t       Disponível");
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
                for(i = 0; i < livros.size(); i++) {
                    if(livros.get(i).getAutor().toLowerCase().startsWith(autor.toLowerCase())) {
                        ct = 1;
                        System.out.println(livros.get(i).getTitulo() + espacamento(livros.get(i).getTitulo(), 6) + livros.get(i).getAutor() + espacamento(livros.get(i).getAutor(), 5) + livros.get(i).getNota() + espacamento(livros.get(i).getNota(), 4) + livros.get(i).getDisponivel());
                    }
                }

                if(ct == 0) {
                    JOptionPane.showMessageDialog(null, "Autor não encontrado!");
                }

                break;

            case 3:
                System.out.println("Título \t\t\t\t\t      Autor \t\t\t\t\t    Nota \t\t\t\t       Disponível");
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
                for(i = 0; i < livros.size(); i++) {
                    if(livros.get(i).getDisponivel().equals("Sim")) {
                        ct = 1;
                        System.out.println(livros.get(i).getTitulo() + espacamento(livros.get(i).getTitulo(), 6) + livros.get(i).getAutor() + espacamento(livros.get(i).getAutor(), 5) + livros.get(i).getNota() + espacamento(livros.get(i).getNota(), 4) + livros.get(i).getDisponivel());
                    }
                }

                if(ct == 0) {
                    JOptionPane.showMessageDialog(null, "Não há livros disponíveis para empréstimo!");
                }

                break;

            case 4:
                String titulo2 = JOptionPane.showInputDialog(null, "Digite o título do livro");
                String nome, cpf;
                int cont = 0;

                Calendar c = Calendar.getInstance();
                Calendar c2  = Calendar.getInstance();
                c.set(Calendar.DAY_OF_MONTH, (c.get(Calendar.DAY_OF_MONTH) + 14));

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String devolver = df.format(c.getTime());

                for(i = 0; i < livros.size(); i++) {
                    if(livros.get(i).getTitulo().equalsIgnoreCase(titulo2) && livros.get(i).getDisponivel().equals("Sim")) {
                        nome = JOptionPane.showInputDialog(null, "Digite seu nome completo");
                        cpf = JOptionPane.showInputDialog(null, "Digite seu CPF");
                        
                        try{
                            criarArquivo("Emprestimos.txt");
                            Emprestimo e = new Emprestimo(nome, cpf, titulo2, devolver);
                            emprestimos.add(e);
                            escreverEmprestimos(emprestimos, nomes, cpfs, emprestado, data);
                            emprestados(emprestimos);
                            criarArquivo("Atrasos.txt");

                            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("Atrasos.txt"), "UTF-8"));
        
                            String linha = null;
                            String info[];
        
                            while((linha = in.readLine()) != null) {
                                info = linha.split(",");
                                
                                Date dev = new SimpleDateFormat("dd/MM/yyyy").parse(info[2]);
                                c.setTime(dev);

                                if(cpf.equals(info[1]) && (c2.get(Calendar.DAY_OF_MONTH) - 7 < c.get(Calendar.DAY_OF_MONTH))) {
                                    cont = 1;
                                    c.add(Calendar.DAY_OF_MONTH, 7);
                                    String atraso = df.format(c.getTime());
                                    
                                    JOptionPane.showMessageDialog(null, String.format("Empréstimo negado! Você só poderá pegar um livro no dia %s", atraso));
                                }
                             }
        
                            in.close();

                        }catch(IOException e) {
                            e.printStackTrace();
                        }

                        if(cont == 0) {
                            JOptionPane.showMessageDialog(null, String.format("Empréstimo de '%s' realizado!\n\nData de devolução: %s", titulo2, devolver));

                            livros.get(i).setDisponivel("Não");
                            escreverArq(livros, "livros.csv");
                        }

                    }else if(livros.get(i).getTitulo().equalsIgnoreCase(titulo2) && livros.get(i).getDisponivel().equals("Não")){
                        JOptionPane.showMessageDialog(null, "O livro solicitado não está disponível para empréstimo... :(");
                    }
                }

                break;

            case 5:
                String titulo3 = JOptionPane.showInputDialog(null, "Digite o título do livro que você vai devolver");
                String nome2, cpf2;

                nome2 = JOptionPane.showInputDialog(null, "Digite seu nome completo");
                cpf2 = JOptionPane.showInputDialog(null, "Digite seu CPF");

                for(i = 0; i < livros.size(); i++) {
                    if(livros.get(i).getTitulo().equalsIgnoreCase(titulo3)) {
                        livros.get(i).setDisponivel("Sim");
                        escreverArq(livros, "livros.csv");
                    }
                }

                escreverEmprestimos(emprestimos, nomes, cpfs, emprestado, data);
                Calendar date = Calendar.getInstance();
                Calendar atual = Calendar.getInstance();
                String devolvido;

                for(i = 0; i < emprestimos.size(); i++) {
                    Emprestimo e2 = emprestimos.get(i);
                    if(emprestimos.get(i).getTitulo().equalsIgnoreCase(titulo3) && emprestimos.get(i).getCpf().equals(cpf2)) {
                        emprestimos.remove(e2);

                        devolvido = data.get(i);
                        Date devolvido2 = new SimpleDateFormat("dd/MM/yyyy").parse(devolvido);
                        date.setTime(devolvido2);
                    }
                }

                emprestados(emprestimos);

                if(((atual.get(Calendar.DAY_OF_MONTH) > date.get(Calendar.DAY_OF_MONTH)) && atual.get(Calendar.MONTH) == date.get(Calendar.MONTH)) || (atual.get(Calendar.MONTH) > date.get(Calendar.MONTH)) || (atual.get(Calendar.YEAR) > date.get(Calendar.YEAR))) {
                    JOptionPane.showMessageDialog(null, "Você devolveu o livro com atraso! Agora só poderá pegar outro daqui a uma semana");
                    DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                    atrasado(nome2, cpf2, df2.format(atual.getTime()));
                }

                break;

            case 6:
                String adm[] = {"Adicionar um livro", "Remover um livro", "Sair"};
                int adm2; 
                String senhaCerta = "Hadm2k#23";

                JLabel label = new JLabel("Digite a senha");
                JPasswordField senha = new JPasswordField();
                JOptionPane.showConfirmDialog(null, new Object[]{label, senha}, "Senha do administrador", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

                String senhaDigitada = new String(senha.getPassword());
                if(senhaCerta.equals(senhaDigitada)) {
                    do{
                        adm2 = JOptionPane.showOptionDialog(null, "O que você deseja fazer?", "Selecione uma opção", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, adm, 0);
                    
                        switch(adm2) {
                            case 0:
                                String livro = JOptionPane.showInputDialog(null, "Digite o título do livro para adicionar");
                                String escritor = JOptionPane.showInputDialog(null, "Digite o nome do autor do livro");
                                String classificao = JOptionPane.showInputDialog(null, "Digite a nota do livro (de * a * * * * *)");

                                Livro livroNovo = new Livro(livro, escritor, classificao, "Sim");
                                livros.add(livroNovo);
                                escreverArq(livros, "livros.csv");

                                break;

                            case 1:
                                String livroRemover = JOptionPane.showInputDialog(null, "Digite o título do livro para remover");

                                for(i = 0; i < livros.size(); i++) {
                                    Livro livroExcluido = livros.get(i);
                                    if(livros.get(i).getTitulo().equalsIgnoreCase(livroRemover)) {
                                        livros.remove(livroExcluido);
                                    }
                                }

                                escreverArq(livros, "livros.csv");

                                break;
                        }
                    }while(adm2 != 2);
                }else {
                    JOptionPane.showMessageDialog(null, "Acesso negado! Senha incorreta!");
                }
        }
    }

    public static File criarArquivo(String nomeArq) throws IOException{
        File f = new File(nomeArq);

        return f;
    }

    public static void lerArquivo(File nomeArq, ArrayList<String> titulos, ArrayList<String> autores, ArrayList<String> notas, ArrayList<String> disp) {
    
        try{

            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(nomeArq), "UTF-8"));

            String linha = null;
            String info[];

            while((linha = in.readLine()) != null) {
                info = linha.split(",");

                titulos.add(info[0]);
                autores.add(info[1]);
                notas.add(info[2]);
                disp.add(info[3]);
            }

            in.close();

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void gerarDados(ArrayList<Livro> livros, ArrayList<String> titulos, ArrayList<String> autores, ArrayList<String> notas, ArrayList<String> disp) {
        Livro l[] = new Livro[titulos.size()];

        for(int i = 0; i < titulos.size(); i++) {
            l[i] = new Livro(titulos.get(i), autores.get(i), notas.get(i), disp.get(i));
            livros.add(l[i]);
        }
    }

    public static String espacamento(String titulo, int qtd) {
        String espaco = "                                        ";
        int aux = espaco.length();
        int aux2 = 1;

        if(titulo.length() < qtd) {
            aux2 = 6 - titulo.length();
        
        }else if(titulo.length() > qtd) {
            aux2 = aux - titulo.length();
            espaco = "      ";
        }

        for(int i = 0; i < aux2; i++) {
            espaco += " ";
        }

        return espaco;
    }

    public static void escreverArq(ArrayList<Livro> livros, String nomeArq) {
        try{
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(nomeArq), "UTF-8");

            for(int i = 0; i < livros.size(); i++) {
                writer.write(livros.get(i).getTitulo() + "," + livros.get(i).getAutor() + "," + livros.get(i).getNota() + "," + livros.get(i).getDisponivel() + "\n");
            }

            writer.close();

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void escreverEmprestimos(ArrayList<Emprestimo> emprestimos,ArrayList<String> nomes, ArrayList<String> cpfs, ArrayList<String> emprestado, ArrayList<String> data) {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("Emprestimos.txt"), "UTF-8"));

            String linha = null;
            String info[];

            while((linha = in.readLine()) != null) {
                info = linha.split(",");

                nomes.add(info[0]);
                cpfs.add(info[1]);
                emprestado.add(info[2]);
                data.add(info[3]);
            }

            in.close();

        }catch(IOException e) {
            e.printStackTrace();
        }

        Emprestimo e[] = new Emprestimo[nomes.size()];
        for(int i = 0; i < nomes.size(); i++) {
            e[i] = new Emprestimo(nomes.get(i), cpfs.get(i), emprestado.get(i), data.get(i));
            emprestimos.add(e[i]);
        }
    }

    public static void emprestados(ArrayList<Emprestimo> emprestimos) {
        try{
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("Emprestimos.txt"), "UTF-8");

            for(int i = 0; i < emprestimos.size(); i++) {
                writer.write(emprestimos.get(i).getNome() + "," + emprestimos.get(i).getCpf() + "," + emprestimos.get(i).getTitulo() + "," + emprestimos.get(i).getDevolucao() + "\n");
            }

            writer.close();

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void atrasado(String nome, String cpf, String data) {
        try{
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("Atrasos.txt", true), "UTF-8");

            writer.write(nome + "," + cpf + "," + data + "\n");
            
            writer.close();

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}