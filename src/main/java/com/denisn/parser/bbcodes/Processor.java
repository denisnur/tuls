package com.denisn.parser.bbcodes;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Nurmuhametov Denis
 * @version $Id$
 * @since 14.02.2009 14:12:29
 */
public class Processor
{

    private static final Logger LOG = LoggerFactory.getLogger(Processor.class);

    private List<BBCode> codes;

    public void setCodes(List<BBCode> codes)
    {
        this.codes = codes;
    }

    public String parse(String text)
    {
        String rst = text;

        for (BBCode code : codes)
        {
            try
            {
                rst = code.parse(rst);
            }
            catch (Exception e)
            {
                LOG.error("Error parser. text=" + text + ", " + code, e);
            }
        }
        return rst;
    }
}
