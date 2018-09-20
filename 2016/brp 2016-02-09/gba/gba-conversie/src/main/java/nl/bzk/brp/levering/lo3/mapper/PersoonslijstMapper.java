/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import org.springframework.stereotype.Component;

/**
 * Mapt een PersoonHisVolledig naar een BrpPersoonslijst.
 */
@Component
public final class PersoonslijstMapper {

    @Inject
    private AdresMapper adresMapper;
    @Inject
    private BehandeldAlsNederlanderIndicatieMapper behandeldAlsNederlanderIndicatieMapper;
    @Inject
    private BijhoudingMapper bijhoudingMapper;
    @Inject
    private BijzondereVerblijfsrechtelijkePositieIndicatieMapper bijzondereVerblijfsrechtelijkePositieIndicatieMapper;
    @Inject
    private BrpIstGezagsverhoudingMapper istGezagsverhoudingMapper;
    @Inject
    private BrpIstHuwelijkOfGpMapper istHuwelijkOfGpMapper;
    @Inject
    private BrpIstKindMapper istKindMapper;
    @Inject
    private BrpIstOuder1Mapper istOuder1Mapper;
    @Inject
    private BrpIstOuder2Mapper istOuder2Mapper;
    @Inject
    private DeelnameEuVerkiezingenMapper deelnameEuVerkiezingenMapper;
    @Inject
    private DerdeHeeftGezagIndicatieMapper derdeHeeftGezagIndicatieMapper;
    @Inject
    private PersoonGeboorteMapper geboorteMapper;
    @Inject
    private PersoonGeslachtsaanduidingMapper geslachtsaanduidingMapper;
    @Inject
    private GeslachtsnaamcomponentenMapper geslachtsnaamcomponentenMapper;
    @Inject
    private PersoonIdentificatieNummersMapper identificatienummersMapper;
    @Inject
    private InschrijvingMapper inschrijvingMapper;
    @Inject
    private MigratieMapper migratieMapper;
    @Inject
    private NaamgebruikMapper naamgebruikMapper;
    @Inject
    private NationaliteitenMapper nationaliteitenMapper;
    @Inject
    private NummerverwijzingMapper nummerverwijzingMapper;
    @Inject
    private OnderCurateleIndicatieMapper onderCurateleIndicatieMapper;
    @Inject
    private OverlijdenMapper overlijdenMapper;
    @Inject
    private PersoonAfgeleidAdministratiefMapper persoonAfgeleidAdministratiefMapper;
    @Inject
    private PersoonskaartHistorieMapper persoonskaartHistorieMapper;
    @Inject
    private ReisdocumentenMapper reisdocumentenMapper;
    @Inject
    private PersoonSamengesteldeNaamMapper samengesteldeNaamMapper;
    @Inject
    private SignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper signaleringMetBetrekkingTotVerstrekkenReisdocumentMapper;
    @Inject
    private StaatloosIndicatieMapper staatloosIndicatieMapper;
    @Inject
    private UitsluitingKiesrechtMapper uitsluitingKiesrechtMapper;
    @Inject
    private VastgesteldNietNederlanderIndicatieMapper vastgesteldNietNederlanderIndicatieMapper;
    @Inject
    private VerblijfsrechtMapper verblijfsrechtMapper;
    @Inject
    private VerificatiesMapper verificatiesMapper;
    @Inject
    private VerstrekkingsbeperkingIndicatieMapper verstrekkingsbeperkingIndicatieMapper;
    @Inject
    private VoornamenMapper voornamenMapper;

    /**
     * Map de objecten.
     *
     * @param volledig
     *            De PersoonHisVolledig, welke omgezet moet worden naar BrpPersoonslijst.
     * @param istStapels
     *            De IST stapels
     * @return BrpPersoonslijst het gemapte object.
     */
    public BrpPersoonslijst map(final PersoonHisVolledig volledig, final Set<Stapel> istStapels) {
        // Builder
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapper(volledig);
        final ActieHisVolledigLocator actieHisVolledigLocator = new ActieHisVolledigLocatorImpl(volledig);

        // Persoonsgegevens
        builder.adresStapel(adresMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.behandeldAlsNederlanderIndicatieStapel(behandeldAlsNederlanderIndicatieMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.bijhoudingStapel(bijhoudingMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.bijzondereVerblijfsrechtelijkePositieIndicatieStapel(
            bijzondereVerblijfsrechtelijkePositieIndicatieMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.deelnameEuVerkiezingenStapel(deelnameEuVerkiezingenMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.derdeHeeftGezagIndicatieStapel(derdeHeeftGezagIndicatieMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.geboorteStapel(geboorteMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.geslachtsaanduidingStapel(geslachtsaanduidingMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.geslachtsnaamcomponentStapels(geslachtsnaamcomponentenMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.identificatienummersStapel(identificatienummersMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.inschrijvingStapel(inschrijvingMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.migratieStapel(migratieMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.naamgebruikStapel(naamgebruikMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.nationaliteitStapels(nationaliteitenMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.nummerverwijzingStapel(nummerverwijzingMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.onderCurateleIndicatieStapel(onderCurateleIndicatieMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.overlijdenStapel(overlijdenMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.persoonAfgeleidAdministratiefStapel(persoonAfgeleidAdministratiefMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.persoonskaartStapel(persoonskaartHistorieMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.reisdocumentStapels(reisdocumentenMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.samengesteldeNaamStapel(samengesteldeNaamMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(
            signaleringMetBetrekkingTotVerstrekkenReisdocumentMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.staatloosIndicatieStapel(staatloosIndicatieMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.uitsluitingKiesrechtStapel(uitsluitingKiesrechtMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.vastgesteldNietNederlanderIndicatieStapel(
            vastgesteldNietNederlanderIndicatieMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.verblijfsrechtStapel(verblijfsrechtMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.verificatieStapels(verificatiesMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.verstrekkingsbeperkingIndicatieStapel(verstrekkingsbeperkingIndicatieMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));
        builder.voornaamStapels(voornamenMapper.map(volledig, onderzoekMapper, actieHisVolledigLocator));

        // TODO: Relaties (BOP Stap 4.3)
        // builder.relaties(relatiesMapper.map(volledig, onderzoekMapper));

        // IST
        if (istStapels.size() > 0) {

            builder.istGezagsverhoudingStapel(istGezagsverhoudingMapper.map(istStapels));
            builder.istHuwelijkOfGpStapels(istHuwelijkOfGpMapper.map(istStapels));
            builder.istKindStapels(istKindMapper.map(istStapels));
            builder.istOuder1Stapel(istOuder1Mapper.map(istStapels));
            builder.istOuder2Stapel(istOuder2Mapper.map(istStapels));
        }

        // Build
        return builder.build();
    }

}
