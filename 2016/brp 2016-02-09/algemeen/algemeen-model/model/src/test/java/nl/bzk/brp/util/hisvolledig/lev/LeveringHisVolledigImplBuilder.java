/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.lev;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.lev.LeveringHisVolledigImpl;

/**
 * Builder klasse voor Levering.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class LeveringHisVolledigImplBuilder {

    private LeveringHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param toegangAbonnementId toegangAbonnementId van Levering.
     * @param dienstId dienstId van Levering.
     * @param datumTijdKlaarzettenLevering datumTijdKlaarzettenLevering van Levering.
     * @param datumMaterieelSelectie datumMaterieelSelectie van Levering.
     * @param datumAanvangMaterielePeriodeResultaat datumAanvangMaterielePeriodeResultaat van Levering.
     * @param datumEindeMaterielePeriodeResultaat datumEindeMaterielePeriodeResultaat van Levering.
     * @param datumTijdAanvangFormelePeriodeResultaat datumTijdAanvangFormelePeriodeResultaat van Levering.
     * @param datumTijdEindeFormelePeriodeResultaat datumTijdEindeFormelePeriodeResultaat van Levering.
     * @param administratieveHandelingId administratieveHandelingId van Levering.
     * @param soortSynchronisatie soortSynchronisatie van Levering.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public LeveringHisVolledigImplBuilder(
        final Integer toegangAbonnementId,
        final Integer dienstId,
        final DatumTijdAttribuut datumTijdKlaarzettenLevering,
        final DatumAttribuut datumMaterieelSelectie,
        final DatumAttribuut datumAanvangMaterielePeriodeResultaat,
        final DatumAttribuut datumEindeMaterielePeriodeResultaat,
        final DatumTijdAttribuut datumTijdAanvangFormelePeriodeResultaat,
        final DatumTijdAttribuut datumTijdEindeFormelePeriodeResultaat,
        final Long administratieveHandelingId,
        final SoortSynchronisatie soortSynchronisatie,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.hisVolledigImpl =
                new LeveringHisVolledigImpl(
                    toegangAbonnementId,
                    dienstId,
                    datumTijdKlaarzettenLevering,
                    datumMaterieelSelectie,
                    datumAanvangMaterielePeriodeResultaat,
                    datumEindeMaterielePeriodeResultaat,
                    datumTijdAanvangFormelePeriodeResultaat,
                    datumTijdEindeFormelePeriodeResultaat,
                    administratieveHandelingId,
                    new SoortSynchronisatieAttribuut(soortSynchronisatie));
        if (hisVolledigImpl.getDatumTijdKlaarzettenLevering() != null) {
            hisVolledigImpl.getDatumTijdKlaarzettenLevering().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getDatumMaterieelSelectie() != null) {
            hisVolledigImpl.getDatumMaterieelSelectie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getDatumAanvangMaterielePeriodeResultaat() != null) {
            hisVolledigImpl.getDatumAanvangMaterielePeriodeResultaat().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getDatumEindeMaterielePeriodeResultaat() != null) {
            hisVolledigImpl.getDatumEindeMaterielePeriodeResultaat().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getDatumTijdAanvangFormelePeriodeResultaat() != null) {
            hisVolledigImpl.getDatumTijdAanvangFormelePeriodeResultaat().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getDatumTijdEindeFormelePeriodeResultaat() != null) {
            hisVolledigImpl.getDatumTijdEindeFormelePeriodeResultaat().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getSoortSynchronisatie() != null) {
            hisVolledigImpl.getSoortSynchronisatie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft. CHECKSTYLE-BRP:OFF -
     * MAX PARAMS
     *
     * @param toegangAbonnementId toegangAbonnementId van Levering.
     * @param dienstId dienstId van Levering.
     * @param datumTijdKlaarzettenLevering datumTijdKlaarzettenLevering van Levering.
     * @param datumMaterieelSelectie datumMaterieelSelectie van Levering.
     * @param datumAanvangMaterielePeriodeResultaat datumAanvangMaterielePeriodeResultaat van Levering.
     * @param datumEindeMaterielePeriodeResultaat datumEindeMaterielePeriodeResultaat van Levering.
     * @param datumTijdAanvangFormelePeriodeResultaat datumTijdAanvangFormelePeriodeResultaat van Levering.
     * @param datumTijdEindeFormelePeriodeResultaat datumTijdEindeFormelePeriodeResultaat van Levering.
     * @param administratieveHandelingId administratieveHandelingId van Levering.
     * @param soortSynchronisatie soortSynchronisatie van Levering.
     */
    public LeveringHisVolledigImplBuilder(
        final Integer toegangAbonnementId,
        final Integer dienstId,
        final DatumTijdAttribuut datumTijdKlaarzettenLevering,
        final DatumAttribuut datumMaterieelSelectie,
        final DatumAttribuut datumAanvangMaterielePeriodeResultaat,
        final DatumAttribuut datumEindeMaterielePeriodeResultaat,
        final DatumTijdAttribuut datumTijdAanvangFormelePeriodeResultaat,
        final DatumTijdAttribuut datumTijdEindeFormelePeriodeResultaat,
        final Long administratieveHandelingId,
        final SoortSynchronisatie soortSynchronisatie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this(toegangAbonnementId,
             dienstId,
             datumTijdKlaarzettenLevering,
             datumMaterieelSelectie,
             datumAanvangMaterielePeriodeResultaat,
             datumEindeMaterielePeriodeResultaat,
             datumTijdAanvangFormelePeriodeResultaat,
             datumTijdEindeFormelePeriodeResultaat,
             administratieveHandelingId,
             soortSynchronisatie,
             false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public LeveringHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
