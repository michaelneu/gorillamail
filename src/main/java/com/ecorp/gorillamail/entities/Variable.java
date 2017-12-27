package com.ecorp.gorillamail.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Table( name = "Variables" )
public class Variable implements Serializable {
    private static final long serialVersionUID = 0L;

    @Column( nullable = false )
    @Getter
    @Setter
    private String name;

    @Column( nullable = false )
    @Getter
    @Setter
    private String value;
}
