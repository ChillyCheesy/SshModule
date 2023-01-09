package com.chillycheesy.sshmodule.event;

import com.chillycheesy.modulo.events.Event;
import com.chillycheesy.sshmodule.SshModule;

public class SshCommandEvent extends Event {

    private final SshModule MODULE;

    public SshCommandEvent(SshModule module) {
        MODULE = module;
    }
}
