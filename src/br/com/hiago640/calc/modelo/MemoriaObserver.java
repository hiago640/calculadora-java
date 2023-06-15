package br.com.hiago640.calc.modelo;

@FunctionalInterface
public interface MemoriaObserver {
	
	void valorAlterado(String novoValor);
}
