/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ist;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CoderingOnjuistAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3RubriekInclCategorieEnGroepAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.ist.StapelVoorkomenStandaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

/**
 * Groep die van toepassing is voor ALLE stapels, dus voor zowel categorie 02, 03, 05, 09 als 11.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractStapelVoorkomenStandaardGroepModel implements StapelVoorkomenStandaardGroepBasis {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdmHnd")
    @JsonProperty
    private AdministratieveHandelingModel administratieveHandeling;

    @Embedded
    @AssociationOverride(name = SoortDocumentAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "SrtDoc"))
    @JsonProperty
    private SoortDocumentAttribuut soortDocument;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Partij"))
    @JsonProperty
    private PartijAttribuut partij;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr8220DatDoc"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut rubriek8220DatumDocument;

    @Embedded
    @AttributeOverride(name = DocumentOmschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DocOms"))
    @JsonProperty
    private DocumentOmschrijvingAttribuut documentOmschrijving;

    @Embedded
    @AttributeOverride(name = LO3RubriekInclCategorieEnGroepAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr8310AandGegevensInOnderz"))
    @JsonProperty
    private LO3RubriekInclCategorieEnGroepAttribuut rubriek8310AanduidingGegevensInOnderzoek;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr8320DatIngangOnderzoek"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut rubriek8320DatumIngangOnderzoek;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr8330DatEindeOnderzoek"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut rubriek8330DatumEindeOnderzoek;

    @Embedded
    @AttributeOverride(name = LO3CoderingOnjuistAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr8410OnjuistStrijdigOpenb"))
    @JsonProperty
    private LO3CoderingOnjuistAttribuut rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr8510IngangsdatGel"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut rubriek8510IngangsdatumGeldigheid;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr8610DatVanOpneming"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut rubriek8610DatumVanOpneming;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractStapelVoorkomenStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param administratieveHandeling administratieveHandeling van Standaard.
     * @param soortDocument soortDocument van Standaard.
     * @param partij partij van Standaard.
     * @param rubriek8220DatumDocument rubriek8220DatumDocument van Standaard.
     * @param documentOmschrijving documentOmschrijving van Standaard.
     * @param rubriek8310AanduidingGegevensInOnderzoek rubriek8310AanduidingGegevensInOnderzoek van Standaard.
     * @param rubriek8320DatumIngangOnderzoek rubriek8320DatumIngangOnderzoek van Standaard.
     * @param rubriek8330DatumEindeOnderzoek rubriek8330DatumEindeOnderzoek van Standaard.
     * @param rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde
     *            van Standaard.
     * @param rubriek8510IngangsdatumGeldigheid rubriek8510IngangsdatumGeldigheid van Standaard.
     * @param rubriek8610DatumVanOpneming rubriek8610DatumVanOpneming van Standaard.
     */
    public AbstractStapelVoorkomenStandaardGroepModel(
        final AdministratieveHandelingModel administratieveHandeling,
        final SoortDocumentAttribuut soortDocument,
        final PartijAttribuut partij,
        final DatumEvtDeelsOnbekendAttribuut rubriek8220DatumDocument,
        final DocumentOmschrijvingAttribuut documentOmschrijving,
        final LO3RubriekInclCategorieEnGroepAttribuut rubriek8310AanduidingGegevensInOnderzoek,
        final DatumEvtDeelsOnbekendAttribuut rubriek8320DatumIngangOnderzoek,
        final DatumEvtDeelsOnbekendAttribuut rubriek8330DatumEindeOnderzoek,
        final LO3CoderingOnjuistAttribuut rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde,
        final DatumEvtDeelsOnbekendAttribuut rubriek8510IngangsdatumGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut rubriek8610DatumVanOpneming)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.administratieveHandeling = administratieveHandeling;
        this.soortDocument = soortDocument;
        this.partij = partij;
        this.rubriek8220DatumDocument = rubriek8220DatumDocument;
        this.documentOmschrijving = documentOmschrijving;
        this.rubriek8310AanduidingGegevensInOnderzoek = rubriek8310AanduidingGegevensInOnderzoek;
        this.rubriek8320DatumIngangOnderzoek = rubriek8320DatumIngangOnderzoek;
        this.rubriek8330DatumEindeOnderzoek = rubriek8330DatumEindeOnderzoek;
        this.rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde = rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde;
        this.rubriek8510IngangsdatumGeldigheid = rubriek8510IngangsdatumGeldigheid;
        this.rubriek8610DatumVanOpneming = rubriek8610DatumVanOpneming;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortDocumentAttribuut getSoortDocument() {
        return soortDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getRubriek8220DatumDocument() {
        return rubriek8220DatumDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentOmschrijvingAttribuut getDocumentOmschrijving() {
        return documentOmschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3RubriekInclCategorieEnGroepAttribuut getRubriek8310AanduidingGegevensInOnderzoek() {
        return rubriek8310AanduidingGegevensInOnderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getRubriek8320DatumIngangOnderzoek() {
        return rubriek8320DatumIngangOnderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getRubriek8330DatumEindeOnderzoek() {
        return rubriek8330DatumEindeOnderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3CoderingOnjuistAttribuut getRubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde() {
        return rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getRubriek8510IngangsdatumGeldigheid() {
        return rubriek8510IngangsdatumGeldigheid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getRubriek8610DatumVanOpneming() {
        return rubriek8610DatumVanOpneming;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (soortDocument != null) {
            attributen.add(soortDocument);
        }
        if (partij != null) {
            attributen.add(partij);
        }
        if (rubriek8220DatumDocument != null) {
            attributen.add(rubriek8220DatumDocument);
        }
        if (documentOmschrijving != null) {
            attributen.add(documentOmschrijving);
        }
        if (rubriek8310AanduidingGegevensInOnderzoek != null) {
            attributen.add(rubriek8310AanduidingGegevensInOnderzoek);
        }
        if (rubriek8320DatumIngangOnderzoek != null) {
            attributen.add(rubriek8320DatumIngangOnderzoek);
        }
        if (rubriek8330DatumEindeOnderzoek != null) {
            attributen.add(rubriek8330DatumEindeOnderzoek);
        }
        if (rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde != null) {
            attributen.add(rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde);
        }
        if (rubriek8510IngangsdatumGeldigheid != null) {
            attributen.add(rubriek8510IngangsdatumGeldigheid);
        }
        if (rubriek8610DatumVanOpneming != null) {
            attributen.add(rubriek8610DatumVanOpneming);
        }
        return attributen;
    }

}
