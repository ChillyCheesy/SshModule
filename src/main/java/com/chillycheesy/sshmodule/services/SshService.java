package com.chillycheesy.sshmodule.services;

import com.chillycheesy.sshmodule.SshModule;
import com.chillycheesy.sshmodule.config.SshConfig;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.session.Session;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;


public class SshService {
    private SshClient client;
    private SshConfig config;

    private final long TIMEOUT = 1000;
    public boolean connectToServer(SshConfig config) throws IOException {
        this.client = SshClient.setUpDefaultClient();
        client.start();
        try (ClientSession session = client.connect(config.getUser(), config.getHost(), config.getPort())
                .verify(TIMEOUT, TimeUnit.SECONDS).getSession()) {
            this.config = config;
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    public String sendCommandToServer(String command) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ClientSession session = client.connect(config.getUser(), config.getHost(), config.getPort())
                .verify(TIMEOUT, TimeUnit.SECONDS).getSession()) {
            session.addPasswordIdentity(config.getPassword());
            session.auth().verify(TIMEOUT, TimeUnit.SECONDS);
            try (ClientChannel channel = session.createExecChannel(command)) {
                channel.setOut(out);
                try {
                    channel.open().verify(TIMEOUT);
                    channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 0L);
                    System.out.println(out.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    channel.close();
                }
                session.close();
            }
        } catch(Exception e) {
            return "";
        }

        return new String(out.toByteArray());
    }

    public boolean disconnectFromServer() throws IOException {
        this.config = null;
        this.client.close();
        return true;
    }

    public static void connectToServer(SshConfig config, SshModule module, long timeout) {
        SshClient client = SshClient.setUpDefaultClient();
        client.start();
        module.info("Start ssh connection");
        try (ClientSession session = client.connect(config.getUser(), config.getHost(), config.getPort())
                .verify(timeout, TimeUnit.SECONDS).getSession()) {
            session.addPasswordIdentity(config.getPassword());
            session.auth().verify(timeout, TimeUnit.SECONDS);
            try (ClientChannel channel = session.createShellChannel()) {
                channel.setIn(System.in);
                channel.setOut(System.out);
                try {
                    channel.open().verify(timeout);
                    channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 0L);
                } finally {
                    channel.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            client.stop();
        }
    }

    public static void execCommandOnServer(SshConfig config, SshModule module, long timeout, String command) throws IOException {
        SshClient client = SshClient.setUpDefaultClient();
        client.start();
        try (ClientSession session = client.connect(config.getUser(), config.getHost(), config.getPort())
                .verify(timeout, TimeUnit.SECONDS).getSession()) {
            session.addPasswordIdentity(config.getPassword());
            session.auth().verify(timeout, TimeUnit.SECONDS);
            module.info("Ssh connection established");
            try (ClientChannel channel = session.createExecChannel(command)) {
                channel.setOut(System.out);
                try {
                    channel.open().verify(timeout);
                    channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 0L);
                } finally {
                    module.info("Ssh connection terminated");
                    channel.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

}
