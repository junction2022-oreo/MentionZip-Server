package junction.oreo.enums;

public enum CategoryType {
    SLACK("SLACK"),
    JIRA("JIRA"),
    ;

    private String type;

    CategoryType(String type) {
        this.type = type;
    }

    public static CategoryType valueByName(String name) {
        for (CategoryType categoryType : values()) {
            if (categoryType.name().equals(name)) {
                return categoryType;
            }
        }
        return null;
    }

}
