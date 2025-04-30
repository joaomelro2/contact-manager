package AgendaContactos;

/**
 * Classe que representa um contacto da agenda.
 * Contém nome, telefone e email como atributos.
 */
public class Contato{
    /** Nome do Contacto */
    private String nome;
    /** Telefone do Contacto */
    private String telefone;
    /** Email do Contacto */
    private String email;

    /**Construtor da classe Contato
     * 
     * @param nome Nome do Contacto
     * @param telefone Telefone do Contacto
     * @param email Email do Contacto
     */
    public Contato(String nome, String telefone, String email)
    {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    /** Devolve o nome do contacto
     * 
     * @return Nome do contacto
     */
    public String getNome()
    {
        return this.nome;
    }

    /** Define o nome do contacto
     * 
     * @param nome Novo nome do contacto
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    /** Devolve o telefone do contacto
     * 
     * @return Telefone do contacto
     */
    public String getTelefone()
    {   
        return this.telefone;
    }

    /** Define o telefone do contacto
     * 
     * @param telefone Novo telefone do contacto
     */
    public void setTelefone(String telefone)
    {
        this.telefone = telefone;
    }

    /** Devolve o email do contacto
     * 
     * @return Email do contacto
     */
    public String getEmail()
    {   
        return this.email;
    }

    /** Define o email do contacto
     * 
     * @param email Novo email do contacto
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /** Devolve uma representação em texto do contacto.
     * 
     * Formato: nome,telefone,email.
     * 
     * @return Representação em texto do contacto
     */
    @Override
    public String toString() {
        return nome + "," + telefone + "," + email;
    }


}