package io.micronaut.jaxrs.runtime.ext.impl;

import io.micronaut.core.annotation.Internal;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Forked from RESTEasy.
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@Internal
class LinkImpl extends Link {
    private static final RuntimeDelegate.HeaderDelegate<Link> DELEGATE =
            RuntimeDelegate.getInstance().createHeaderDelegate(Link.class);


    /**
     * A map for all the link parameters such as "rel", "type", etc.
     */
    protected final Map<String, String> map;

    private final URI uri;

    /**
     * Default constructor.
     * @param uri The URI
     * @param map The parameters
     */
    LinkImpl(final URI uri, final Map<String, String> map) {
        this.uri = uri;
        this.map = map.isEmpty() ? Collections.emptyMap() : Collections
                .unmodifiableMap(new HashMap<>(map));
    }

    /**
     * Creates a link for the given value.
     * @param value The value
     * @return The link
     */
    public static Link valueOf(String value) {
        return DELEGATE.fromString(value);
    }

    @Override
    public URI getUri() {
        return uri;
    }

    @Override
    public UriBuilder getUriBuilder() {
        return UriBuilder.fromUri(uri);
    }

    @Override
    public String getRel() {
        return map.get(REL);
    }

    @Override
    public List<String> getRels() {
        final String rels = map.get(REL);
        return rels == null ? Collections.emptyList() : Arrays.asList(rels.split(" +"));
    }

    @Override
    public String getTitle() {
        return map.get(TITLE);
    }

    @Override
    public String getType() {
        return map.get(TYPE);
    }

    @Override
    public Map<String, String> getParams() {
        return map;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof LinkImpl) {
            final LinkImpl otherLink = (LinkImpl) other;
            return uri.equals(otherLink.uri) && map.equals(otherLink.map);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.uri != null ? this.uri.hashCode() : 0);
        hash = 89 * hash + (this.map != null ? this.map.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return DELEGATE.toString(this);
    }

}
