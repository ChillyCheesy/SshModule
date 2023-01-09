package com.chillycheesy.sshmodule.page;

import com.chillycheesy.modulo.pages.factory.GetRequest;
import com.chillycheesy.modulo.pages.factory.ResponseResource;

public class Console {

    @GetRequest("console")
    @ResponseResource
    public String page() {
        return "console";
    }

}
