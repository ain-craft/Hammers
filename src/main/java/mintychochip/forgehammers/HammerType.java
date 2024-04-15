package mintychochip.forgehammers;

public enum HammerType {
    TRAD("traditional"),
    PATTERNED("patterned");

    private final String key;

    HammerType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
