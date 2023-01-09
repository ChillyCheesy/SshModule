package com.chillycheesy.sshmodule.listeners;

import com.chillycheesy.modulo.events.EventHandler;
import com.chillycheesy.modulo.listener.Listener;
import com.chillycheesy.sshmodule.event.SshConfigEvent;

public class SshConfigListener implements Listener {

    @EventHandler
    public void onSshConfigEvent(SshConfigEvent event) {
        event.getEmitter().info("Ssh config : "+event.getConfig());

    }

}
