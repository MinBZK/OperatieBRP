/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
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
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonGeboorteGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep, PersoonGeboorteGroepBasis,
        MetaIdentificeerbaar
{

    private static final Integer META_ID = 3514;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3673, 3675, 3676, 3677, 3530, 3678, 3543);
    private DatumEvtDeelsOnbekendAttribuut datumGeboorte;
    private String gemeenteGeboorteCode;
    private GemeenteAttribuut gemeenteGeboorte;
    private NaamEnumeratiewaardeAttribuut woonplaatsnaamGeboorte;
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsGeboorte;
    private BuitenlandseRegioAttribuut buitenlandseRegioGeboorte;
    private LocatieomschrijvingAttribuut omschrijvingLocatieGeboorte;
    private String landGebiedGeboorteCode;
    private LandGebiedAttribuut landGebiedGeboorte;

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumGeboorte() {
        return datumGeboorte;
    }

    /**
     * Retourneert Gemeente geboorte van Geboorte.
     *
     * @return Gemeente geboorte.
     */
    public String getGemeenteGeboorteCode() {
        return gemeenteGeboorteCode;
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
     * Retourneert Land/gebied geboorte van Geboorte.
     *
     * @return Land/gebied geboorte.
     */
    public String getLandGebiedGeboorteCode() {
        return landGebiedGeboorteCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandGebiedAttribuut getLandGebiedGeboorte() {
        return landGebiedGeboorte;
    }

    /**
     * Zet Datum geboorte van Geboorte.
     *
     * @param datumGeboorte Datum geboorte.
     */
    public void setDatumGeboorte(final DatumEvtDeelsOnbekendAttribuut datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    /**
     * Zet Gemeente geboorte van Geboorte.
     *
     * @param gemeenteGeboorteCode Gemeente geboorte.
     */
    public void setGemeenteGeboorteCode(final String gemeenteGeboorteCode) {
        this.gemeenteGeboorteCode = gemeenteGeboorteCode;
    }

    /**
     * Zet Gemeente geboorte van Geboorte.
     *
     * @param gemeenteGeboorte Gemeente geboorte.
     */
    public void setGemeenteGeboorte(final GemeenteAttribuut gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    /**
     * Zet Woonplaatsnaam geboorte van Geboorte.
     *
     * @param woonplaatsnaamGeboorte Woonplaatsnaam geboorte.
     */
    public void setWoonplaatsnaamGeboorte(final NaamEnumeratiewaardeAttribuut woonplaatsnaamGeboorte) {
        this.woonplaatsnaamGeboorte = woonplaatsnaamGeboorte;
    }

    /**
     * Zet Buitenlandse plaats geboorte van Geboorte.
     *
     * @param buitenlandsePlaatsGeboorte Buitenlandse plaats geboorte.
     */
    public void setBuitenlandsePlaatsGeboorte(final BuitenlandsePlaatsAttribuut buitenlandsePlaatsGeboorte) {
        this.buitenlandsePlaatsGeboorte = buitenlandsePlaatsGeboorte;
    }

    /**
     * Zet Buitenlandse regio geboorte van Geboorte.
     *
     * @param buitenlandseRegioGeboorte Buitenlandse regio geboorte.
     */
    public void setBuitenlandseRegioGeboorte(final BuitenlandseRegioAttribuut buitenlandseRegioGeboorte) {
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
    }

    /**
     * Zet Omschrijving locatie geboorte van Geboorte.
     *
     * @param omschrijvingLocatieGeboorte Omschrijving locatie geboorte.
     */
    public void setOmschrijvingLocatieGeboorte(final LocatieomschrijvingAttribuut omschrijvingLocatieGeboorte) {
        this.omschrijvingLocatieGeboorte = omschrijvingLocatieGeboorte;
    }

    /**
     * Zet Land/gebied geboorte van Geboorte.
     *
     * @param landGebiedGeboorteCode Land/gebied geboorte.
     */
    public void setLandGebiedGeboorteCode(final String landGebiedGeboorteCode) {
        this.landGebiedGeboorteCode = landGebiedGeboorteCode;
    }

    /**
     * Zet Land/gebied geboorte van Geboorte.
     *
     * @param landGebiedGeboorte Land/gebied geboorte.
     */
    public void setLandGebiedGeboorte(final LandGebiedAttribuut landGebiedGeboorte) {
        this.landGebiedGeboorte = landGebiedGeboorte;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean bevatElementMetMetaId(final Integer metaId) {
        return ONDERLIGGENDE_ATTRIBUTEN.contains(metaId);
    }

}
