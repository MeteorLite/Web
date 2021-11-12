package meteor.web.collisions;

public interface CollisionMap {
    boolean n(int x, int y, int z);

    boolean e(int x, int y, int z);

    default boolean s(int x, int y, int z) {
        return n(x, y - 1, z);
    }

    default boolean w(int x, int y, int z) {
        return e(x - 1, y, z);
    }

    default boolean ne(int x, int y, int z) {
        return n(x, y, z) && e(x, y + 1, z) && e(x, y, z) && n(x + 1, y, z);
    }

    default boolean nw(int x, int y, int z) {
        return n(x, y, z) && w(x, y + 1, z) && w(x, y, z) && n(x - 1, y, z);
    }

    default boolean se(int x, int y, int z) {
        return s(x, y, z) && e(x, y - 1, z) && e(x, y, z) && s(x + 1, y, z);
    }

    default boolean sw(int x, int y, int z) {
        return s(x, y, z) && w(x, y - 1, z) && w(x, y, z) && s(x - 1, y, z);
    }
}
