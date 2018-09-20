/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.DocumentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.DocumentStandaardGroepBasis;

/**
 * Vorm van historie: formeel. Motivatie: het objecttype wordt gebruikt voor de verantwoording van een specifieke BRP
 * actie. Denkbaar is dat twee verschillende BRP acties verwijzen naar hetzelfde Document; relevant is welke gegevens er
 * toen geregistreerd stonden bij het Document, vandaar dat formele historie relevant is. NB: dit onderdeel van het
 * model is nog in ontwikkeling. Denkbaar is dat de modellering anders wordt.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractDocumentStandaardGroepModel implements DocumentStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = DocumentIdentificatieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Ident"))
    @JsonProperty
    private DocumentIdentificatieAttribuut identificatie;

    @Embedded
    @AttributeOverride(name = AktenummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Aktenr"))
    @JsonProperty
    private AktenummerAttribuut aktenummer;

    @Embedded
    @AttributeOverride(name = DocumentOmschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Oms"))
    @JsonProperty
    private DocumentOmschrijvingAttribuut omschrijving;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Partij"))
    @JsonProperty
    private PartijAttribuut partij;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
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
    public AbstractDocumentStandaardGroepModel(
        final DocumentIdentificatieAttribuut identificatie,
        final AktenummerAttribuut aktenummer,
        final DocumentOmschrijvingAttribuut omschrijving,
        final PartijAttribuut partij)
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
    public DocumentIdentificatieAttribuut getIdentificatie() {
        return identificatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AktenummerAttribuut getAktenummer() {
        return aktenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentOmschrijvingAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (identificatie != null) {
            attributen.add(identificatie);
        }
        if (aktenummer != null) {
            attributen.add(aktenummer);
        }
        if (omschrijving != null) {
            attributen.add(omschrijving);
        }
        if (partij != null) {
            attributen.add(partij);
        }
        return attributen;
    }

}
