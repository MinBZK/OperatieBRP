/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Aktenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijving;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.logisch.kern.DocumentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.DocumentStandaardGroepBasis;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Vorm van historie: formeel. Motivatie: het objecttype wordt gebruikt voor de verantwoording van een specifieke BRP
 * actie. Denkbaar is dat twee verschillende BRP acties verwijzen naar hetzelfde Document; relevant is welke gegevens er
 * toen geregistreerd stonden bij het Document, vandaar dat formele historie relevant is. NB: dit onderdeel van het
 * model is nog in ontwikkeling. Denkbaar is dat de modellering anders wordt. RvdP 17 jan 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@MappedSuperclass
public abstract class AbstractDocumentStandaardGroepModel implements DocumentStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Ident"))
    @JsonProperty
    private DocumentIdentificatie identificatie;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Aktenr"))
    @JsonProperty
    private Aktenummer            aktenummer;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    @JsonProperty
    private DocumentOmschrijving  omschrijving;

    @ManyToOne
    @JoinColumn(name = "Partij")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij                partij;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractDocumentStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param identificatie identificatie van Standaard.
     * @param aktenummer aktenummer van Standaard.
     * @param omschrijving omschrijving van Standaard.
     * @param partij partij van Standaard.
     */
    public AbstractDocumentStandaardGroepModel(final DocumentIdentificatie identificatie, final Aktenummer aktenummer,
            final DocumentOmschrijving omschrijving, final Partij partij)
    {
        this.identificatie = identificatie;
        this.aktenummer = aktenummer;
        this.omschrijving = omschrijving;
        this.partij = partij;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param documentStandaardGroep te kopieren groep.
     */
    public AbstractDocumentStandaardGroepModel(final DocumentStandaardGroep documentStandaardGroep) {
        this.identificatie = documentStandaardGroep.getIdentificatie();
        this.aktenummer = documentStandaardGroep.getAktenummer();
        this.omschrijving = documentStandaardGroep.getOmschrijving();
        this.partij = documentStandaardGroep.getPartij();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentIdentificatie getIdentificatie() {
        return identificatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Aktenummer getAktenummer() {
        return aktenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentOmschrijving getOmschrijving() {
        return omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getPartij() {
        return partij;
    }

}
