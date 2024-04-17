package mintychochip.forgehammers.container;

public enum HammerType {
  TRADITIONAL("traditional"),
  PATTERNED("patterned");

  private final String deserializationType;

  HammerType(String deserializationType) {
    this.deserializationType = deserializationType;
  }

  public String getDeserializationType() {
    return deserializationType;
  }
}
