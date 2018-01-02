package com.ecorp.gorillamail.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table( name = "ExternalResources" )
@NamedQueries({
    @NamedQuery(name = ExternalResource.QUERY_BY_SHORT_URL, query = "SELECT e FROM ExternalResource e WHERE e.shortenedUrl = :url"),
    @NamedQuery(name = ExternalResource.QUERY_BY_ORIGINAL_URL, query = "SELECT e FROM ExternalResource e WHERE e.originalUrl = :url"),
})
public class ExternalResource extends AbstractLongEntity {
    public static final String QUERY_BY_SHORT_URL = "query_by_short_url",
                               QUERY_BY_ORIGINAL_URL = "query_by_original_url";
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
