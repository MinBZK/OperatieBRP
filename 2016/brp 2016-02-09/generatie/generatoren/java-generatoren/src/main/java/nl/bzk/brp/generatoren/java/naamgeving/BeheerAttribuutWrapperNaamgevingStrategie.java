/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.naamgeving;

import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.metaregister.model.GeneriekElement;

/**
 * Naamgeving strategie voor de attribuut 'wrapper' klasses die om attributen 'heen' zitten,
 * zodat er meta data op gezet kan worden. Deze naamgeving strategie is een uitbreiding van de
 * algemene naamgeving strategie en voegt slechts een suffix toe.
 */
public class BeheerAttribuutWrapperNaamgevingStrategie extends AbstractNaamgevingStrategie {

    /** De klasse naam suffix voor alle attribuut klassen. */
    public static final String ATTRIBUUT_SUFFIX = "Attribuut";

    private AlgemeneNaamgevingStrategie algemeneNaamgevingStrategie;
    private BeheerNaamgevingStrategie beheerNaamgevingStrategie;

    /**
     * Standaard constructor a new Algemene naamgeving strategie.
     */
    public BeheerAttribuutWrapperNaamgevingStrategie() {
        this.algemeneNaamgevingStrategie = new AlgemeneNaamgevingStrategie();
        this.beheerNaamgevingStrategie = new BeheerNaamgevingStrategie();
    }

    /** {@inheritDoc} */
    @Override
    protected String bepaalPackageNaamVoorElement(final GeneriekElement element) {
        return bepaalPackageNaamVoorElement(element, false);
    }

    protected String bepaalPackageNaamVoorElement(final GeneriekElement element, final boolean association) {
        if (association) {
            return beheerNaamgevingStrategie.bepaalPackageNaamVoorElement(element);
        } else {
            return algemeneNaamgevingStrategie.bepaalPackageNaamVoorElement(element);
        }
    }


    public final JavaType getJavaTypeVoorElement(final GeneriekElement element, final boolean association) {
        return new JavaType(bepaalJavaTypeNaamVoorElement(element, association), bepaalPackageNaamVoorElement(element, association));
    }

    /** {@inheritDoc} */
    @Override
    protected String bepaalJavaTypeNaamVoorElement(final GeneriekElement element) {
        return bepaalJavaTypeNaamVoorElement(element, false);
    }


    protected String bepaalJavaTypeNaamVoorElement(final GeneriekElement element, final boolean association) {
        String javaTypeNaam = algemeneNaamgevingStrategie.bepaalJavaTypeNaamVoorElement(element);

        if (isJuisteSoortElement(element)) {
            if (!association) {
                javaTypeNaam += ATTRIBUUT_SUFFIX;
            }
        } else {
            throw new IllegalArgumentException("Onverwacht element: "
                    + element.getNaam() + " voor attribuut wrapper naamgeving.");
        }

        return javaTypeNaam;
    }

    /**
     * Checkt of we met het juiste soort element te maken hebben.
     * Het moet een stamgegeven of attribuut type zijn.
     *
     * @param element het element
     * @return juist of niet
     */
    private boolean isJuisteSoortElement(final GeneriekElement element) {
        boolean isJuisteSoortElement = false;
        final String soortCode = element.getSoortElement().getCode();
        if (BmrElementSoort.getBmrElementSoortBijCode(soortCode) == BmrElementSoort.OBJECTTYPE) {
            final BmrSoortInhoud soortInhoud = BmrSoortInhoud.getBmrSoortInhoudVoorCode(element.getSoortInhoud());
            if (soortInhoud == BmrSoortInhoud.STATISCH_STAMGEGEVEN
                    || soortInhoud == BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN)
            {
                isJuisteSoortElement = true;
            }
        } else if (BmrElementSoort.getBmrElementSoortBijCode(soortCode) == BmrElementSoort.ATTRIBUUTTYPE) {
            isJuisteSoortElement = true;
        }
        return isJuisteSoortElement;
    }

}
