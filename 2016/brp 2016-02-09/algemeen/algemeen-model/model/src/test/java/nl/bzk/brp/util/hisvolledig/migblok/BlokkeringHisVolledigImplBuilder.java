/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.migblok;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.ProcessInstantieIDAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.migblok.RedenBlokkering;
import nl.bzk.brp.model.algemeen.stamgegeven.migblok.RedenBlokkeringAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.migblok.BlokkeringHisVolledigImpl;

/**
 * Builder klasse voor Blokkering.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class BlokkeringHisVolledigImplBuilder {

    private BlokkeringHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param administratienummer administratienummer van Blokkering.
     * @param redenBlokkering redenBlokkering van Blokkering.
     * @param processInstantieID processInstantieID van Blokkering.
     * @param lO3GemeenteVestiging lO3GemeenteVestiging van Blokkering.
     * @param lO3GemeenteRegistratie lO3GemeenteRegistratie van Blokkering.
     * @param tijdstipRegistratie tijdstipRegistratie van Blokkering.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public BlokkeringHisVolledigImplBuilder(
        final AdministratienummerAttribuut administratienummer,
        final RedenBlokkering redenBlokkering,
        final ProcessInstantieIDAttribuut processInstantieID,
        final LO3GemeenteCodeAttribuut lO3GemeenteVestiging,
        final LO3GemeenteCodeAttribuut lO3GemeenteRegistratie,
        final DatumTijdAttribuut tijdstipRegistratie,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl =
                new BlokkeringHisVolledigImpl(
                    administratienummer,
                    new RedenBlokkeringAttribuut(redenBlokkering),
                    processInstantieID,
                    lO3GemeenteVestiging,
                    lO3GemeenteRegistratie,
                    tijdstipRegistratie);
        if (hisVolledigImpl.getAdministratienummer() != null) {
            hisVolledigImpl.getAdministratienummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getRedenBlokkering() != null) {
            hisVolledigImpl.getRedenBlokkering().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getProcessInstantieID() != null) {
            hisVolledigImpl.getProcessInstantieID().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getLO3GemeenteVestiging() != null) {
            hisVolledigImpl.getLO3GemeenteVestiging().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getLO3GemeenteRegistratie() != null) {
            hisVolledigImpl.getLO3GemeenteRegistratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getTijdstipRegistratie() != null) {
            hisVolledigImpl.getTijdstipRegistratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param administratienummer administratienummer van Blokkering.
     * @param redenBlokkering redenBlokkering van Blokkering.
     * @param processInstantieID processInstantieID van Blokkering.
     * @param lO3GemeenteVestiging lO3GemeenteVestiging van Blokkering.
     * @param lO3GemeenteRegistratie lO3GemeenteRegistratie van Blokkering.
     * @param tijdstipRegistratie tijdstipRegistratie van Blokkering.
     */
    public BlokkeringHisVolledigImplBuilder(
        final AdministratienummerAttribuut administratienummer,
        final RedenBlokkering redenBlokkering,
        final ProcessInstantieIDAttribuut processInstantieID,
        final LO3GemeenteCodeAttribuut lO3GemeenteVestiging,
        final LO3GemeenteCodeAttribuut lO3GemeenteRegistratie,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        this(administratienummer, redenBlokkering, processInstantieID, lO3GemeenteVestiging, lO3GemeenteRegistratie, tijdstipRegistratie, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public BlokkeringHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
