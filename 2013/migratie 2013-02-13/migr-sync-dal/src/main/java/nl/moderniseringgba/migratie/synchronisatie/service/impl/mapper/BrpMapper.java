/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BRPActie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Document;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.DocumentHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.FormeleHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MaterieleHistorie;

/**
 * Basis mapper.
 * 
 * @param <T>
 *            BRP database type
 * @param <G>
 *            BRP conversiemodel type
 */
public abstract class BrpMapper<T extends FormeleHistorie, G extends BrpGroepInhoud> {

    /**
     * Map de BRP database set naar het BRP conversiemodel.
     * 
     * @param historieSet
     *            BRP database set
     * @return BRP conversiemodel stapel
     */
    public final BrpStapel<G> map(final Set<T> historieSet) {
        if (historieSet == null || historieSet.isEmpty()) {
            return null;
        }

        final List<BrpGroep<G>> result = new ArrayList<BrpGroep<G>>();

        for (final T historie : historieSet) {
            result.add(map(historie));
        }

        return new BrpStapel<G>(result);
    }

    private BrpGroep<G> map(final T historie) {
        final G inhoud = mapInhoud(historie);
        final BrpHistorie hist = mapHistorie(historie);
        final BrpActie actieInhoud = mapActie(historie.getActieInhoud());
        final BrpActie actieVerval = mapActie(historie.getActieVerval());
        final BrpActie actieGeldigheid;
        if (historie instanceof MaterieleHistorie) {
            actieGeldigheid = mapActie(((MaterieleHistorie) historie).getActieAanpassingGeldigheid());
        } else {
            actieGeldigheid = null;
        }

        return new BrpGroep<G>(inhoud, hist, actieInhoud, actieVerval, actieGeldigheid);
    }

    /**
     * Map de inhoud van een historie naar een BRP conversiemodel groep.
     * 
     * @param historie
     *            historie
     * @return BRP conversiemodel groep
     */
    protected abstract G mapInhoud(final T historie);

    private static BrpHistorie mapHistorie(final FormeleHistorie historie) {
        final BrpDatum aanvang;
        final BrpDatum einde;
        if (historie instanceof MaterieleHistorie) {
            final MaterieleHistorie matHist = (MaterieleHistorie) historie;
            aanvang = BrpMapperUtil.mapDatum(matHist.getDatumAanvangGeldigheid());
            einde = BrpMapperUtil.mapDatum(matHist.getDatumEindeGeldigheid());
        } else {
            aanvang = null;
            einde = null;
        }

        final BrpDatumTijd registratie = BrpMapperUtil.mapBrpDatumTijd(historie.getDatumTijdRegistratie());
        final BrpDatumTijd verval = BrpMapperUtil.mapBrpDatumTijd(historie.getDatumTijdVerval());

        return new BrpHistorie(aanvang, einde, registratie, verval);

    }

    /**
     * Map een BRP database actie naar een BRP conversiemodel actie.
     * 
     * @param actie
     *            BRP database actie
     * @return BRP conversiemodel actie
     */
    public static BrpActie mapActie(final BRPActie actie) {
        if (actie == null) {
            return null;
        }

        // @formatter:off
        return new BrpActie(
                actie.getId(), 
                BrpMapperUtil.mapBrpSoortActieCode(actie.getSoortActie()), 
                BrpMapperUtil.mapBrpPartijCode(actie.getPartij()), 
                BrpMapperUtil.mapBrpverdragCode(actie.getVerdrag()), 
                BrpMapperUtil.mapBrpDatumTijd(actie.getDatumTijdOntlening()), 
                BrpMapperUtil.mapBrpDatumTijd(actie.getDatumTijdRegistratie()),
                mapDocumenten(actie.getDocumentSet()),
                0,
                BrpMapper.mapLo3Herkomst(actie.getLo3Herkomst())
            );
        // @formatter:on
    }

    private static Lo3Herkomst mapLo3Herkomst(
            final nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.Lo3Herkomst lo3HerkomstEntity) {
        if (lo3HerkomstEntity == null) {
            return null;
        }
        return new Lo3Herkomst(Lo3CategorieEnum.valueOfCategorie(lo3HerkomstEntity.getCategorie()),
                lo3HerkomstEntity.getStapel(), lo3HerkomstEntity.getVoorkomen());
    }

    private static List<BrpStapel<BrpDocumentInhoud>> mapDocumenten(final Set<Document> documentSet) {
        if (documentSet == null || documentSet.isEmpty()) {
            return null;
        }

        final List<BrpStapel<BrpDocumentInhoud>> result = new ArrayList<BrpStapel<BrpDocumentInhoud>>();

        for (final Document document : documentSet) {
            result.add(mapDocument(document));
        }

        return result;
    }

    private static BrpStapel<BrpDocumentInhoud> mapDocument(final Document document) {
        final List<BrpGroep<BrpDocumentInhoud>> result = new ArrayList<BrpGroep<BrpDocumentInhoud>>();

        for (final DocumentHistorie documentHistorie : document.getDocumentHistorieSet()) {
            result.add(mapDocumentHistorie(documentHistorie));
        }

        return new BrpStapel<BrpDocumentInhoud>(result);
    }

    private static BrpGroep<BrpDocumentInhoud> mapDocumentHistorie(final DocumentHistorie document) {
        // @formatter:off
        final BrpDocumentInhoud inhoud = new BrpDocumentInhoud(
                BrpMapperUtil.mapBrpSoortDocumentCode(document.getDocument().getSoortDocument()), 
                document.getIdentificatie(), 
                document.getAktenummer(), 
                document.getOmschrijving(), 
                BrpMapperUtil.mapBrpPartijCode(document.getPartij())
            );
                
        // @formatter:on

        return new BrpGroep<BrpDocumentInhoud>(inhoud, mapHistorie(document), null, null, null);
    }
}
