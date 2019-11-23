public final class RectExtension {
    private RectExtension() {
    }
    public static float P(Rect a) {
        float length = a.getBottomRight().getX() - a.getTopLeft().getX();
        float width = a.getTopLeft().getY() - a.getBottomRight().getY();
        return 2*(length + width);
    }
    public static float S(Rect a) {
        float length = a.getBottomRight().getX() - a.getTopLeft().getX();
        float width = a.getTopLeft().getY() - a.getBottomRight().getY();
        return length*width;
    }
    public static boolean isSquare(Rect a) {
        float length = a.getBottomRight().getX() - a.getTopLeft().getX();
        float width = a.getTopLeft().getY() - a.getBottomRight().getY();
        return length == width;
    }
}
