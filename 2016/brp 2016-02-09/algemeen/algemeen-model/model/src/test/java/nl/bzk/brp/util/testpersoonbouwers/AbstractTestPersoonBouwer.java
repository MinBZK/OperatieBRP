/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.testpersoonbouwers;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.IdComparator;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.VerantwoordingTestUtil;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 */
public abstract class AbstractTestPersoonBouwer {

    private static final String ID_VELD   = "iD";
    private              int    idCounter = 100_000;

    /**
     * Bouw een actie die gekoppeld is aan de handeling.
     *
     * @param datumAanvang             datum aanvang
     * @param datumEinde               datum einde
     * @param datumRegistratie         datum tijd registratie
     * @param administratieveHandeling de handeling die bij de actie hoort
     * @return een actie met gekoppelde handeling.
     */
    protected ActieModel bouwActie(final Integer datumAanvang, final Integer datumEinde, final Integer datumRegistratie,
        final AdministratieveHandelingModel administratieveHandeling)
    {
        final String datumTijdRegistratieString = datumRegistratie.toString();
        DatumEvtDeelsOnbekendAttribuut datumEindeAttribuut = null;
        if (datumEinde != null) {
            datumEindeAttribuut = new DatumEvtDeelsOnbekendAttribuut(datumEinde);
        }

        final ActieModel actie = new ActieModel(null, administratieveHandeling, new PartijAttribuut(TestPartijBuilder.maker().metCode(36101).maak()),
                                                new DatumEvtDeelsOnbekendAttribuut(datumAanvang), datumEindeAttribuut,
                                                DatumTijdAttribuut.bouwDatumTijd(
                                                    Integer.valueOf(datumTijdRegistratieString.substring(0, 4)),
                                                    Integer.valueOf(datumTijdRegistratieString.substring(4, 6)),
                                                    Integer.valueOf(datumTijdRegistratieString.substring(6, 8))), null);

        ReflectionTestUtils.setField(actie, ID_VELD, (long) getUniekId());

        return actie;
    }

    /**
     * Bouw actie.
     *
     * @param actieId de actie id
     * @param soortActie de soort actie
     * @param soortAdministratieveHandeling de soort administratieve handeling
     * @param tsreg de tsreg
     * @return actie model
     */
    protected ActieModel bouwActie(final Long actieId, final SoortActie soortActie, final SoortAdministratieveHandeling soortAdministratieveHandeling,
        final Date tsreg)
    {
        final AdministratieveHandelingModel administratieveHandeling =
            VerantwoordingTestUtil.bouwAdministratieveHandeling(soortAdministratieveHandeling,
                                                                new PartijAttribuut(TestPartijBuilder.maker().metCode(36101).maak()), null,
                                                                new DatumTijdAttribuut(tsreg));
        ReflectionTestUtils.setField(administratieveHandeling, ID_VELD, actieId);

        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(soortActie), administratieveHandeling,
                           new PartijAttribuut(TestPartijBuilder.maker().metCode(36101).maak()),
                           new DatumEvtDeelsOnbekendAttribuut(new DatumAttribuut(tsreg)), null, new DatumTijdAttribuut(tsreg), null);
        ReflectionTestUtils.setField(actieModel, ID_VELD, actieId);
        final SortedSet<ActieModel> acties = new TreeSet<>(new IdComparator());
        acties.add(actieModel);
        ReflectionTestUtils.setField(administratieveHandeling, "acties", acties);

        return actieModel;
    }

    /**
     * Bouw abonnement.
     *
     * @param leveringautorisatieNaam de abonnement naam
     * @param id de id
     * @return abonnement
     */
    protected Leveringsautorisatie bouwLeveringsautorisatie(final String leveringautorisatieNaam, final int id) {
        return TestLeveringsautorisatieBuilder.maker().metId(id).metNaam(leveringautorisatieNaam).metPopulatiebeperking("WAAR")
            .metDatumIngang(DatumAttribuut.gisteren())
            .maak();
    }

    /**
     * Maak dienst.
     *
     * @return dienst model
     */
    protected Dienst maakDienst() {
        return TestDienstBuilder.maker().metSoortDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE).maak();
    }

    /**
     * Geeft een uniek id.
     *
     * @return het unieke id
     */
    private int getUniekId() {
        return this.idCounter++;
    }
}
