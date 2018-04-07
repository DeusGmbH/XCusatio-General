package com.deusgmbh.xcusatio.context.wildcard;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public abstract class Wildcard {

    private String identifier;
    private String uiDescription;

    /**
     * 
     * @param identifier
     *            The identifier of a {@link Wildcard} is the string that is
     *            going to be replaced. It is case sensitive and is to be used
     *            with a $ symbol in the front and back. So for example, if the
     *            identifier is <i>testifier</i> than the text that is going to
     *            be replaced should be <i><b>$</b>testifier<b>$</b></i>
     * @param uiDescription
     *            The description that should be displayed in the UI as tool tip
     *            and explain what the identifier is going to be replaced with
     */
    public Wildcard(String identifier, String uiDescription) {
        this.identifier = identifier;
        this.uiDescription = uiDescription;
    }

    /**
     * This function replaces every occurrence of the identifier within the
     * source with some context based text
     * 
     * @param source
     *            the text containing the identifier of the wildcard that should
     *            be replaced
     * @param apiContext
     *            the data source the new content should be based on
     * @returns text without any occurrence of the identifier
     */
    abstract public String replace(String source, APIContext apiContext);

    /**
     * This method should be used to check whether the
     * {@linkplain #replace(String, APIContext)} method of this particular
     * {@link Wildcard} can be used when providing the same apiContext
     * 
     * @param apiContext
     *            the data source to be checked for null values
     * @return true if apiContext contains all the data needed to use
     *         {@link #replace(String, APIContext)} without causing any null
     *         pointer exceptions, false otherwise
     */
    abstract public boolean isValidContext(APIContext apiContext);

    public String getIdentifierRegEx() {
        return "\\$" + identifier + "\\$";
    }

    public String getIdentifierExcuseString() {
        return "$" + identifier + "$";
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getUIDescription() {
        return uiDescription;
    }
}
