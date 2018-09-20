/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.verconv;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ByteaopslagAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3ReferentieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3BerichtenBron;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3BerichtenBronAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.verconv.LO3BerichtHisVolledigImpl;

/**
 * Builder klasse voor LO3 Bericht.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class LO3BerichtHisVolledigImplBuilder {

    private LO3BerichtHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param indicatieBerichtsoortOnderdeelLO3Stelsel indicatieBerichtsoortOnderdeelLO3Stelsel van LO3 Bericht.
     * @param referentie referentie van LO3 Bericht.
     * @param bron bron van LO3 Bericht.
     * @param administratienummer administratienummer van LO3 Bericht.
     * @param persoon persoon van LO3 Bericht.
     * @param berichtdata berichtdata van LO3 Bericht.
     * @param checksum checksum van LO3 Bericht.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public LO3BerichtHisVolledigImplBuilder(
        final JaNeeAttribuut indicatieBerichtsoortOnderdeelLO3Stelsel,
        final LO3ReferentieAttribuut referentie,
        final LO3BerichtenBron bron,
        final AdministratienummerAttribuut administratienummer,
        final PersoonHisVolledigImpl persoon,
        final ByteaopslagAttribuut berichtdata,
        final ChecksumAttribuut checksum,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.hisVolledigImpl =
                new LO3BerichtHisVolledigImpl(
                    indicatieBerichtsoortOnderdeelLO3Stelsel,
                    referentie,
                    new LO3BerichtenBronAttribuut(bron),
                    administratienummer,
                    persoon,
                    berichtdata,
                    checksum);
        if (hisVolledigImpl.getIndicatieBerichtsoortOnderdeelLO3Stelsel() != null) {
            hisVolledigImpl.getIndicatieBerichtsoortOnderdeelLO3Stelsel().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getReferentie() != null) {
            hisVolledigImpl.getReferentie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getBron() != null) {
            hisVolledigImpl.getBron().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getAdministratienummer() != null) {
            hisVolledigImpl.getAdministratienummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getBerichtdata() != null) {
            hisVolledigImpl.getBerichtdata().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getChecksum() != null) {
            hisVolledigImpl.getChecksum().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param indicatieBerichtsoortOnderdeelLO3Stelsel indicatieBerichtsoortOnderdeelLO3Stelsel van LO3 Bericht.
     * @param referentie referentie van LO3 Bericht.
     * @param bron bron van LO3 Bericht.
     * @param administratienummer administratienummer van LO3 Bericht.
     * @param persoon persoon van LO3 Bericht.
     * @param berichtdata berichtdata van LO3 Bericht.
     * @param checksum checksum van LO3 Bericht.
     */
    public LO3BerichtHisVolledigImplBuilder(
        final JaNeeAttribuut indicatieBerichtsoortOnderdeelLO3Stelsel,
        final LO3ReferentieAttribuut referentie,
        final LO3BerichtenBron bron,
        final AdministratienummerAttribuut administratienummer,
        final PersoonHisVolledigImpl persoon,
        final ByteaopslagAttribuut berichtdata,
        final ChecksumAttribuut checksum)
    {
        this(indicatieBerichtsoortOnderdeelLO3Stelsel, referentie, bron, administratienummer, persoon, berichtdata, checksum, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public LO3BerichtHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
