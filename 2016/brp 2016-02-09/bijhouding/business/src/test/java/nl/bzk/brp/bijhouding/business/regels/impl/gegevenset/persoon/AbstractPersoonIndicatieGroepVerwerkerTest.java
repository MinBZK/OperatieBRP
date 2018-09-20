/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractPersoonIndicatieGroepVerwerkerTest<T extends HisPersoonIndicatieModel, M extends PersoonIndicatieHisVolledigImpl<T>> {

    private static final Integer DATUM_GEBOORTE = 20120101;
    private static final DatumTijdAttribuut TIJDSTIP_REGISTRATIE = DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1);
    private static final DatumTijdAttribuut TIJDSTIP_REGISTRATIE_2 = DatumTijdAttribuut.bouwDatumTijd(2012, 2, 1);
    private AbstractPersoonIndicatieGroepVerwerker<T, M> verwerker;
    private PersoonIndicatieBericht persoonIndicatieBericht;
    private PersoonIndicatieHisVolledigImpl<T> persoonIndicatieHisVolledigImpl;
    private PersoonHisVolledigImpl persoonHisVolledigImpl;

    protected abstract AbstractPersoonIndicatieGroepVerwerker<T, M> maakNieuweVerwerker(
            PersoonIndicatieBericht bericht,
            PersoonIndicatieHisVolledigImpl<T> model,
            ActieModel actie);
    protected abstract PersoonIndicatieHisVolledigImpl<T> maakPersoonIndicatieHisVolledigImpl();
    protected abstract Regel getVerwachteRegel();
    protected abstract SoortIndicatie getSoortIndicatie();

    public AbstractPersoonIndicatieGroepVerwerker<T, M> getVerwerker() {
        return verwerker;
    }

    public PersoonIndicatieBericht getPersoonIndicatieBericht() {
        return persoonIndicatieBericht;
    }

    public PersoonIndicatieHisVolledigImpl<T> getPersoonIndicatieHisVolledigImpl() {
        return persoonIndicatieHisVolledigImpl;
    }

    public PersoonHisVolledigImpl getPersoonHisVolledigImpl() {
        return persoonHisVolledigImpl;
    }

    @Before
    public void init() {
        final ActieModel actie = creeerActie();
        persoonHisVolledigImpl = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoonIndicatieBericht = new PersoonIndicatieBericht(new SoortIndicatieAttribuut(getSoortIndicatie()));
        final PersoonIndicatieStandaardGroepBericht persoonIndicatieStandaardGroepBericht =
                new PersoonIndicatieStandaardGroepBericht();
        persoonIndicatieStandaardGroepBericht.setDatumAanvangGeldigheid(
                new DatumEvtDeelsOnbekendAttribuut(DATUM_GEBOORTE));
        persoonIndicatieBericht.setStandaard(persoonIndicatieStandaardGroepBericht);

        persoonIndicatieHisVolledigImpl = maakPersoonIndicatieHisVolledigImpl();
        verwerker = maakNieuweVerwerker(persoonIndicatieBericht, persoonIndicatieHisVolledigImpl, actie);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(getVerwachteRegel(), verwerker.getRegel());
    }

    @Test
    public void testNeemBerichtDataOverInModel() {
        Assert.assertEquals(0, persoonIndicatieHisVolledigImpl.getPersoonIndicatieHistorie().getAantal());

        verwerker.neemBerichtDataOverInModel();

        Assert.assertEquals(1, persoonIndicatieHisVolledigImpl.getPersoonIndicatieHistorie().getAantal());
    }

    @Test
    public void testNeemBerichtDataOverInModelTweeActies() {
        Assert.assertEquals(0, persoonIndicatieHisVolledigImpl.getPersoonIndicatieHistorie().getAantal());

        verwerker.neemBerichtDataOverInModel();

        Assert.assertEquals(1, persoonIndicatieHisVolledigImpl.getPersoonIndicatieHistorie().getAantal());

        verwerker = maakNieuweVerwerker(persoonIndicatieBericht,
                                        persoonIndicatieHisVolledigImpl,
                                        creeerNogEenActie());

        verwerker.neemBerichtDataOverInModel();

        Assert.assertEquals(2, persoonIndicatieHisVolledigImpl.getPersoonIndicatieHistorie().getAantal());
    }

    /**
     * Creeer een standaard actie.
     *
     * @return het actie model
     */
    protected ActieModel creeerActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
                              new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                                      SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND), null,
                                      null, null), null, new DatumEvtDeelsOnbekendAttribuut(DATUM_GEBOORTE),
                              null, TIJDSTIP_REGISTRATIE, null);
    }

    /**
     * Creeer een standaard actie.
     *
     * @return het actie model
     */
    protected ActieModel creeerNogEenActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND), null,
                        null, null), null, new DatumEvtDeelsOnbekendAttribuut(DATUM_GEBOORTE + 3),
                null, TIJDSTIP_REGISTRATIE_2, null);
    }

}
