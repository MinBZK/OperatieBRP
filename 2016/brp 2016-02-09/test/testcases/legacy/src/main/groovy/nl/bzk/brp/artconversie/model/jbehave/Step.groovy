package nl.bzk.brp.artconversie.model.jbehave

/**
 *
 */
class Step {
    Type type
    String text

    Step(Type t, String txt) {
        type = t
        text = txt
    }

    @Override
    int hashCode() {
        return type.hashCode() + text.hashCode()
    }

    @Override
    boolean equals(final Object obj) {
        def other = (Step) obj
        return this.type.equals(other.type) && this.text.equals(other.text)
    }

    enum Type {
        GIVEN('Given'), WHEN('When'), THEN('Then'), COMMENT('!--'), LEEG('');

        final String regel
        Type(String regel) { this.regel = regel }

        @Override
        String toString() {
            return this.regel
        }
    }
}
