package com.github.julio.entity;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class Funcionario extends Pessoa {
	private BigDecimal salario;
	private String funcao;

	public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
		super(nome, dataNascimento);
		this.salario = salario;
		this.funcao = funcao;
	}
	public Funcionario() {}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public String getSalarioAsString() {
		NumberFormat pattern = NumberFormat.getNumberInstance(new Locale.Builder().setLanguage("pt").setRegion("BR").build());
		pattern.setMinimumFractionDigits(2);
		pattern.setMaximumFractionDigits(2);
		return pattern.format(getSalario());
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public void aumentarSalarioPorcento(float percentual) {
		this.setSalario(this.getSalario().multiply(new BigDecimal(percentual / 100 + 1)));
	}

	@Override
	public String toString() {
		return 	"{\"nome\": " + "\"" + this.getNome() + "\", " +
				"\"dataNascimento\": " + "\"" + this.getDataNascimentoAsString() + "\", " +
				"\"salario\": \"" + this.getSalarioAsString() + "\", " +
				"\"funcao\": " + "\"" +  this.getFuncao() + "\"}";
	}
}
