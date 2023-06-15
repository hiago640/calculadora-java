package br.com.hiago640.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {

	private static final Memoria instancia = new Memoria();

	private final List<MemoriaObserver> observers = new ArrayList<>();

	private TipoComandoEnum operacao;
	private boolean replace = false;
	private String textoAtual = "";
	private String textoBuffer = "";

	private Memoria() {

	}

	public static Memoria getInstancia() {
		return instancia;
	}

	public void addObservers(MemoriaObserver observer) {
		observers.add(observer);
	}

	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}

	public void processarComando(String valor) {

		TipoComandoEnum tipoComando = detectarTipoComando(valor);

		if (tipoComando == null)
			return;
		
		switch (tipoComando) {
		case ZERAR:
			textoAtual = "";
			textoBuffer = "";
			replace = false;
			operacao = null;
			break;

		case NUMERO:
		case VIRGULA:
			textoAtual = replace ? valor : textoAtual + valor;
			replace = false;
			break;
		default:
			replace = true;

			textoAtual = obtemResultado();
			textoBuffer = textoAtual;
			operacao = tipoComando;
			
			break;
		}

		observers.forEach(o -> o.valorAlterado(textoAtual));
	}

	private String obtemResultado() {
		if(operacao == null || operacao == TipoComandoEnum.IGUAL)
			return textoAtual;
		
		double buffer = Double.parseDouble(textoBuffer.replace(',', '.'));
		double inputAtual = Double.parseDouble(textoAtual.replace(',', '.'));
		double resultado = 0;
		
		switch (operacao) {
		case ADICAO:
			resultado = buffer + inputAtual;
			break;
		case SUBTRACAO:
			resultado = buffer - inputAtual;
			break;
		case MULTIPLICACAO:
			resultado = buffer * inputAtual;
			break;
		case DIVISAO:
			resultado = buffer / inputAtual;
			break;
		default:
			break;
		}
		
		String resultadoString = String.valueOf(resultado).replace('.', ',');
		boolean inteiro = resultadoString.endsWith(",0");
		return inteiro ? resultadoString.replace(",0", "") : resultadoString;
	}

	private TipoComandoEnum detectarTipoComando(String valor) {

		if (textoAtual.isEmpty() && "0".equals(valor))
			return null;

		try {
			Integer.parseInt(valor);
			return TipoComandoEnum.NUMERO;
		} catch (Exception e) {
			if("AC".equals(valor))
				return TipoComandoEnum.ZERAR;
			else if("/".equals(valor))
				return TipoComandoEnum.DIVISAO;
			else if("*".equals(valor))
				return TipoComandoEnum.MULTIPLICACAO;
			else if("+".equals(valor))
				return TipoComandoEnum.ADICAO;
			else if("-".equals(valor))
				return TipoComandoEnum.SUBTRACAO;
			else if("=".equals(valor))
				return TipoComandoEnum.IGUAL;
			else if(",".equals(valor) && !textoAtual.contains(","))
				return TipoComandoEnum.VIRGULA;

			return null;
			
		
		}

	}
}
