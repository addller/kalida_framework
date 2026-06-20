package com.kalida.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDTO implements Serializable{
   
   private static final long serialVersionUID = 1L;

   private Long id;

   private TechnologyDTO technology;

   private short startYear;
}
