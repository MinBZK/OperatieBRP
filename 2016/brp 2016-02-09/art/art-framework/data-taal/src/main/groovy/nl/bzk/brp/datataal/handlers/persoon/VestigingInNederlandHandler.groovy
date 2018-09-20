package nl.bzk.brp.datataal.handlers.persoon
import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverCodeAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PartnerHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder
/**
 *
 */
class VestigingInNederlandHandler extends AbstractGebeurtenisHandler implements WijzigNamenAbility {
    final ActieModel             vestigingActie = maakActie(SoortActie.REGISTRATIE_MIGRATIE)
    final PersoonHisVolledigImpl nietIngeschrevene

    VestigingInNederlandHandler(final GebeurtenisAttributen attr, final PersoonHisVolledigImplBuilder b, final PersoonHisVolledigImpl persoon) {
        super(attr, b)

        nietIngeschrevene = persoon
        assert !persoon.isIngeschrevene() : 'Alleen personen waarvan de soort geen INGESCHREVENE is kunnen zich vestigen'
    }

    @Override
    void startGebeurtenis() {
        builder.hisVolledigImpl.persoonAfgeleidAdministratiefHistorie.
            voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, vestigingActie, 1))

        // bijhouding voorkomen
        builder.nieuwBijhoudingRecord(vestigingActie).bijhoudingsaard(Bijhoudingsaard.INGEZETENE)
            .nadereBijhoudingsaard(NadereBijhoudingsaard.ACTUEEL)
            .bijhoudingspartij(vestigingActie.partij.waarde)
            .indicatieOnverwerktDocumentAanwezig(Boolean.FALSE)
            .eindeRecord()

        builder.nieuwMigratieRecord(vestigingActie)
            .aangeverMigratie(referentieData.vindAangeverAdreshoudingOpCode(new AangeverCodeAttribuut('I')))
            .redenWijzigingMigratie(referentieData.vindRedenWijzingVerblijfOpCode(new RedenWijzigingVerblijfCodeAttribuut('A')))
            .landGebiedMigratie(referentieData.nederland)
            .soortMigratie(SoortMigratie.IMMIGRATIE)
            .eindeRecord()

        builder.nieuwInschrijvingRecord(vestigingActie)
            .versienummer(1L)
            .datumInschrijving(vestigingActie.datumAanvangGeldigheid)
            .datumtijdstempel(vestigingActie.tijdstipRegistratie)
            .eindeRecord()

        neemGegegevensOver()
    }

    private void neemGegegevensOver() {
        // geslachtsaanduiding
        def gesl = nietIngeschrevene.persoonGeslachtsaanduidingHistorie.actueleRecord?.geslachtsaanduiding?.waarde
        if (gesl) {
            builder.nieuwGeslachtsaanduidingRecord(vestigingActie).geslachtsaanduiding(gesl).eindeRecord()
        }

        // geboorte
        def geboorteRecord = builder.nieuwGeboorteRecord(vestigingActie)
        def geboorte = nietIngeschrevene.persoonGeboorteHistorie.actueleRecord
        if (geboorte) {
            geboorteRecord.with {
                if (geboorte.datumGeboorte) { datumGeboorte(geboorte.datumGeboorte.waarde)}
                if (geboorte.gemeenteGeboorte) { gemeenteGeboorte(geboorte.gemeenteGeboorte.waarde)}
                if (geboorte.woonplaatsnaamGeboorte) { woonplaatsnaamGeboorte(geboorte.woonplaatsnaamGeboorte.waarde)}
                if (geboorte.omschrijvingLocatieGeboorte) { omschrijvingLocatieGeboorte(geboorte.omschrijvingLocatieGeboorte.waarde)}
                if (geboorte.landGebiedGeboorte) { landGebiedGeboorte(geboorte.landGebiedGeboorte.waarde)}
                if (geboorte.buitenlandsePlaatsGeboorte) { buitenlandsePlaatsGeboorte(geboorte.buitenlandsePlaatsGeboorte.waarde)}
                if (geboorte.buitenlandseRegioGeboorte) { buitenlandseRegioGeboorte(geboorte.buitenlandseRegioGeboorte.waarde)}
            }
        }
        geboorteRecord.eindeRecord()
    }

    @Override
    void eindeGebeurtenis() {
        if (builder.hisVolledigImpl.persoonSamengesteldeNaamHistorie.aantal == 0) {
            // samengestelde naam
            def naamRecord = builder.nieuwSamengesteldeNaamRecord(vestigingActie)
            def naam = nietIngeschrevene.persoonSamengesteldeNaamHistorie.actueleRecord
            if(naam) {
                naamRecord.with {
                    if (naam.adellijkeTitel) { adellijkeTitel(naam.adellijkeTitel.waarde.code)}
                    if (naam.predicaat) { predicaat(naam.predicaat.waarde.code)}
                    if (naam.geslachtsnaamstam) { geslachtsnaamstam(naam.geslachtsnaamstam.waarde)}
                    if (naam.voornamen) { voornamen(naam.voornamen.waarde)}
                    if (naam.scheidingsteken) { scheidingsteken(naam.scheidingsteken.waarde)}
                    indicatieAfgeleid(Boolean.TRUE)
                    indicatieNamenreeks(Boolean.FALSE)
                }
            }
            naamRecord.eindeRecord()

            GegevensAfleider.leidNaamgebruikAf(builder, vestigingActie)
        }

        nietIngeschrevene.with {
            persoonGeboorteHistorie.verval(vestigingActie, vestigingActie.tijdstipRegistratie)
            persoonSamengesteldeNaamHistorie.beeindig(vestigingActie, vestigingActie)
            persoonGeslachtsaanduidingHistorie.beeindig(vestigingActie, vestigingActie)
        }
    }

    def identificatienummers(Map<String, ? extends Number> nums) {
        def actie = maakActie(SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS)

        assert nums.keySet().sort() == ['anummer', 'bsn'] : 'anummer en bsn zijn verplicht'

        def anummer = nums.anummer as long
        def bsn = nums.bsn as int
        builder.nieuwIdentificatienummersRecord(actie).burgerservicenummer(bsn).administratienummer(anummer).eindeRecord()
    }

    def nationaliteiten(String... nats) {
        def actie = maakActie(SoortActie.REGISTRATIE_NATIONALITEIT)

        nats.each {
            def natBuilder = new PersoonNationaliteitHisVolledigImplBuilder(builder.hisVolledigImpl, referentieData.vindNationaliteitOpNaam(it))
            natBuilder.nieuwStandaardRecord(actie).eindeRecord()
            builder.voegPersoonNationaliteitToe(natBuilder.build())
        }
    }

    def ouderVan(PersoonHisVolledigImpl... personen) {
        personen.each {kindPersoon ->
            def familie = kindPersoon.kindBetrokkenheid.relatie
            OuderHisVolledigImpl oudeBetr = familie.ouderBetrokkenheden.find {
                it?.persoon?.ID == nietIngeschrevene.ID
            }

            if (oudeBetr) {
                kindPersoon.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(kindPersoon, vestigingActie, 2))

                oudeBetr.betrokkenheidHistorie.verval(vestigingActie, vestigingActie.tijdstipRegistratie)

                def ouderOuderschapHistorie = oudeBetr.ouderOuderschapHistorie.actueleRecord
                def ouderlijkGezagHistorie = oudeBetr.ouderOuderlijkGezagHistorie.actueleRecord

                def ouderBetrBuilder = new OuderHisVolledigImplBuilder(familie, builder.hisVolledigImpl)
                    .metVerantwoording(vestigingActie)

                if (ouderOuderschapHistorie) {
                    oudeBetr.ouderOuderschapHistorie.vervalActueleRecords(vestigingActie, vestigingActie.datumTijdVerval)
                    if (oudeBetr.ouderOuderschapHistorie?.actueleRecord?.indicatieOuder?.waarde) {
                        ouderBetrBuilder.nieuwOuderschapRecord(vestigingActie)
                                .indicatieOuder(Ja.J)
                                .eindeRecord()
                    }
                }

                if (ouderlijkGezagHistorie) {
                    oudeBetr.ouderOuderlijkGezagHistorie.vervalActueleRecords(vestigingActie, vestigingActie.datumTijdVerval)
                    if (ouderlijkGezagHistorie?.indicatieOuderHeeftGezag) {
                        ouderBetrBuilder.nieuwOuderlijkGezagRecord(vestigingActie)
                                .indicatieOuderHeeftGezag(Ja.J)
                                .eindeRecord()
                    }
                }

                ouderBetrBuilder.build()
            }
        }
    }

    def partnerVan(PersoonHisVolledigImpl partnerPersoon) {
        def huwelijk = partnerPersoon.partnerBetrokkenheden.find {
            it.relatie.relatieHistorie.heeftActueelRecord() && it.relatie.betrokkenheden.any { p -> p?.persoon?.ID == partnerPersoon.ID }
        }?.relatie
        PartnerHisVolledigImpl oudeBetr = huwelijk?.betrokkenheden?.find { PartnerHisVolledigImpl it ->
            it?.persoon?.ID == nietIngeschrevene.ID
        }

        if (oudeBetr) {
            partnerPersoon.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(partnerPersoon, vestigingActie, 2))

            oudeBetr.betrokkenheidHistorie.verval(vestigingActie, vestigingActie.tijdstipRegistratie)

            new PartnerHisVolledigImplBuilder(huwelijk, builder.hisVolledigImpl)
                .metVerantwoording(vestigingActie)
                .build()
        }
    }
}
