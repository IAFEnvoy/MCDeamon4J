package iafenvoy.MCDeamon4J.Util;

public class Identifier {
  private final String name, id;

  public Identifier(String name, String id) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }
}
