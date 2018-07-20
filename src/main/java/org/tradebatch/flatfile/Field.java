package org.tradebatch.flatfile;

class Field {

    private int index;
    private String rawContent;

    /**
     * Create a new field
     *
     * @param index      of the field
     * @param rawContent read from the record
     */
    Field(final int index, final String rawContent) {
        this.index = index;
        this.rawContent = rawContent;
    }

    /**
     * Get field index.
     *
     * @return field index
     */
    int getIndex() {
        return index;
    }

    /**
     * Get raw content.
     *
     * @return raw content
     */
    String getRawContent() {
        return rawContent;
    }

    @Override
    public String toString() {
        return "Field: {" +
                "index=" + index +
                ", rawContent='" + rawContent + '\'' +
                '}';
    }
}