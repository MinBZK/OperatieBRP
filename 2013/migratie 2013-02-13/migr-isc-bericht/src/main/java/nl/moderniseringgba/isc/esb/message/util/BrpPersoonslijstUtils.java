/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.brp.generated.GroepBetrokkenheidOuderschap;
import nl.moderniseringgba.isc.esb.message.brp.generated.GroepPersoonGeboorte;
import nl.moderniseringgba.isc.esb.message.brp.generated.GroepPersoonGeslachtsaanduiding;
import nl.moderniseringgba.isc.esb.message.brp.generated.GroepPersoonIdentificatienummers;
import nl.moderniseringgba.isc.esb.message.brp.generated.GroepPersoonOverlijden;
import nl.moderniseringgba.isc.esb.message.brp.generated.GroepPersoonSamengesteldeNaam;
import nl.moderniseringgba.isc.esb.message.brp.generated.JaS;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjecttypeBron;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjecttypePersoon;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjecttypePersoonGeslachtsnaamcomponent;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjecttypePersoonNationaliteit;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjecttypePersoonVoornaam;
import nl.moderniseringgba.isc.esb.message.brp.generated.ViewOuder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.moderniseringgba.migratie.conversie.proces.UniqueSequence;

/**
 * Help functies voor het vullen van een BrpPersoonslijst op basis van XML-objecten.
 * 
 */
// CHECKSTYLE:OFF Er zijn teveel import in deze klasse doordat we een ontvangen BRP-PL in XML moeten omzetten naar een
// BRP-PL object.
public final class BrpPersoonslijstUtils {
    // CHECKSTYLE:ON

    /**
     * Private constructor: niet instantieerbaar.
     */
    private BrpPersoonslijstUtils() {
    }

    /**
     * Maakt een BrpActie {@link BrpActie} object op basis van bron/historie informatie.
     * 
     * @param bron
     *            Het bron type met daarin de bron informatie.
     * @param historie
     *            De historie gegevens.
     * @param tijdstipOntlening
     *            Het tijdstip van ontlening.
     * @param tijdstipRegistratie
     *            Het tijdstip van registratie.
     * @return Het samengestelde BrpActie object.
     */
    private static BrpActie getActie(
            final ObjecttypeBron bron,
            final BrpHistorie historie,
            final Long tijdstipOntlening,
            final Long tijdstipRegistratie) {
        final BrpDocumentInhoud documentInhoud =
                new BrpDocumentInhoud(new BrpSoortDocumentCode(bron.getDocument().getSoortNaam().getValue()), null,
                        bron.getDocument().getAktenummer().getValue(), bron.getDocument().getOmschrijving()
                                .getValue(), new BrpPartijCode(null, Integer.valueOf(bron.getDocument()
                                .getPartijCode().getValue())));
        final BrpGroep<BrpDocumentInhoud> documentGroep =
                new BrpGroep<BrpDocumentInhoud>(documentInhoud, historie, null, null, null);
        final List<BrpGroep<BrpDocumentInhoud>> documentLijst = new ArrayList<BrpGroep<BrpDocumentInhoud>>();
        documentLijst.add(documentGroep);
        final BrpStapel<BrpDocumentInhoud> documentStapel = new BrpStapel<BrpDocumentInhoud>(documentLijst);
        final List<BrpStapel<BrpDocumentInhoud>> documentStapels = new ArrayList<BrpStapel<BrpDocumentInhoud>>();
        documentStapels.add(documentStapel);

        final BrpActie actie =
                new BrpActie(UniqueSequence.next(), BrpSoortActieCode.CONVERSIE_GBA, new BrpPartijCode(null,
                        Integer.valueOf(bron.getDocument().getPartijCode().getValue())), null,
                        BrpDatumTijd.fromDatumTijd(tijdstipOntlening),
                        BrpDatumTijd.fromDatumTijd(tijdstipRegistratie), documentStapels, 1, null);
        return actie;
    }

    /**
     * Maakt een voornaam stapel op basis van de meegeleverde informatie aan.
     * 
     * @param persoon
     *            Het persoon type met informatie over de persoon.
     * @param historie
     *            De historie informatie.
     * @param actie
     *            De gekoppelde actie informatie.
     * @return De aangemaakte voornaam stapel.
     */
    private static BrpStapel<BrpVoornaamInhoud> getVoorNaamStapel(
            final ObjecttypePersoon persoon,
            final BrpHistorie historie,
            final BrpActie actie) {
        // Naam (Groep 02)
        final List<BrpGroep<BrpVoornaamInhoud>> voornaamLijst = new ArrayList<BrpGroep<BrpVoornaamInhoud>>();

        for (int index = 0; index < persoon.getVoornamen().getVoornaam().size(); index++) {

            final ObjecttypePersoonVoornaam kindVoornaam = persoon.getVoornamen().getVoornaam().get(index);

            final BrpVoornaamInhoud voornaamInhoud =
                    new BrpVoornaamInhoud(kindVoornaam.getNaam().getValue(), kindVoornaam.getVolgnummer().getValue()
                            .intValue());
            final BrpGroep<BrpVoornaamInhoud> voornaamGroep =
                    new BrpGroep<BrpVoornaamInhoud>(voornaamInhoud, historie, actie, null, null);
            voornaamLijst.add(voornaamGroep);
        }

        return new BrpStapel<BrpVoornaamInhoud>(voornaamLijst);
    }

    /**
     * Maakt een geslachtsnaam component stapel op basis van de meegeleverde informatie aan.
     * 
     * @param persoon
     *            Het persoon type met informatie over de persoon.
     * @param historie
     *            De historie informatie.
     * @param actie
     *            De gekoppelde actie informatie.
     * @return De aangemaakte geslachtsnaam component stapel.
     */
    private static BrpStapel<BrpGeslachtsnaamcomponentInhoud> getGeslachtsnaamcomponentStapel(
            final ObjecttypePersoon persoon,
            final BrpHistorie historie,
            final BrpActie actie) {

        if (persoon.getGeslachtsnaamcomponenten() != null
                && persoon.getGeslachtsnaamcomponenten().getGeslachtsnaamcomponent() != null
                && persoon.getGeslachtsnaamcomponenten().getGeslachtsnaamcomponent().size() > 0) {

            final List<BrpGroep<BrpGeslachtsnaamcomponentInhoud>> geslachtsnaamcomponentLijst =
                    new ArrayList<BrpGroep<BrpGeslachtsnaamcomponentInhoud>>();

            for (final ObjecttypePersoonGeslachtsnaamcomponent geslachtsnaamComponent : persoon
                    .getGeslachtsnaamcomponenten().getGeslachtsnaamcomponent()) {

                final BrpPredikaatCode predikaatCode =
                        geslachtsnaamComponent.getPredikaatCode() == null ? null : new BrpPredikaatCode(
                                geslachtsnaamComponent.getPredikaatCode().getValue().toString());

                final BrpAdellijkeTitelCode adellijkeTitelCode =
                        geslachtsnaamComponent.getAdellijkeTitelCode() == null ? null : new BrpAdellijkeTitelCode(
                                geslachtsnaamComponent.getAdellijkeTitelCode().getValue().toString());

                final BrpGeslachtsnaamcomponentInhoud geslachtsnaamcomponentenInhoud =
                        new BrpGeslachtsnaamcomponentInhoud(geslachtsnaamComponent.getVoorvoegsel().getValue(),
                                geslachtsnaamComponent.getScheidingsteken().getValue().charAt(0),
                                geslachtsnaamComponent.getNaam().getValue(), predikaatCode, adellijkeTitelCode,
                                geslachtsnaamComponent.getVolgnummer().getValue().intValue());

                final BrpGroep<BrpGeslachtsnaamcomponentInhoud> geslachtsnaamcomponentGroep =
                        new BrpGroep<BrpGeslachtsnaamcomponentInhoud>(geslachtsnaamcomponentenInhoud, historie,
                                actie, null, null);
                geslachtsnaamcomponentLijst.add(geslachtsnaamcomponentGroep);
            }

            return new BrpStapel<BrpGeslachtsnaamcomponentInhoud>(geslachtsnaamcomponentLijst);
        } else {
            return null;
        }

    }

    /**
     * Maakt een geboorte stapel op basis van de meegeleverde informatie aan.
     * 
     * @param persoon
     *            Het persoon type met informatie over de persoon.
     * @param historie
     *            De historie informatie.
     * @param actie
     *            De gekoppelde actie informatie.
     * @return De aangemaakte geboorte stapel.
     */
    private static BrpStapel<BrpGeboorteInhoud> getGeboorteStapel(
            final ObjecttypePersoon persoon,
            final BrpHistorie historie,
            final BrpActie actie) {

        if (persoon.getGeboorte() != null && persoon.getGeboorte().size() > 0) {
            final List<BrpGroep<BrpGeboorteInhoud>> geboorteLijst = new ArrayList<BrpGroep<BrpGeboorteInhoud>>();

            for (final GroepPersoonGeboorte persoonGeboorte : persoon.getGeboorte()) {
                final BrpGeboorteInhoud geboorteInhoud =
                        new BrpGeboorteInhoud(new BrpDatum(persoonGeboorte.getDatum().getValue().intValue()), null,
                                new BrpPlaatsCode(persoonGeboorte.getWoonplaatsCode().getValue()), null, null,
                                new BrpLandCode(Integer.valueOf(persoonGeboorte.getLandCode().getValue())), null);

                final BrpGroep<BrpGeboorteInhoud> geboorteGroep =
                        new BrpGroep<BrpGeboorteInhoud>(geboorteInhoud, historie, actie, null, null);
                geboorteLijst.add(geboorteGroep);
            }

            return new BrpStapel<BrpGeboorteInhoud>(geboorteLijst);
        } else {
            return null;
        }

    }

    /**
     * Maakt een geboorte stapel op basis van de meegeleverde informatie aan.
     * 
     * @param persoon
     *            Het persoon type met informatie over de persoon.
     * @param historie
     *            De historie informatie.
     * @param actie
     *            De gekoppelde actie informatie.
     * @return De aangemaakte geboorte stapel.
     */
    private static BrpStapel<BrpOverlijdenInhoud> getOverlijdenStapel(
            final ObjecttypePersoon persoon,
            final BrpHistorie historie,
            final BrpActie actie) {

        if (persoon.getOverlijden() != null && persoon.getOverlijden().size() > 0) {

            final List<BrpGroep<BrpOverlijdenInhoud>> overlijdenLijst =
                    new ArrayList<BrpGroep<BrpOverlijdenInhoud>>();

            for (final GroepPersoonOverlijden persoonOverlijden : persoon.getOverlijden()) {

                final BrpOverlijdenInhoud overlijdenInhoud =
                        new BrpOverlijdenInhoud(new BrpDatum(persoonOverlijden.getDatum().getValue().intValue()),
                                new BrpGemeenteCode(new BigDecimal(persoonOverlijden.getGemeenteCode().getValue())),
                                new BrpPlaatsCode(persoonOverlijden.getWoonplaatsCode().getValue()),
                                persoonOverlijden.getBuitenlandsePlaats().getValue(), persoonOverlijden
                                        .getBuitenlandseRegio().getValue(), new BrpLandCode(
                                        Integer.valueOf(persoonOverlijden.getLandCode().getValue())),
                                persoonOverlijden.getOmschrijvingLocatie().getValue());

                final BrpGroep<BrpOverlijdenInhoud> overlijdenGroep =
                        new BrpGroep<BrpOverlijdenInhoud>(overlijdenInhoud, historie, actie, null, null);

                overlijdenLijst.add(overlijdenGroep);
            }

            return new BrpStapel<BrpOverlijdenInhoud>(overlijdenLijst);
        } else {
            return null;
        }
    }

    /**
     * Maakt een geslachtsaanduiding stapel op basis van de meegeleverde informatie aan.
     * 
     * @param persoon
     *            Het persoon type met informatie over de persoon.
     * @param historie
     *            De historie informatie.
     * @param actie
     *            De gekoppelde actie informatie.
     * @return De aangemaakte geslachtsaanduiding stapel.
     */
    private static BrpStapel<BrpGeslachtsaanduidingInhoud> getGeslachtsaanduidingStapel(
            final ObjecttypePersoon persoon,
            final BrpHistorie historie,
            final BrpActie actie) {

        if (persoon.getGeslachtsaanduiding() != null && persoon.getGeslachtsaanduiding().size() > 0) {
            final List<BrpGroep<BrpGeslachtsaanduidingInhoud>> geslachtsaanduidingLijst =
                    new ArrayList<BrpGroep<BrpGeslachtsaanduidingInhoud>>();

            for (final GroepPersoonGeslachtsaanduiding groepPersoonGeslachtsaanduiding : persoon
                    .getGeslachtsaanduiding()) {

                final BrpGeslachtsaanduidingInhoud geslachtsaanduidingInhoud =
                        new BrpGeslachtsaanduidingInhoud(
                                BrpGeslachtsaanduidingCode.valueOfBrpCode(groepPersoonGeslachtsaanduiding.getCode()
                                        .getValue().toString()));

                final BrpGroep<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingGroep =
                        new BrpGroep<BrpGeslachtsaanduidingInhoud>(geslachtsaanduidingInhoud, historie, actie, null,
                                null);

                geslachtsaanduidingLijst.add(geslachtsaanduidingGroep);
            }

            return new BrpStapel<BrpGeslachtsaanduidingInhoud>(geslachtsaanduidingLijst);
        } else {
            return null;
        }
    }

    /**
     * Maakt een samengestelde naam stapel op basis van de meegeleverde informatie aan.
     * 
     * @param persoon
     *            Het persoon type met informatie over de persoon.
     * @param historie
     *            De historie informatie.
     * @param actie
     *            De gekoppelde actie informatie.
     * @return De aangemaakte samengestelde naam stapel.
     */
    private static BrpStapel<BrpSamengesteldeNaamInhoud> getSamengesteldeNaamStapel(
            final ObjecttypePersoon persoon,
            final BrpHistorie historie,
            final BrpActie actie) {

        if (persoon.getSamengesteldeNaam() != null && persoon.getSamengesteldeNaam().size() > 0) {

            final List<BrpGroep<BrpSamengesteldeNaamInhoud>> samengesteldenaamLijst =
                    new ArrayList<BrpGroep<BrpSamengesteldeNaamInhoud>>();

            for (final GroepPersoonSamengesteldeNaam samengesteldenaam : persoon.getSamengesteldeNaam()) {

                final BrpPredikaatCode predikaatCode =
                        samengesteldenaam.getPredikaatCode() == null ? null : new BrpPredikaatCode(samengesteldenaam
                                .getPredikaatCode().getValue().toString());

                final BrpAdellijkeTitelCode adellijkeTitelCode =
                        samengesteldenaam.getAdellijkeTitelCode() == null ? null : new BrpAdellijkeTitelCode(
                                samengesteldenaam.getAdellijkeTitelCode().getValue().toString());

                final BrpSamengesteldeNaamInhoud samengesteldenaamInhoud =
                        new BrpSamengesteldeNaamInhoud(predikaatCode, samengesteldenaam.getVoornamen().getValue(),
                                samengesteldenaam.getVoorvoegsel().getValue(), samengesteldenaam.getScheidingsteken()
                                        .getValue().charAt(0), adellijkeTitelCode, samengesteldenaam
                                        .getGeslachtsnaam().getValue(), false, false);

                final BrpGroep<BrpSamengesteldeNaamInhoud> samengesteldenaamGroep =
                        new BrpGroep<BrpSamengesteldeNaamInhoud>(samengesteldenaamInhoud, historie, actie, null, null);
                samengesteldenaamLijst.add(samengesteldenaamGroep);
            }
            return new BrpStapel<BrpSamengesteldeNaamInhoud>(samengesteldenaamLijst);
        } else {
            return null;
        }
    }

    /**
     * Maakt een identificatienummers stapel op basis van de meegeleverde informatie aan.
     * 
     * @param persoon
     *            Het persoon type met informatie over de persoon.
     * @param historie
     *            De historie informatie.
     * @param actie
     *            De gekoppelde actie informatie.
     * @return De aangemaakte identificatienummers stapel.
     */
    private static BrpStapel<BrpIdentificatienummersInhoud> getIdentificatienummersStapel(
            final ObjecttypePersoon persoon,
            final BrpHistorie historie,
            final BrpActie actie) {

        if (persoon.getIdentificatienummers() != null && persoon.getIdentificatienummers().size() > 0) {

            final List<BrpGroep<BrpIdentificatienummersInhoud>> identificatienummersLijst =
                    new ArrayList<BrpGroep<BrpIdentificatienummersInhoud>>();

            for (final GroepPersoonIdentificatienummers identificatienummers : persoon.getIdentificatienummers()) {

                final BrpIdentificatienummersInhoud identificatienummersInhoud =
                        new BrpIdentificatienummersInhoud(null, Long.valueOf(identificatienummers
                                .getBurgerservicenummer().getValue()));

                final BrpGroep<BrpIdentificatienummersInhoud> identificatienummersGroep =
                        new BrpGroep<BrpIdentificatienummersInhoud>(identificatienummersInhoud, historie, actie,
                                null, null);
                identificatienummersLijst.add(identificatienummersGroep);
            }
            return new BrpStapel<BrpIdentificatienummersInhoud>(identificatienummersLijst);
        } else {
            return null;
        }
    }

    /**
     * Maakt een identificatienummers stapel op basis van de meegeleverde informatie aan.
     * 
     * @param persoon
     *            Het persoon type met informatie over de persoon.
     * @param historie
     *            De historie informatie.
     * @param actie
     *            De gekoppelde actie informatie.
     * @return De aangemaakte identificatienummers stapel.
     */
    private static BrpStapel<BrpNationaliteitInhoud> getNationaliteitStapel(
            final ObjecttypePersoon persoon,
            final BrpHistorie historie,
            final BrpActie actie) {

        if (persoon.getNationaliteiten() != null && persoon.getNationaliteiten().getNationaliteit() != null
                && persoon.getNationaliteiten().getNationaliteit().size() > 0) {

            final List<BrpGroep<BrpNationaliteitInhoud>> nationaliteitenLijst =
                    new ArrayList<BrpGroep<BrpNationaliteitInhoud>>();

            for (final ObjecttypePersoonNationaliteit nationaliteit : persoon.getNationaliteiten().getNationaliteit()) {

                final BrpNationaliteitInhoud nationaliteitInhoud =
                        new BrpNationaliteitInhoud(new BrpNationaliteitCode(Integer.valueOf(nationaliteit
                                .getNationaliteitNaam().getValue())), new BrpRedenVerkrijgingNederlandschapCode(
                                new BigDecimal(nationaliteit.getRedenVerkrijgingNaam().getValue())),
                                new BrpRedenVerliesNederlandschapCode(new BigDecimal(nationaliteit
                                        .getRedenVerliesNaam().getValue())));

                final BrpGroep<BrpNationaliteitInhoud> nationaliteitGroep =
                        new BrpGroep<BrpNationaliteitInhoud>(nationaliteitInhoud, historie, actie, null, null);
                nationaliteitenLijst.add(nationaliteitGroep);
            }
            return new BrpStapel<BrpNationaliteitInhoud>(nationaliteitenLijst);
        } else {
            return null;
        }
    }

    /**
     * Stelt de betrokkenheid samen op basis van de meegeleverde informatie.
     * 
     * @param ouder
     *            Het ouder object met daarin Ouder informatie.
     * @param historie
     *            De historie informatie.
     * @param actie
     *            De gekoppelde actie informatie.
     * @param ouderIdentificatienummersStapel
     *            De identificatienummers stapel van de ouder.
     * @param ouderGeboorteStapel
     *            De geboorte stapel van de ouder.
     * @param ouderSamengesteldeNaamStapel
     *            De samengesteldenaam stapel van de ouder.
     * @param ouderGeslachtsaanduidingStapel
     *            De geslachtsaanduiding stapel van de ouder.
     * @return De samengestelde betrokkenheid.
     */
    private static BrpBetrokkenheid getOuderBetrokkenheid(
            final ViewOuder ouder,
            final BrpHistorie historie,
            final BrpActie actie,
            final BrpStapel<BrpIdentificatienummersInhoud> ouderIdentificatienummersStapel,
            final BrpStapel<BrpGeboorteInhoud> ouderGeboorteStapel,
            final BrpStapel<BrpSamengesteldeNaamInhoud> ouderSamengesteldeNaamStapel,
            final BrpStapel<BrpGeslachtsaanduidingInhoud> ouderGeslachtsaanduidingStapel) {

        final GroepBetrokkenheidOuderschap betrokkenheidOuderschap = ouder.getOuderschap().get(0);

        final BrpDatum datumAanvang = new BrpDatum(betrokkenheidOuderschap.getDatumAanvang().getValue().intValue());
        Boolean indicatieAdresgevend = false;
        if (betrokkenheidOuderschap != null && betrokkenheidOuderschap.getIndicatieAdresgevend() != null) {
            indicatieAdresgevend =
                    JaS.J == betrokkenheidOuderschap.getIndicatieAdresgevend().getValue() ? true : false;
        }

        final BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel = null;

        final BrpOuderInhoud ouderInhoud = new BrpOuderInhoud(indicatieAdresgevend, datumAanvang);
        final BrpGroep<BrpOuderInhoud> ouderGroep =
                new BrpGroep<BrpOuderInhoud>(ouderInhoud, historie, actie, null, null);
        final List<BrpGroep<BrpOuderInhoud>> ouderLijst = new ArrayList<BrpGroep<BrpOuderInhoud>>();
        ouderLijst.add(ouderGroep);
        final BrpStapel<BrpOuderInhoud> ouderStapel = new BrpStapel<BrpOuderInhoud>(ouderLijst);

        final BrpBetrokkenheid betrokkenheid =
                new BrpBetrokkenheid(BrpSoortBetrokkenheidCode.OUDER, ouderIdentificatienummersStapel,
                        ouderGeslachtsaanduidingStapel, ouderGeboorteStapel, ouderlijkGezagStapel,
                        ouderSamengesteldeNaamStapel, ouderStapel);

        return betrokkenheid;
    }

    /**
     * Stelt de kind relatie samen op basis van de meegeleverde informatie.
     * 
     * @param kind
     *            Het kind object met daarin Ouder informatie.
     * @param historie
     *            De historie informatie.
     * @param actie
     *            De gekoppelde actie informatie.
     * @param kindGeboorteStapel
     *            De geboorte stapel van het kind.
     * @param kindGeslachtsaanduidingStapel
     *            De geslachtsaanduiding stapel van het kind.
     * @param ouder1Betrokkenheid
     *            De betrokkenheid van ouder 1.
     * @param ouder2Betrokkenheid
     *            De betrokkenheid van ouder 2.
     * @return De samengestelde kind relatie.
     */
    private static BrpRelatie getKindRelatie(
            final ObjecttypePersoon kind,
            final BrpHistorie historie,
            final BrpActie actie,
            final BrpStapel<BrpGeboorteInhoud> kindGeboorteStapel,
            final BrpStapel<BrpGeslachtsaanduidingInhoud> kindGeslachtsaanduidingStapel,
            final BrpBetrokkenheid ouder1Betrokkenheid,
            final BrpBetrokkenheid ouder2Betrokkenheid) {

        final List<BrpBetrokkenheid> betrokkenheden = new ArrayList<BrpBetrokkenheid>();
        if (ouder1Betrokkenheid != null) {
            betrokkenheden.add(ouder1Betrokkenheid);
        }
        if (ouder2Betrokkenheid != null) {
            betrokkenheden.add(ouder2Betrokkenheid);
        }

        return new BrpRelatie(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.KIND,
                betrokkenheden, null);

    }

    /**
     * Stelt op basis van het beschikbare verzoek type een BrpPersoonslijst op.
     * 
     * @param datumAanvangGeldigheid
     *            De aanvangsdatumgeldigheid van het verzoek.
     * @param tijdstipOntlening
     *            Het tijdstip ontlening van het verzoek.
     * @param bron
     *            De bron van het verzoek.
     * @param persoon
     *            Het persoon van het verzoek.
     * @param ouder1
     *            De ouder1 van het verzoek.
     * @param ouder2
     *            De ouder2 van het verzoek.
     * 
     * @return De op basis van het type samengestelde BRP persoonslijst.
     */
    public static BrpPersoonslijst converteerNaarBrpPersoonslijst(
            final Integer datumAanvangGeldigheid,
            final Long tijdstipOntlening,
            final ObjecttypeBron bron,
            final ObjecttypePersoon persoon,
            final ViewOuder ouder1,
            final ViewOuder ouder2) {

        // Parse Persoonslijst

        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        // Algemene gegevens.
        final BrpHistorie historie =
                new BrpHistorie(new BrpDatum(datumAanvangGeldigheid), null,
                        BrpDatumTijd.fromDatumTijd(tijdstipOntlening), null);

        final BrpActie actie =
                BrpPersoonslijstUtils.getActie(bron, historie, tijdstipOntlening, datumAanvangGeldigheid.longValue());

        /**
         * Persoon.
         */
        // Identificatienummer (Categorie 01)
        final BrpStapel<BrpIdentificatienummersInhoud> persoonIdentificatienummersInhoud =
                BrpPersoonslijstUtils.getIdentificatienummersStapel(persoon, historie, actie);

        // Naam (Categorie 02)
        final BrpStapel<BrpVoornaamInhoud> persoonVoornaamStapel =
                BrpPersoonslijstUtils.getVoorNaamStapel(persoon, historie, actie);

        final BrpStapel<BrpGeslachtsnaamcomponentInhoud> persoonGeslachtsnaamcomponentStapel =
                BrpPersoonslijstUtils.getGeslachtsnaamcomponentStapel(persoon, historie, actie);

        // Geboorte inhoud (Categorie 03)
        final BrpStapel<BrpGeboorteInhoud> persoonGeboorteStapel =
                BrpPersoonslijstUtils.getGeboorteStapel(persoon, historie, actie);

        // Geslachtnaam aanduiding (Categorie 04)
        final BrpStapel<BrpGeslachtsaanduidingInhoud> persoonGeslachtsaanduidingStapel =
                BrpPersoonslijstUtils.getGeslachtsaanduidingStapel(persoon, historie, actie);

        // Nationaliteit (Categorie 05)
        final BrpStapel<BrpNationaliteitInhoud> persoonNationaliteitStapel =
                BrpPersoonslijstUtils.getNationaliteitStapel(persoon, historie, actie);

        // Overlijden stapel (Categorie 08)
        final BrpStapel<BrpOverlijdenInhoud> persoonOverlijdenStapel =
                BrpPersoonslijstUtils.getOverlijdenStapel(persoon, historie, actie);

        // Aan BRP persoonslijst toevoegen.
        builder.identificatienummersStapel(persoonIdentificatienummersInhoud);
        builder.voornaamStapel(persoonVoornaamStapel);
        builder.geslachtsnaamcomponentStapel(persoonGeslachtsnaamcomponentStapel);
        builder.geboorteStapel(persoonGeboorteStapel);
        builder.nationaliteitStapel(persoonNationaliteitStapel);
        builder.overlijdenStapel(persoonOverlijdenStapel);
        builder.geslachtsaanduidingStapel(persoonGeslachtsaanduidingStapel);

        if (ouder1 != null) {
            final BrpBetrokkenheid ouder1Betrokkenheid =
                    vulOuderStapelsEnBetrokkenheden(ouder1, historie, actie, persoonGeboorteStapel,
                            persoonGeslachtsaanduidingStapel);

            if (ouder2 == null) {
                builder.relatie(BrpPersoonslijstUtils.getKindRelatie(persoon, historie, actie, persoonGeboorteStapel,
                        persoonGeslachtsaanduidingStapel, ouder1Betrokkenheid, null));
            } else {
                final BrpBetrokkenheid ouder2Betrokkenheid =
                        vulOuderStapelsEnBetrokkenheden(ouder2, historie, actie, persoonGeboorteStapel,
                                persoonGeslachtsaanduidingStapel);

                builder.relatie(BrpPersoonslijstUtils.getKindRelatie(persoon, historie, actie, persoonGeboorteStapel,
                        persoonGeslachtsaanduidingStapel, ouder1Betrokkenheid, ouder2Betrokkenheid));
            }
        }
        /**
         * Construeer BRP persoonslijst.
         */
        return builder.build();
    }

    /**
     * Vult op basis van de meegegeven oudergegevens de stapels en betrokkenheden voor de betreffende ouder voor in de
     * persoonslijst van de meegegeven persoon.
     * 
     * @param ouder
     *            Het meegegeven ouder object {@link ViewOuder}
     * @param historie
     *            De historie informatie.
     * @param actie
     *            De gekoppelde actie informatie.
     * @param persoonGeboorteStapel
     *            De geboorte stapel van de persoon.
     * @param persoonGeslachtsaanduidingStapel
     *            De geslachtsaanduiding stapel van de persoon.
     * @return
     */
    private static BrpBetrokkenheid vulOuderStapelsEnBetrokkenheden(
            final ViewOuder ouder,
            final BrpHistorie historie,
            final BrpActie actie,
            final BrpStapel<BrpGeboorteInhoud> persoonGeboorteStapel,
            final BrpStapel<BrpGeslachtsaanduidingInhoud> persoonGeslachtsaanduidingStapel) {

        BrpBetrokkenheid ouderBetrokkenheid = null;

        if (ouder != null) {

            final ObjecttypePersoon ouderPersoon = ouder.getPersoon();

            final BrpStapel<BrpIdentificatienummersInhoud> ouderIdentificatienummersStapel =
                    BrpPersoonslijstUtils.getIdentificatienummersStapel(ouderPersoon, historie, actie);

            // Naam (Categorie 02)
            final BrpStapel<BrpSamengesteldeNaamInhoud> ouderSamengesteldeNaamStapel =
                    BrpPersoonslijstUtils.getSamengesteldeNaamStapel(ouderPersoon, historie, actie);

            // Geboorte inhoud (Categorie 03)
            final BrpStapel<BrpGeboorteInhoud> ouderGeboorteStapel =
                    BrpPersoonslijstUtils.getGeboorteStapel(ouderPersoon, historie, actie);

            // Geslachtnaam aanduiding (Categorie 04)
            final BrpStapel<BrpGeslachtsaanduidingInhoud> ouderGeslachtsaanduidingStapel =
                    BrpPersoonslijstUtils.getGeslachtsaanduidingStapel(ouderPersoon, historie, actie);

            ouderBetrokkenheid =
                    BrpPersoonslijstUtils.getOuderBetrokkenheid(ouder, historie, actie,
                            ouderIdentificatienummersStapel, ouderGeboorteStapel, ouderSamengesteldeNaamStapel,
                            ouderGeslachtsaanduidingStapel);

        }
        return ouderBetrokkenheid;
    }
}
