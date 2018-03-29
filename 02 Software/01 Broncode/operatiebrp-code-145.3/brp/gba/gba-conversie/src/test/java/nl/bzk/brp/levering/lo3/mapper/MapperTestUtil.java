/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

// package nl.bzk.brp.levering.lo3.mapper;
//
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.concurrent.atomic.AtomicLong;
// import nl.bzk.algemeenbrp.util.common.logging.Logger;
// import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
// import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
// import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
// import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
// import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
// import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
// import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
// import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
// import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
// import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
// import nl.bzk.brp.model.operationeel.kern.ActieModel;
// import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
// import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
// import org.springframework.test.util.ReflectionTestUtils;
//
// public final class MapperTestUtil {
//
// protected static final SoortActie DEFAULT_SOORT_ACTIE = SoortActie.CONVERSIE_G_B_A;
//
// private static final AtomicLong ID_GENERATOR = new AtomicLong(123L);
// private static final Long DEFAULT_DATUM_REGISTRATIE = 20140101000000L;
//
// private static final Integer DEFAULT_PARTIJ_CODE = BrpPartijCode.MIGRATIEVOORZIENING_CODE;
// private static final Logger LOGGER = LoggerFactory.getLogger();
// private static final String ID = "iD";
//
// private MapperTestUtil() {
// }
//
// public static ActieModel maakActieModel(
// final Long id,
// final Long datumRegistratie,
// final Integer datumAanvangGeldigheid,
// final Integer datumEindeGeldigheid,
// final Integer partijCode,
// final SoortActie soortActie)
// {
//
// final DatumTijdAttribuut tijdstipRegistratie = datumRegistratie == null ? null :
// maakDatumTijdAttribuut(datumRegistratie);
// final PartijAttribuut partij =
// partijCode == null ? null
// : new PartijAttribuut(new Partij(null, null, new PartijCodeAttribuut(partijCode), null, null, null, null, null,
// null));
//
// final AdministratieveHandelingModel administratieveHandeling =
// new AdministratieveHandelingModel(
// new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL),
// partij,
// null,
// tijdstipRegistratie);
// ReflectionTestUtils.setField(administratieveHandeling, ID, id);
//
// final ActieModel actieModel =
// new ActieModel(
// new SoortActieAttribuut(soortActie),
// administratieveHandeling,
// partij,
// datumAanvangGeldigheid == null ? null : new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid),
// datumEindeGeldigheid == null ? null : new DatumEvtDeelsOnbekendAttribuut(datumEindeGeldigheid),
// tijdstipRegistratie,
// null);
// ReflectionTestUtils.setField(actieModel, ID, id);
//
// administratieveHandeling.getActies().add(actieModel);
//
// return actieModel;
// }
//
// public static ActieModel maakActieModel(final Long datumRegistratie) {
// return maakActieModel(ID_GENERATOR.getAndIncrement(), datumRegistratie, null, null, DEFAULT_PARTIJ_CODE,
// DEFAULT_SOORT_ACTIE);
// }
//
// public static ActieModel maakActieModel(final Long datumRegistratie, final Integer datumAanvangGeldigheid, final
// Integer datumEindeGeldigheid) {
// return maakActieModel(
// ID_GENERATOR.getAndIncrement(),
// datumRegistratie,
// datumAanvangGeldigheid,
// datumEindeGeldigheid,
// DEFAULT_PARTIJ_CODE,
// DEFAULT_SOORT_ACTIE);
// }
//
// public static ActieModel maakActieModel() {
// return maakActieModel(ID_GENERATOR.getAndIncrement(), DEFAULT_DATUM_REGISTRATIE, null, null, DEFAULT_PARTIJ_CODE,
// DEFAULT_SOORT_ACTIE);
// }
//
// public static DatumTijdAttribuut maakDatumTijdAttribuut(final Long datumTijd) {
// final SimpleDateFormat dtf = new SimpleDateFormat("yyyyMMddHHmmss");
// try {
// return new DatumTijdAttribuut(dtf.parse(datumTijd.toString()));
// } catch (final ParseException e) {
// LOGGER.error("Fout bij parsen van Long naar datum tijd.", e);
// }
// return null;
// }
// }
