package me.falci.amigo_ladrao;

public class Presente {
	
	private static final int MAX_VEZES_ROUBADO = 2;
	private final String descricao;
	private int vezesRoubado = 0;
	private Amigo dono = null;
	
	public Presente(String descricao) {
		
		this.descricao = descricao;		
	}
	
	private void incrementVezesRoubado() {
		this.vezesRoubado++;
	}
	
	public boolean podeSerRoubado(){
		return this.temDono() && this.vezesRoubado < MAX_VEZES_ROUBADO;
	}
	
	public void setDono(Amigo dono) {
		if(temDono()){
			incrementVezesRoubado();
			
			System.out.println(String.format("%s roubou o/a %s do/a %s", dono.getNome(), descricao, this.dono.getNome()));
			
			this.dono.ficarSemPresente();
		} else {
			System.out.println(String.format("%s pegou o/a %s", dono.getNome(), descricao));
		}
		
		this.dono = dono;
	}
	
	public boolean temDono(){
		return this.dono != null;
	}
	
	public Amigo getDono() {
		return dono;
	}
	
	@Override
	public String toString() {
		return String.format("Presente: %s, roubado %d vezes", descricao, vezesRoubado);
	}
	
	public String getDescricao() {
		return descricao;
	}

}
