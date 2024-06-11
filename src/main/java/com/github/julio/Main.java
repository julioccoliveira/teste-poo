package com.github.julio;

import com.github.julio.entity.Funcionario;
import com.github.julio.entity.Pessoa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

public class Main {
	public static void main(String[] args) {

		List<Funcionario> funcionarios = new ArrayList<>(List.of(
				new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador"),
				new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador"),
				new Funcionario("Caio", LocalDate.of(1961, 5, 2), BigDecimal.valueOf(9836.14), "Coordenador"),
				new Funcionario("Miguel", LocalDate.of(1988, 10, 14), BigDecimal.valueOf(19119.88), "Diretor"),
				new Funcionario("Alice", LocalDate.of(1995, 1, 5), BigDecimal.valueOf(2234.68), "Recepcionista"),
				new Funcionario("Heitor", LocalDate.of(1999, 11, 19), BigDecimal.valueOf(1582.72), "Operador"),
				new Funcionario("Arthur", LocalDate.of(1993, 3, 31), BigDecimal.valueOf(4071.84), "Contador"),
				new Funcionario("Laura", LocalDate.of(1994, 7, 8), BigDecimal.valueOf(3017.45), "Gerente"),
				new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), BigDecimal.valueOf(1606.85), "Eletricista"),
				new Funcionario("Helena", LocalDate.of(1996, 9, 2), BigDecimal.valueOf(2799.93), "Gerente")
		));

		removeFuncionario("João", funcionarios);

		System.out.println(funcionarios);

		aumentoGlobal(10f, funcionarios);
		
		Map<String, List<Funcionario>> mapa = orderByFuncao(funcionarios);

		System.out.println(mapa);

		System.out.println(getAniversarioByMes(funcionarios, 10, 12));

		System.out.println(getMaisVelho(funcionarios));

		orderByName(funcionarios);
		System.out.println(funcionarios);

		System.out.println(somarSalarios(funcionarios));

		System.out.println(contadorSalarios(funcionarios));

	}

	public static void removeFuncionario(String name, List<Funcionario> funcionarios) {
		Funcionario toRemove = new Funcionario();
		for (Funcionario f : funcionarios) {
			if (f.getNome().equals(name)) {
				toRemove = f;
			}
		}
		funcionarios.remove(toRemove);
	}

	public static void aumentoGlobal(float porcentual, List<Funcionario> funcionarios) {
		funcionarios.forEach(f -> f.aumentarSalarioPorcento(porcentual));
	}
	
	public static Map<String, List<Funcionario>> orderByFuncao(List<Funcionario> funcionarios) {
		Map<String, List<Funcionario>> mapa = new HashMap<>();

		funcionarios.forEach(f -> {
			if (mapa.containsKey(f.getFuncao())) {
				List<Funcionario> listaProvisoria = mapa.get(f.getFuncao());
				listaProvisoria.add(f);
			} else {
				mapa.put(f.getFuncao(), new ArrayList<>(List.of(f)));
			}
		});

		return mapa;
	}

	public static List<Funcionario> getAniversarioByMes(List<Funcionario> funcionarios, int... meses) {
		List<Funcionario> response = new ArrayList<>();

		for (int mes : meses) {
			response.addAll(funcionarios.stream().filter(f -> f.getDataNascimento().getMonthValue() == mes).toList());
		}

		return response;
	}

	public static String getMaisVelho(List<Funcionario> funcionarios) {
		Funcionario funcionario = null;

		if (funcionarios == null || funcionarios.isEmpty()) {
			return "404 - Lista de Funcionários not found.";
		}
		for (Funcionario f: funcionarios) {
			if (funcionario == null || f.getDataNascimento().isBefore(funcionario.getDataNascimento())) {
				funcionario = f;
			}
		}

		return "{\"nome\": \"" + funcionario.getNome() + "\"" + ", \"idade\": " + funcionario.getIdade() + "}";
	}

	public static void orderByName(List<Funcionario> funcionarios) {
		funcionarios.sort(Comparator.comparing(Pessoa::getNome));
	}

	public static String somarSalarios(List<Funcionario> funcionarios) {
		NumberFormat pattern = NumberFormat.getNumberInstance(new Locale.Builder().setLanguage("pt").setRegion("BR").build());
		pattern.setMinimumFractionDigits(2);
		pattern.setMaximumFractionDigits(2);

		return pattern.format(funcionarios
					.stream()
					.map(Funcionario::getSalario)
					.reduce(BigDecimal.ZERO, BigDecimal::add));
	}

	public static String contadorSalarios(List<Funcionario> funcionarios) {
		final BigDecimal salarioMinQuestao = new BigDecimal(1212);
		StringBuilder response = new StringBuilder();
		response.append("[");
		for (Funcionario f : funcionarios) {
			response.append("{");
			response.append("\"nome\": ").append("\"").append(f.getNome()).append("\", ");
			response.append("\"salario\": ").append(f.getSalario().divide(salarioMinQuestao, 0, RoundingMode.FLOOR));
			response.append("}, ");
		}
		response.delete(response.length() - 2, response.length());
		response.append("]");

		return response.toString();
	}
}