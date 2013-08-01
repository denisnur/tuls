package com.denisn.parser.bbcodes;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.TestCase;

/**
 * User: denisn
 * Date: 31.07.13
 * Time: 10:44
 */
public class ProcessorTest extends TestCase
{
    private Processor processor;

    @BeforeClass
    public void setUp() throws Exception
    {
        FileSystemXmlApplicationContext ctx =
                new FileSystemXmlApplicationContext(new String[]{"classpath:com/denisn/parser/bbcodes/bbcodes.xml"});
        processor = (Processor) ctx.getBean("bbProcessor");
    }

    @Test
    public void testInclude()
    {
        String text = "[b]This [b]text[/b] is [i]BOLD.[/i][/b] This in not bold.";
        String result = processor.parse(text);

        assertEquals("<p><b>This <b>text</b> is <i>BOLD.</i></b> This in not bold.</p>", result.trim());
    }

    @Test
    public void testOl()
    {
        String text = "[ul]\nitem1\nitem2\nitem3[/ul]";
        String result = processor.parse(text);

        assertEquals("<p><ul><li>item1</li><li>item2</li><li>item3</li></ul></p>", result.trim());
    }

    @Test
    public void testMain()
    {
        String text = "[h1]Header.[/h1]\nSample text. [b]This [b]text[/b] is [i]BOLD.[/i][/b] This in not bold." +
                "\n        This new line." +
                "\n        This new line. text     " +
                "\n[ul]\nitem1\nitem2\nitem3[/ul]" +
                "\n[ol]\nitem1\nitem2\nitem3[/ol]" +
                "\n[a    url=\\files\\doc.pdf]document[/a]" +
                "\n[anchor name=#anchor1]document[/anchor]" +
                "\n[anchor name=anchor2]document[/anchor]" +
                "\n[h1]Header2.[/h1]" +
                "\n[h1][/h1]" +
                "\nhttp://non-barrier.ru or http://www.non-barrier.ru or www.non-barrier.ru or ftp.denisn.com" +
                "\nmail@denisn.com or bugs@denisn.com";
        String result = processor.parse(text);

        String expected = "<p><h1>Header.</h1></p>\n" +
                "<p>Sample text. <b>This <b>text</b> is <i>BOLD.</i></b> This in not bold.</p>\n" +
                "<p>This new line.</p>\n" +
                "<p>This new line. text</p>\n" +
                "<p><ul><li>item1</li><li>item2</li><li>item3</li></ul></p>\n" +
                "<p><ol><li>item1</li><li>item2</li><li>item3</li></ol></p>\n" +
                "<p><a href=\"\\files\\doc.pdf\" target=\"_blank\">document</a></p>\n" +
                "<p><a name=\"anchor1\">document</a></p>\n" +
                "<p><a name=\"anchor2\">document</a></p>\n" +
                "<p><h1>Header2.</h1></p>\n" +
                "<p><h1></h1></p>\n" +
                "<p><a href=\"http://non-barrier.ru\" target=\"_blank\">http://non-barrier.ru</a> or <a href=\"http://www.non-barrier.ru\" target=\"_blank\">http://www.non-barrier.ru</a> or <a href=\"www.non-barrier.ru\" target=\"_blank\">www.non-barrier.ru</a> or <a href=\"ftp.denisn.com\" target=\"_blank\">ftp.denisn.com</a></p>\n" +
                "<p><a href=\"mailto:mail@denisn.com\">mail@denisn.com</a> or&nbsp;<a href=\"mailto:bugs@denisn.com\">bugs@denisn.com</a></p>";

        assertEquals(expected, result.trim());
    }
}
