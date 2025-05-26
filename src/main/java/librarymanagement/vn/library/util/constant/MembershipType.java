package librarymanagement.vn.library.util.constant;

public enum MembershipType {
    BASIC("Cơ bản"),
    PREMIUM("Cao cấp"),
    STUDENT("Sinh viên"),
    VIP("VIP");

    private final String label;

    MembershipType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}