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
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonOnderzoekBasis;

/**
 * De constatering dat een Onderzoek een Persoon raakt.
 *
 * Als er gegevens van een Persoon in Onderzoek staan, dan wordt (redundant) vastgelegd dat de Persoon onderwerp is van
 * een Onderzoek. Er wordt een koppeling tussen een Persoon en een Onderzoek gelegd indien er een gegeven in onderzoek
 * is dat behoort tot het objecttype Persoon, of tot de naar de Persoon verwijzende objecttypen. Een speciaal geval is
 * de Relatie: is de Relatie zelf in onderzoek, dan zijn alle Personen die betrokken zijn in die Relatie ook in
 * onderzoek.
 *
 * Het objecttype 'Persoon/Onderzoek' had ook de naam "Persoon in Onderzoek" kunnen heten. We sluiten echter aan bij de
 * naamgeving van andere koppeltabellen.
 *
 * De exemplaren van Persoon/Onderzoek zijn volledig afleidbaar uit de exemplaren van Gegevens-in-onderzoek. We leggen
 * dit gegeven echter redundant vast om snel de vraag te kunnen beantwoorden of 'de gegevens over de Persoon' in
 * onderzoek zijn.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonOnderzoekBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar,
        PersoonOnderzoekBasis
{

    private static final Integer META_ID = 6127;
    private PersoonBericht persoon;
    private OnderzoekBericht onderzoek;
    private PersoonOnderzoekStandaardGroepBericht standaard;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBericht getPersoon() {
        return persoon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnderzoekBericht getOnderzoek() {
        return onderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonOnderzoekStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * Zet Persoon van Persoon \ Onderzoek.
     *
     * @param persoon Persoon.
     */
    public void setPersoon(final PersoonBericht persoon) {
        this.persoon = persoon;
    }

    /**
     * Zet Onderzoek van Persoon \ Onderzoek.
     *
     * @param onderzoek Onderzoek.
     */
    public void setOnderzoek(final OnderzoekBericht onderzoek) {
        this.onderzoek = onderzoek;
    }

    /**
     * Zet Standaard van Persoon \ Onderzoek.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonOnderzoekStandaardGroepBericht standaard) {
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
