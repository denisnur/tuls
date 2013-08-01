package com.denisn.parser.bbcodes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nurmuhametov Denis
 * @version $Id$
 * @since 14.02.2009 14:12:29
 */
public class BBAttribute
{
    private int group;

    private Pattern regexp;

    private String name;

    public void setRegexp(String regexp)
    {
        this.regexp = Pattern.compile(regexp);
    }

    public int getGroup()
    {
        return group;
    }

    public void setGroup(int group)
    {
        this.group = group;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<String> parse(String text)
    {
        List<String> rst = new ArrayList<String>();
        Matcher matcher = regexp.matcher(text);

        while (matcher.find())
        {
            rst.add(matcher.group(group));
        }

        return rst;
    }

}
