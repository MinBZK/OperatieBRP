/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIndicatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Een mapper voor een BRP indicatie die een van de Indicatie stapels uit het migratie model mapped op PersoonIndicatie.
 * 
 * @param <T>
 *            het type indicatie die hier gemapped wordt
 */
public final class PersoonIndicatieMapper<T extends AbstractBrpIndicatieGroepInhoud>
        extends AbstractHistorieMapperStrategie<T, PersoonIndicatieHistorie, PersoonIndicatie>
{

    /**
     * Maakt een PersoonIndicatieMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper
     *            de mapper voor onderzoeken
     */
    public PersoonIndicatieMapper(
        final DynamischeStamtabelRepository dynamischeStamtabelRepository,
        final BRPActieFactory brpActieFactory,
        final OnderzoekMapper onderzoekMapper)
    {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * Converteer AbstractBrpIndicatieGroepInhoud class naar SortIndicate.
     *
     * @param brpIndicatieClass
     *            class
     * @return SortIndicatie
     */
    static SoortIndicatie mapBrpClassOpIndicatie(final Class<? extends AbstractBrpIndicatieGroepInhoud> brpIndicatieClass) {
        final SoortIndicatie result;
        if (BrpBehandeldAlsNederlanderIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.BEHANDELD_ALS_NEDERLANDER;
        } else if (BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT;
        } else if (BrpDerdeHeeftGezagIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.DERDE_HEEFT_GEZAG;
        } else if (BrpOnderCurateleIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.ONDER_CURATELE;
        } else if (BrpStaatloosIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.STAATLOOS;
        } else if (BrpVerstrekkingsbeperkingIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING;
        } else if (BrpVastgesteldNietNederlanderIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER;
        } else if (BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE;
        } else {
            throw new IllegalStateException("Onbekende indicatie stapel: " + brpIndicatieClass);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonIndicatieHistorie historie, final PersoonIndicatie entiteit) {
        entiteit.addPersoonIndicatieHistorie(historie);
        entiteit.setMigratieRedenBeeindigenNationaliteit(historie.getMigratieRedenBeeindigenNationaliteit());
        entiteit.setMigratieRedenOpnameNationaliteit(historie.getMigratieRedenOpnameNationaliteit());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonIndicatieHistorie historie, final PersoonIndicatie entiteit) {
        entiteit.setWaarde(historie.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonIndicatieHistorie mapHistorischeGroep(final T groepInhoud, final PersoonIndicatie entiteit) {
        final PersoonIndicatieHistorie result = new PersoonIndicatieHistorie(entiteit, Boolean.TRUE.equals(groepInhoud.heeftIndicatie()));

        final BrpString redenVerkrijgingNlCode = groepInhoud.getMigratieRedenOpnameNationaliteit();
        if (redenVerkrijgingNlCode != null) {
            result.setMigratieRedenOpnameNationaliteit(redenVerkrijgingNlCode.getWaarde());
        }
        final BrpString redenVerliesNlcode = groepInhoud.getMigratieRedenBeeindigingNationaliteit();
        if (redenVerliesNlcode != null) {
            result.setMigratieRedenBeeindigenNationaliteit(redenVerliesNlcode.getWaarde());
        }
        getOnderzoekMapper().mapOnderzoek(
            result,
            groepInhoud.getMigratieRedenOpnameNationaliteit(),
            Element.PERSOON_INDICATIE_MIGRATIEREDENOPNAMENATIONALITEIT);
        getOnderzoekMapper().mapOnderzoek(
            result,
            groepInhoud.getMigratieRedenBeeindigingNationaliteit(),
            Element.PERSOON_INDICATIE_MIGRATIEREDENBEEINDIGENNATIONALITEIT);

        // onderzoek
        switch (entiteit.getSoortIndicatie()) {
            case BEHANDELD_ALS_NEDERLANDER:
                getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER);
                break;
            case SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT:
                getOnderzoekMapper().mapOnderzoek(
                    result,
                    groepInhoud.getIndicatie(),
                    Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT);
                break;
            case DERDE_HEEFT_GEZAG:
                getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG);
                break;
            case ONDER_CURATELE:
                getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_ONDERCURATELE);
                break;
            case STAATLOOS:
                getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_STAATLOOS);
                break;
            case VOLLEDIGE_VERSTREKKINGSBEPERKING:
                getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING);
                break;
            case VASTGESTELD_NIET_NEDERLANDER:
                getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER);
                break;
            case BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE:
                getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE);
                break;
            default:
                throw new IllegalStateException("Onbekend soort indicatie");
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mapActueleGegevens(final BrpStapel<T> brpStapel, final PersoonIndicatie entiteit) {
        entiteit.setSoortIndicatie(PersoonIndicatieMapper.mapBrpClassOpIndicatie(brpStapel.getLaatsteElement().getInhoud().getClass()));
    }
}
