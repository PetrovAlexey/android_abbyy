import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

class RectExtensionTest {

    private Rect _rect;

    @org.junit.jupiter.api.Test
    void p() {
        _rect = new Rect(new Point(0,10), new Point(10,0));
        Assert.assertEquals(40, RectExtension.P(_rect), 0.05);
    }

    @org.junit.jupiter.api.Test
    void s() {
        _rect = new Rect(new Point(0,10), new Point(10,0));
        Assert.assertEquals(100, RectExtension.S(_rect), 0.05);
    }

    @org.junit.jupiter.api.Test
    void isSquare() {
        _rect = new Rect(new Point(0,10), new Point(10,0));
        Assert.assertTrue(RectExtension.isSquare(_rect));
    }
}