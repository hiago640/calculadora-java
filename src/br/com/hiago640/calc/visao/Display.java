package br.com.hiago640.calc.visao;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.hiago640.calc.modelo.Memoria;
import br.com.hiago640.calc.modelo.MemoriaObserver;

public class Display extends JPanel implements MemoriaObserver {

	private static final long serialVersionUID = 5427488727958123478L;

	private JLabel label = new JLabel();
	public Display() {
		Memoria.getInstancia().addObservers(this);
		
		setBackground(new Color(46, 49, 50));
		label.setText(Memoria.getInstancia().getTextoAtual());
		label.setForeground(Color.WHITE);
		label.setFont(new Font("nunito", Font.PLAIN, 30));
		
		setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 25));
		
		add(label);
	}
	
	@Override
	public void valorAlterado(String novoValor) {
		label.setText(novoValor);
	}
}
