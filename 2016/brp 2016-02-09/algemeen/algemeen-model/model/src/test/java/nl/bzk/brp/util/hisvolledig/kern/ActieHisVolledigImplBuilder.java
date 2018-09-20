/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieBronHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Actie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class ActieHisVolledigImplBuilder {

    private ActieHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param soort soort van Actie.
     * @param administratieveHandeling administratieveHandeling van Actie.
     * @param partij partij van Actie.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Actie.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Actie.
     * @param tijdstipRegistratie tijdstipRegistratie van Actie.
     * @param datumOntlening datumOntlening van Actie.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public ActieHisVolledigImplBuilder(
        final SoortActie soort,
        final AdministratieveHandelingHisVolledigImpl administratieveHandeling,
        final Partij partij,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid,
        final DatumTijdAttribuut tijdstipRegistratie,
        final DatumEvtDeelsOnbekendAttribuut datumOntlening,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.hisVolledigImpl =
                new ActieHisVolledigImpl(
                    new SoortActieAttribuut(soort),
                    administratieveHandeling,
                    new PartijAttribuut(partij),
                    datumAanvangGeldigheid,
                    datumEindeGeldigheid,
                    tijdstipRegistratie,
                    datumOntlening);
        if (hisVolledigImpl.getSoort() != null) {
            hisVolledigImpl.getSoort().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getPartij() != null) {
            hisVolledigImpl.getPartij().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getDatumAanvangGeldigheid() != null) {
            hisVolledigImpl.getDatumAanvangGeldigheid().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getDatumEindeGeldigheid() != null) {
            hisVolledigImpl.getDatumEindeGeldigheid().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getTijdstipRegistratie() != null) {
            hisVolledigImpl.getTijdstipRegistratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getDatumOntlening() != null) {
            hisVolledigImpl.getDatumOntlening().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Actie.
     * @param administratieveHandeling administratieveHandeling van Actie.
     * @param partij partij van Actie.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Actie.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Actie.
     * @param tijdstipRegistratie tijdstipRegistratie van Actie.
     * @param datumOntlening datumOntlening van Actie.
     */
    public ActieHisVolledigImplBuilder(
        final SoortActie soort,
        final AdministratieveHandelingHisVolledigImpl administratieveHandeling,
        final Partij partij,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid,
        final DatumTijdAttribuut tijdstipRegistratie,
        final DatumEvtDeelsOnbekendAttribuut datumOntlening)
    {
        this(soort, administratieveHandeling, partij, datumAanvangGeldigheid, datumEindeGeldigheid, tijdstipRegistratie, datumOntlening, false);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param soort soort van Actie.
     * @param partij partij van Actie.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Actie.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Actie.
     * @param tijdstipRegistratie tijdstipRegistratie van Actie.
     * @param datumOntlening datumOntlening van Actie.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public ActieHisVolledigImplBuilder(
        final SoortActie soort,
        final Partij partij,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid,
        final DatumTijdAttribuut tijdstipRegistratie,
        final DatumEvtDeelsOnbekendAttribuut datumOntlening,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl =
                new ActieHisVolledigImpl(
                    new SoortActieAttribuut(soort),
                    null,
                    new PartijAttribuut(partij),
                    datumAanvangGeldigheid,
                    datumEindeGeldigheid,
                    tijdstipRegistratie,
                    datumOntlening);
        if (hisVolledigImpl.getSoort() != null) {
            hisVolledigImpl.getSoort().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getPartij() != null) {
            hisVolledigImpl.getPartij().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getDatumAanvangGeldigheid() != null) {
            hisVolledigImpl.getDatumAanvangGeldigheid().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getDatumEindeGeldigheid() != null) {
            hisVolledigImpl.getDatumEindeGeldigheid().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getTijdstipRegistratie() != null) {
            hisVolledigImpl.getTijdstipRegistratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getDatumOntlening() != null) {
            hisVolledigImpl.getDatumOntlening().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Actie.
     * @param partij partij van Actie.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Actie.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Actie.
     * @param tijdstipRegistratie tijdstipRegistratie van Actie.
     * @param datumOntlening datumOntlening van Actie.
     */
    public ActieHisVolledigImplBuilder(
        final SoortActie soort,
        final Partij partij,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid,
        final DatumTijdAttribuut tijdstipRegistratie,
        final DatumEvtDeelsOnbekendAttribuut datumOntlening)
    {
        this(soort, null, partij, datumAanvangGeldigheid, datumEindeGeldigheid, tijdstipRegistratie, datumOntlening, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public ActieHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Voeg een Actie \ Bron toe. Zet tevens de back-reference van Actie \ Bron.
     *
     * @param actieBron een Actie \ Bron
     * @return his volledig builder
     */
    public ActieHisVolledigImplBuilder voegActieBronToe(final ActieBronHisVolledigImpl actieBron) {
        this.hisVolledigImpl.getBronnen().add(actieBron);
        ReflectionTestUtils.setField(actieBron, "actie", this.hisVolledigImpl);
        return this;
    }

}
