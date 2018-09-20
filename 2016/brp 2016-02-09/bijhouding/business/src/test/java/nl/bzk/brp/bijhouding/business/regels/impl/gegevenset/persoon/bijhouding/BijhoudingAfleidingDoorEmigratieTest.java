/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class BijhoudingAfleidingDoorEmigratieTest {

    @Mock
    private ReferentieDataRepository referentieDataRepository;
    private final LandGebied    afghanistan       = new LandGebied(null, null, null, null, null);
    private final SoortDocument ministerBesluit   =
        new SoortDocument(NaamEnumeratiewaardeAttribuut.DOCUMENT_NAAM_MINISTERIEEL_BESLUIT, null, null);
    private final SoortDocument koninklijkBesluit =
        new SoortDocument(NaamEnumeratiewaardeAttribuut.DOCUMENT_NAAM_KONINKLIJK_BESLUIT, null, null);
    private final Partij        ministertje       = TestPartijBuilder.maker().metNaam("ministertje").maak();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(referentieDataRepository.vindPartijOpCode(PartijCodeAttribuut.MINISTER))
            .thenReturn(ministertje);
    }

    @Test
    public void testLeidAfPersoonIsNietIngezetene() {
        final ActieModel actieModel = maakActie(null);
        final PersoonHisVolledigImpl persoonHisVolledig =
            maakPersoonModel(Bijhoudingsaard.NIET_INGEZETENE, SoortMigratie.EMIGRATIE, afghanistan);

        final List<Afleidingsregel> afleidingsregels =
            new BijhoudingAfleidingDoorEmigratie(persoonHisVolledig, actieModel).leidAf().getVervolgAfleidingen();

        //Geen recordjes dus bijgekomen!
        Assert.assertEquals(1, persoonHisVolledig.getPersoonBijhoudingHistorie().getAantal());
        Assert.assertTrue(afleidingsregels.isEmpty());
    }

    @Test
    public void testLeidAfPersoonIsGeimigreerd() {
        final ActieModel actieModel = maakActie(null);
        final PersoonHisVolledigImpl persoonHisVolledig =
            maakPersoonModel(Bijhoudingsaard.INGEZETENE, SoortMigratie.IMMIGRATIE, afghanistan);

        final List<Afleidingsregel> afleidingsregels =
            new BijhoudingAfleidingDoorEmigratie(persoonHisVolledig, actieModel).leidAf().getVervolgAfleidingen();

        //Geen recordjes dus bijgekomen!
        Assert.assertEquals(1, persoonHisVolledig.getPersoonBijhoudingHistorie().getAantal());
        Assert.assertTrue(afleidingsregels.isEmpty());
    }

    @Test
    public void testLeidAfEmigratie() {
        final ActieModel actieModel = maakActie(null);
        final PersoonHisVolledigImpl persoonHisVolledig =
            maakPersoonModel(Bijhoudingsaard.INGEZETENE, SoortMigratie.EMIGRATIE, afghanistan);

        final BijhoudingAfleidingDoorEmigratie bijhoudingAfleidingDoorEmigratie =
            new BijhoudingAfleidingDoorEmigratie(persoonHisVolledig, actieModel);
        ReflectionTestUtils.setField(bijhoudingAfleidingDoorEmigratie, "referentieDataRepository", referentieDataRepository);
        final List<Afleidingsregel> afleidingsregels = bijhoudingAfleidingDoorEmigratie.leidAf().getVervolgAfleidingen();
        Assert.assertEquals(3, persoonHisVolledig.getPersoonBijhoudingHistorie().getAantal());

        final HisPersoonBijhoudingModel afgeleideBijhouding =
                persoonHisVolledig.getPersoonBijhoudingHistorie().getActueleRecord();

        Assert.assertEquals(NadereBijhoudingsaard.EMIGRATIE, afgeleideBijhouding.getNadereBijhoudingsaard().getWaarde());
        Assert.assertEquals(Bijhoudingsaard.NIET_INGEZETENE, afgeleideBijhouding.getBijhoudingsaard().getWaarde());
        Assert.assertEquals(ministertje, afgeleideBijhouding.getBijhoudingspartij().getWaarde());
        Assert.assertNull(afgeleideBijhouding.getIndicatieOnverwerktDocumentAanwezig());
        Assert.assertEquals(20140101, afgeleideBijhouding.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertTrue(afleidingsregels.isEmpty());
    }

    @Test
    public void testLeidAfVertrokkenNaarOnbekendWaarheen() {
        final ActieModel actieModel = maakActie(null);
        final PersoonHisVolledigImpl persoonHisVolledig =
                maakPersoonModel(Bijhoudingsaard.INGEZETENE, SoortMigratie.EMIGRATIE, null);

        final BijhoudingAfleidingDoorEmigratie bijhoudingAfleidingDoorEmigratie =
                new BijhoudingAfleidingDoorEmigratie(persoonHisVolledig, actieModel);
        ReflectionTestUtils.setField(bijhoudingAfleidingDoorEmigratie, "referentieDataRepository", referentieDataRepository);
        final List<Afleidingsregel> afleidingsregels = bijhoudingAfleidingDoorEmigratie.leidAf().getVervolgAfleidingen();
        Assert.assertEquals(3, persoonHisVolledig.getPersoonBijhoudingHistorie().getAantal());

        final HisPersoonBijhoudingModel afgeleideBijhouding =
                persoonHisVolledig.getPersoonBijhoudingHistorie().getActueleRecord();

        Assert.assertEquals(NadereBijhoudingsaard.VERTROKKEN_ONBEKEND_WAARHEEN,
                            afgeleideBijhouding.getNadereBijhoudingsaard().getWaarde());
        Assert.assertEquals(Bijhoudingsaard.NIET_INGEZETENE, afgeleideBijhouding.getBijhoudingsaard().getWaarde());
        Assert.assertEquals(ministertje, afgeleideBijhouding.getBijhoudingspartij().getWaarde());
        Assert.assertNull(afgeleideBijhouding.getIndicatieOnverwerktDocumentAanwezig());
        Assert.assertEquals(20140101, afgeleideBijhouding.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertTrue(afleidingsregels.isEmpty());

    }

    @Test
    public void testLeidAfMinistrieelBesluit() {
        final ActieModel actieModel = maakActie(ministerBesluit);
        final PersoonHisVolledigImpl persoonHisVolledig =
                maakPersoonModel(Bijhoudingsaard.INGEZETENE, SoortMigratie.EMIGRATIE, afghanistan);

        final BijhoudingAfleidingDoorEmigratie bijhoudingAfleidingDoorEmigratie =
                new BijhoudingAfleidingDoorEmigratie(persoonHisVolledig, actieModel);
        ReflectionTestUtils.setField(bijhoudingAfleidingDoorEmigratie, "referentieDataRepository", referentieDataRepository);
        final List<Afleidingsregel> afleidingsregels = bijhoudingAfleidingDoorEmigratie.leidAf().getVervolgAfleidingen();
        Assert.assertEquals(3, persoonHisVolledig.getPersoonBijhoudingHistorie().getAantal());

        final HisPersoonBijhoudingModel afgeleideBijhouding =
                persoonHisVolledig.getPersoonBijhoudingHistorie().getActueleRecord();

        Assert.assertEquals(NadereBijhoudingsaard.MINISTERIEEL_BESLUIT,
                            afgeleideBijhouding.getNadereBijhoudingsaard().getWaarde());
        Assert.assertEquals(Bijhoudingsaard.NIET_INGEZETENE, afgeleideBijhouding.getBijhoudingsaard().getWaarde());
        Assert.assertEquals(ministertje, afgeleideBijhouding.getBijhoudingspartij().getWaarde());
        Assert.assertNull(afgeleideBijhouding.getIndicatieOnverwerktDocumentAanwezig());
        Assert.assertEquals(20140101, afgeleideBijhouding.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertTrue(afleidingsregels.isEmpty());
    }

    @Test
    public void testLeidAfVertrokkenOnbekendWaarheenOndanksMinistrieelBesluit() {
        final ActieModel actieModel = maakActie(ministerBesluit);
        final PersoonHisVolledigImpl persoonHisVolledig =
                maakPersoonModel(Bijhoudingsaard.INGEZETENE, SoortMigratie.EMIGRATIE, null);

        final BijhoudingAfleidingDoorEmigratie bijhoudingAfleidingDoorEmigratie =
                new BijhoudingAfleidingDoorEmigratie(persoonHisVolledig, actieModel);
        ReflectionTestUtils.setField(bijhoudingAfleidingDoorEmigratie, "referentieDataRepository", referentieDataRepository);
        final List<Afleidingsregel> afleidingsregels = bijhoudingAfleidingDoorEmigratie.leidAf().getVervolgAfleidingen();
        Assert.assertEquals(3, persoonHisVolledig.getPersoonBijhoudingHistorie().getAantal());

        final HisPersoonBijhoudingModel afgeleideBijhouding =
                persoonHisVolledig.getPersoonBijhoudingHistorie().getActueleRecord();

        Assert.assertEquals(NadereBijhoudingsaard.VERTROKKEN_ONBEKEND_WAARHEEN,
                            afgeleideBijhouding.getNadereBijhoudingsaard().getWaarde());
        Assert.assertEquals(Bijhoudingsaard.NIET_INGEZETENE, afgeleideBijhouding.getBijhoudingsaard().getWaarde());
        Assert.assertEquals(ministertje, afgeleideBijhouding.getBijhoudingspartij().getWaarde());
        Assert.assertNull(afgeleideBijhouding.getIndicatieOnverwerktDocumentAanwezig());
        Assert.assertEquals(20140101, afgeleideBijhouding.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertTrue(afleidingsregels.isEmpty());
    }

    @Test
    public void testLeidAfAnderSoortDocumentAlsVerantwoording() {
        final ActieModel actieModel = maakActie(koninklijkBesluit);
        final PersoonHisVolledigImpl persoonHisVolledig =
                maakPersoonModel(Bijhoudingsaard.INGEZETENE, SoortMigratie.EMIGRATIE, afghanistan);

        final BijhoudingAfleidingDoorEmigratie bijhoudingAfleidingDoorEmigratie =
                new BijhoudingAfleidingDoorEmigratie(persoonHisVolledig, actieModel);
        ReflectionTestUtils.setField(bijhoudingAfleidingDoorEmigratie, "referentieDataRepository", referentieDataRepository);
        final List<Afleidingsregel> afleidingsregels = bijhoudingAfleidingDoorEmigratie.leidAf().getVervolgAfleidingen();
        Assert.assertEquals(3, persoonHisVolledig.getPersoonBijhoudingHistorie().getAantal());

        final HisPersoonBijhoudingModel afgeleideBijhouding =
                persoonHisVolledig.getPersoonBijhoudingHistorie().getActueleRecord();

        Assert.assertEquals(NadereBijhoudingsaard.EMIGRATIE,
                            afgeleideBijhouding.getNadereBijhoudingsaard().getWaarde());
        Assert.assertEquals(Bijhoudingsaard.NIET_INGEZETENE, afgeleideBijhouding.getBijhoudingsaard().getWaarde());
        Assert.assertEquals(ministertje, afgeleideBijhouding.getBijhoudingspartij().getWaarde());
        Assert.assertNull(afgeleideBijhouding.getIndicatieOnverwerktDocumentAanwezig());
        Assert.assertEquals(20140101, afgeleideBijhouding.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertTrue(afleidingsregels.isEmpty());
    }

    private ActieModel maakActie(final SoortDocument verantwoordingDocument) {
        final ActieModel actieModel = new ActieModel(
                new SoortActieAttribuut(SoortActie.REGISTRATIE_MIGRATIE),
                new AdministratieveHandelingModel(
                        new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND),
                        null,
                        null,
                        null),
                null,
                new DatumEvtDeelsOnbekendAttribuut(20140101),
                null,
                DatumTijdAttribuut.nu(), null);

        if (verantwoordingDocument != null) {
            DocumentModel documentModel = new DocumentModel(new SoortDocumentAttribuut(verantwoordingDocument));
            ActieBronModel actieBronModel = new ActieBronModel(actieModel, documentModel, null, null);
            actieModel.getBronnen().add(actieBronModel);
        }
        return actieModel;
    }

    private PersoonHisVolledigImpl maakPersoonModel(final Bijhoudingsaard huidigeBijhAard,
                                                    final SoortMigratie huidigeSoortMigratie,
                                                    final LandGebied landGebiedMigratie)
    {
        if (landGebiedMigratie == null) {
            return new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                    .nieuwMigratieRecord(20120101, null, 20120101)
                    .soortMigratie(huidigeSoortMigratie).eindeRecord()
                    .nieuwBijhoudingRecord(20120101, null, 20120101)
                    .bijhoudingsaard(huidigeBijhAard).eindeRecord().build();
        } else {
            return new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                    .nieuwMigratieRecord(20120101, null, 20120101)
                    .soortMigratie(huidigeSoortMigratie)
                    .landGebiedMigratie(landGebiedMigratie).eindeRecord()
                    .nieuwBijhoudingRecord(20120101, null, 20120101)
                    .bijhoudingsaard(huidigeBijhAard).eindeRecord().build();
        }
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.VR00015b, new BijhoudingAfleidingDoorEmigratie(null, null).getRegel());
    }
}
