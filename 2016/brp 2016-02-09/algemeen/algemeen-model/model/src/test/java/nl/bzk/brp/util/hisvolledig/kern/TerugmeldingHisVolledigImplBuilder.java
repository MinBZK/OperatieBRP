/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInTerugmeldingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.TerugmeldingHisVolledigImpl;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Terugmelding.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class TerugmeldingHisVolledigImplBuilder {

    private TerugmeldingHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param terugmeldendePartij terugmeldendePartij van Terugmelding.
     * @param persoon persoon van Terugmelding.
     * @param bijhoudingsgemeente bijhoudingsgemeente van Terugmelding.
     * @param tijdstipRegistratie tijdstipRegistratie van Terugmelding.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public TerugmeldingHisVolledigImplBuilder(
        final Partij terugmeldendePartij,
        final PersoonHisVolledigImpl persoon,
        final Partij bijhoudingsgemeente,
        final DatumTijdAttribuut tijdstipRegistratie,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl =
                new TerugmeldingHisVolledigImpl(
                    new PartijAttribuut(terugmeldendePartij),
                    persoon,
                    new PartijAttribuut(bijhoudingsgemeente),
                    tijdstipRegistratie);
        if (hisVolledigImpl.getTerugmeldendePartij() != null) {
            hisVolledigImpl.getTerugmeldendePartij().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getBijhoudingsgemeente() != null) {
            hisVolledigImpl.getBijhoudingsgemeente().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getTijdstipRegistratie() != null) {
            hisVolledigImpl.getTijdstipRegistratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param terugmeldendePartij terugmeldendePartij van Terugmelding.
     * @param persoon persoon van Terugmelding.
     * @param bijhoudingsgemeente bijhoudingsgemeente van Terugmelding.
     * @param tijdstipRegistratie tijdstipRegistratie van Terugmelding.
     */
    public TerugmeldingHisVolledigImplBuilder(
        final Partij terugmeldendePartij,
        final PersoonHisVolledigImpl persoon,
        final Partij bijhoudingsgemeente,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        this(terugmeldendePartij, persoon, bijhoudingsgemeente, tijdstipRegistratie, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public TerugmeldingHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Voeg een Gegeven in terugmelding toe. Zet tevens de back-reference van Gegeven in terugmelding.
     *
     * @param gegevenInTerugmelding een Gegeven in terugmelding
     * @return his volledig builder
     */
    public TerugmeldingHisVolledigImplBuilder voegGegevenInTerugmeldingToe(final GegevenInTerugmeldingHisVolledigImpl gegevenInTerugmelding) {
        this.hisVolledigImpl.getGegevens().add(gegevenInTerugmelding);
        ReflectionTestUtils.setField(gegevenInTerugmelding, "terugmelding", this.hisVolledigImpl);
        return this;
    }

}
