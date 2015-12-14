package me.falci.amigo_ladrao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Hello world!
 *
 */
public class App  {
	public static final AtomicInteger ORDEM = new AtomicInteger();
	public final List<Presente> presentes = new ArrayList<>();
	
	public void run(){

    	List<Amigo> participantes = new ArrayList<>();
    	participantes.add(new Amigo("Fulano"));
    	participantes.add(new Amigo("Siclano"));
    	participantes.add(new Amigo("Beltrano"));
    	
    	presentes.add(new Presente("Pacote grande e azul"));
    	presentes.add(new Presente("Socola verde"));
    	presentes.add(new Presente("Caixa embalada com jornal"));    	
    			
    	
    	participantes
    		.parallelStream() // random
    		.forEach(a -> {
	    		a.setOrdem(ORDEM.incrementAndGet());
	    	});
    	
    	participantes.sort(Comparator.comparingInt(Amigo::getOrdem));
    	
    	// o primeiro precisa pegar, não pode roubar
    	participantes.get(0).pegar( presentes.stream().findAny().get() );    	
		
		while(true){
			
			Optional<Amigo> amigo = participantes.stream()
					.filter(((Predicate<Amigo>)Amigo::temPresente).negate()) // sem presente
					.sorted()
					.findFirst();
			
			if(amigo.isPresent()){ // existe algum sem presente?
				Presente presente;
				
				if( tentarRoubar() ){ // quer roubar ou pegar um novo?
					
					presente = getPresenteQuePodeSerRoubado() // primeira opção: roubar
							.orElseGet(getPresenteSemDono()::get);
					
				} else {
					presente = getPresenteSemDono() // primeira opção: sem dono
							.orElseGet(getPresenteQuePodeSerRoubado()::get);
				}
				
				amigo.get().pegar(presente);
			} else {
				break;
			}
		}
	}
	
    public static void main( String[] args ) {
    	new App().run();
    	
    }
    
    public Optional<Presente> getPresenteQuePodeSerRoubado(){
		return presentes.parallelStream()
			.filter(Presente::podeSerRoubado)
			.findAny();    	
    }
    
    public Optional<Presente> getPresenteSemDono(){
		return presentes.parallelStream()
				.filter(((Predicate<Presente>) Presente::temDono).negate())
				.findAny();    	
    }
    
	private boolean tentarRoubar() {
		return Math.ceil(Math.random() * 2) %2 == 0;
	}
}
