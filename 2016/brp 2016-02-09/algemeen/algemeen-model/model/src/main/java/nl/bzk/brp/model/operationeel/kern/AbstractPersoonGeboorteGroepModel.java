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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroepBasis;

/**
 * Geboortegevens over een Persoon.
 *
 * Geboortegegevens zijn belangrijke identificerende gegevens. De geboortelocatie is zodanig gespecificeerd dat nagenoeg
 * alle situaties verwerkt kunnen worden. Verreweg de meeste gevallen passen in de structuur 'land - regio -
 * buitenlandse plaats' of indien het een geboorte is in het Europese deel van Nederland in de structuur 'land -
 * gemeente - woonplaats'. In uitzonderingssituaties zijn deze structuren niet toereikend. In dat geval wordt
 * 'omschrijving geboortelocatie' gebruikt. In voorkomende gevallen kan hier een verwijzing naar bijvoorbeeld
 * geografische coördinaten staan.
 *
 * 1. Het is denkbaar om 'Geboorte' als een levensgebeurtenis te beschouwen, c.q. een objecttype te construeren die
 * klinkt als 'Gebeurtenis'. Hiervan zouden dan een aantal relevante attributen onderkend kunnen worden, zoals een punt
 * in de tijddimensie (bijv. datum of datum) en in de ruimte (middels woonplaats, gemeente, land, etc etc). Hier is niet
 * voor gekozen.
 *
 * Het binnen de scope van de BRP brengen van Levensgebeurtenis heeft echter grote impact, en introduceert vragen waar
 * nu nog geen antwoord op bekend is ('is adoptie een levensgebeurtenis?'). De feiten die we bij bijvoorbeeld de
 * Geboorte, Overlijden, sluiten en ontbinding Huwelijk willen weten is in essentie de aanuiding van ruimte (plaats,
 * gemeente, ...) en tijd. Behalve deze logische identificatie van ruimte en tijd is er echter 'niets' dat we hierover
 * willen weten. Anders gesteld: een dergelijke entiteit heeft géén bestaansrecht. We onderkennen daarom niet een apart
 * objecttype hiervoor, en modelleren de relevante attributen (datum, gemeente, woonplaats, ..., land) uit daar waar het
 * zich voordoet.
 *
 * Consequenties: Door gebeurtenis direct 'specifiek' uit te modelleren (en dus bijvoorbeeld gewoon een groepje
 * 'geboortegegevens' op te nemen bij de Persoon) wordt het model eenvoudiger. Het nadeel is dat dezelfde soort gegevens
 * (aanduiding van een punt in tijd en tuimte) herhaaldelijk gespecificeerd moet worden, maar dat is een overzienbaar
 * resultaat. 2. Geboorte kent géén (aparte) materiële historie: het refereert naar één moment in de (materiële) tijd,
 * op de datum geboorte; er is verder geen (materiële) 'geldigheidsperiode' voor bijv. datum geboorte: dit jaar, vorig
 * jaar en volgend jaar ben je nog steeds in 1969 geboren
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonGeboorteGroepModel implements PersoonGeboorteGroepBasis {

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatGeboorte"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumGeboorte;

    @Embedded
    @AssociationOverride(name = GemeenteAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "GemGeboorte"))
    @JsonProperty
    private GemeenteAttribuut gemeenteGeboorte;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "WplnaamGeboorte"))
    @JsonProperty
    private NaamEnumeratiewaardeAttribuut woonplaatsnaamGeboorte;

    @Embedded
    @AttributeOverride(name = BuitenlandsePlaatsAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLPlaatsGeboorte"))
    @JsonProperty
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsGeboorte;

    @Embedded
    @AttributeOverride(name = BuitenlandseRegioAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLRegioGeboorte"))
    @JsonProperty
    private BuitenlandseRegioAttribuut buitenlandseRegioGeboorte;

    @Embedded
    @AttributeOverride(name = LocatieomschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OmsLocGeboorte"))
    @JsonProperty
    private LocatieomschrijvingAttribuut omschrijvingLocatieGeboorte;

    @Embedded
    @AssociationOverride(name = LandGebiedAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "LandGebiedGeboorte"))
    @JsonProperty
    private LandGebiedAttribuut landGebiedGeboorte;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonGeboorteGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumGeboorte datumGeboorte van Geboorte.
     * @param gemeenteGeboorte gemeenteGeboorte van Geboorte.
     * @param woonplaatsnaamGeboorte woonplaatsnaamGeboorte van Geboorte.
     * @param buitenlandsePlaatsGeboorte buitenlandsePlaatsGeboorte van Geboorte.
     * @param buitenlandseRegioGeboorte buitenlandseRegioGeboorte van Geboorte.
     * @param omschrijvingLocatieGeboorte omschrijvingLocatieGeboorte van Geboorte.
     * @param landGebiedGeboorte landGebiedGeboorte van Geboorte.
     */
    public AbstractPersoonGeboorteGroepModel(
        final DatumEvtDeelsOnbekendAttribuut datumGeboorte,
        final GemeenteAttribuut gemeenteGeboorte,
        final NaamEnumeratiewaardeAttribuut woonplaatsnaamGeboorte,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsGeboorte,
        final BuitenlandseRegioAttribuut buitenlandseRegioGeboorte,
        final LocatieomschrijvingAttribuut omschrijvingLocatieGeboorte,
        final LandGebiedAttribuut landGebiedGeboorte)
    {
        this.datumGeboorte = datumGeboorte;
        this.gemeenteGeboorte = gemeenteGeboorte;
        this.woonplaatsnaamGeboorte = woonplaatsnaamGeboorte;
        this.buitenlandsePlaatsGeboorte = buitenlandsePlaatsGeboorte;
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
        this.omschrijvingLocatieGeboorte = omschrijvingLocatieGeboorte;
        this.landGebiedGeboorte = landGebiedGeboorte;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonGeboorteGroep te kopieren groep.
     */
    public AbstractPersoonGeboorteGroepModel(final PersoonGeboorteGroep persoonGeboorteGroep) {
        this.datumGeboorte = persoonGeboorteGroep.getDatumGeboorte();
        this.gemeenteGeboorte = persoonGeboorteGroep.getGemeenteGeboorte();
        this.woonplaatsnaamGeboorte = persoonGeboorteGroep.getWoonplaatsnaamGeboorte();
        this.buitenlandsePlaatsGeboorte = persoonGeboorteGroep.getBuitenlandsePlaatsGeboorte();
        this.buitenlandseRegioGeboorte = persoonGeboorteGroep.getBuitenlandseRegioGeboorte();
        this.omschrijvingLocatieGeboorte = persoonGeboorteGroep.getOmschrijvingLocatieGeboorte();
        this.landGebiedGeboorte = persoonGeboorteGroep.getLandGebiedGeboorte();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumGeboorte() {
        return datumGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemeenteAttribuut getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamEnumeratiewaardeAttribuut getWoonplaatsnaamGeboorte() {
        return woonplaatsnaamGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsGeboorte() {
        return buitenlandsePlaatsGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandseRegioAttribuut getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieomschrijvingAttribuut getOmschrijvingLocatieGeboorte() {
        return omschrijvingLocatieGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandGebiedAttribuut getLandGebiedGeboorte() {
        return landGebiedGeboorte;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumGeboorte != null) {
            attributen.add(datumGeboorte);
        }
        if (gemeenteGeboorte != null) {
            attributen.add(gemeenteGeboorte);
        }
        if (woonplaatsnaamGeboorte != null) {
            attributen.add(woonplaatsnaamGeboorte);
        }
        if (buitenlandsePlaatsGeboorte != null) {
            attributen.add(buitenlandsePlaatsGeboorte);
        }
        if (buitenlandseRegioGeboorte != null) {
            attributen.add(buitenlandseRegioGeboorte);
        }
        if (omschrijvingLocatieGeboorte != null) {
            attributen.add(omschrijvingLocatieGeboorte);
        }
        if (landGebiedGeboorte != null) {
            attributen.add(landGebiedGeboorte);
        }
        return attributen;
    }

}
