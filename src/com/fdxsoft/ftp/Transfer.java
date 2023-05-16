/*
 * Autor Federico Martinez E.
 */
package com.fdxsoft.ftp;

import com.jcraft.jsch.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author federico
 */
public class Transfer {

    public static void main(String args[]) throws FileNotFoundException, IOException {
        JSch jsch = new JSch();
        Session session = null;

        try {
            session = jsch.getSession("federico", "www.fdxsoft.com");
            session.setPassword("AhMesAmies2506");
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("PreferredAuthentications",
                    "publickey,keyboard-interactive,password");

            session.setConfig(config);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            System.out.println("sftp channel opened and connected.");
            ChannelSftp channelSftp = (ChannelSftp) channel;

            String sftpDirectory = "sftp/";

            if (args.length == 1) {
                String filename = args[0];
                channelSftp.put(filename, sftpDirectory, ChannelSftp.OVERWRITE);
                System.out.println(filename + " transferred to " + sftpDirectory);
            } else {
                File directory = new File("c:/toSend");
                File[] fList = directory.listFiles();

                for (File file : fList) {
                    if (file.isFile()) {
                        String filename = file.getAbsolutePath();
                        channelSftp.put(filename, sftpDirectory, ChannelSftp.OVERWRITE);
                        System.out.println(filename + " transferred to " + sftpDirectory);
                    }
                }
            }
        } catch (JSchException | SftpException e) {
        } finally {
            session.disconnect();
            System.out.println("Transfer Process Completed...");
        }
    }
}
