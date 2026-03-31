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
public class ExperienceNewDTO implements Serializable{
   

   private IDDTO<Integer> technology;

   private short startYear;
}
