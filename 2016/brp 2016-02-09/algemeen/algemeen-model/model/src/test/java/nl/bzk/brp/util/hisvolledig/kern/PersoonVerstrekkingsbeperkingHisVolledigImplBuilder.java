/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerstrekkingsbeperkingHisVolledigImpl;

/**
 * Builder klasse voor Persoon \ Verstrekkingsbeperking.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PersoonVerstrekkingsbeperkingHisVolledigImplBuilder {

    private PersoonVerstrekkingsbeperkingHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param persoon persoon van Persoon \ Verstrekkingsbeperking.
     * @param partij partij van Persoon \ Verstrekkingsbeperking.
     * @param omschrijvingDerde omschrijvingDerde van Persoon \ Verstrekkingsbeperking.
     * @param gemeenteVerordening gemeenteVerordening van Persoon \ Verstrekkingsbeperking.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonVerstrekkingsbeperkingHisVolledigImplBuilder(
        final PersoonHisVolledigImpl persoon,
        final Partij partij,
        final OmschrijvingEnumeratiewaardeAttribuut omschrijvingDerde,
        final Partij gemeenteVerordening,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl =
                new PersoonVerstrekkingsbeperkingHisVolledigImpl(persoon, new PartijAttribuut(partij), omschrijvingDerde, new PartijAttribuut(
                    gemeenteVerordening));
        if (hisVolledigImpl.getPartij() != null) {
            hisVolledigImpl.getPartij().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getOmschrijvingDerde() != null) {
            hisVolledigImpl.getOmschrijvingDerde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getGemeenteVerordening() != null) {
            hisVolledigImpl.getGemeenteVerordening().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Verstrekkingsbeperking.
     * @param partij partij van Persoon \ Verstrekkingsbeperking.
     * @param omschrijvingDerde omschrijvingDerde van Persoon \ Verstrekkingsbeperking.
     * @param gemeenteVerordening gemeenteVerordening van Persoon \ Verstrekkingsbeperking.
     */
    public PersoonVerstrekkingsbeperkingHisVolledigImplBuilder(
        final PersoonHisVolledigImpl persoon,
        final Partij partij,
        final OmschrijvingEnumeratiewaardeAttribuut omschrijvingDerde,
        final Partij gemeenteVerordening)
    {
        this(persoon, partij, omschrijvingDerde, gemeenteVerordening, false);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param partij partij van Persoon \ Verstrekkingsbeperking.
     * @param omschrijvingDerde omschrijvingDerde van Persoon \ Verstrekkingsbeperking.
     * @param gemeenteVerordening gemeenteVerordening van Persoon \ Verstrekkingsbeperking.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonVerstrekkingsbeperkingHisVolledigImplBuilder(
        final Partij partij,
        final OmschrijvingEnumeratiewaardeAttribuut omschrijvingDerde,
        final Partij gemeenteVerordening,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl =
                new PersoonVerstrekkingsbeperkingHisVolledigImpl(null, new PartijAttribuut(partij), omschrijvingDerde, new PartijAttribuut(
                    gemeenteVerordening));
        if (hisVolledigImpl.getPartij() != null) {
            hisVolledigImpl.getPartij().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getOmschrijvingDerde() != null) {
            hisVolledigImpl.getOmschrijvingDerde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getGemeenteVerordening() != null) {
            hisVolledigImpl.getGemeenteVerordening().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param partij partij van Persoon \ Verstrekkingsbeperking.
     * @param omschrijvingDerde omschrijvingDerde van Persoon \ Verstrekkingsbeperking.
     * @param gemeenteVerordening gemeenteVerordening van Persoon \ Verstrekkingsbeperking.
     */
    public PersoonVerstrekkingsbeperkingHisVolledigImplBuilder(
        final Partij partij,
        final OmschrijvingEnumeratiewaardeAttribuut omschrijvingDerde,
        final Partij gemeenteVerordening)
    {
        this(null, partij, omschrijvingDerde, gemeenteVerordening, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public PersoonVerstrekkingsbeperkingHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
