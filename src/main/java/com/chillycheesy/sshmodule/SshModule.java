package com.chillycheesy.sshmodule;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.events.EventManager;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.pages.PageManager;
import com.chillycheesy.sshmodule.config.SshConfig;
import com.chillycheesy.sshmodule.controller.SshController;
import com.chillycheesy.sshmodule.event.SshConfigEvent;
import com.chillycheesy.sshmodule.page.Console;
import com.chillycheesy.sshmodule.services.SshService;


public class SshModule extends Module {

    private final EventManager EVENT_MANAGER =  ModuloAPI.getEvent().getEventManager();
    private final PageManager PAGE_MANAGER = ModuloAPI.getPage().getPageManager();

    private SshConfig sshConfig;

    @Override
    protected void onLoad() {
        info("Ssh module is loading");
//        SshConfigListener configListener = new SshConfigListener();
//        this.EVENT_MANAGER.registerItem(this, configListener);
        SshConfigEvent configEvent = new SshConfigEvent(this);
        this.sshConfig = configEvent.getConfig();
        this.EVENT_MANAGER.emitEvent(this, configEvent);

        this.PAGE_MANAGER.buildAndRegisterPage(this, new Console(), new SshController(this.sshConfig));
    }

    @Override
    protected void onStart() {
        info("Ssh module is started");
//        try {
//            SshService.connectToServer(this.sshConfig, this, 1000);
//        } catch (Exception e) {
//            error(e.getMessage());
//        }
    }

    @Override
    protected void onStop() {
        info("Ssh module is stopped");
        this.EVENT_MANAGER.removeAllItems(this);
    }

    public void doSshCommand() {

    }
}
