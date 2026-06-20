package com.kalida.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Experience implements Serializable {

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @EqualsAndHashCode.Include
   private Long id;

   @JsonBackReference
   @ManyToOne(fetch = FetchType.LAZY, optional=false)
   @JoinColumn(name = "user_id", nullable=false)
   private User user;

   @JsonManagedReference
   @ManyToOne(fetch = FetchType.LAZY, optional=false)
   @JoinColumn(name = "technology_id", nullable=false)
   private Technology technology;

   @Column(name="start_year", nullable =  false)
   private short startYear;
}
