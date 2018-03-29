/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.util;


import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Iterables;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 */
public class ExpressietaalTestPersoon {

    public static MetaObject PERSOON;
    public static Persoonslijst PERSOONSLIJST;
    public static Persoonslijst PERSOONSLIJST_LEEG;


    static {

        ZonedDateTime VANDAAG = DatumUtil.nuAlsZonedDateTime();
        ZonedDateTime EERGISTER = VANDAAG.minusDays(2);
        ZonedDateTime EEREERGISTER = VANDAAG.minusDays(3);

        final AdministratieveHandeling handelingEerEergister = maakHandeling(EEREERGISTER, 1000, "000123", SoortActie.CONVERSIE_GBA);
        final AdministratieveHandeling handelingEergister = maakHandeling(EERGISTER, 2000, "000123", SoortActie.BEEINDIGING_OUDER);
        final AdministratieveHandeling handelingVandaag = maakHandeling(VANDAAG, 3000, "000123", SoortActie.ONTRELATEREN);

        Actie actieVandaag = Iterables.get(handelingVandaag.getActies(), 0);
        Actie actieEergisteren = Iterables.get(handelingEergister.getActies(), 0);
        Actie actieEerEergisteren = Iterables.get(handelingEerEergister.getActies(), 0);

        //@formatter:off
        PERSOON = MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON)
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS))
                .metRecord()
                .metId(1)
                .metActieInhoud(actieVandaag)
                .metDatumAanvangGeldigheid(20010104)
                .metIndicatieTbvLeveringMutaties(false)
                .metAttribuut(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER), "0123456789")
                .metAttribuut(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER), "987654321")
                .eindeRecord()
                .metRecord()
                .metId(2)
                .metActieInhoud(actieEerEergisteren)
                .metActieVerval(actieVandaag)
                .metDatumAanvangGeldigheid(20020104)
                .metDatumEindeGeldigheid(20020104)
                .metAttribuut(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER), "123456788")
                .metAttribuut(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER), "987654322")
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE))
                .metRecord()
                .metId(1)
                .metActieInhoud(actieVandaag)
                .metAttribuut(getAttribuutElement(Element.PERSOON_GEBOORTE_GEMEENTECODE), 130)
                .metAttribuut(getAttribuutElement(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE), 21)
                .metAttribuut(getAttribuutElement(Element.PERSOON_GEBOORTE_WOONPLAATSNAAM), "Pisa")
                .metAttribuut(getAttribuutElement(Element.PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE), "Om de hoek")
                .metAttribuut(getAttribuutElement(Element.PERSOON_GEBOORTE_BUITENLANDSEPLAATS), "Jakarta")
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_MIGRATIE))
                .metRecord()
                .metId(1)
                .metActieInhoud(actieVandaag)
                .metAttribuut(getAttribuutElement(Element.PERSOON_MIGRATIE_SOORTCODE), "B")
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_NAAMGEBRUIK))
                .metRecord()
                .metId(1)
                .metActieInhoud(actieVandaag)
                .metAttribuut(getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_ADELLIJKETITELCODE), "B")
                .metAttribuut(getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_VOORVOEGSEL), "De")
                .eindeRecord()
                .eindeGroep()
                .metObject()
                .metId(10)
                .metObjectElement(Element.PERSOON_REISDOCUMENT.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_REISDOCUMENT_STANDAARD.getId())
                .metRecord()
                .metActieInhoud(actieVandaag)
                .metAttribuut(Element.PERSOON_REISDOCUMENT_NUMMER.getId(), "10")
                .metAttribuut(Element.PERSOON_REISDOCUMENT_DATUMINGANGDOCUMENT.getId(), 20100101)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .metObject()
                .metId(20)
                .metObjectElement(Element.PERSOON_REISDOCUMENT)
                .metGroep()
                .metGroepElement(Element.PERSOON_REISDOCUMENT_STANDAARD)
                .metRecord()
                .metActieInhoud(actieVandaag)
                .metAttribuut(Element.PERSOON_REISDOCUMENT_NUMMER.getId(), "12")
                .metAttribuut(Element.PERSOON_REISDOCUMENT_DATUMINGANGDOCUMENT.getId(), 20130101)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .metObject()
                .metObjectElement(Element.PERSOON_NATIONALITEIT)
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD))
                .metRecord()
                .metId(1)
                .metActieInhoud(actieVandaag)
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT))
                .metRecord()
                .metId(2)
                .metAttribuut(getAttribuutElement(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE), 90210)
                .metAttribuut(getAttribuutElement(Element.PERSOON_NATIONALITEIT_INDICATIEBIJHOUDINGBEEINDIGD), true)
                .metAttribuut(getAttribuutElement(Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE), 1)
                .metAttribuut(getAttribuutElement(Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE), 2)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .metObject()
                .metObjectElement(getObjectElement(Element.PERSOON_OUDER))
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_OUDER_IDENTITEIT))
                .metRecord().metId(1).metActieInhoud(actieVandaag).eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_OUDER_OUDERSCHAP))
                .metRecord().metId(1).metActieInhoud(actieVandaag).eindeRecord()
                .eindeGroep()
                .metObject()
                .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                .metGroep()
                .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD))
                .metRecord().metActieInhoud(actieVandaag).eindeRecord()
                .eindeGroep()
                .metObject()
                .metObjectElement(getObjectElement(Element.GERELATEERDEKIND))
                .metGroep()
                .metGroepElement(getGroepElement(Element.GERELATEERDEKIND_IDENTITEIT))
                .metRecord().metActieInhoud(actieVandaag).eindeRecord()
                .eindeGroep()
                .metObject()
                .metObjectElement(getObjectElement(Element.GERELATEERDEKIND_PERSOON))
                .metGroep()
                .metGroepElement(getGroepElement(Element.GERELATEERDEKIND_PERSOON_IDENTITEIT))
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS.getId())
                .metRecord()
                .metActieInhoud(actieVandaag)
                .metAttribuut(Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), "787654321")
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .eindeObject()
                .eindeObject()
                .eindeObject()
                .metObject()
                .metObjectElement(Element.PERSOON_INDICATIE_ONDERCURATELE.getId())
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_INDICATIE_ONDERCURATELE_STANDAARD))
                .metRecord().metId(1).metActieInhoud(actieVandaag).eindeRecord()
                .eindeGroep()
                .eindeObject()
                .metObject()
                .metObjectElement(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER.getId())
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_STANDAARD))
                .metRecord().metId(1).metActieInhoud(actieVandaag).eindeRecord()
                .eindeGroep()
                .eindeObject()
                .metObject()
                .metObjectElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE.getId())
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_STANDAARD))
                .metRecord().metId(1).metActieInhoud(actieVandaag).eindeRecord()
                .eindeGroep()
                .eindeObject()
                .metObject()
                .metObjectElement(Element.PERSOON_INDICATIE_STAATLOOS.getId())
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_INDICATIE_STAATLOOS_STANDAARD))
                .metRecord().metId(1).metActieInhoud(actieVandaag).eindeRecord()
                .eindeGroep()
                .eindeObject()
                .metObject()
                .metObjectElement(Element.PERSOON_VERIFICATIE.getId())
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_VERIFICATIE_IDENTITEIT))
                .metRecord()
                .metAttribuut(getAttribuutElement(Element.PERSOON_VERIFICATIE_PARTIJCODE.getId()), 250002)
                .metAttribuut(getAttribuutElement(Element.PERSOON_VERIFICATIE_SOORT.getId()), "Koninklijk besluit")
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_VERIFICATIE_STANDAARD))
                .metRecord().metId(1).metActieInhoud(actieVandaag)
                .metAttribuut(getAttribuutElement(Element.PERSOON_VERIFICATIE_DATUM.getId()), 20001010)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .build();
        //@formatter:on
        BrpNu.set(VANDAAG);
        PERSOONSLIJST = new Persoonslijst(PERSOON, 0L);
        PERSOONSLIJST_LEEG = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
    }

    private static AdministratieveHandeling maakHandeling(final ZonedDateTime dateTime, int idBase, String partijCode, SoortActie soortActie) {
        //@formatter:off
        MetaObject administratievehandeling = MetaObject.maakBuilder()
                .metObjectElement(Element.ADMINISTRATIEVEHANDELING.getId())
                .metId(idBase)
                .metGroep()
                .metGroepElement(Element.ADMINISTRATIEVEHANDELING_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.ADMINISTRATIEVEHANDELING_SOORTNAAM.getId(),
                        SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND.getId())
                .metAttribuut(Element.ADMINISTRATIEVEHANDELING_PARTIJCODE.getId(), partijCode)
                .metAttribuut(Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE.getId(), dateTime)
                .metAttribuut(Element.ADMINISTRATIEVEHANDELING_TOELICHTINGONTLENING.getId(), "toelichting" + idBase)
                .eindeRecord()
                .eindeGroep()
                .metObject()
                .metObjectElement(Element.ACTIE.getId())
                .metId(idBase)
                .metGroep()
                .metGroepElement(getGroepElement(Element.ACTIE_IDENTITEIT))
                .metRecord()
                .metAttribuut(Element.ACTIE_SOORTNAAM.getId(), soortActie.getId())
                .metAttribuut(Element.ACTIE_PARTIJCODE.getId(), partijCode)
                .metAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE.getId(), dateTime)
                .metAttribuut(Element.ACTIE_DATUMONTLENING.getId(), 20010101)
                .eindeRecord()
                .eindeGroep()
                .metObject()
                .metObjectElement(Element.ACTIEBRON.getId())
                .metId(idBase)
                .metGroep()
                .metGroepElement(Element.ACTIEBRON_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.ACTIEBRON_RECHTSGRONDCODE.getId(), String.valueOf(idBase))
                .metAttribuut(Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING.getId(), "rechtsgrondomschrijving" + idBase)
                .eindeRecord()
                .eindeGroep()
                .metObject()
                .metId(idBase)
                .metObjectElement(Element.DOCUMENT.getId())
                .metGroep()
                .metGroepElement(Element.DOCUMENT_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.DOCUMENT_SOORTNAAM.getId(), "soortnaam" + idBase)
                .metAttribuut(Element.DOCUMENT_AKTENUMMER.getId(), "aktenummer" + idBase)
                .metAttribuut(Element.DOCUMENT_OMSCHRIJVING.getId(), "omschrijving" + idBase)
                .metAttribuut(Element.DOCUMENT_PARTIJCODE.getId(), partijCode)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .eindeObject()
                .eindeObject()

                .metObject()
                .metObjectElement(Element.ACTIE.getId())
                .metId(idBase + 1)
                .metGroep()
                .metGroepElement(getGroepElement(Element.ACTIE_IDENTITEIT))
                .metRecord()
                .metAttribuut(Element.ACTIE_SOORTNAAM.getId(), soortActie.getId())
                .metAttribuut(Element.ACTIE_PARTIJCODE.getId(), partijCode)
                .metAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE.getId(), dateTime)
                .metAttribuut(Element.ACTIE_DATUMONTLENING.getId(), 20010101)
                .eindeRecord()
                .eindeGroep()
                .metObject()
                .metObjectElement(Element.ACTIEBRON.getId())
                .metId(idBase + 1)
                .metGroep()
                .metGroepElement(Element.ACTIEBRON_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.ACTIEBRON_RECHTSGRONDCODE.getId(), String.valueOf(idBase + 1))
                .metAttribuut(Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING.getId(), "rechtsgrondomschrijving" + idBase + 1)
                .eindeRecord()
                .eindeGroep()
                .metObject()
                .metId(idBase + 1)
                .metObjectElement(Element.DOCUMENT.getId())
                .metGroep()
                .metGroepElement(Element.DOCUMENT_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.DOCUMENT_SOORTNAAM.getId(), "soortnaam" + idBase + 1)
                .metAttribuut(Element.DOCUMENT_AKTENUMMER.getId(), "aktenummer" + idBase + 1)
                .metAttribuut(Element.DOCUMENT_OMSCHRIJVING.getId(), "omschrijving" + idBase + 1)
                .metAttribuut(Element.DOCUMENT_PARTIJCODE.getId(), partijCode)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .eindeObject()
                .eindeObject()
                .build();
        //@formatter:on

        return AdministratieveHandeling.converter().converteer(administratievehandeling);
    }
}
