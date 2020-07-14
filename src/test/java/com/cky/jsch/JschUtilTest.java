package com.cky.jsch;

import com.jcraft.jsch.ChannelSftp;
import org.junit.Test;

import static org.junit.Assert.*;

public class JschUtilTest {

    @Test
    public void recursiveDelete() {
    }

    @Test
    public void isFileExist() {
      ChannelSftp sftpChannel = JschUtil.getSftpChannel(new ServerConfig("192.168.208.128", 22, "虚拟机192.168.208.128", "chenkaiyu", "root", "qq634691"));
        boolean fileExist = JschUtil.isFileExist(sftpChannel, "/home");
        System.out.println(fileExist);
    }

    @Test
    public void getRootSession() {
    }

    @Test
    public void getAsyncExecResult() {
    }

    @Test
    public void getExecResult() {
    }

    @Test
    public void execShell() {
    }

    @Test
    public void getSftpChannel() {

    }
}