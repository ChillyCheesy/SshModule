package com.chillycheesy.sshmodule.event;

import com.chillycheesy.modulo.events.Cancelable;
import com.chillycheesy.modulo.events.CancelableAction;
import com.chillycheesy.modulo.events.Event;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.sshmodule.SshModule;
import com.chillycheesy.sshmodule.config.SshConfig;

public class SshConfigEvent extends Event implements Cancelable {

    private SshConfig config;

    public SshConfigEvent(SshModule module) {
        module.info("SSh config is loading");
        this.config = SshConfig.loadDefaultConfig(module.getConfiguration());
    }

    public SshConfig getConfig() {
        return config;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public void setCanceled(boolean cancel) {

    }

    @Override
    public Cancelable setCancelableAction(CancelableAction action) {
        return null;
    }

    @Override
    public CancelableAction getCancelableAction() {
        return null;
    }
}
