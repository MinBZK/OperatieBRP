/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;

/**
 */
public class TestVerantwoording {

    public static Actie maakActie(final long id) {
        return maakActie(id, DatumUtil.nuAlsZonedDateTime());
    }

    public static Actie maakActie(final long id, final ZonedDateTime datumTijdAttribuut) {
        final MetaObject.Builder builder = maakAdministratieveHandeling(id, "000002", datumTijdAttribuut, SoortAdministratieveHandeling.CORRECTIE_ADRES);
        builder.metObject(maakActieBuilder(id, SoortActie.BEEINDIGING_OUDER, datumTijdAttribuut, "000123", DatumUtil.vandaag()));
        final AdministratieveHandeling administratieveHandeling = AdministratieveHandeling.converter().converteer(builder.build());
        return Iterables.getOnlyElement(administratieveHandeling.getActies());
    }

    public static AdministratieveHandeling maakAdministratieveHandeling(SoortAdministratieveHandeling soortAdministratieveHandeling) {
        final ZonedDateTime tsReg = DatumUtil.nuAlsZonedDateTime();
        final String partijCode = "000124";
        final MetaObject.Builder builder = maakAdministratieveHandeling(1, partijCode, tsReg,
                soortAdministratieveHandeling);

        builder.metObject(maakActieBuilder(1, SoortActie.BEEINDIGING_OUDER, tsReg, partijCode, 1));
        return AdministratieveHandeling.converter().converteer(builder.build());
    }

    public static AdministratieveHandeling maakAdministratieveHandeling(final long id) {
        final ZonedDateTime tsReg = DatumUtil.nuAlsZonedDateTime();
        final String partijCode = "000124";
        final MetaObject.Builder builder = maakAdministratieveHandeling(id, partijCode, tsReg,
                SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND);

        builder.metObject(maakActieBuilder(1, SoortActie.BEEINDIGING_OUDER, tsReg, partijCode, 1));
        return AdministratieveHandeling.converter().converteer(builder.build());
    }

    public static MetaObject.Builder maakAdministratieveHandeling(final long id, final String partijCode, final ZonedDateTime tsReg,
                                                                  final SoortAdministratieveHandeling srt) {
        //@formatter:off
        final MetaObject.Builder builder = MetaObject.maakBuilder().metObject()
            .metObjectElement(Element.ADMINISTRATIEVEHANDELING.getId())
            .metId(id)
            .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.ADMINISTRATIEVEHANDELING_IDENTITEIT))
                .metRecord()
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_SOORTNAAM.getId(), srt.getId())
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_PARTIJCODE.getId(), partijCode)
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE.getId(), tsReg)
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_TOELICHTINGONTLENING.getId(), "toelichting")
                .eindeRecord()
            .eindeGroep();
        //@formatter:on
        return builder;
    }

    public static MetaObject.Builder maakActieBuilder(long id, SoortActie soortActie, final ZonedDateTime tsReg, String partijCode, Integer datumOntlening) {

        final LinkedList<MetaAttribuut.Builder> attribuutLijst = Lists.newLinkedList();
        if (datumOntlening != null) {
            attribuutLijst.add(MetaAttribuut.maakBuilder().metType(Element.ACTIE_DATUMONTLENING.getId()).metWaarde(datumOntlening));
        }
        attribuutLijst.add(MetaAttribuut.maakBuilder().metType(Element.ACTIE_SOORTNAAM.getId()).metWaarde(soortActie.getId()));
        attribuutLijst.add(MetaAttribuut.maakBuilder().metType(Element.ACTIE_TIJDSTIPREGISTRATIE.getId()).metWaarde(tsReg));
        attribuutLijst.add(MetaAttribuut.maakBuilder().metType(Element.ACTIE_PARTIJCODE.getId()).metWaarde(partijCode));

        //@formatter:off
        final MetaObject.Builder builder = MetaObject.maakBuilder().metObject()
            .metObjectElement(Element.ACTIE.getId())
            .metId(id)
            .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.ACTIE_IDENTITEIT))
                .metRecord()
                    .metAttributen(attribuutLijst)
                .eindeRecord()
            .eindeGroep();
        //@formatter:on
        return builder;
    }

    public static MetaObject.Builder maakActiebronBuilder(final int actiebronId, final String rechtsgrondCode, final String rechtsgrondOmschrijving) {
        final LinkedList<MetaAttribuut.Builder> attribuutLijst = Lists.newLinkedList();
        if (rechtsgrondCode != null) {
            attribuutLijst.add(MetaAttribuut.maakBuilder().metType(Element.ACTIEBRON_RECHTSGRONDCODE.getId()).metWaarde(rechtsgrondCode));
        }
        if (rechtsgrondOmschrijving != null) {
            attribuutLijst.add(MetaAttribuut.maakBuilder().metType(Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING.getId()).metWaarde(rechtsgrondOmschrijving));
        }
        //@formatter:off
        final MetaObject.Builder documentBuilder = MetaObject.maakBuilder().metObject()
            .metObjectElement(Element.ACTIEBRON.getId())
            .metId(actiebronId)
            .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.ACTIEBRON_IDENTITEIT))
                .metRecord()
                    .metAttributen(attribuutLijst)
                .eindeRecord()
            .eindeGroep() ;
        //@formatter:on
        return documentBuilder;
    }

    public static MetaObject.Builder maakDocumentBuilder(final int documentId) {
        return maakDocumentBuilder(documentId, "soortDoc", "aktenummer", "omschrijving", "000123");
    }

    public static MetaObject.Builder maakDocumentBuilder(final int documentId, final String soortDoc,
                                                         final String aktenummer, final String documentOmschrijving, String partijCode) {

        final LinkedList<MetaAttribuut.Builder> attribuutLijst = Lists.newLinkedList();
        if (soortDoc != null) {
            attribuutLijst.add(MetaAttribuut.maakBuilder().metType(Element.DOCUMENT_SOORTNAAM.getId()).metWaarde(soortDoc));
        }
        if (aktenummer != null) {
            attribuutLijst.add(MetaAttribuut.maakBuilder().metType(Element.DOCUMENT_AKTENUMMER.getId()).metWaarde(aktenummer));
        }
        if (documentOmschrijving != null) {
            attribuutLijst.add(MetaAttribuut.maakBuilder().metType(Element.DOCUMENT_OMSCHRIJVING.getId()).metWaarde(documentOmschrijving));
        }

        attribuutLijst.add(MetaAttribuut.maakBuilder().metType(Element.DOCUMENT_PARTIJCODE.getId()).metWaarde(partijCode));

        //@formatter:off
        final MetaObject.Builder documentBuilder = MetaObject.maakBuilder().metObject()
            .metObjectElement(Element.DOCUMENT.getId())
            .metId(documentId)
            .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.DOCUMENT_IDENTITEIT))
                .metRecord()
                    .metId(documentId)
                    .metAttributen(attribuutLijst)
                .eindeRecord()
            .eindeGroep() ;
        //@formatter:on
        return documentBuilder;
    }

    public static AdministratieveHandeling maakAdministratievehandelingMetActies(int id) {
        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();
        final String partijCode = "000123";
        final MetaObject ah = TestVerantwoording
                .maakAdministratieveHandeling(id, partijCode, nu, SoortAdministratieveHandeling.CORRECTIE_ADRES)
                .metObjecten(Lists.newArrayList(
                        TestVerantwoording.maakActieBuilder(id, SoortActie.BEEINDIGING_VOORNAAM, nu, partijCode, 0),
                        TestVerantwoording.maakActieBuilder(id + 1, SoortActie.BEEINDIGING_VOORNAAM, nu, partijCode, 0),
                        TestVerantwoording.maakActieBuilder(id + 2, SoortActie.BEEINDIGING_VOORNAAM, nu, partijCode, 0),
                        TestVerantwoording.maakActieBuilder(id + 3, SoortActie.BEEINDIGING_VOORNAAM, nu, partijCode, 0)
                ))
                .build();
        return AdministratieveHandeling.converter().converteer(ah);
    }

    public static AdministratieveHandeling maakAdministratievehandelingMetActies(int id, ZonedDateTime zonedDateTime) {
        final String partijCode = "000123";
        final MetaObject ah = TestVerantwoording
                .maakAdministratieveHandeling(id, partijCode, zonedDateTime, SoortAdministratieveHandeling.CORRECTIE_ADRES)
                .metObjecten(Lists.newArrayList(
                        TestVerantwoording.maakActieBuilder(id, SoortActie.BEEINDIGING_VOORNAAM, zonedDateTime, partijCode, 0),
                        TestVerantwoording.maakActieBuilder(id + 1, SoortActie.BEEINDIGING_VOORNAAM, zonedDateTime, partijCode, 0),
                        TestVerantwoording.maakActieBuilder(id + 2, SoortActie.BEEINDIGING_VOORNAAM, zonedDateTime, partijCode, 0),
                        TestVerantwoording.maakActieBuilder(id + 3, SoortActie.BEEINDIGING_VOORNAAM, zonedDateTime, partijCode, 0)
                ))
                .build();
        return AdministratieveHandeling.converter().converteer(ah);
    }


}
