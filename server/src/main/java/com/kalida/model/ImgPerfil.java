package com.kalida.model;

import java.io.Serializable;
import java.util.Base64;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "img_perfil")
public class ImgPerfil implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Lob
    @Column(nullable = false)
    private byte[] data;

    @JsonBackReference
    @OneToOne(optional = false)
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable=false)
    private User user;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private long lastTimeModified;

    public void setInfo(byte[] data, User user, String extension, long lastTimeModified){
        this.data = data;
        this.user = user;
        this.extension = extension;
        this.lastTimeModified = lastTimeModified;
    }

    public String toBase64(){
        return "data:image/"+extension+";base64," + Base64.getEncoder()
            .encodeToString(data);
    }
    
    
}
