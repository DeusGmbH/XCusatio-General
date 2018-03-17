package com.deusgmbh.xcusatio.context.wildcard;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public abstract class Wildcard {

    private String identifier;

    /**
     * 
     * @param identifier
     *            The identifier of a {@link Wildcard} is the string that is
     *            going to be replaced. It is case sensitive and should
     *            conventionally have a $ symbol as first and last character
     */
    public Wildcard(String identifier) {
        this.identifier = identifier;
    }

    abstract public String replaceWildcard(String source, WildcardContext wildcardContext);

    public String getIdentifier() {
        return identifier;
    }
}
