package com.fiap.bean;

import com.google.gson.JsonObject;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EnderecoCompleto {

	private String logradouro;
	
	private String complemento;
	
	private String bairro;
	
	private String cidade;
	
	private String estado;
	
	private String CEP;
	
	public EnderecoCompleto (JsonObject json) {
		try {
			
			this.logradouro = json.get("logradouro").toString().replaceAll("\"", "");
			this.complemento = json.get("complemento").toString().replaceAll("\"", "");
			this.bairro = json.get("bairro").toString().replaceAll("\"", "");
			this.cidade = json.get("localidade").toString().replaceAll("\"", "");
			this.estado = json.get("uf").toString().replaceAll("\"", "");
			this.CEP = json.get("cep").toString().replaceAll("\"", "");
			
		} catch (NullPointerException e) {
			
		}
	}

	/**
	 * Utilizando o formato dos correio para o endereço completo.
	 * 
	 * @Example: [Logradouro], [Número] - [Complemento] - [Bairro], [Cidade] - [Estado], [CEP]
	 * 
	 */
	@Override
	public String toString() {
		if ( this.logradouro != null && this.bairro != null && this.cidade != null ) {
			return this.logradouro + ", " + this.complemento + " - " + this.bairro + ", " + this.cidade + " - " + this.estado + ", " + this.CEP;	
		} else {
			return "Nenhum endereço encontrado para o CEP consultado.";
		}
	}
}
