package com.funtl.myshop.plus.business.tests;

import com.jh.myshop.plus.business.dto.ProfileParam;
import com.jh.myshop.plus.commons.utils.MapperUtils;
import org.junit.Test;

public class PrintJsonTests {

    @Test
    public void testPrintProfileParam() throws Exception {
        System.out.println(MapperUtils.obj2json(new ProfileParam()));
    }

}