/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.pojo;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.blob.PlBlobOpslagplaats;
import nl.bzk.brp.dataaccess.repository.jpa.serialization.BlobSerializer;
import nl.bzk.brp.dataaccess.repository.jpa.serialization.JacksonBlobSerializer;
import nl.bzk.brp.model.blob.PlBlob;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeboorteHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsaanduidingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonIdentificatienummersHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.pojo.PersoonHisModel;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PojoBlobTest extends AbstractRepositoryTestCase {
	private final Logger LOGGER = LoggerFactory.getLogger(PojoBlobTest.class);

	@Inject
	private PersoonRepository persoonRepository;

	@Inject
	private PlBlobOpslagplaats blobOpslagplaats;

	@Inject
	@Named("jacksonBlobSerializer")
	private BlobSerializer jacksonBlobSerializer;

	@Inject
	@Named("externalizableBlobSerializer")
	private BlobSerializer externalizableBlobSerializer;

	@Test
	public void serializeerJacksonPersoonBlob() throws Exception {
		PersoonModel persoon = getPersoonModel();
		PersoonHisModel hisModel = getPersoonHisModel();

		SoortPersoon soort = hisModel.getSoort();
		int aantalGeslAanduidingen = hisModel.getGeslachtsaanduiding().size();
		int aantalGeboorten = hisModel.getGeboorte().size();
		int aantalAdressen = hisModel.getAdressen().size();

		// when
		byte[] json = jacksonBlobSerializer.serializeObject(hisModel);
		((JacksonBlobSerializer) jacksonBlobSerializer).enablePrettyPrint();
		byte[] jsonPretty = jacksonBlobSerializer.serializeObject(hisModel);
		LOGGER.info("HisModel: {} \n----------\nSize JSON: {}", new String(jsonPretty), json.length);

		PlBlob blob = creeerPlBlob(persoon, json);
		PersoonHisModel copy = jacksonBlobSerializer.deserializeObject(blob);

		// then
		Assert.assertEquals(soort, copy.getSoort());
		Assert.assertEquals(aantalGeslAanduidingen, copy.getGeslachtsaanduiding() != null ? copy
				.getGeslachtsaanduiding().size() : 0);
		Assert.assertEquals(aantalGeboorten, copy.getGeboorte() != null ? copy.getGeboorte().size() : 0);
		Assert.assertEquals(aantalAdressen, copy.getAdressen() != null ? copy.getAdressen().size() : 0);

	}

	@Test
	public void externalizeerPersoonBlob() throws Exception {

		PersoonModel persoon = getPersoonModel();
		PersoonHisModel hisModel = getPersoonHisModel();

		SoortPersoon soort = hisModel.getSoort();
		int aantalGeslAanduidingen = hisModel.getGeslachtsaanduiding().size();
		int aantalGeboorten = hisModel.getGeboorte().size();
		int aantalAdressen = hisModel.getAdressen().size();
		int aantalIdentificatienummers = hisModel.getIdentificatienummers().size();
		int aantalGeslnaamcomponenten = hisModel.getGeslachtsnaamcomponent() != null ? hisModel.getGeslachtsnaamcomponent().size() : 0;

		// when
		byte[] byteArray = externalizableBlobSerializer.serializeObject(hisModel);
		LOGGER.info(String.format("HisModel: %s \n----------\nSize EXTERNALIZABLE: %s", new String(byteArray),
				byteArray.length));

		PlBlob blob = creeerPlBlob(persoon, byteArray);
		PersoonHisModel copy = externalizableBlobSerializer.deserializeObject(blob);

		// then
		Assert.assertEquals(aantalGeslAanduidingen, copy.getGeslachtsaanduiding() != null ? copy
				.getGeslachtsaanduiding().size() : 0);
		Assert.assertEquals(aantalGeboorten, copy.getGeboorte() != null ? copy.getGeboorte().size() : 0);
		Assert.assertEquals(aantalAdressen, copy.getAdressen() != null ? copy.getAdressen().size() : 0);
		Assert.assertEquals(aantalIdentificatienummers, copy.getIdentificatienummers() != null ? copy
				.getIdentificatienummers().size() : 0);
		Assert.assertEquals(soort.getCode(), copy.getSoort().getCode());
		Assert.assertEquals(soort.getNaam(), copy.getSoort().getNaam());

		// Test inhoud geslachtsaanduidingen
		for (int i = 0; i < aantalGeslAanduidingen; i++) {
			PersoonGeslachtsaanduidingHisModel geslAanduiding = hisModel.getGeslachtsaanduiding().get(i);
			PersoonGeslachtsaanduidingHisModel kopieGeslAanduiding = copy.getGeslachtsaanduiding().get(i);

			if (geslAanduiding.getActieAanpassingGeldigheid() != null) {
				Assert.assertEquals(geslAanduiding.getActieAanpassingGeldigheid().getId(), kopieGeslAanduiding
						.getActieAanpassingGeldigheid().getId());
			}
			if (geslAanduiding.getActieInhoud() != null) {
				Assert.assertEquals(geslAanduiding.getActieInhoud().getId(), kopieGeslAanduiding.getActieInhoud()
						.getId());
			}
			if (geslAanduiding.getActieVerval() != null) {
				Assert.assertEquals(geslAanduiding.getActieVerval().getId(), kopieGeslAanduiding.getActieVerval()
						.getId());
			}
			if (geslAanduiding.getDatumAanvangGeldigheid() != null) {
				Assert.assertEquals(geslAanduiding.getDatumAanvangGeldigheid().getWaarde(), kopieGeslAanduiding
						.getDatumAanvangGeldigheid().getWaarde());
			}
			if (geslAanduiding.getDatumEindeGeldigheid() != null) {
				Assert.assertEquals(geslAanduiding.getDatumEindeGeldigheid().getWaarde(), kopieGeslAanduiding
						.getDatumEindeGeldigheid().getWaarde());
			}
			if (geslAanduiding.getDatumTijdRegistratie() != null) {
				Assert.assertEquals(geslAanduiding.getDatumTijdRegistratie().getWaarde().getTime(), kopieGeslAanduiding
						.getDatumTijdRegistratie().getWaarde().getTime());
			}
			if (geslAanduiding.getDatumTijdVerval() != null) {
				Assert.assertEquals(geslAanduiding.getDatumTijdVerval().getWaarde().getTime(), kopieGeslAanduiding
						.getDatumTijdVerval().getWaarde().getTime());
			}
			if (geslAanduiding.getGeslachtsaanduiding() != null) {
				Assert.assertEquals(geslAanduiding.getGeslachtsaanduiding().getCode(), kopieGeslAanduiding
						.getGeslachtsaanduiding().getCode());
			}
			if (geslAanduiding.getHistorie() != null) {
				Assert.assertEquals(geslAanduiding.getHistorie().getDatumAanvangGeldigheid().getWaarde(),
						kopieGeslAanduiding.getHistorie().getDatumAanvangGeldigheid().getWaarde());
			}
			if (geslAanduiding.getId() != null) {
				Assert.assertEquals(geslAanduiding.getId(), kopieGeslAanduiding.getId());
			}
		}

		// Test inhoud geboorten
		for (int i = 0; aantalGeboorten < i; i++) {
			PersoonGeboorteHisModel geb = hisModel.getGeboorte().get(i);
			PersoonGeboorteHisModel kopieGeb = copy.getGeboorte().get(i);

			if (geb.getId() != null) {
				Assert.assertEquals(geb.getId(), kopieGeb.getId());
			}
			if (geb.getBuitenlandseGeboortePlaats() != null) {
				Assert.assertEquals(geb.getBuitenlandseGeboortePlaats().getWaarde(), kopieGeb
						.getBuitenlandseGeboortePlaats().getWaarde());
			}
			if (geb.getDatumGeboorte() != null) {
				Assert.assertEquals(geb.getDatumGeboorte().getWaarde(), kopieGeb.getDatumGeboorte().getWaarde());
			}
			if (geb.getBuitenlandseRegioGeboorte() != null) {
				Assert.assertEquals(geb.getBuitenlandseRegioGeboorte().getWaarde(), kopieGeb
						.getBuitenlandseRegioGeboorte().getWaarde());
			}
			if (geb.getGemeenteGeboorte() != null) {
				Assert.assertEquals(geb.getGemeenteGeboorte().getNaam(), kopieGeb.getGemeenteGeboorte().getNaam());
			}
			if (geb.getLandGeboorte() != null) {
				Assert.assertEquals(geb.getLandGeboorte().getCode(), kopieGeb.getLandGeboorte().getCode());
			}
			if (geb.getWoonplaatsGeboorte() != null) {
				Assert.assertEquals(geb.getWoonplaatsGeboorte().getNaam(), kopieGeb.getWoonplaatsGeboorte().getNaam());
			}
			if (geb.getOmschrijvingGeboorteLocatie() != null) {
				Assert.assertEquals(geb.getOmschrijvingGeboorteLocatie().getWaarde(), kopieGeb
						.getOmschrijvingGeboorteLocatie().getWaarde());
			}
			if (geb.getActieInhoud() != null) {
				Assert.assertEquals(geb.getActieInhoud().getDatumAanvangGeldigheid().getWaarde(), kopieGeb
						.getActieInhoud().getDatumAanvangGeldigheid().getWaarde());
			}
		}

		// Test inhoud adressen
		for (int i = 0; i < aantalAdressen; i++) {
			PersoonAdresHisModel adres = hisModel.getAdressen().get(i);
			PersoonAdresHisModel kopieAdres = copy.getAdressen().get(i);

			if (adres.getAangeverAdresHouding() != null) {
				Assert.assertEquals(adres.getAangeverAdresHouding().getCode(), kopieAdres.getAangeverAdresHouding()
						.getCode());
			}
			if (adres.getAdresseerbaarObject() != null) {
				Assert.assertEquals(adres.getAdresseerbaarObject().getWaarde(), kopieAdres.getAdresseerbaarObject()
						.getWaarde());
			}
			if (adres.getIdentificatiecodeNummeraanduiding() != null) {
				Assert.assertEquals(adres.getIdentificatiecodeNummeraanduiding().getWaarde(), kopieAdres
						.getIdentificatiecodeNummeraanduiding().getWaarde());
			}
			if (adres.getGemeente() != null) {
				Assert.assertEquals(adres.getGemeente().getNaam().getWaarde(), kopieAdres.getGemeente().getNaam()
						.getWaarde());
			}
			if (adres.getNaamOpenbareRuimte() != null) {
				Assert.assertEquals(adres.getNaamOpenbareRuimte().getWaarde(), kopieAdres.getNaamOpenbareRuimte()
						.getWaarde());
			}
			if (adres.getAfgekorteNaamOpenbareRuimte() != null) {
				Assert.assertEquals(adres.getAfgekorteNaamOpenbareRuimte().getWaarde(), adres
						.getAfgekorteNaamOpenbareRuimte().getWaarde());
			}
			if (adres.getGemeentedeel() != null) {
				Assert.assertEquals(adres.getGemeentedeel().getWaarde(), kopieAdres.getGemeentedeel().getWaarde());
			}
			if (adres.getHuisnummer() != null) {
				Assert.assertEquals(adres.getHuisnummer().getWaarde(), kopieAdres.getHuisnummer().getWaarde());
			}
			if (adres.getHuisletter() != null) {
				Assert.assertEquals(adres.getHuisletter().getWaarde(), kopieAdres.getHuisletter().getWaarde());
			}
			if (adres.getPostcode() != null) {
				Assert.assertEquals(adres.getPostcode().getWaarde(), kopieAdres.getPostcode().getWaarde());
			}
			if (adres.getHuisnummertoevoeging() != null) {
				Assert.assertEquals(adres.getHuisnummertoevoeging().getWaarde(), kopieAdres.getHuisnummertoevoeging()
						.getWaarde());
			}
			if (adres.getWoonplaats() != null) {
				Assert.assertEquals(adres.getWoonplaats().getNaam().getWaarde(), kopieAdres.getWoonplaats().getNaam()
						.getWaarde());
			}
			if (adres.getLocatietovAdres() != null) {
				Assert.assertEquals(adres.getLocatietovAdres().getWaarde(), kopieAdres.getLocatietovAdres().getWaarde());
			}
			if (adres.getLocatieOmschrijving() != null) {
				Assert.assertEquals(adres.getLocatieOmschrijving().getWaarde(), kopieAdres.getLocatieOmschrijving()
						.getWaarde());
			}
			if (adres.getBuitenlandsAdresRegel1() != null) {
				Assert.assertEquals(adres.getBuitenlandsAdresRegel1().getWaarde(), kopieAdres
						.getBuitenlandsAdresRegel1().getWaarde());
			}
			if (adres.getLand() != null) {
				Assert.assertEquals(adres.getLand().getCode().getWaarde(), kopieAdres.getLand().getCode().getWaarde());
			}
			if (adres.getSoort() != null) {
				Assert.assertEquals(adres.getSoort().toString(), kopieAdres.getSoort().toString());
			}
			if (adres.getRedenWijziging() != null) {
				Assert.assertEquals(adres.getRedenWijziging().getNaam(), kopieAdres.getRedenWijziging().getNaam());
			}
			if (adres.getActieInhoud() != null && adres.getActieInhoud().getDatumAanvangGeldigheid() != null) {
				Assert.assertEquals(adres.getActieInhoud().getDatumAanvangGeldigheid().getWaarde(), kopieAdres
						.getActieInhoud().getDatumAanvangGeldigheid().getWaarde());
			}
		}

		// Test inhoud identificatienummers
		for (int i = 0; i < aantalIdentificatienummers; i++) {
			PersoonIdentificatienummersHisModel identnummer = hisModel.getIdentificatienummers().get(i);
			PersoonIdentificatienummersHisModel kopieIdentnummer = copy.getIdentificatienummers().get(i);

			if (identnummer.getActieAanpassingGeldigheid() != null) {
				Assert.assertEquals(identnummer.getActieAanpassingGeldigheid().getId(), kopieIdentnummer
						.getActieAanpassingGeldigheid().getId());
			}
			if (identnummer.getActieInhoud() != null) {
				Assert.assertEquals(identnummer.getActieInhoud().getId(), kopieIdentnummer.getActieInhoud().getId());
			}
			if (identnummer.getActieVerval() != null) {
				Assert.assertEquals(identnummer.getActieVerval().getId(), kopieIdentnummer.getActieVerval().getId());
			}
			if (identnummer.getDatumAanvangGeldigheid() != null) {
				Assert.assertEquals(identnummer.getDatumAanvangGeldigheid().getWaarde(), kopieIdentnummer
						.getDatumAanvangGeldigheid().getWaarde());
			}
			if (identnummer.getDatumEindeGeldigheid() != null) {
				Assert.assertEquals(identnummer.getDatumEindeGeldigheid().getWaarde(), kopieIdentnummer
						.getDatumEindeGeldigheid().getWaarde());
			}
			if (identnummer.getDatumTijdRegistratie() != null) {
				Assert.assertEquals(identnummer.getDatumTijdRegistratie().getWaarde().getTime(), kopieIdentnummer
						.getDatumTijdRegistratie().getWaarde().getTime());
			}
			if (identnummer.getDatumTijdVerval() != null) {
				Assert.assertEquals(identnummer.getDatumTijdVerval().getWaarde().getTime(), kopieIdentnummer
						.getDatumTijdVerval().getWaarde().getTime());
			}
			if (identnummer.getId() != null) {
				Assert.assertEquals(identnummer.getId(), kopieIdentnummer.getId());
			}
			if (identnummer.getAdministratienummer() != null) {
				Assert.assertEquals(identnummer.getAdministratienummer().getWaarde(), kopieIdentnummer
						.getAdministratienummer().getWaarde());
			}
			if (identnummer.getBurgerservicenummer() != null) {
				Assert.assertEquals(identnummer.getBurgerservicenummer().getWaarde(), kopieIdentnummer
						.getBurgerservicenummer().getWaarde());
			}
		}

		// Test inhoud geslachtsnaamcomponenten
		for (int i = 0; i < aantalGeslnaamcomponenten; i++) {
			PersoonGeslachtsaanduidingHisModel geslnaamcomp = hisModel.getGeslachtsaanduiding().get(i);
			PersoonGeslachtsaanduidingHisModel kopieGeslnaamcomp = copy.getGeslachtsaanduiding().get(i);
			if (geslnaamcomp.getActieAanpassingGeldigheid() != null) {
				Assert.assertEquals(geslnaamcomp.getActieAanpassingGeldigheid().getId(), kopieGeslnaamcomp
						.getActieAanpassingGeldigheid().getId());
			}
			if (geslnaamcomp.getActieInhoud() != null) {
				Assert.assertEquals(geslnaamcomp.getActieInhoud().getId(), kopieGeslnaamcomp.getActieInhoud().getId());
			}
			if (geslnaamcomp.getActieVerval() != null) {
				Assert.assertEquals(geslnaamcomp.getActieVerval().getId(), kopieGeslnaamcomp.getActieVerval().getId());
			}
			if (geslnaamcomp.getDatumAanvangGeldigheid() != null) {
				Assert.assertEquals(geslnaamcomp.getDatumAanvangGeldigheid().getWaarde(), kopieGeslnaamcomp
						.getDatumAanvangGeldigheid().getWaarde());
			}
			if (geslnaamcomp.getDatumEindeGeldigheid() != null) {
				Assert.assertEquals(geslnaamcomp.getDatumEindeGeldigheid().getWaarde(), kopieGeslnaamcomp
						.getDatumEindeGeldigheid().getWaarde());
			}
			if (geslnaamcomp.getDatumTijdRegistratie() != null) {
				Assert.assertEquals(geslnaamcomp.getDatumTijdRegistratie().getWaarde().getTime(), kopieGeslnaamcomp
						.getDatumTijdRegistratie().getWaarde().getTime());
			}
			if (geslnaamcomp.getDatumTijdVerval() != null) {
				Assert.assertEquals(geslnaamcomp.getDatumTijdVerval().getWaarde().getTime(), kopieGeslnaamcomp
						.getDatumTijdVerval().getWaarde().getTime());
			}
			if (geslnaamcomp.getId() != null) {
				Assert.assertEquals(geslnaamcomp.getId(), kopieGeslnaamcomp.getId());
			}
			if (geslnaamcomp.getGeslachtsaanduiding() != null) {
				Assert.assertEquals(geslnaamcomp.getGeslachtsaanduiding().getCode(), kopieGeslnaamcomp.getGeslachtsaanduiding().getCode());
			}
		}
	}

	private PlBlob creeerPlBlob(final PersoonModel persoon, final byte[] byteArray) {
		PlBlob plBlob = new PlBlob();
		plBlob.setPersoon(persoon);
		plBlob.setPl(byteArray);

		return plBlob;
	}

	private PersoonHisModel getPersoonHisModel() throws IOException, ClassNotFoundException {
		return blobOpslagplaats.leesPlBlob(1001);
	}

	private PersoonModel getPersoonModel() {
		return persoonRepository.haalPersoonMetAdres(1001);
	}

}
