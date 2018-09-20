/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteitHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapped BrpNationaliteitInhoud op de PersoonNationaliteit entity inclusief historie.
 */
public final class NationaliteitMapper
        extends AbstractHistorieMapperStrategie<BrpNationaliteitInhoud, PersoonNationaliteitHistorie, PersoonNationaliteit>
{

    /**
     * Maakt een NationaliteitMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper
     *            de mapper voor onderzoeken
     */
    public NationaliteitMapper(
        final DynamischeStamtabelRepository dynamischeStamtabelRepository,
        final BRPActieFactory brpActieFactory,
        final OnderzoekMapper onderzoekMapper)
    {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonNationaliteitHistorie historie, final PersoonNationaliteit entiteit) {
        entiteit.addPersoonNationaliteitHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonNationaliteitHistorie historie, final PersoonNationaliteit entiteit) {
        entiteit.setRedenVerkrijgingNLNationaliteit(historie.getRedenVerkrijgingNLNationaliteit());
        entiteit.setRedenVerliesNLNationaliteit(historie.getRedenVerliesNLNationaliteit());
        entiteit.setMigratieRedenOpnameNationaliteit(historie.getMigratieRedenOpnameNationaliteit());
        entiteit.setMigratieRedenBeeindigenNationaliteit(historie.getMigratieRedenBeeindigenNationaliteit());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonNationaliteitHistorie mapHistorischeGroep(final BrpNationaliteitInhoud groepInhoud, final PersoonNationaliteit entiteit) {
        final PersoonNationaliteitHistorie result = new PersoonNationaliteitHistorie(entiteit);

        final BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNlCode = groepInhoud.getRedenVerkrijgingNederlandschapCode();
        result.setRedenVerkrijgingNLNationaliteit(getStamtabelMapping().findRedenVerkrijgingNLNationaliteitByCode(redenVerkrijgingNlCode));
        final BrpRedenVerliesNederlandschapCode redenVerliesNlcode = groepInhoud.getRedenVerliesNederlandschapCode();
        result.setRedenVerliesNLNationaliteit(getStamtabelMapping().findRedenVerliesNLNationaliteitByCode(redenVerliesNlcode));

        final BrpDatum migratieDatum = groepInhoud.getMigratieDatum();
        if (migratieDatum != null) {
            result.setMigratieDatumEindeBijhouding(migratieDatum.getWaarde());
        }

        final BrpBoolean eindeBijhouding = groepInhoud.getEindeBijhouding();
        if (eindeBijhouding != null) {
            result.setIndicatieBijhoudingBeeindigd(eindeBijhouding.getWaarde());
        }

        final BrpString migratieRedenOpnameNationaliteit = groepInhoud.getMigratieRedenOpnameNationaliteit();
        if (migratieRedenOpnameNationaliteit != null) {
            result.setMigratieRedenOpnameNationaliteit(migratieRedenOpnameNationaliteit.getWaarde());
        }

        final BrpString migratieRedenBeeindigingNationaliteit = groepInhoud.getMigratieRedenBeeindigingNationaliteit();
        if (migratieRedenBeeindigingNationaliteit != null) {
            result.setMigratieRedenBeeindigenNationaliteit(migratieRedenBeeindigingNationaliteit.getWaarde());
        }

        getOnderzoekMapper().mapOnderzoek(result, redenVerkrijgingNlCode, Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE);
        getOnderzoekMapper().mapOnderzoek(result, redenVerliesNlcode, Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE);

        getOnderzoekMapper().mapOnderzoek(result, eindeBijhouding, Element.PERSOON_NATIONALITEIT_INDICATIEBIJHOUDINGBEEINDIGD);
        getOnderzoekMapper().mapOnderzoek(
            result,
            migratieRedenBeeindigingNationaliteit,
            Element.PERSOON_NATIONALITEIT_MIGRATIEREDENBEEINDIGENNATIONALITEIT);
        getOnderzoekMapper().mapOnderzoek(result, migratieRedenOpnameNationaliteit, Element.PERSOON_NATIONALITEIT_MIGRATIEREDENOPNAMENATIONALITEIT);
        getOnderzoekMapper().mapOnderzoek(result, migratieDatum, Element.PERSOON_NATIONALITEIT_MIGRATIEDATUMEINDEBIJHOUDING);
        getOnderzoekMapper().mapOnderzoek(entiteit, groepInhoud.getNationaliteitCode(), Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mapActueleGegevens(final BrpStapel<BrpNationaliteitInhoud> brpStapel, final PersoonNationaliteit entiteit) {
        final BrpNationaliteitInhoud inhoud = brpStapel.getLaatsteElement().getInhoud();
        final BrpNationaliteitCode brpNationaliteitCode = inhoud.getNationaliteitCode();

        entiteit.setNationaliteit(getStamtabelMapping().findNationaliteitByCode(brpNationaliteitCode));
        final BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNlCode = inhoud.getRedenVerkrijgingNederlandschapCode();
        entiteit.setRedenVerkrijgingNLNationaliteit(getStamtabelMapping().findRedenVerkrijgingNLNationaliteitByCode(redenVerkrijgingNlCode));
        final BrpRedenVerliesNederlandschapCode redenVerliesNlcode = inhoud.getRedenVerliesNederlandschapCode();
        entiteit.setRedenVerliesNLNationaliteit(getStamtabelMapping().findRedenVerliesNLNationaliteitByCode(redenVerliesNlcode));

        if (inhoud.getMigratieDatum() != null) {
            entiteit.setMigratieDatumEindeBijhouding(inhoud.getMigratieDatum().getWaarde());
        }

        if (inhoud.getEindeBijhouding() != null) {
            entiteit.setIndicatieBijhoudingBeeindigd(inhoud.getEindeBijhouding().getWaarde());
        }

        if (inhoud.getMigratieRedenOpnameNationaliteit() != null) {
            entiteit.setMigratieRedenOpnameNationaliteit(inhoud.getMigratieRedenOpnameNationaliteit().getWaarde());
        }
        if (inhoud.getMigratieRedenBeeindigingNationaliteit() != null) {
            entiteit.setMigratieRedenBeeindigenNationaliteit(inhoud.getMigratieRedenBeeindigingNationaliteit().getWaarde());
        }
    }
}
