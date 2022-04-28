package br.sp.parksguide.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class Avaliacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Parque parque;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar dataVisita;
	private String comentario;
	private double nota;
	@ManyToOne
	private Usuario usuario;
	
}
