/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

/**
 * Basis voor omzetten categorieen die geen materiele historie hebben.
 * @param <L> Lo3 categorie inhoud type
 */
public abstract class AbstractBrpImmaterieleCategorienConverteerder<L extends Lo3CategorieInhoud> implements BrpImmaterieleCategorienConverteerder<L> {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Converteer.
     * @param brpStapels brp stapels
     * @return lo3 stapel
     */
    @Override
    @SafeVarargs
    public final Lo3Stapel<L> converteer(final BrpStapel<? extends BrpGroepInhoud>... brpStapels) {
        LOG.debug("converteer(#brpStapel={})", brpStapels.length);
        final List<BrpGroep<? extends BrpGroepInhoud>> groepen = new ArrayList<>();
        for (final BrpStapel<? extends BrpGroepInhoud> brpStapel : brpStapels) {
            final BrpGroep<? extends BrpGroepInhoud> converteerGroep = bepaalGroep(brpStapel);

            if (converteerGroep != null) {
                groepen.add(converteerGroep);
            }
        }

        LOG.debug("actuele #groepen: {}", groepen.size());
        if (groepen.isEmpty()) {
            return null;
        }

        final L lo3Inhoud = bepaalInhoud(groepen);
        final Lo3Documentatie documentatie = bepaalDocumentatie(groepen);
        final Lo3Historie historie = bepaalHistorie(groepen);
        final Lo3Herkomst herkomst = bepaalHerkomst(groepen);

        final Lo3Categorie<L> categorie = new Lo3Categorie<>(lo3Inhoud, documentatie, historie, herkomst);
        final List<Lo3Categorie<L>> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        return new Lo3Stapel<>(categorieen);
    }

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
        return converteerder.vulInhoud(beginInhoud == null ? converteerder.maakNieuweInhoud() : beginInhoud, groep.getInhoud(), null);
    }

    /**
     * Bepaal de meeste recente (datum registratie), niet beeindige en niet vervallen groep uit een stapel.
     * @param <T> BrpGroepInhoud
     * @param brpStapel stapel
     * @return meest recente, niet vervallen groep
     */
    protected static <T extends BrpGroepInhoud> BrpGroep<T> bepaalActueleGroep(final BrpStapel<T> brpStapel) {
        BrpGroep<T> recentste = null;

        if (brpStapel != null) {
            for (final BrpGroep<T> groep : brpStapel) {
                if (groep.getHistorie().getDatumTijdVerval() != null || groep.getHistorie().getDatumEindeGeldigheid() != null) {
                    continue;
                }

                if (recentste == null || groep.getHistorie().getDatumTijdRegistratie().compareTo(recentste.getHistorie().getDatumTijdRegistratie()) > 0) {
                    recentste = groep;
                }
            }
        }

        return recentste;
    }

    /**
     * Bepaal de te converteren groep. In de meeste gevallen zal dit de actuele zijn. Maar voor bijvoorbeeld bijhouding
     * geld dit weer niet.
     * @param <T> BrpGroepInhoud
     * @param brpStapel stapel
     * @return de te converteren groep
     */
    protected abstract <T extends BrpGroepInhoud> BrpGroep<T> bepaalGroep(final BrpStapel<T> brpStapel);

    /**
     * Bepaal de historie om toe te voegen.
     * @param groepen meeste recente niet vervallen groepen
     * @return documentatie
     */
    protected abstract Lo3Historie bepaalHistorie(List<BrpGroep<? extends BrpGroepInhoud>> groepen);

    /**
     * Bepaal de herkomst om toe te voegen.
     * @param groepen meeste recente niet vervallen groepen
     * @return documentatie
     */
    protected abstract Lo3Herkomst bepaalHerkomst(List<BrpGroep<? extends BrpGroepInhoud>> groepen);

    /**
     * Bepaal de documentatie om toe te voegen.
     * @param groepen meeste recente niet vervallen groepen
     * @return documentatie
     */
    protected abstract Lo3Documentatie bepaalDocumentatie(List<BrpGroep<? extends BrpGroepInhoud>> groepen);

    /**
     * Bepaal de BrpGroepConverteerder voor een bepaalde inhoud. Wordt 1 keer aangeroepen per stapel.
     * @param <B> brp groep inhoud type
     * @param inhoud inhoud
     * @return brp groep converteerder
     */
    protected abstract <B extends BrpGroepInhoud> BrpGroepConverteerder<B, L> bepaalConverteerder(B inhoud);

}
