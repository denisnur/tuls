package com.denisn.parser.bbcodes;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * @author Nurmuhametov Denis
 * @version $Id$
 * @since 14.02.2009 14:12:29
 */
public class BBCode
{
    private Pattern regexp;

    private String replaceTemplate;

    private Map<Integer, BBAttribute> attributes;

    private boolean recursive = false;

    public void setRegexp(String pattern)
    {
        this.regexp = Pattern.compile(pattern);
    }

    public void setReplaceTemplate(String replaceTemplate)
    {
        this.replaceTemplate = replaceTemplate;
    }

    public void setAttributes(Map<Integer, BBAttribute> attributes)
    {
        this.attributes = attributes;
    }

    public void setRecursive(boolean recursive)
    {
        this.recursive = recursive;
    }

    public String parse(String text) throws Exception
    {
        StringBuilder rst = new StringBuilder();
        boolean result = findCode(text, rst);
        if (result && recursive)
        {
            return parse(rst.toString());
        }
        else
        {
            return rst.toString();
        }
    }

    private boolean findCode(String text, StringBuilder rst) throws Exception
    {
        boolean isFind = false;
        int start = 0;
        Matcher matcher = regexp.matcher(text);
        while (matcher.find())
        {
            rst.append(text.substring(start, matcher.start()));

            String find = matcher.group(0);
            if (find != null && !find.trim().isEmpty())
            {
                VelocityContext ctx = new VelocityContext();
                for (int i = 1; i <= matcher.groupCount(); i++)
                {
                    BBAttribute attr = attributes.get(i);
                    String match = matcher.group(i);
                    if (match != null)
                    {
                        List<String> values = attr.parse(match.trim());
                        ctx.put(attr.getName(), (values.size() == 1 ? values.get(0) : values));
                    }
                }
                StringWriter writer = new StringWriter();

                Velocity.init();
                Velocity.evaluate(ctx, writer, text, replaceTemplate);

                rst.append(writer.getBuffer());

                isFind = true;
            }
            start = matcher.end();
        }
        if (start < text.length())
        {
            rst.append(text.substring(start, text.length()));
        }

        return isFind;
    }

    @Override
    public String toString()
    {
        return "BBCode{" +
                "regexp=" + regexp +
                ", replaceTemplate='" + replaceTemplate + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
