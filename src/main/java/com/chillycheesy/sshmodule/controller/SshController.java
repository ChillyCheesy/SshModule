package com.chillycheesy.sshmodule.controller;

import com.chillycheesy.modulo.pages.factory.GetRequest;
import com.chillycheesy.modulo.pages.factory.PostRequest;
import com.chillycheesy.modulo.pages.factory.ResponseBody;
import com.chillycheesy.modulo.pages.factory.ResponseResource;
import com.chillycheesy.sshmodule.config.SshConfig;
import com.chillycheesy.sshmodule.services.SshService;
import jdk.jfr.ContentType;
import org.apache.tomcat.util.json.JSONParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

public class SshController {

    private SshConfig config;
    private SshService service;

    public SshController(SshConfig config) {
        this.config = config;
        this.service = new SshService();
    }

    @GetRequest("host")
    @ResponseBody
    public String host(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin","*");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        return "{\"host\":\""+this.config.getHost()+"\"}";
    }

    @GetRequest("user")
    @ResponseBody
    public String user(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin","*");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        return "{\"user\":\""+this.config.getUser()+"\"}";
    }

    @GetRequest("connect")
    @ResponseBody
    public boolean connect(HttpServletResponse response) throws IOException {
        response.addHeader("Access-Control-Allow-Origin","*");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        return this.service.connectToServer(this.config);
    }

    @GetRequest("command")
    @ResponseBody
    public String sendCommand(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String command = new String(request.getInputStream().readAllBytes()).split(":")[1].replace("}", "").replace("\"", "");
//        System.out.println(command);
        response.addHeader("Access-Control-Allow-Origin","*");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        String res = "[";
        for(String s: this.service.sendCommandToServer("ls -la").split("\n")) {
            res = res.concat("\""+s+"\",");
        }
        res = res.substring(0, res.length())+"]";
        return "{"+res+"}";
    }

    @GetRequest("disconnect")
    @ResponseBody
    public boolean disconnect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(request.toString());
        response.addHeader("Access-Control-Allow-Origin","*");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        return this.service.disconnectFromServer();
    }



}
