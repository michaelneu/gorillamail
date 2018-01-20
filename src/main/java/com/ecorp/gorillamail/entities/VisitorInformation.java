package com.ecorp.gorillamail.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "VisitorInformation" )
public class VisitorInformation extends AbstractLongEntity {
    private static final long serialVersionUID = 0L;

    @Column( nullable = false )
    @Getter
    @Setter
    private String userAgent;

    @Column( nullable = false )
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date visitedAt;

    @Fetch( FetchMode.SELECT )
    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "resource" )
    @Getter
    @Setter
    private ExternalResource resource = null;
}
