/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Basis voor omzetten categorieen die geen materiele historie hebben.
 * 
 * @param <L>
 *            Lo3 categorie inhoud type
 */
@Component
public abstract class BrpImmaterieleCategorienConverteerder<L extends Lo3CategorieInhoud> {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Converteer.
     * 
     * @param brpStapels
     *            brp stapels
     * @return lo3 stapel
     */
    public final Lo3Stapel<L> converteer(final BrpStapel<? extends BrpGroepInhoud>... brpStapels) {
        LOG.debug("converteer(#brpStapel={})", brpStapels.length);
        final List<BrpGroep<? extends BrpGroepInhoud>> groepen = new ArrayList<BrpGroep<? extends BrpGroepInhoud>>();
        for (final BrpStapel<? extends BrpGroepInhoud> brpStapel : brpStapels) {
            final BrpGroep<? extends BrpGroepInhoud> actueleGroep = bepaalActueleGroep(brpStapel);

            if (actueleGroep != null) {
                groepen.add(actueleGroep);
            }
        }
        LOG.debug("actuele #groepen: {}", groepen.size());
        if (groepen.isEmpty()) {
            return null;
        }

        final L lo3Inhoud = bepaalInhoud(groepen);

        final Lo3Documentatie documentatie = bepaalDocumentatie(groepen);

        final Lo3Historie historie = bepaalHistorie(groepen);

        final Lo3Categorie<L> categorie = new Lo3Categorie<L>(lo3Inhoud, documentatie, historie, null);
        final List<Lo3Categorie<L>> categorieen = new ArrayList<Lo3Categorie<L>>();
        categorieen.add(categorie);

        return new Lo3Stapel<L>(categorieen);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private L bepaalInhoud(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        LOG.debug("bepaalInhoud(#groepen={})", groepen.size());
        L inhoud = null;

        for (final BrpGroep<? extends BrpGroepInhoud> groep : groepen) {
            inhoud = verwerkGroep(inhoud, groep);
        }
        return inhoud;
    }

    private <T extends BrpGroepInhoud> L verwerkGroep(final L beginInhoud, final BrpGroep<T> groep) {
        if (groep == null) {
            return beginInhoud;
        }
        final BrpGroepConverteerder<T, L> converteerder = bepaalConverteerder(groep.getInhoud());
        // vorigeInhoud is hier niet nodig, aangezien dit alleen nodig is voor Nationaliteit (Nationaliteit is geen
        // immateriele categorie)
        return converteerder.vulInhoud(beginInhoud == null ? converteerder.maakNieuweInhoud() : beginInhoud,
                groep.getInhoud(), null);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Bepaal de meeste recente (datum registratie), niet beeindige en niet vervallen groep uit een stapel.
     * 
     * @param brpStapel
     *            stapel
     * @return meest recente, niet vervallen groep
     */
    private static <T extends BrpGroepInhoud> BrpGroep<T> bepaalActueleGroep(final BrpStapel<T> brpStapel) {
        BrpGroep<T> recentste = null;

        if (brpStapel != null) {
            for (final BrpGroep<T> groep : brpStapel) {
                if (groep.getHistorie().getDatumTijdVerval() != null) {
                    continue;
                }
                if (groep.getHistorie().getDatumEindeGeldigheid() != null) {
                    continue;
                }

                if (recentste == null
                        || groep.getHistorie().getDatumTijdRegistratie()
                                .compareTo(recentste.getHistorie().getDatumTijdRegistratie()) > 0) {
                    recentste = groep;
                }
            }
        }

        return recentste;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Bepaal de historie om toe te voegen.
     * 
     * @param groepen
     *            meeste recente niet vervallen groepen
     * @return documentatie
     */
    protected abstract Lo3Historie bepaalHistorie(List<BrpGroep<? extends BrpGroepInhoud>> groepen);

    /**
     * Bepaal de documentatie om toe te voegen.
     * 
     * @param groepen
     *            meeste recente niet vervallen groepen
     * @return documentatie
     */
    protected abstract Lo3Documentatie bepaalDocumentatie(List<BrpGroep<? extends BrpGroepInhoud>> groepen);

    /**
     * Bepaal de BrpGroepConverteerder voor een bepaalde inhoud. Wordt 1 keer aangeroepen per stapel.
     * 
     * @param <B>
     *            brp groep inhoud type
     * @param inhoud
     *            inhoud
     * @return brp groep converteerder
     */
    protected abstract <B extends BrpGroepInhoud> BrpGroepConverteerder<B, L> bepaalConverteerder(B inhoud);

}
