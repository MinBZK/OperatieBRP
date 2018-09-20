/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteitBasis;

/**
 * Een aanduiding van de nationaliteit die de persoon bezit.
 *
 * 1. Waarden 'Vastgesteld niet-Nederlander', 'Behandelen als Nederlander' en 'Statenloos' worden niet geregistreerd als
 * Nationaliteit, maar onder een aparte groep. Motivatie voor het apart behandelen van 'Vastgesteld niet-Nederlander',
 * is dat het een expliciete uitspraak is (van een rechter), dat iemand geen Nederlander (meer) is. Deze waarde kan best
 * náást bijvoorbeeld een Belgische Nationaliteit gelden, en moet niet worden gezien als 'deelinformatie' over de
 * Nationaliteit. De 'Behandelen als Nederlander' gaat over de wijze van behandeling, en past dientengevolge minder goed
 * als waarde voor 'Nationaliteit'. Als er (vermoedelijk) wel een Nationaliteit is, alleen die is onbekend, dán wordt
 * "Onbekend" ingevuld.
 *
 * 2.Nationaliteit is niet sterk gedefinieerd. Het wijkt af van de Wikipedia definitie (althans, op 23 februari 2011).
 * Beste bron lijkt een Europees verdrag (Europees Verdrag inzake nationaliteit, Straatsburg, 06-11-1997), en dan de
 * Nederlandse vertaling ervan:
 * "de juridische band tussen een persoon en een Staat; deze term verwijst niet naar de etnische afkomst van de persoon"
 * . Deze zin loop niet heel lekker en is ook niet éénduidig (er is ook een juridische band tussen een president van een
 * Staat en die Staat ten gevolge van het presidentschap, en die wordt niet bedoeld). Daarom gekozen voor een nadere
 * toelichting met verwijzing. Verder goed om in de toelichting te beschrijven hoe wordt omgegaan met meerdere
 * Nationaliteiten (ook wel dubbele Nationaliteiten genoemd), dan wel statenloosheid en vastgesteld niet-nederlander.
 *
 * 3. Dit Objecttype wijkt sterk af van de wijze van registeren zoals geintroduceerd is bij LO 3.9. Zie voor meer
 * informatie de A:"Persoon \ Nationaliteit.Bijhouding beeindigd?" en _Migratie:_ attributen.
 *
 * Het betreft de juridische band tussen een persoon en een staat zoals bedoeld in het Europees verdrag inzake
 * nationaliteit, Straatsburg 06-11-1997.
 *
 * Indien iemand tegelijk meerdere nationaliteiten heeft, zijn dit ook aparte exemplaren van Nationaliteit. Indien
 * aannemelijk is dat iemand een Nationaliteit heeft, maar deze is onbekend, dat wordt dit vastgelegd als 'Onbekend'.
 * Situaties als 'behandeld als Nederlander', 'Vastgesteld niet-Nederlander' en 'Staatloos' worden geregistreerd onder
 * 'overige indicaties', en niet als Nationaliteit.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonNationaliteitBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar,
        PersoonNationaliteitBasis
{

    private static final Integer META_ID = 3129;
    private PersoonBericht persoon;
    private String nationaliteitCode;
    private NationaliteitAttribuut nationaliteit;
    private PersoonNationaliteitStandaardGroepBericht standaard;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBericht getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Nationaliteit van Identiteit.
     *
     * @return Nationaliteit.
     */
    public String getNationaliteitCode() {
        return nationaliteitCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NationaliteitAttribuut getNationaliteit() {
        return nationaliteit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonNationaliteitStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * Zet Persoon van Persoon \ Nationaliteit.
     *
     * @param persoon Persoon.
     */
    public void setPersoon(final PersoonBericht persoon) {
        this.persoon = persoon;
    }

    /**
     * Zet Nationaliteit van Identiteit.
     *
     * @param nationaliteitCode Nationaliteit.
     */
    public void setNationaliteitCode(final String nationaliteitCode) {
        this.nationaliteitCode = nationaliteitCode;
    }

    /**
     * Zet Nationaliteit van Persoon \ Nationaliteit.
     *
     * @param nationaliteit Nationaliteit.
     */
    public void setNationaliteit(final NationaliteitAttribuut nationaliteit) {
        this.nationaliteit = nationaliteit;
    }

    /**
     * Zet Standaard van Persoon \ Nationaliteit.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonNationaliteitStandaardGroepBericht standaard) {
        this.standaard = standaard;
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
    public List<BerichtEntiteit> getBerichtEntiteiten() {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteitGroep> getBerichtEntiteitGroepen() {
        final List<BerichtEntiteitGroep> berichtGroepen = new ArrayList<>();
        berichtGroepen.add(getStandaard());
        return berichtGroepen;
    }

}
