/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.brp.groep.FoutmeldingUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

/**
 * Bevat conversie mapping functionaliteit.
 * @param <L> de LO3 waarde
 * @param <B> de BRP waarde
 */
public abstract class AbstractConversietabel<L, B> implements Conversietabel<L, B> {

    private final Map<L, B> mapLo3NaarBrp;
    private final Map<B, L> mapBrpNaarLo3;

    /**
     * Maakt een AbstractConversietabel object.
     * @param conversieLijst de lijst met conversies.
     */
    public AbstractConversietabel(final List<Map.Entry<L, B>> conversieLijst) {
        mapLo3NaarBrp = new HashMap<>();
        mapBrpNaarLo3 = new HashMap<>();

        for (final Map.Entry<L, B> conversie : conversieLijst) {
            mapLo3NaarBrp.put(conversie.getKey(), conversie.getValue());
            mapBrpNaarLo3.put(conversie.getValue(), conversie.getKey());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final B converteerNaarBrp(final L input) {
        final B result;
        if (input != null) {
            final L genormalizeerdeInput = verwijderOnderzoekLo3(input);
            if (genormalizeerdeInput != null) {
                if (!mapLo3NaarBrp.containsKey(genormalizeerdeInput)) {
                    throw FoutmeldingUtil.getValidatieExceptie(String.format(
                            "Er is geen mapping naar BRP voor LO3 waarde '%s' in conversietabel %s",
                            genormalizeerdeInput,
                            this.getClass().getSimpleName()), SoortMeldingCode.PRE054, null);
                }
                result = voegOnderzoekToeBrp(mapLo3NaarBrp.get(genormalizeerdeInput), bepaalOnderzoekLo3(input));
            } else {
                result = voegOnderzoekToeBrp(null, bepaalOnderzoekLo3(input));
            }
        } else {
            result = null;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final L converteerNaarLo3(final B input) {
        final L result;
        if (input != null) {
            final B genormalizeerdeInput = verwijderOnderzoekBrp(input);
            if (genormalizeerdeInput != null) {
                if (!mapBrpNaarLo3.containsKey(genormalizeerdeInput)) {
                    throw FoutmeldingUtil.getValidatieExceptie(String.format(
                            "Er is geen mapping naar LO3 voor BRP waarde '%s' in conversietabel %s",
                            genormalizeerdeInput,
                            this.getClass().getSimpleName()), SoortMeldingCode.PRE054, null);
                }
                result = voegOnderzoekToeLo3(mapBrpNaarLo3.get(genormalizeerdeInput), bepaalOnderzoekBrp(input));
            } else {
                result = voegOnderzoekToeLo3(null, bepaalOnderzoekBrp(input));
            }
        } else {
            result = null;
        }

        return result;
    }

    @Override
    public final boolean valideerLo3(final L input) {
        final L genormaliseerdeInput = verwijderOnderzoekLo3(input);
        return genormaliseerdeInput == null || mapLo3NaarBrp.containsKey(genormaliseerdeInput);
    }

    @Override
    public final boolean valideerBrp(final B input) {
        final B genormaliseerdeInput = verwijderOnderzoekBrp(input);
        return genormaliseerdeInput == null || mapBrpNaarLo3.containsKey(genormaliseerdeInput);
    }

    /**
     * Maak een kopie van de L (Lo3) input parameter waarbij een eventueel Lo3Onderzoek niet wordt meegekopieerd.
     * @param input een L (Lo3) input waarde met mogelijk onderzoek
     * @return een kopie van de input waarde zonder onderzoek
     */
    protected final L verwijderOnderzoekLo3(final L input) {
        return voegOnderzoekToeLo3(input, null);
    }

    /**
     * Maak een kopie van de B (Brp) input parameter waarbij een eventueel Lo3Onderzoek niet wordt meegekopieerd.
     * @param input een B (Brp) input waarde met mogelijk onderzoek
     * @return een kopie van de input waarde zonder onderzoek
     */
    protected final B verwijderOnderzoekBrp(final B input) {
        return voegOnderzoekToeBrp(input, null);
    }

    /**
     * Geef het onderzoek terug dat behoort bij de L (Lo3) input parameter.
     * @param input een L (Lo3) input waarde met mogelijk onderzoek
     * @return een eventueel onderzoek dat aan de input parameter gekoppeld was, of null als er geen onderzoek was
     */
    protected abstract Lo3Onderzoek bepaalOnderzoekLo3(final L input);

    /**
     * Geef het onderzoek terug dat behoort bij de B (Brp) input parameter.
     * @param input een B (Brp) input waarde met mogelijk onderzoek
     * @return een eventueel onderzoek dat aan de input parameter gekoppeld was, of null als er geen onderzoek was
     */
    protected abstract Lo3Onderzoek bepaalOnderzoekBrp(final B input);

    /**
     * Voeg een onderzoek toe aan een L (Lo3) waarde.
     * @param input een L (Lo3) input waarde
     * @param onderzoek het onderzoek dat toegevoegd moet worden aan de input waarde
     * @return een kopie van de L (Lo3) input waarde met daaraan toegevoegd het onderzoek
     */
    protected abstract L voegOnderzoekToeLo3(final L input, final Lo3Onderzoek onderzoek);

    /**
     * Voeg een onderzoek toe aan een B (Brp) waarde.
     * @param input een B (Brp) input waarde
     * @param onderzoek het onderzoek dat toegevoegd moet worden aan de input waarde
     * @return een kopie van de B (Brp) input waarde met daaraan toegevoegd het onderzoek
     */
    protected abstract B voegOnderzoekToeBrp(final B input, final Lo3Onderzoek onderzoek);
}
