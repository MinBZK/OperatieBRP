/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.OnderzoekBasis;

/**
 * Onderzoek naar gegevens in de BRP.
 *
 * Normaliter is er geen reden om te twijfelen aan de in de BRP geregistreerde gegevens. Soms echter is dat wel aan de
 * orde. Vanuit verschillende hoeken kan een signaal komen dat bepaalde gegevens niet correct zijn. Dit kan om zowel
 * actuele gegevens als om (materieel) historische gegevens gaan. Met het objecttype Onderzoek wordt vastgelegd dat
 * gegevens in onderzoek zijn, en welke gegevens het betreft.
 *
 * Nog onderzoeken: Relatie met Terugmeld voorziening (TMV)/TMF.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractOnderzoekBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar, OnderzoekBasis
{

    private static final Integer META_ID = 3167;
    private OnderzoekStandaardGroepBericht standaard;
    private OnderzoekAfgeleidAdministratiefGroepBericht afgeleidAdministratief;
    private List<GegevenInOnderzoekBericht> gegevensInOnderzoek;
    private List<PersoonOnderzoekBericht> personenInOnderzoek;
    private List<PartijOnderzoekBericht> partijenInOnderzoek;

    /**
     * {@inheritDoc}
     */
    @Override
    public OnderzoekStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnderzoekAfgeleidAdministratiefGroepBericht getAfgeleidAdministratief() {
        return afgeleidAdministratief;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GegevenInOnderzoekBericht> getGegevensInOnderzoek() {
        return gegevensInOnderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersoonOnderzoekBericht> getPersonenInOnderzoek() {
        return personenInOnderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PartijOnderzoekBericht> getPartijenInOnderzoek() {
        return partijenInOnderzoek;
    }

    /**
     * Zet Standaard van Onderzoek.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final OnderzoekStandaardGroepBericht standaard) {
        this.standaard = standaard;
    }

    /**
     * Zet Afgeleid administratief van Onderzoek.
     *
     * @param afgeleidAdministratief Afgeleid administratief.
     */
    public void setAfgeleidAdministratief(final OnderzoekAfgeleidAdministratiefGroepBericht afgeleidAdministratief) {
        this.afgeleidAdministratief = afgeleidAdministratief;
    }

    /**
     * Zet Onderwerpen van onderzoek van Onderzoek.
     *
     * @param gegevensInOnderzoek Onderwerpen van onderzoek.
     */
    public void setGegevensInOnderzoek(final List<GegevenInOnderzoekBericht> gegevensInOnderzoek) {
        this.gegevensInOnderzoek = gegevensInOnderzoek;
    }

    /**
     * Zet Personen \ Onderzoek van Onderzoek.
     *
     * @param personenInOnderzoek Personen \ Onderzoek.
     */
    public void setPersonenInOnderzoek(final List<PersoonOnderzoekBericht> personenInOnderzoek) {
        this.personenInOnderzoek = personenInOnderzoek;
    }

    /**
     * Zet Partijen \ Onderzoek van Onderzoek.
     *
     * @param partijenInOnderzoek Partijen \ Onderzoek.
     */
    public void setPartijenInOnderzoek(final List<PartijOnderzoekBericht> partijenInOnderzoek) {
        this.partijenInOnderzoek = partijenInOnderzoek;
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
        final List<BerichtEntiteit> berichtEntiteiten = new ArrayList<>();
        if (gegevensInOnderzoek != null) {
            berichtEntiteiten.addAll(getGegevensInOnderzoek());
        }
        if (personenInOnderzoek != null) {
            berichtEntiteiten.addAll(getPersonenInOnderzoek());
        }
        if (partijenInOnderzoek != null) {
            berichtEntiteiten.addAll(getPartijenInOnderzoek());
        }
        return berichtEntiteiten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteitGroep> getBerichtEntiteitGroepen() {
        final List<BerichtEntiteitGroep> berichtGroepen = new ArrayList<>();
        berichtGroepen.add(getStandaard());
        berichtGroepen.add(getAfgeleidAdministratief());
        return berichtGroepen;
    }

}
