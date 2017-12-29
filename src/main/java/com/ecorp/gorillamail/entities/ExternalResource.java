package com.ecorp.gorillamail.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table( name = "ExternalResources" )
public class ExternalResource extends AbstractLongEntity {
    private static final long serialVersionUID = 0L;

    @Column( nullable = false )
    @Getter
    @Setter
    private String originalUrl;

    @Column( nullable = false )
    @Getter
    @Setter
    private String shortenedUrl;

    @ManyToOne
    @JoinColumn( name = "template" )
    @Getter
    @Setter
    private Template template = null;

    @OneToMany( mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true )
    @Getter
    private Set<VisitorInformation> visitors = new HashSet<>();

    public ExternalResource(String originalUrl, String shortenedUrl) {
        setOriginalUrl(originalUrl);
        setShortenedUrl(shortenedUrl);
    }
}
