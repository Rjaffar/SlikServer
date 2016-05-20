/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slickserver;

import java.io.IOException;
import java.net.InetAddress;

/**
 *
 * @author ric
 */
public class Server {

    // description d'un serveur
    private String name;
    private String adressIpv4;
    private boolean isReachable = false;
    private int timeout = 1000;

    private boolean isAlive() {
        boolean islive;
        try {
            islive = InetAddress.getByName(this.name).isReachable(timeout);
            /*
            Pattern pattern = Pattern.compile(Pattern.quote("/") + "(.*?)" + Pattern.quote("/"));
            Matcher matcher = pattern.matcher((InetAddress.getByName(this.name).toString()) + "/");
            matcher.find();
            this.adressIpv4 = matcher.group(1);
             */
            String ip[] = (InetAddress.getByName(this.name).toString()).split("/");
            this.adressIpv4 = ip[1];
        } catch (IOException ex) {
            islive = false;
        }
        return islive;
    }

    public Server(String name) {
        this.name = name;
        isReachable = isAlive();
    }

    public String getName() {
        return name;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isIsReachable() {
        return isReachable;
    }

    public String getAdressIpv4() {
        return adressIpv4;
    }


    @Override
    public String toString() {
        return "Server{" + "name=" + name + ", adressIpv4=" + adressIpv4 + ", isReachable=" + isReachable + '}';
    }

}
