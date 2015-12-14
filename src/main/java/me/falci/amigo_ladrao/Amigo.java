package me.falci.amigo_ladrao;

public class Amigo implements Comparable<Amigo>{
	
	private final String nome;
	private Integer ordem = -1;
	private Presente presente = null;
	
	public Amigo(String nome) {
		
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	
	public int getOrdem() {
		return ordem;
	}
	
	public boolean temPresente(){
		return this.presente != null;
	}
	
	public void pegar(Presente presente){
		
		this.presente = presente;	
		presente.setDono(this);
	}
	
	@Override
	public String toString() {
		return String.format("Amigo: %s, %d, %s", nome, ordem, presente);
	}

	@Override
	public int compareTo(Amigo o) {
		return ordem.compareTo(o.ordem);
	}

	public void ficarSemPresente() {
		this.presente = null;
		this.ordem = App.ORDEM.incrementAndGet(); // fim da fila
	}

}
