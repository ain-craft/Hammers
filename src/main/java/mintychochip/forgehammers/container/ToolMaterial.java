package mintychochip.forgehammers.container;

public enum ToolMaterial {
  WOODEN (5),
  IRON (5),
  GOLD (5),
  DIAMOND (50),
  NETHERITE (50);

  private final float hardness;

  ToolMaterial(float hardness) {
    this.hardness = hardness;
  }

  public float getHardness() {
    return hardness;
  }
}
