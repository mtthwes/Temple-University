import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextToHtmlConverterTest extends TextToHtmlConverter {

    @Test
    public void testConvertLinks() {
        String input = "This is a link [[http://example.com][Example]] to a website.";
        String expected = "This is a link <a href=\"http://example.com\">Example</a> to a website.";
        String result = convertLinks(input);
        assertEquals(expected, result);
    }

    @Test
    public void testConvertBoldAndItalic() {
        String input = "This is **bold** and *italic* text.";
        String expected = "This is <b>bold</b> and <i>italic</i> text.";
        String result = convertBoldAndItalic(input);
        assertEquals(expected, result);
    }
}
